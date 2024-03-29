package util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DBUtils {
    private static String sqlName = null;
    private static String url = null;
    private static String name = null;
    private static String password = null;

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    static  {
        try {
            Properties properties = new Properties();
            InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(in);

            sqlName = properties.getProperty("sqlName");
            url = properties.getProperty("url");
            name = properties.getProperty("name");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void update(String sql, Object... args){//第一个参数用来传插入数据库的名称
        try {
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            release(null, preparedStatement, connection);
        }
    }

    public static <T> T get(Class<T> clazz, String sql, Object ... args) {
        List<T> result = getList(clazz, sql, args);

        if (result.size() > 0) // has result
            return result.get(0);

        // no result
        return null;
    }

    public static <E> E getValue(String sql, Object ... args) {
        E value = null;
        try {
            // query to get result set
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                value = (E)resultSet.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }

        return value;
    }

    public static <T> List<T> getList(Class<T> clazz, String sql, Object ... args) {
        List<T> entities = new ArrayList<>();

        try {
            // query to get result set
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> listOfValues = getListOfValues(resultSet);

            // if has entry in map values, construct object by reflection
            entities = getListOfEntities(clazz, listOfValues);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }

        return entities;
    }

    /**
     * get list of entities from list of maps of values from table
     * @param clazz Class of entities
     * @param listOfValues list of maps of values
     * @param <T> type of entities
     * @throws InstantiationException e
     * @throws IllegalAccessException e
     * @throws NoSuchFieldException e
     */
    private static <T> List<T> getListOfEntities(Class<T> clazz, List<Map<String, Object>> listOfValues)
            throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> result = new ArrayList<>();
        if (listOfValues.size() > 0) {
            for (Map<String, Object> values : listOfValues) {
                result.add(getEntityFromMap(clazz, values));
            }
        }
        return result;
    }

    /**
     * get entity object from map of values from table
     * @param clazz Class of entity
     * @param values map of values
     * @param <T> type of entity
     * @return entity object
     * @throws InstantiationException e
     * @throws NoSuchFieldException e
     * @throws IllegalAccessException e
     */
    private static <T> T getEntityFromMap(Class<T> clazz, Map<String, Object> values)
            throws InstantiationException, NoSuchFieldException, IllegalAccessException {
        T entity = clazz.newInstance();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            // set field to value
            Field entityField = entity.getClass().getDeclaredField(field);
            entityField.setAccessible(true);
            entityField.set(entity, value);
        }
        return entity;
    }

    /**
     * get list of maps from result set queried, one map for one entry in database
     * @param resultSet queried
     * @return list of maps
     * @throws SQLException e
     */
    private static List<Map<String, Object>> getListOfValues(ResultSet resultSet) throws SQLException {
        List<String> columnLabels = getColumnLabels(resultSet);

        // get list of Map, key: columnName, value: columnValue
        List<Map<String, Object>> listOfValues = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> values = new Hashtable<>();

            for (String columnLabel : columnLabels) {
                Object columnValue = resultSet.getObject(columnLabel);
                values.put(columnLabel, columnValue);
            }

            listOfValues.add(values);
        }
        return listOfValues;
    }

    /**
     * get list of column names in a table from result set queried
     * @param resultSet queried
     * @return list of column names
     * @throws SQLException e
     */
    private static List<String> getColumnLabels(ResultSet resultSet) throws SQLException {
        List<String> labels = new ArrayList<>();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            labels.add(resultSetMetaData.getColumnLabel(i + 1));
        }

        return labels;
    }

    private static Connection registerDriverAndGetConnection() {
        try {
            Class.forName(sqlName);
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // for query operations
    private static void release(ResultSet rs, Statement st, Connection con) {
        CloseRS(rs);
        CloseST(st);
        CloseCon(con);
    }

    private static void CloseRS(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs = null;
        }
    }

    private static void CloseST(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            st = null;
        }
    }

    private static void CloseCon(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }
}