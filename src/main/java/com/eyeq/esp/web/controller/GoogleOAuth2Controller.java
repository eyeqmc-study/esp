package com.eyeq.esp.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Hana Lee
 * @since 0.1.1 2013. 2. 5. 오전 3:25:35
 * @revision $LastChangedRevision: 5940 $
 * @date $LastChangedDate: 2013-02-05 07:15:54 +0900 (화, 05 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
@SessionAttributes(value = { "oAuthState", "access_token" }, types = {
		String.class, String.class })
public class GoogleOAuth2Controller {

	private final String CLIENT_ID = "734849919114.apps.googleusercontent.com";

	@RequestMapping(value = "/oauth2callback")
	public String googleOAuth2CallBackHandler(
			Model model,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "access_token", required = false) String accessToken,
			@RequestParam(value = "token_type", required = false) String tokenType,
			@RequestParam(value = "expires_in", required = false) String expiresIn,
			HttpSession session) {
		if (StringUtils.isNotBlank(state)) {
			model.addAttribute("oAuthState", state);
		}
		if (StringUtils.isNotBlank(accessToken)) {
			model.addAttribute("accessToken", accessToken);
		}
		return "redirect:/schedule";
	}

	@RequestMapping(value = "/oAuth2-not-use")
	public String googleOAuth2PageHandler() {
		StringBuffer sb = new StringBuffer();
		sb.append("https://accounts.google.com/o/oauth2/auth");
		sb.append("?scope=https://www.googleapis.com/auth/userinfo.email");
		sb.append("+https://www.googleapis.com/auth/userinfo.profile");
		sb.append("&state=/success");
		sb.append("&redirect_uri=http://localhost:8080/esp/oauth2callback");
		sb.append("&response_type=code");
		sb.append("&client_id=");
		sb.append(CLIENT_ID);
		sb.append("&approval_prompt=auto");
		return "redirect:" + sb.toString();
	}
}
