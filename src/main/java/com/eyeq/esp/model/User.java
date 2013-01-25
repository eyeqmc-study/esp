package com.eyeq.esp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:11:00
 * @revision $LastChangedRevision: 5847 $
 * @date $LastChangedDate: 2013-01-24 18:03:35 +0900 (목, 24 1월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
	@NamedQuery(name = "com.eyeq.esp.model.User@getUser():param.userId", query = "from User as user where ID = :userId"),
	@NamedQuery(name = "com.eyeq.esp.model.User@getUser():param.uId", query = "from User as user where U_ID = :uId"),
	@NamedQuery(name = "com.eyeq.esp.model.User@getUsers()", query = "from User as user")})
public class User {// extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	 //uid 를 부적합한 식별자 때문에 변경하였음.
	@Column(name = "U_ID")
	private String uid;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(targetEntity = Article.class, mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Article> articles;

	@Column(name = "PENALTY")
	private Integer penaltyScore = 0;

	@ManyToMany
	@JoinTable(name = "USER_STUDYROOM", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "STUDYROOM_ID") })
	private Set<StudyRoom> studyRooms;

	@OneToOne
	@JoinColumn(name = "ownStudy")
	private StudyRoom ownStudy;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userImage")
	private Image userImage;

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

	public User() {
	}

	/**
	 * @param id
	 * @param createdDate
	 * @param modifiedDate
	 * @param deletedDate
	 * @param enabled
	 * @param name
	 * @param email
	 * @param password
	 * @param articles
	 * @param penaltyScore
	 * @param userImage
	 */
	public User(Integer id, Date createdDate, Date modifiedDate,
			Date deletedDate, Boolean enabled, String name, String email,
			String password, Set<Article> articles, Integer penaltyScore,
			Image userImage, StudyRoom ownStudy) {
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.deletedDate = deletedDate;
		this.enabled = enabled;
		this.name = name;
		this.email = email;
		this.password = password;
		this.articles = articles;
		this.penaltyScore = penaltyScore;
		this.userImage = userImage;
		this.ownStudy = ownStudy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the article
	 */
	protected Set<Article> getArticlesInternal() {
		if (this.articles == null) {
			this.articles = new HashSet<Article>();
		}
		return articles;
	}

	/**
	 * @param article
	 *            the article to set
	 */
	protected void setArticlesInternal(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * @return
	 */
	public List<Article> getArticles() {
		List<Article> sortedArticles = new ArrayList<Article>(
				getArticlesInternal());
		PropertyComparator.sort(sortedArticles, new MutableSortDefinition("id",
				true, true));
		return Collections.unmodifiableList(sortedArticles);
	}

	/**
	 * @param article
	 */
	public void addArticle(Article article) {
		getArticlesInternal().add(article);
		article.setOwner(this);
	}

	/**
	 * @param article
	 * @return
	 */
	public Article getArticle(Article article) {
		List<Article> articles = getArticles();
		int idx = articles.indexOf(article);
		if (idx != -1) {
			return articles.get(idx);
		}
		return null;
	}

	/**
	 * @param articleId
	 * @return
	 */
	public Article getArticle(Integer articleId) {
		for (Article article : getArticlesInternal()) {
			if (article.getId().equals(articleId)
					&& article.getOwner().equals(this)) {
				return article;
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	protected Set<StudyRoom> getStudyRoomsInternal() {
		if (this.studyRooms == null) {
			this.studyRooms = new HashSet<StudyRoom>();
		}
		return studyRooms;
	}

	/**
	 * @param studyRooms
	 */
	protected void setStudyRoomsInternal(Set<StudyRoom> studyRooms) {
		this.studyRooms = studyRooms;
	}

	/**
	 * @return
	 */
	public List<StudyRoom> getStudyRooms() {
		List<StudyRoom> sortedStudyRooms = new ArrayList<StudyRoom>(
				getStudyRoomsInternal());
		PropertyComparator.sort(sortedStudyRooms, new MutableSortDefinition(
				"id", true, true));
		return Collections.unmodifiableList(sortedStudyRooms);
	}

	/**
	 * @param studyRoom
	 */
	public void addStudyRoom(StudyRoom studyRoom) {
		getStudyRoomsInternal().add(studyRoom);
		studyRoom.setOwner(this);
	}

	/**
	 * @param studyRoom
	 * @return
	 */
	public StudyRoom getStudyRoom(StudyRoom studyRoom) {
		List<StudyRoom> StudyRooms = getStudyRooms();
		int idx = StudyRooms.indexOf(studyRoom);
		if (idx != -1) {
			return StudyRooms.get(idx);
		}
		return null;
	}

	/**
	 * @param StudyRoomId
	 * @return
	 */
	public StudyRoom getStudyRoom(Integer StudyRoomId) {
		for (StudyRoom article : getStudyRoomsInternal()) {
			if (article.getId().equals(StudyRoomId)
					&& article.getOwner().equals(this)) {
				return article;
			}
		}
		return null;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the penaltyScore
	 */
	public Integer getPenaltyScore() {
		return penaltyScore;
	}

	/**
	 * @param penaltyScore
	 *            the penaltyScore to set
	 */
	public void setPenaltyScore(Integer penaltyScore) {
		this.penaltyScore = penaltyScore;
	}

	/**
	 * @return the userImage
	 */
	public Image getUserImage() {
		return userImage;
	}

	/**
	 * @param userImage
	 *            the userImage to set
	 */
	public void setUserImage(Image userImage) {
		this.userImage = userImage;
	}

	/**
	 * @return the ownStudy
	 */
	public StudyRoom getOwnStudy() {
		return ownStudy;
	}

	/**
	 * @param ownStudy
	 *            the ownStudy to set
	 */
	public void setOwnStudy(StudyRoom ownStudy) {
		this.ownStudy = ownStudy;
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

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User information (id : " + this.getId() + ", uid : "
				+ this.getUid() + ", name : " + this.getName() + ", email : "
				+ this.getEmail() + ")";
	}

}
