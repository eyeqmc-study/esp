package com.eyeq.esp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:29
 * @revision $LastChangedRevision: 5983 $
 * @date $LastChangedDate: 2013-02-09 02:38:37 +0900 (토, 09 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "ARTICLES")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.Article@getArticle(articleId)", query = "from Article as article where ID = :articleId"),
		@NamedQuery(name = "com.eyeq.esp.model.Article@getEnabledArticles(studyRooomId)", query = "from Article as article where STUDYROOM_ID = :studyRoomId and ENABLED = 1 order by ID desc"),
		@NamedQuery(name = "com.eyeq.esp.model.Article@getArticles()", query = "from Article as article order by ID desc") })
public class Article {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@ManyToOne(cascade = { CascadeType.ALL })
	// 임시
	@JoinColumn(name = "owner")
	private User owner;

	@Lob
	@Column
	private String content;

	@Column(name = "TITLE")
	private String title;

	@OneToMany(targetEntity = ArticleReply.class, mappedBy = "article", cascade = CascadeType.ALL)
	private List<ArticleReply> articleReplies;

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
	private Boolean enabled = true;

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
	 * @return the articleReplies
	 */
	public List<ArticleReply> getArticleReplies() {
		return articleReplies;
	}

	/**
	 * @param articleReplies
	 *            the articleReplies to set
	 */
	public void setArticleReplies(List<ArticleReply> articleReplies) {
		this.articleReplies = articleReplies;
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
