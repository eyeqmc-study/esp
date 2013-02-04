package com.eyeq.esp.model;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.eyeq.esp.service.StudyRoomManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:54
 * @revision $LastChangedRevision: 5912 $
 * @date $LastChangedDate: 2013-02-03 01:27:27 +0900 (일, 03 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/esp-test-context.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class StudyRoomTest {

	@Autowired
	private UserManager ownerManager;

	@Autowired
	private StudyRoomManager studyRoomManager;

	@Test
	public void testCreateStudyRoom() {
		User owner = ownerManager.getUser("voyaging");
		if (owner == null) {
			owner = new User();
			owner.setName("이하나");
			owner.setEmail("voyaging@naver.com");
			owner.setPassword("dlgksk");
			owner.setEnabled(true);
			owner.setPenaltyScore(0);
			ownerManager.createUser(owner);
		}
		StudyRoom room = new StudyRoom();
		room.setName("TOGA 설치2");
		room.setStartDate(new Date());
		room.setEndDate(new Date());
		room.setEnabled(true);
		room.setOwner(owner);
		room.addMember(owner);

		studyRoomManager.createStudyRoom(room);
		
		StudyRoom room2 = new StudyRoom();
		room2.setName("TOGA 설치3");
		room2.setStartDate(new Date());
		room2.setEndDate(new Date());
		room2.setEnabled(false);
		room2.setOwner(owner);
		
		studyRoomManager.createStudyRoom(room2);

		assertNotNull(room.getId());
	}

	@Test
	@Ignore
	public void testUpdateStudyRoom() {
		List<StudyRoom> rooms = studyRoomManager.getStudyRooms();
		for (StudyRoom room : rooms) {
			if (room.getStudyImage() == null) {
				Image image = new Image();
				image.setName("TOGA image");
				room.setStudyImage(image);
				studyRoomManager.updateStudyRoom(room);
			}
		}
	}

	@Test
	@Ignore
	public void testGetStudyRooms() {
		User user = ownerManager.getUser("voyaging");
		assertNotNull(user);
		
		List<StudyRoom> rooms = studyRoomManager.getStudyRooms(user);
		for (StudyRoom room : rooms) {
			assertNotNull(room.getName());
		}
	}

	@Test
	@Ignore
	public void testGetStudyMember() {
		User user = ownerManager.getUser("voyaging");
		assertNotNull(user);
		
		List<StudyRoom> rooms = studyRoomManager.getStudyRooms();
		for (StudyRoom room : rooms) {
			assertNotNull(room.getName());
		}
	}

}