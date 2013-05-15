package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.Place;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.PlaceManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:21:31
 * @revision $LastChangedRevision: 6070 $
 * @date $LastChangedDate: 2013-02-16 12:31:02 +0900 (토, 16 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Service("placeManager")
@Transactional
public class PlaceManagerImpl extends AbstractJpaDaoService implements
		PlaceManager {

	/**
	 * @see com.eyeq.esp.service.PlaceManager#getPlace(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Place getPlace(Integer placeId) {
		return getEntityManager().find(Place.class, placeId);
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#deletePlace(com.eyeq.esp.model.Place)
	 */
	public void deletePlace(Place place) {
		place.setDeletedDate(new Date());
		place.setEnabled(false);
		getEntityManager().merge(place);
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#updatePlace(com.eyeq.esp.model.Place)
	 */
	public void updatePlace(Place place) {
		place.setModifiedDate(new Date());
		getEntityManager().merge(place);
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#createPlace(com.eyeq.esp.model.Place)
	 */
	public void createPlace(Place place) {
		place.setCreatedDate(new Date());
		getEntityManager().persist(place);
	}

	/**
	 * @see com.eyeq.esp.service.PlaceManager#getPlaces()
	 */
	@Transactional(readOnly = true)
	public List<Place> getPlaces() {
		List<Place> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.Place@getPlaces()", Place.class)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

}
