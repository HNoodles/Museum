package entity;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "2019summervacation")
public class UsersEntity {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String type;
    private Timestamp time;
    private String signature;


    public UsersEntity() {
    }

    public UsersEntity(String name, String email, String password, String type, String signature) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.signature = signature;
    }

    public UsersEntity(int userId, String name, String email, String password, String type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    @Id
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(type, that.type) &&
                Objects.equals(time, that.time) &&
                Objects.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email, password, type, time, signature);
    }
}
