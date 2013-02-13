package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.ArticleReply;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:10
 * @revision $LastChangedRevision: 5984 $
 * @date $LastChangedDate: 2013-02-09 05:11:03 +0900 (토, 09 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
public interface ArticleManager {

	public Article getArticle(Integer articleId);

	public void deleteArticle(Article article);

	public void updateArticle(Article article);

	public void createArticle(Article article);

	public List<Article> getArticles();

	public List<Article> getEnabledArticles(Integer studyRoomId);

	ArticleReply getArticleReply(Integer articleReplyId);

	void createArticleReply(ArticleReply reply);

	void updateArticleReply(ArticleReply reply);

	void deleteArticleReply(ArticleReply reply);

	List<ArticleReply> getEnabledArticleReplies(Integer articleId);
}
