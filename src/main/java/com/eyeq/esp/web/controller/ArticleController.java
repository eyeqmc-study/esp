package com.eyeq.esp.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.eyeq.esp.exception.ArticleNotFoundException;
import com.eyeq.esp.exception.RoomNotFoundException;
import com.eyeq.esp.exception.UserNotAutorityException;
import com.eyeq.esp.exception.UserNotFoundException;
import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.ArticleReply;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.StudyRoomManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:13
 * @revision $LastChangedRevision: 5992 $
 * @date $LastChangedDate: 2013-02-10 00:13:19 +0900 (일, 10 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Controller
public class ArticleController {

	@Autowired
	private ArticleManager articleManager;

	@Autowired
	private UserManager userManager;

	@Autowired
	private StudyRoomManager studyRoomManager;

	@RequestMapping(value = "/study-article/edit-form", method = RequestMethod.GET)
	public String articleEditForm(
			Model model,
			HttpSession session,
			@RequestParam(value = "articleId", required = false) Integer articleId,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws UserNotFoundException {
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null || currentUser.getId() == null) {
			throw new UserNotFoundException();
		}

		if (articleId != null) {
			Article article = articleManager.getArticle(articleId);
			model.addAttribute("article", article);
		}

		model.addAttribute("studyRoomId", studyRoomId);
		return "/pages/study-article/edit-form";
	}

	@RequestMapping(value = "/study-article/edit-form-submit", method = RequestMethod.POST)
	public String articleEditSubmit(Model model,
			@ModelAttribute("Article") Article article, HttpSession session,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws UserNotFoundException, RoomNotFoundException,
			UserNotAutorityException {
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null || currentUser.getId() == null) {
			throw new UserNotFoundException();
		}

		StudyRoom room = studyRoomManager.getStudyRoom(studyRoomId);
		if (room == null || studyRoomId == null) {
			throw new RoomNotFoundException();
		}

		if (!currentUserArticleAccessControlCheck(article, currentUser)) {
			throw new UserNotAutorityException();
		}

		article.setOwner(currentUser);
		article.setStudyRoom(room);

		if (article.getId() == null) {
			articleManager.createArticle(article);
			return "redirect:/study-article/list?studyRoomId=" + studyRoomId;
		} else {
			articleManager.updateArticle(article);
			return "redirect:/study-article/content?articleId="
					+ article.getId() + "&studyRoomId=" + studyRoomId;
		}
	}

	@RequestMapping(value = "/study-article/delete", method = RequestMethod.GET)
	public String articleDelete(Model model, HttpSession session,
			@RequestParam("articleId") Integer articleId,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws UserNotAutorityException, UserNotFoundException {
		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null || currentUser.getId() == null) {
			throw new UserNotFoundException();
		}

		Article article = articleManager.getArticle(articleId);

		if (!currentUserArticleAccessControlCheck(article, currentUser)) {
			throw new UserNotAutorityException();
		}

		articleManager.deleteArticle(article);
		return "redirect:/study-article/list?studyRoomId=" + studyRoomId;
	}

	@RequestMapping(value = "/study-article/content", method = RequestMethod.GET)
	public String getArticleContent(Model model,
			@RequestParam(value = "articleId") Integer articleId,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws ArticleNotFoundException {
		Article article = articleManager.getArticle(articleId);

		if (article == null) {
			throw new ArticleNotFoundException();

		}
		model.addAttribute("article", article);
		model.addAttribute("studyRoomId", studyRoomId);

		List<ArticleReply> articleReplies = articleManager
				.getEnabledArticleReplies(article.getId());

		model.addAttribute("articleReplies", articleReplies);

		return "/pages/study-article/content";
	}

	@RequestMapping(value = "/study-article/list", method = RequestMethod.GET)
	public String getArticles(
			Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "listCount", defaultValue = "5") int listCount,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws RoomNotFoundException {

		StudyRoom room = studyRoomManager.getStudyRoom(studyRoomId);
		if (room == null) {
			throw new RoomNotFoundException();
		}

		int startNum = page * listCount;
		List<Article> articles = articleManager.getEnabledArticles(studyRoomId);

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

		model.addAttribute("pageCount", pageCount);
		model.addAttribute("articles", currentArticles);
		model.addAttribute("studyRoom", room);
		model.addAttribute("studyRoomId", studyRoomId);

		return "/pages/study-article/list";
	}

	@RequestMapping(value = "/study-article-reply/edit-form-submit", method = RequestMethod.POST)
	public String articleReplyEditSubmit(
			Model model,
			HttpSession session,
			@ModelAttribute("articleReply") ArticleReply reply,
			@RequestParam(value = "articleId", required = false) Integer articleId,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws ArticleNotFoundException, UserNotFoundException {
		User currentUser = (User) session.getAttribute("user");
		if (currentUser == null) {
			throw new UserNotFoundException();
		}

		Article article = articleManager.getArticle(articleId);

		if (article == null) {
			throw new ArticleNotFoundException();
		}

		reply.setArticle(article);
		reply.setOwner(currentUser);
		if (reply.getArticleReplyId() == null) {
			articleManager.createArticleReply(reply);
		} else {
			articleManager.updateArticleReply(reply);
		}

		return "redirect:/study-article/content?articleId=" + articleId
				+ "&studyRoomId=" + studyRoomId;
	}

	@RequestMapping(value = "/study-article-reply/delete", method = RequestMethod.GET)
	public String articleReplyDelete(Model model, HttpSession session,
			@RequestParam("articleReplyId") Integer articleReplyId,
			@RequestParam("articleId") Integer articleId,
			@RequestParam(value = "studyRoomId") Integer studyRoomId)
			throws UserNotAutorityException, UserNotFoundException {

		User currentUser = (User) session.getAttribute("user");

		if (currentUser == null || currentUser.getId() == null) {
			throw new UserNotFoundException();
		}

		ArticleReply reply = articleManager.getArticleReply(articleReplyId);
		if (!currentUserArticleReplyAccessControlCheck(reply, currentUser)) {
			throw new UserNotAutorityException();
		}

		articleManager.deleteArticleReply(reply);

		return "redirect:/study-article/content?articleId=" + articleId
				+ "&studyRoomId=" + studyRoomId;
	}

	/*
	 * @ExceptionHandler(UserNotFoundException.class)
	 * 
	 * @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	 * 
	 * @ResponseBody public String
	 * handleUserNotFoundException(UserNotFoundException ex) { return
	 * "UserNotFoundException"; }
	 */

	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(UserNotFoundException ex) {
		return "/pages/study-article/error-page";
	}

	@ExceptionHandler(ArticleNotFoundException.class)
	public String handleArticleNotFoundException(ArticleNotFoundException ex) {
		return "/pages/study-article/error-page";
	}

	@ExceptionHandler(RoomNotFoundException.class)
	public String handleRoomNotFoundException(RoomNotFoundException ex) {
		return "/pages/study-article/error-page";
	}

	@ExceptionHandler(UserNotAutorityException.class)
	public String handleUserNotAutorityException(UserNotAutorityException ex) {
		return "/pages/study-article/error-page";
	}

	protected boolean currentUserArticleAccessControlCheck(Article article,
			User currentUser) {
		Integer articleId = article.getId();
		if (articleId != null) {
			Article previousArticle = articleManager.getArticle(articleId);
			User articleOwner = previousArticle.getOwner();
			if (articleOwner != null && currentUser != null) {
				String articleOwnerUId = articleOwner.getUid();
				String currentUserUId = currentUser.getUid();
				if (!articleOwnerUId.equals(currentUserUId)) {
					// TODO
					return false;
				}
			}
		}
		return true;
	}

	protected boolean currentUserArticleReplyAccessControlCheck(
			ArticleReply reply, User currentUser) {
		Integer replyId = reply.getArticleReplyId();
		if (replyId != null) {
			ArticleReply previousArticleReply = articleManager
					.getArticleReply(replyId);
			User replyOwner = previousArticleReply.getOwner();
			if (replyOwner != null && currentUser != null) {
				String articleOwnerUId = replyOwner.getUid();
				String currentUserUId = currentUser.getUid();
				if (!articleOwnerUId.equals(currentUserUId)) {
					// TODO
					return false;
				}
			}
		}
		return true;
	}
}
