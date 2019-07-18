package dao;

import entity.FavorEntity;

import java.util.List;

public interface FavorDao {
    void insert(FavorEntity favor);

    void delete(int favorId);

    List<FavorEntity> getFavorEntities(int userId);
}