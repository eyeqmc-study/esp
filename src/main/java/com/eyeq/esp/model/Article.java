package com.eyeq.esp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:29
 * @revision $LastChangedRevision: 5847 $
 * @date $LastChangedDate: 2013-01-24 18:03:35 +0900 (목, 24 1월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "ARTICLES")
@NamedQueries({
	@NamedQuery(name = "com.eyeq.esp.model.Article@getArticles():param.userId", query = "from Article as article where ID = :userId"),
	@NamedQuery(name = "com.eyeq.esp.model.Article@getArticles()", query = "from Article as article")})
public class Article {// extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "owner")
	private User owner;

	// lazy
	private String content;

	@Column(name = "TITLE")
	private String title;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "STUDYROOM_ID")
	private StudyRoom studyRoom;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "DELETED_DATE")
	@Temporal(TemporalType.DATE)
	private Date deletedDate;

	@Column(name = "ENABLED")
	private Boolean enabled;

	public Article() {
	}

	/**
	 * @param id
	 * @param createdDate
	 * @param modifiedDate
	 * @param deletedDate
	 * @param enabled
	 * @param owner
	 * @param content
	 * @param title
	 * @param studyRoom
	 */
	public Article(Integer id, Date createdDate, Date modifiedDate,
			Date deletedDate, Boolean enabled, User owner, String content,
			String title, StudyRoom studyRoom) {
		this.id = id;
		this.owner = owner;
		this.content = content;
		this.title = title;
		this.studyRoom = studyRoom;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.deletedDate = deletedDate;
		this.enabled = enabled;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the studyRoom
	 */
	public StudyRoom getStudyRoom() {
		return studyRoom;
	}

	/**
	 * @param studyRoom
	 *            the studyRoom to set
	 */
	public void setStudyRoom(StudyRoom studyRoom) {
		this.studyRoom = studyRoom;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
