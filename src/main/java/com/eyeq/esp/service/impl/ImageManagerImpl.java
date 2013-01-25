package com.eyeq.esp.service.impl;

import java.util.List;

import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.ImageManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:21:15
 * @revision $LastChangedRevision: 5847 $
 * @date $LastChangedDate: 2013-01-24 18:03:35 +0900 (목, 24 1월 2013) $
 * @by $LastChangedBy: jmlim $
 */
public class ImageManagerImpl extends AbstractJpaDaoService implements
		ImageManager {

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImage(java.lang.Integer)
	 */
	public Image getImage(Integer imageId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImage(com.eyeq.esp.model.User)
	 */
	public Image getImage(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImage(com.eyeq.esp.model.StudyRoom)
	 */
	public Image getImage(StudyRoom studyRoom) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#deleteImage(com.eyeq.esp.model.Image)
	 */
	public void deleteImage(Image image) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#updateImage(com.eyeq.esp.model.Image)
	 */
	public void updateImage(Image image) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#createImage(com.eyeq.esp.model.Image)
	 */
	public void createImage(Image image) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImages()
	 */
	public List<Image> getImages() {
		// TODO Auto-generated method stub
		return null;
	}

}
