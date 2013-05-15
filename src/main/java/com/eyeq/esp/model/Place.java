package com.eyeq.esp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:48
 * @revision $LastChangedRevision: 6112 $
 * @date $LastChangedDate: 2013-02-22 23:59:39 +0900 (금, 22 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "PLACES")
@NamedQueries({ @NamedQuery(name = "com.eyeq.esp.model.Place@getPlaces()", query = "from Place as place") })
public class Place extends BaseEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@Column(name = "LONGITUED", nullable = false)
	private Double longitude;

	@Column(name = "LATITUDE", nullable = false)
	private Double latitude;

	public Place() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
