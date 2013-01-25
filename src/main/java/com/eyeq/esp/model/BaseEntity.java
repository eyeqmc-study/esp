package com.eyeq.esp.model;

import java.util.Date;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:37
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public class BaseEntity {

	private Integer id;

	private Date createdDate;

	private Date modifiedDate;

	private Date deletedDate;

	private Boolean enabled;

	public BaseEntity() {
	}

	/**
	 * @param id
	 * @param createdDate
	 * @param modifiedDate
	 * @param deletedDate
	 * @param enabled
	 */
	public BaseEntity(Integer id, Date createdDate, Date modifiedDate,
			Date deletedDate, Boolean enabled) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.deletedDate = deletedDate;
		this.enabled = enabled;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deleteDate
	 *            the deleteDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isNew() {
		return (this.id == null);
	}

}
