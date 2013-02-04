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
import javax.persistence.ManyToOne;
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
 * @since 0.0.2 2013. 1. 21. 오전 7:14:54
 * @revision $LastChangedRevision: 5921 $
 * @date $LastChangedDate: 2013-02-04 00:57:36 +0900 (월, 04 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Entity
@Table(name = "STUDYROOMS")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms():param.ownerId", query = "from StudyRoom as studyRoom where OWNER = :ownerId"),
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms():param.enabled", query = "from StudyRoom as studyRoom where ENABLED = :enabled"),
		@NamedQuery(name = "com.eyeq.esp.model.StudyRoom@getStudyRooms()", query = "from StudyRoom as studyRoom") })
public class StudyRoom {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION", length = 1024)
	private String description;

	@ManyToMany
	@JoinTable(name = "USER_STUDYROOM", joinColumns = { @JoinColumn(name = "STUDYROOM_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<User> members;

	@ManyToOne
	@JoinColumn(name = "ownerId")
	private User owner;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(targetEntity = Article.class, mappedBy = "studyRoom", cascade = CascadeType.ALL)
	private Set<Article> articles;

	@OneToOne
	@JoinColumn(name = "placeId")
	private Place studyPlace;

	@OneToOne
	@JoinColumn(name = "imageId")
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
	private Boolean enabled = false;

	public StudyRoom() {
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
