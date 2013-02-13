package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.User;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:00
 * @revision $LastChangedRevision: 6000 $
 * @date $LastChangedDate: 2013-02-12 21:09:28 +0900 (화, 12 2월 2013) $
 * @by $LastChangedBy: samkwang.na $
 */
@Service("userManager")
@Transactional
public class UserManagerImpl extends AbstractJpaDaoService implements
		UserManager, UserDetailsService {

	/**
	 * @see com.eyeq.esp.service.UserManager#getUser(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public User getUser(Integer id) {

		List<User> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.User@getUser():param.userId")
				.setParameter("userId", id).getResultList();

		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.UserManager#getUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public User getUser(String userId) {

		List<User> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.User@getUser():param.userId")
				.setParameter("userId", userId).getResultList();

		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.UserManager#createUser(com.eyeq.esp.model.User)
	 */
	public void createUser(User user) {
		user.setCreatedDate(new Date());
		if (user.getRole() == null) {
			user.setRole("ROLE_USER");
		}
		getEntityManager().persist(user);
	}

	/**
	 * @see com.eyeq.esp.service.UserManager#updateUser(com.eyeq.esp.model.User)
	 */
	public void updateUser(User user) {
		user.setModifiedDate(new Date());
		getEntityManager().merge(user);
	}

	/**
	 * @see com.eyeq.esp.service.UserManager#deleteUser(com.eyeq.esp.model.User)
	 */
	public void deleteUser(User user) {
		user.setDeletedDate(new Date());
		user.setEnabled(false);
		getEntityManager().merge(user);
	}

	/**
	 * @see com.eyeq.esp.service.UserManager#getUsers()
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<User> getUsers() {

		List<User> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.User@getUser()").getResultList();
		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return null;
	}
}
