package com.eyeq.esp.model;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:54
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/esp-context.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class UserTest {

	@Autowired
	private UserManager ownerManager;

	@Autowired
	private ArticleManager articleManager;

	@Test
	public void test() {
		User user = ownerManager.getUser("voyaging");
		if (user == null) {
			user = new User();
			user.setUid("voyaging");
			user.setName("이하나");
			user.setPassword("dlgksk");
			user.setEmail("voyaging@naver.com");
			user.setEnabled(true);
		}
		Article article = new Article();

		article.setContent("테스트");
		article.setTitle("테스트");
		article.setEnabled(true);
		user.addArticle(article);

		StudyRoom room = new StudyRoom();
		room.setName("TOGA 설치");
		room.addArticle(article);
		room.setStartDate(new Date());
		room.setEndDate(new Date());
		room.setEnabled(true);
		user.addStudyRoom(room);
		article.setStudyRoom(room);

		Place place = new Place();
		place.setName("강남 토즈");
		place.setLatitude("123.123");
		place.setLongitude("123.123");
		room.setStudyPlace(place);

		Image image = new Image();
		image.setName("TOGA");
		room.setStudyImage(image);
		user.setUserImage(image);

		ownerManager.createUser(user);

		assertNotNull(user.getId());
	}

}