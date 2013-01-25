package com.eyeq.esp.web.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:33
 * @revision $LastChangedRevision: 5854 $
 * @date $LastChangedDate: 2013-01-26 02:17:59 +0900 (토, 26 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
public class MainController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ArticleManager articleManager;

	@Autowired
	private UserManager ownerManager;

	@ModelAttribute("allArticles")
	public List<Article> populateArticles() {
		return this.articleManager.getArticles();
	}

	@InitBinder
	public void initDateBinder(WebDataBinder dataBinder, Locale locale) {
		String dateformat = this.messageSource.getMessage("date.format", null,
				locale);
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		sdf.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,
				false));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomeHandler(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			model.addAttribute("currentUser", user);
		} else {
			model.addAttribute("currentUser", new User(null, null, null, null,
					true, "이름", "이메일", null, null, 0, null, null));
		}
		return "main";
	}

	@RequestMapping(value = "/editor")
	public String editorPageHandler(Model model) {
		return "/pages/editor";
	}

	@RequestMapping(value = "/admin")
	public String adminPageHandler(Model model) {
		return "/pages/admin";
	}
}
