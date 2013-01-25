package com.eyeq.esp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.ArticleManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:55
 * @revision $LastChangedRevision: 5847 $
 * @date $LastChangedDate: 2013-01-24 18:03:35 +0900 (목, 24 1월 2013) $
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
		getEntityManager().remove(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#updateArticle(com.eyeq.esp.model.Article)
	 */
	public void updateArticle(Article article) {
		getEntityManager().merge(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#createArticle(com.eyeq.esp.model.Article)
	 */
	public void createArticle(Article article) {
		getEntityManager().persist(article);
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getArticles()
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Article> getArticles() {

		List<Article> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.Article@getArticles()").getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}

		return null;
	}

	/**
	 * @see com.eyeq.esp.service.ArticleManager#getArticles(com.eyeq.esp.model.User)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Article> getArticles(User user) {
		List<Article> results = getEntityManager()
				.createNamedQuery(
						"com.eyeq.esp.model.Article@getArticles():param.userId")
				.setParameter(0, user.getId()).getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}
		return null;
	}

}
