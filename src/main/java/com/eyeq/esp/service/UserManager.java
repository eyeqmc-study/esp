package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:48
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface UserManager {

	public User getUser(Integer ownerId);

	public User getUser(String uid);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

	public List<User> getUsers();
}
