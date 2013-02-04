package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:42
 * @revision $LastChangedRevision: 5921 $
 * @date $LastChangedDate: 2013-02-04 00:57:36 +0900 (월, 04 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface StudyRoomManager {

	public StudyRoom getStudyRoom(Integer studyRoomId);

	public void deleteStudyRoom(StudyRoom studyRoom);

	public void updateStudyRoom(StudyRoom studyRoom);

	public void createStudyRoom(StudyRoom studyRoom);

	public List<StudyRoom> getStudyRooms();

	public List<StudyRoom> getStudyRooms(User user);

	public List<StudyRoom> getEnabledStudyRooms();
}
