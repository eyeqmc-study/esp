package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Place;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:37
 * @revision $LastChangedRevision: 5999 $
 * @date $LastChangedDate: 2013-02-12 07:22:21 +0900 (화, 12 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface PlaceManager {

	public Place getPlace(Integer placeId);

	public void deletePlace(Place place);

	public void updatePlace(Place place);

	public void createPlace(Place place);

	public List<Place> getPlaces();

}
