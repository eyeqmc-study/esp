package com.eyeq.esp.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eyeq.esp.model.Penalty;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.PenaltyManager;
import com.eyeq.esp.service.UserManager;

@Controller
public class PenaltyController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private PenaltyManager penaltyManager;

	@ModelAttribute(value = "allUserList")
	public List<User> getAllUserList() {
		return this.userManager.getUsers();
	}

	@ModelAttribute("user")
	public User getUser() {
		User user = new User();
		return user;
	}

	@RequestMapping(value = "/admin")
	public String adminPageHandler(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		int penaltyScore = getPenaltyScore(user);

		model.addAttribute("userPenaltyScore", penaltyScore);
		return "/pages/admin";
	}

	@RequestMapping(value = "/admin/penalty/update", method = { RequestMethod.POST })
	@ResponseBody
	public Integer penaltyScoreHandler(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		int penaltyScore = getPenaltyScore(user);
		return penaltyScore;
	}

	@RequestMapping(value = "/admin/user-penalty", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<User> getUserPenalty(Model model, HttpSession httpSession,
			@RequestParam(value = "uid") Integer userId) {
		return this.userManager.getUsers();
	}

	private Integer getPenaltyScore(User user) {
		int penaltyScore = 0;
		Set<Penalty> penalties = user.getPenalties();
		for (Penalty penalty : penalties) {
			penaltyScore += penalty.getScore();
		}
		return penaltyScore;
	}
}
