package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:24
 * @revision $LastChangedRevision: 5807 $
 * @date $LastChangedDate: 2013-01-21 07:19:28 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface ImageManager {

	public Image getImage(Integer imageId);

	public Image getImage(User user);

	public Image getImage(StudyRoom studyRoom);

	public void deleteImage(Image image);

	public void updateImage(Image image);

	public void createImage(Image image);

	public List<Image> getImages();

}
