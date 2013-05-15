package com.eyeq.esp.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eyeq.esp.exception.ArticleNotFoundException;
import com.eyeq.esp.exception.RoomNotFoundException;
import com.eyeq.esp.exception.UserNotFoundException;
import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.ArticleReply;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:33
 * @revision $LastChangedRevision: 6035 $
 * @date $LastChangedDate: 2013-02-15 02:52:03 +0900 (금, 15 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
public class UserPanelController {

	@Autowired
	private UserManager ownerManager;

	@Autowired
	private ArticleManager articleManager;

	@RequestMapping(value = "/user-article/list", method = RequestMethod.GET)
	public String getArticles(Model model, HttpSession session,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "listCount", defaultValue = "7") int listCount)
			throws RoomNotFoundException, UserNotFoundException {

		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null || currentUser.getUid() == null) {
			throw new UserNotFoundException();
		}

		currentUser = ownerManager.getUser(currentUser.getUid());

		int startNum = page * listCount;
		List<Article> articles = currentUser.getArticles();
		// List<Article> articles =
		// articleManager.getEnabledArticles(studyRoomId);

		if (articles == null) {
			articles = new ArrayList<Article>();
		}

		int articlesSize = articles.size();

		int toIndex = startNum + listCount;
		if (articlesSize < toIndex) {
			toIndex = articlesSize;
		}
		List<Article> currentArticles = articles.subList(startNum, toIndex);

		int pageCount = (articlesSize - 1) / listCount;
		int maxOffset = pageCount / 10;

		if (offset < 0) {
			offset = 0;
		}

		if (maxOffset < offset) {
			offset = maxOffset;
		}

		int startCount = (offset + 0) * 10;

		model.addAttribute("maxOffset", maxOffset);
		if ((offset + 1) * 9 < pageCount) {
			pageCount = (offset + 1) * 9;
		}

		model.addAttribute("startCount", startCount);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("offset", offset);
		model.addAttribute("articles", currentArticles);
		// model.addAttribute("studyRoomId", studyRoomId);

		return "/pages/user-article/list";
	}

	@RequestMapping(value = "/user-article/content", method = RequestMethod.GET)
	public String getArticleContent(Model model,
			@RequestParam(value = "articleId") Integer articleId)
			throws ArticleNotFoundException {
		Article article = articleManager.getArticle(articleId);

		if (article == null) {
			throw new ArticleNotFoundException();

		}

		model.addAttribute("article", article);
		model.addAttribute("articleReply", new ArticleReply());

		List<ArticleReply> articleReplies = articleManager
				.getEnabledArticleReplies(article.getId());

		model.addAttribute("articleReplies", articleReplies);

		return "/pages/user-article/content";
	}
}
