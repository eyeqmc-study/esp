package com.eyeq.esp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author jmlim
 * @since 0.0.2 2013. 2. 4. 오후 12:11:27
 * @revision $LastChangedRevision: 6115 $
 * @date $LastChangedDate: 2013-02-25 12:18:46 +0900 (월, 25 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "ARTICLE_REPLIES")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.ArticleReply@getArticleReplies(articleId)", query = "from ArticleReply as reply where article_id = :articleId and ENABLED = 1 order by ID"),
		@NamedQuery(name = "com.eyeq.esp.model.ArticleReply@getArticleReply(articleReplyId)", query = "from ArticleReply as reply where ID = :articleReplyId") })
public class ArticleReply extends BaseEntity {

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private User owner;

	@Lob
	@Column(nullable = false)
	private String content;

	public ArticleReply() {
		super();
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

}