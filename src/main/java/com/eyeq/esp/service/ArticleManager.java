package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:15:10
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface ArticleManager {

	public Article getArticle(Integer articleId);

	public void deleteArticle(Article article);

	public void updateArticle(Article article);

	public void createArticle(Article article);

	public List<Article> getArticles();

	public List<Article> getArticles(User user);
}
