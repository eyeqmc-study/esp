package com.eyeq.esp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.eyeq.esp.model.User;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:27
 * @revision $LastChangedRevision: 5921 $
 * @date $LastChangedDate: 2013-02-04 00:57:36 +0900 (월, 04 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
@SessionAttributes(value = { "user" }, types = { User.class })
public class LoginController {

	@Autowired(required = false)
	private UserDetailsManager detailsManager;
	
	@Autowired
	private UserManager ownerManager;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginPageHandler(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "/pages/login";
	}

	@RequestMapping(value = { "/ldap/authentication" })
	public String ldapLoginHandler(@ModelAttribute("user") User user) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			LdapUserDetails ldapUserDetails = (LdapUserDetails) authentication
					.getPrincipal();

			InetOrgPerson inetOrgPerson = (InetOrgPerson) detailsManager
					.loadUserByUsername(ldapUserDetails.getUsername());
			user.setUid(inetOrgPerson.getUid());
			user.setName(inetOrgPerson.getCn()[0]);
			user.setEmail(inetOrgPerson.getMail());
			
			if (!userExsit(inetOrgPerson.getUid())) {
				ownerManager.createUser(user);
			}
		}

		return "redirect:/";
	}
	
	private Boolean userExsit(String uid) {
		User user = ownerManager.getUser(uid);
		return user != null;
	}

	@RequestMapping(value = { "/dummy/authentication" })
	public String dummyLoginHandler(@ModelAttribute("user") User user) {
		user.setUid("guest");
		user.setName("게스트");
		user.setEmail("guest@eyeq.co.kr");
		user.setRole("ROLE_GUEST");
		ownerManager.createUser(user);
		return "redirect:/";
	}
}
