package com.eyeq.esp.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author jmlim
 * @since 0.0.2 2013. 2. 4. 오후 12:11:27
 * @revision $LastChangedRevision: 5927 $
 * @date $LastChangedDate: 2013-02-04 12:14:31 +0900 (월, 04 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "ARTICLE_REPLIES")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.ArticleReply@getArticleReplies(articleId)", query = "from ArticleReply as reply where article_id = :articleId order by articleId desc"),
		@NamedQuery(name = "com.eyeq.esp.model.ArticleReply@getArticleReply(articleReplyId)", query = "from ArticleReply as reply where articleReplyId = :articleReplyId") })
public class ArticleReply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer articleReplyId;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "article_id")
	private Article article;

	@ManyToOne(cascade = { CascadeType.ALL })
	private User owner;

	@Lob
	@Column
	private String content;

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

	public ArticleReply() {
		super();
	}

	public ArticleReply(Integer articleReplyId, Article article, User owner,
			String content, Date createdDate, Date modifiedDate) {
		super();
		this.articleReplyId = articleReplyId;
		this.article = article;
		this.owner = owner;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the articleReplyId
	 */
	public Integer getArticleReplyId() {
		return articleReplyId;
	}

	/**
	 * @param articleReplyId
	 *            the articleReplyId to set
	 */
	public void setArticleReplyId(Integer articleReplyId) {
		this.articleReplyId = articleReplyId;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article
	 *            the article to set
	 */
	public void setArticle(Article article) {
		this.article = article;
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

}