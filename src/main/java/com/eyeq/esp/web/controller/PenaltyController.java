package com.eyeq.esp.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * @author Samkwang Na
 * @since 0.3.1 2013. 2. 14. 오전 2:32:55
 * @revision $LastChangedRevision$
 * @date $LastChangedDate$
 * @by $LastChangedBy$
 */
@Controller
public class PenaltyController {
	Log log = LogFactory.getLog(getClass());
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
		return "/pages/admin";
	}

	@RequestMapping(value = "/penalty-list")
	public String penaltyListPageHandler(Model model, HttpSession httpSession) {
		return "/pages/penalty-list";
	}

	@RequestMapping(value = "/admin/penalty/add", method = { RequestMethod.POST })
	@ResponseBody
	public List<Penalty> penaltyAddHandler(Model model,
			HttpSession httpSession, @RequestParam(value = "id") Integer id,
			@RequestParam(value = "reason") String reason,
			@RequestParam(value = "score") Integer score) {
		Penalty penalty = new Penalty();
		penalty.setReason(reason);
		penalty.setScore(score);
		User user = this.userManager.getUser(id);
		user.addPenalty(penalty);
		userManager.updateUser(user);

		return this.userManager.getUser(id).getPenalties();
	}

	@RequestMapping(value = "/admin/penalty/edit", method = { RequestMethod.POST })
	@ResponseBody
	public List<Penalty> penaltyEditHandler(Model model,
			HttpSession httpSession, @RequestParam(value = "id") Integer id,
			@RequestParam(value = "reason") String reason,
			@RequestParam(value = "score") Integer score,
			@RequestParam(value = "itemid") Integer itemid) {
		Penalty penalty = penaltyManager.getPenalty(itemid);
		penalty.setReason(reason);
		penalty.setScore(score);
		penaltyManager.updatePenalty(penalty);

		return this.userManager.getUser(id).getPenalties();
	}

	@RequestMapping(value = "/admin/user-penalty", method = { RequestMethod.POST })
	@ResponseBody
	public List<Penalty> getUserPenalty(Model model, HttpSession httpSession,
			@RequestParam(value = "uid") Integer userId) {
		return this.userManager.getUser(userId).getPenalties();
	}
}
