package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:48
 * @revision $LastChangedRevision: 5914 $
 * @date $LastChangedDate: 2013-02-03 02:23:18 +0900 (일, 03 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
public interface UserManager {

	public User getUser(Integer id);

	public User getUser(String userId);

	public void createUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

	public List<User> getUsers();
}
