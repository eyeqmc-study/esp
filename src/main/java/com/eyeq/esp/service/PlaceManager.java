package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Place;
import com.eyeq.esp.model.StudyRoom;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:37
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface PlaceManager {

	public Place getPlace(Integer placeId);

	public Place getPlace(StudyRoom studyRoom);

	public void deletePlace(Place place);

	public void updatePlace(Place place);

	public void createPlace(Place place);

	public List<Place> getPlaces();

}
