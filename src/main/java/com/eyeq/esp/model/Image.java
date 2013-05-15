package com.eyeq.esp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:43
 * @revision $LastChangedRevision: 6112 $
 * @date $LastChangedDate: 2013-02-22 23:59:39 +0900 (금, 22 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "IMAGES")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.Image@getImages():param.ownerId", query = "from Image as studyRoom where OWNER = :ownerId"),
		@NamedQuery(name = "com.eyeq.esp.model.Image@getImages()", query = "from Image as image") })
public class Image extends BaseEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "REAL_NAME", nullable = false)
	private String realName;

	@Column(name = "IMAGE_SIZE", nullable = false)
	private Long size;

	@Column(name = "IMAGE_URL")
	private String url;

	@OneToOne
	private User owner;

	@Column(name = "IMAGE_PATH")
	private String path;

	@Column(name = "CONTENT_TYPE")
	private String contentType;

	public Image() {
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
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
