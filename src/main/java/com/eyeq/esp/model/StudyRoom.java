package com.eyeq.esp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:54
 * @revision $LastChangedRevision: 6112 $
 * @date $LastChangedDate: 2013-02-22 23:59:39 +0900 (금, 22 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "STUDYROOMS")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms(ownerId)", query = "from StudyRoom as studyRoom where OWNER = :ownerId"),
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms(enabled)", query = "from StudyRoom as studyRoom where ENABLED = :enabled"),
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRoom(id)", query = "from StudyRoom as studyRoom where id = :studyRoomId"),
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms()", query = "from StudyRoom as studyRoom") })
public class StudyRoom extends BaseEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", length = 1024)
	private String description;

	@ManyToMany
	@JoinTable(name = "USER_STUDYROOM", joinColumns = { @JoinColumn(name = "STUDYROOM_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<User> members;

	@ManyToOne
	@JoinColumn(name = "ownerId", nullable = false)
	private User owner;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(targetEntity = Article.class, mappedBy = "studyRoom", cascade = {
			CascadeType.MERGE, CascadeType.REMOVE })
	@OrderBy("id desc")
	private Set<Article> articles;

	@OneToOne
	@JoinColumn(name = "placeId")
	private Place studyPlace;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "imageId")
	private Image studyImage;

	public StudyRoom() {
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the members
	 */
	public Set<User> getMembers() {
		if (members == null) {
			this.members = new HashSet<User>();
		}
		return members;
	}

	/**
	 * @param member
	 */
	public void addMember(User member) {
		getMembers().add(member);
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the articles
	 */
	public Set<Article> getArticles() {
		if (articles == null) {
			this.articles = new HashSet<Article>();
		}
		return this.articles;
	}

	/**
	 * @param member
	 */
	public void addArticle(Article article) {
		getArticles().add(article);
	}

	/**
	 * @return the studyPlace
	 */
	public Place getStudyPlace() {
		return studyPlace;
	}

	/**
	 * @param studyPlace
	 *            the studyPlace to set
	 */
	public void setStudyPlace(Place studyPlace) {
		this.studyPlace = studyPlace;
	}

	/**
	 * @return the studyImage
	 */
	public Image getStudyImage() {
		return studyImage;
	}

	/**
	 * @param studyImage
	 *            the studyImage to set
	 */
	public void setStudyImage(Image studyImage) {
		this.studyImage = studyImage;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(Set<User> members) {
		this.members = members;
	}

	/**
	 * @param articles
	 *            the articles to set
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

}
