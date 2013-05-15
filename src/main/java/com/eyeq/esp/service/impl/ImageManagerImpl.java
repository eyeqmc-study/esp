package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.ImageManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:21:15
 * @revision $LastChangedRevision: 6070 $
 * @date $LastChangedDate: 2013-02-16 12:31:02 +0900 (토, 16 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Service("imageManager")
@Transactional
public class ImageManagerImpl extends AbstractJpaDaoService implements
		ImageManager {

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImage(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Image getImage(Integer imageId) {
		return getEntityManager().find(Image.class, imageId);
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImage(com.eyeq.esp.model.User)
	 */
	@Transactional(readOnly = true)
	public List<Image> getImages(User owner) {
		List<Image> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.Image@getImages():param.ownerId",
						Image.class).setParameter("ownerId", owner.getId())
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#deleteImage(com.eyeq.esp.model.Image)
	 */
	public void deleteImage(Image image) {
		image.setDeletedDate(new Date());
		image.setEnabled(false);
		getEntityManager().merge(image);
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#updateImage(com.eyeq.esp.model.Image)
	 */
	public void updateImage(Image image) {
		image.setModifiedDate(new Date());
		getEntityManager().merge(image);
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#createImage(com.eyeq.esp.model.Image)
	 */
	public void createImage(Image image) {
		image.setCreatedDate(new Date());
		getEntityManager().persist(image);
	}

	/**
	 * @see com.eyeq.esp.service.ImageManager#getImages()
	 */
	public List<Image> getImages() {
		List<Image> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.Image@getImages()", Image.class)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

}
