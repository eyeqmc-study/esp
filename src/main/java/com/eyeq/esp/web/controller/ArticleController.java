package com.eyeq.esp.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eyeq.esp.exception.UserNotFoundException;
import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.ArticleReply;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:13
 * @revision $LastChangedRevision: 5926 $
 * @date $LastChangedDate: 2013-02-04 05:55:03 +0900 (월, 04 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Controller
public class ArticleController {

	@Autowired
	private ArticleManager articleManager;

	@Autowired
	private UserManager userManager;

	@RequestMapping(value = "/article/edit-form", method = RequestMethod.GET)
	public ModelAndView articleEditForm(
			ModelAndView mav,
			@RequestParam(value = "articleId", required = false) Integer articleId) {

		if (articleId != null) {
			Article article = articleManager.getArticle(articleId);
			mav.addObject("article", article);
		}
		mav.setViewName("/pages/article/edit-form");
		return mav;
	}

	@RequestMapping(value = "/article/edit-form-submit", method = RequestMethod.POST)
	public ModelAndView articleEditSubmit(ModelAndView mav,
			@ModelAttribute("Article") Article article, HttpSession session)
			throws UserNotFoundException {
		User owner = (User) session.getAttribute("user");

		if (owner == null || owner.getId() == null) {
			throw new UserNotFoundException();
		}

		article.setOwner(owner);

		if (article.getId() == null) {
			articleManager.createArticle(article);
			mav.setViewName("redirect:/article/list");
		} else {
			articleManager.updateArticle(article);
			mav.setViewName("redirect:/article/content?articleId="
					+ article.getId());
		}

		return mav;
	}

	@RequestMapping(value = "/article/delete", method = RequestMethod.GET)
	public ModelAndView articleDelete(ModelAndView mav,
			@RequestParam("articleId") Integer articleId) {

		Article article = articleManager.getArticle(articleId);
		articleManager.deleteArticle(article);
		mav.setViewName("redirect:/article/list");
		return mav;
	}

	@RequestMapping(value = "/article/content", method = RequestMethod.GET)
	public ModelAndView getArticleContent(ModelAndView mav,
			@RequestParam(value = "articleId") Integer articleId,
			HttpSession session) {
		Article article = articleManager.getArticle(articleId);

		mav.addObject("article", article);
		mav.setViewName("/pages/article/content");
		return mav;
	}

	@RequestMapping(value = "/article/list", method = RequestMethod.GET)
	public ModelAndView getArticles(ModelAndView mav,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "listCount", defaultValue = "5") int listCount) {

		int startNum = page * listCount;

		List<Article> articles = articleManager.getArticles();

		if (articles == null) {
			articles = new ArrayList<Article>();
		}

		int articlesSize = articles.size();

		int toIndex = startNum + listCount;
		if (articlesSize < toIndex) {
			toIndex = articlesSize;
		}
		List<Article> currentArticles = articles.subList(startNum, toIndex);
		int pageCount = articlesSize / listCount;

		mav.addObject("pageCount", pageCount);
		mav.addObject("articles", currentArticles);
		mav.setViewName("/pages/article/list");
		return mav;
	}

	@RequestMapping(value = "/article-reply/edit-form-submit", method = RequestMethod.POST)
	public ModelAndView articleReplyEditSubmit(
			ModelAndView mav,
			@ModelAttribute("articleReply") ArticleReply reply,
			@RequestParam(value = "articleId", required = false) Integer articleId) {
		Article article = articleManager.getArticle(articleId);

		reply.setArticle(article);
		if (reply.getArticleReplyId() == null) {
			articleManager.createArticleReply(reply);
		} else {
			articleManager.updateArticleReply(reply);
		}
		mav.setViewName("redirect:/article/content?articleId=" + articleId);

		return mav;
	}

	@RequestMapping(value = "/article-reply/delete", method = RequestMethod.GET)
	public ModelAndView articleReplyDelete(ModelAndView mav,
			@RequestParam("articleReplyId") Integer articleReplyId,
			@RequestParam("articleId") Integer articleId) {
		ArticleReply reply = articleManager.getArticleReply(articleReplyId);
		articleManager.deleteArticleReply(reply);
		mav.setViewName("redirect:/article/content?articleId=" + articleId);
		return mav;
	}

}
