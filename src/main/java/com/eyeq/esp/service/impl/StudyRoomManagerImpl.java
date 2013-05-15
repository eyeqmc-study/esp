package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.StudyRoomManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:21:48
 * @revision $LastChangedRevision: 6070 $
 * @date $LastChangedDate: 2013-02-16 12:31:02 +0900 (토, 16 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Service("studyManager")
@Transactional
public class StudyRoomManagerImpl extends AbstractJpaDaoService implements
		StudyRoomManager {

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#getStudyRoom(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public StudyRoom getStudyRoom(Integer studyRoomId) {
		return getEntityManager().find(StudyRoom.class, studyRoomId);
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#deleteStudyRoom(com.eyeq.esp.model.StudyRoom)
	 */
	public void deleteStudyRoom(StudyRoom studyRoom) {
		studyRoom.setDeletedDate(new Date());
		studyRoom.setEnabled(false);
		getEntityManager().merge(studyRoom);
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#updateStudyRoom(com.eyeq.esp.model.StudyRoom)
	 */
	public void updateStudyRoom(StudyRoom studyRoom) {
		studyRoom.setModifiedDate(new Date());
		getEntityManager().merge(studyRoom);
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#createStudyRoom(com.eyeq.esp.model.StudyRoom)
	 */
	public void createStudyRoom(StudyRoom studyRoom) {
		studyRoom.setCreatedDate(new Date());
		if (getEnabledStudyRooms() == null) {
			studyRoom.setEnabled(true);
		}
		getEntityManager().persist(studyRoom);
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#getStudyRooms()
	 */
	@Transactional(readOnly = true)
	public List<StudyRoom> getStudyRooms() {
		List<StudyRoom> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.StudyRoom@getStudyRooms()",
						StudyRoom.class).getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#getStudyRooms(com.eyeq.esp.model.User)
	 */
	@Transactional(readOnly = true)
	public List<StudyRoom> getStudyRooms(User user) {
		List<StudyRoom> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.StudyRoom@getStudyRooms(ownerId)",
						StudyRoom.class).setParameter("ownerId", user.getId())
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.StudyRoomManager#getEnabledStudyRooms()
	 */
	@Transactional(readOnly = true)
	public List<StudyRoom> getEnabledStudyRooms() {
		List<StudyRoom> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.StudyRoom@getStudyRooms(enabled)",
						StudyRoom.class).setParameter("enabled", true)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}
		return null;
	}

}
