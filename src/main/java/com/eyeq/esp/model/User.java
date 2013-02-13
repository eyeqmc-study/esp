package com.eyeq.esp.model;

import java.util.Date;
import java.util.HashSet;
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

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:11:00
 * @revision $LastChangedRevision: 6000 $
 * @date $LastChangedDate: 2013-02-12 21:09:28 +0900 (화, 12 2월 2013) $
 * @by $LastChangedBy: samkwang.na $
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.User@getUser():param.userId", query = "from User as user where ID = :userId"),
		@NamedQuery(name = "com.eyeq.esp.model.User@getUser():param.uId", query = "from User as user where U_ID = :uId"),
		@NamedQuery(name = "com.eyeq.esp.model.User@getUser()", query = "from User as user") })
public class User {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	// uid 를 부적합한 식별자 때문에 변경하였음.
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
	private Boolean enabled = true;

	@Column(name = "ROLE")
	private String role;

	@OneToMany(targetEntity = Penalty.class, cascade = CascadeType.ALL)
	private Set<Penalty> penalties;

	public User() {
	}

	/**
	 * @param uid
	 * @param name
	 * @param email
	 */
	public User(String uid, String name, String email) {
		this.uid = uid;
		this.name = name;
		this.email = email;
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
	 * @return the articles
	 */
	public Set<Article> getArticles() {
		if (this.articles == null) {
			this.articles = new HashSet<Article>();
		}
		return this.articles;
	}

	/**
	 * @param article
	 */
	protected void addArticle(Article article) {
		getArticles().add(article);
	}

	/**
	 * @param penalty
	 */
	protected void addPenalty(Penalty penalty) {
		getPenalties().add(penalty);
	}

	/**
	 * @return the penalties
	 */
	public Set<Penalty> getPenalties() {
		if (this.penalties == null) {
			this.penalties = new HashSet<Penalty>();
		}
		return this.penalties;
	}

	/**
	 * @param penalties
	 *            the penalties to set
	 */
	public void setPenalties(Set<Penalty> penalties) {
		this.penalties = penalties;
	}

	/**
	 * @return the studyRooms
	 */
	public Set<StudyRoom> getStudyRooms() {
		if (this.studyRooms == null) {
			this.studyRooms = new HashSet<StudyRoom>();
		}
		return this.studyRooms;
	}

	/**
	 * @param studyRoom
	 */
	public void addStudyRoom(StudyRoom studyRoom) {
		getStudyRooms().add(studyRoom);
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
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
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate
	 *            the deletedDate to set
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

	/**
	 * @param articles
	 *            the articles to set
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * @param studyRooms
	 *            the studyRooms to set
	 */
	public void setStudyRooms(Set<StudyRoom> studyRooms) {
		this.studyRooms = studyRooms;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
