package com.eyeq.esp.service.impl;

import java.util.List;

import com.eyeq.esp.model.Place;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.PlaceManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:21:31
 * @revision $LastChangedRevision: 5847 $
 * @date $LastChangedDate: 2013-01-24 18:03:35 +0900 (목, 24 1월 2013) $
 * @by $LastChangedBy: jmlim $
 */
public class PlaceManagerImpl extends AbstractJpaDaoService implements
		PlaceManager {

	/**
	 * @see com.eyeq.esp.service.PlaceManager#getPlace(java.lang.Integer)
	 */
	public Place getPlace(Integer placeId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#getPlace(com.eyeq.esp.model.StudyRoom)
	 */
	public Place getPlace(StudyRoom studyRoom) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#deletePlace(com.eyeq.esp.model.Place)
	 */
	public void deletePlace(Place place) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#updatePlace(com.eyeq.esp.model.Place)
	 */
	public void updatePlace(Place place) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#createPlace(com.eyeq.esp.model.Place)
	 */
	public void createPlace(Place place) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#getPlaces()
	 */
	public List<Place> getPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

}
