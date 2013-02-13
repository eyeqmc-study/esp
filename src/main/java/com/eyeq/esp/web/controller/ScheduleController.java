package com.eyeq.esp.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eyeq.esp.model.User;

/**
 * @author Hana Lee
 * @since 0.2.1 2013. 2. 8. 오전 3:19:09
 * @revision $LastChangedRevision$
 * @date $LastChangedDate$
 * @by $LastChangedBy$
 */
@Controller
public class ScheduleController {

	@RequestMapping(value = "/schedule")
	public String schedulePageHandler(Model model, HttpSession httpSession)
			throws UnsupportedEncodingException {
		User user = (User) httpSession.getAttribute("user");
		String state = (String) httpSession.getAttribute("oAuthState");
		String token = (String) httpSession.getAttribute("accessToken");
		model.addAttribute("currentUser", user);
		model.addAttribute("oAuthState", state);
		model.addAttribute("accessToken", token);
		return "/pages/schedule";
	}
}
