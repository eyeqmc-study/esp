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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:54
 * @revision $LastChangedRevision: 5840 $
 * @date $LastChangedDate: 2013-01-24 00:01:12 +0900 (목, 24 1월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "STUDYROOMS")
public class StudyRoom {// extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;

	@ManyToMany
	@JoinTable(name = "USER_STUDYROOM", joinColumns = { @JoinColumn(name = "STUDYROOM_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<User> members;

	@OneToOne(mappedBy = "ownStudy")
	private User owner;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(targetEntity = Article.class, mappedBy = "studyRoom", cascade = CascadeType.ALL)
	private Set<Article> articles;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "studyPlace")
	private Place studyPlace;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "studyImage")
	private Image studyImage;

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

	public StudyRoom() {
	}

	/**
	 * @param id
	 * @param createdDate
	 * @param modifiedDate
	 * @param deletedDate
	 * @param enabled
	 * @param name
	 * @param description
	 * @param members
	 * @param owner
	 * @param startDate
	 * @param endDate
	 * @param articles
	 * @param studyPlace
	 * @param studyImage
	 */
	public StudyRoom(Integer id, Date createdDate, Date modifiedDate,
			Date deletedDate, Boolean enabled, String name, String description,
			Set<User> members, User owner, Date startDate, Date endDate,
			Set<Article> articles, Place studyPlace, Image studyImage) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.members = members;
		this.owner = owner;
		this.startDate = startDate;
		this.endDate = endDate;
		this.articles = articles;
		this.studyPlace = studyPlace;
		this.studyImage = studyImage;
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
	 * @return
	 */
	protected Set<User> getMembersInternal() {
		if (this.members == null) {
			this.members = new HashSet<User>();
		}
		return members;
	}

	/**
	 * @param members
	 */
	protected void setMembersInternal(Set<User> members) {
		this.members = members;
	}

	/**
	 * @return
	 */
	public List<User> getMembers() {
		List<User> sortedMembers = new ArrayList<User>(getMembersInternal());
		PropertyComparator.sort(sortedMembers, new MutableSortDefinition("id",
				true, true));
		return Collections.unmodifiableList(sortedMembers);
	}

	/**
	 * @param member
	 */
	public void addMember(User member) {
		getMembersInternal().add(member);
		member.addStudyRoom(this);
	}

	/**
	 * @param member
	 * @return
	 */
	public User getMember(User member) {
		List<User> members = getMembers();
		int idx = members.indexOf(member);
		if (idx != -1) {
			return members.get(idx);
		}
		return null;
	}

	/**
	 * @param memberId
	 * @return
	 */
	public User getMember(Integer memberId) {
		for (User member : getMembersInternal()) {
			if (member.getId().equals(memberId)
					&& member.getStudyRoom(this).equals(this)) {
				return member;
			}
		}
		return null;
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
		article.setStudyRoom(this);
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
					&& article.getStudyRoom().equals(this)) {
				return article;
			}
		}
		return null;
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
