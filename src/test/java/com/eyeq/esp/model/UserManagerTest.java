package com.eyeq.esp.model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:54
 * @revision $LastChangedRevision: 5903 $
 * @date $LastChangedDate: 2013-02-02 18:05:52 +0900 (토, 02 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/esp-test-context.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class UserManagerTest {

	@Autowired
	private UserManager userManager;

	private Integer id;

	@Before
	public void testSetup() {
		assertNotNull(userManager);

		User user = new User();
		user.setUid("voyaging");
		user.setName("이하나");
		user.setPassword("dlgksk");
		user.setEmail("voyaging@naver.com");
		user.setEnabled(true);

		userManager.createUser(user);

		assertNotNull(user.getId());
		this.id = user.getId();

		assertThat("이하나", is(user.getName()));
		assertThat("voyaging@naver.com", is(user.getEmail()));
	}

	@Test
	public void testUpdateUser() {
		User user = userManager.getUser(id);
		assertNotNull(user);
	}

}