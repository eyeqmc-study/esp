package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.ArticleReply;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.ArticleManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:55
 * @revision $LastChangedRevision: 6070 $
 * @date $LastChangedDate: 2013-02-16 12:31:02 +0900 (토, 16 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Service("articleManager")
@Transactional
public class ArticleManagerImpl extends AbstractJpaDaoService implements
		ArticleManager {

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getArticle(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public Article getArticle(Integer articleId) {
		return getEntityManager().find(Article.class, articleId);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#deleteArticle(com.eyeq.esp.model.Article)
	 */
	public void deleteArticle(Article article) {
		article.setDeletedDate(new Date());
		article.setEnabled(false);
		getEntityManager().merge(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#updateArticle(com.eyeq.esp.model.Article)
	 */
	public void updateArticle(Article article) {
		article.setModifiedDate(new Date());
		getEntityManager().merge(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#createArticle(com.eyeq.esp.model.Article)
	 */
	public void createArticle(Article article) {
		article.setCreatedDate(new Date());
		getEntityManager().merge(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getArticles()
	 */
	@Transactional(readOnly = true)
	public List<Article> getArticles() {

		List<Article> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.Article@getArticles()", Article.class)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getEnabledArticles(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public List<Article> getEnabledArticles(Integer studyRoomId) {
		// TODO Auto-generated method stub
		List<Article> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.Article@getEnabledArticles(studyRooomId)",
						Article.class).setParameter("studyRoomId", studyRoomId)
				.getResultList();
		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getArticleReply(java.lang.Integer)
	 */
	public ArticleReply getArticleReply(Integer articleReplyId) {
		// TODO Auto-generated method stub
		return getEntityManager().find(ArticleReply.class, articleReplyId);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#createArticleReply(com.eyeq.esp.model.ArticleReply)
	 */
	public void createArticleReply(ArticleReply reply) {
		reply.setCreatedDate(new Date());
		getEntityManager().merge(reply);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#updateArticleReply(com.eyeq.esp.model.ArticleReply)
	 */
	public void updateArticleReply(ArticleReply reply) {
		reply.setModifiedDate(new Date());
		getEntityManager().merge(reply);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#deleteArticleReply(com.eyeq.esp.model.ArticleReply)
	 */
	public void deleteArticleReply(ArticleReply reply) {
		reply.setDeletedDate(new Date());
		reply.setEnabled(false);
		getEntityManager().merge(reply);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getEnabledArticleReplies(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public List<ArticleReply> getEnabledArticleReplies(Integer articleId) {
		// TODO Auto-generated method stub
		List<ArticleReply> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.ArticleReply@getArticleReplies(articleId)",
						ArticleReply.class)
				.setParameter("articleId", articleId).getResultList();
		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}
}
