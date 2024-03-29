package service;

import entity.UsersEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    UsersEntity login(String username, String password);

    boolean checkSignUpUsername(String username);

    void signUp(UsersEntity user);

    UsersEntity get(String username);

    List<UsersEntity> getAllUsers();

    UsersEntity update(UsersEntity user);

    boolean checkModifyUser(int userId, String password);

    void addUser(UsersEntity users);

    void modifyUser(UsersEntity users);

    void deleteUser(String name);

    void deleteUser(int userId);

    List<UsersEntity> getFriends(int userId);

    List<UsersEntity> getFriendRequestSenders(int receiverId);

    List<UsersEntity> getUserByNameLike(String username);

    UsersEntity get(int userId);

    Map<UsersEntity, String> getRecommendedFriends(int userId);

    void updateViewHistory(int userId,int artworkId);

    void updateDeleteHistory(int userId,int artworkId);

    boolean queryDeleteHistory(int userId,int artworkId);
}
