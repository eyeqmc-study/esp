package com.eyeq.esp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:14:29
 * @revision $LastChangedRevision: 6112 $
 * @date $LastChangedDate: 2013-02-22 23:59:39 +0900 (금, 22 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "ARTICLES")
@NamedQueries({
		@NamedQuery(name = "com.eyeq.esp.model.Article@getArticle(articleId)", query = "from Article as article where ID = :articleId"),
		@NamedQuery(name = "com.eyeq.esp.model.Article@getEnabledArticles(studyRooomId)", query = "from Article as article where STUDYROOM_ID = :studyRoomId and ENABLED = 1 order by ID desc"),
		@NamedQuery(name = "com.eyeq.esp.model.Article@getArticles()", query = "from Article as article order by ID desc") })
public class Article extends BaseEntity {

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	// 임시
	@JoinColumn(name = "owner", nullable = false)
	private User owner;

	@Lob
	@Column(nullable = false)
	private String content;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@OneToMany(targetEntity = ArticleReply.class, mappedBy = "article", cascade = {
			CascadeType.MERGE, CascadeType.REMOVE })
	private List<ArticleReply> articleReplies;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "STUDYROOM_ID")
	private StudyRoom studyRoom;

	public Article() {
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

}
