package com.eyeq.esp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.eyeq.esp.system.config.SpringAppConfig;
import com.eyeq.esp.system.config.SpringWebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringAppConfig.class, SpringWebConfig.class })
@TransactionConfiguration
@Transactional
public class UserAuthenticationTest {

	private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager am;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.addFilters(this.springSecurityFilterChain).build();
	}

	@Test
	public void userAuthenticates() throws Exception {
		final String username = "guest";
		mockMvc.perform(
				post("/j_spring_security_check").param("j_username", username)
						.param("j_password", "1")).andExpect(
				new ResultMatcher() {
					public void match(MvcResult mvcResult) throws Exception {
						HttpSession session = mvcResult.getRequest()
								.getSession();
						SecurityContext securityContext = (SecurityContext) session
								.getAttribute(SEC_CONTEXT_ATTR);
						Assert.assertEquals(securityContext.getAuthentication()
								.getName(), username);
					}
				});
	}

	@Test
	public void userAuthenticateFails() throws Exception {
		final String username = "guest";
		mockMvc.perform(
				post("/j_spring_security_check").param("j_username", username)
						.param("j_password", "invalid")).andExpect(
				new ResultMatcher() {
					public void match(MvcResult mvcResult) throws Exception {
						HttpSession session = mvcResult.getRequest()
								.getSession();
						SecurityContext securityContext = (SecurityContext) session
								.getAttribute(SEC_CONTEXT_ATTR);
						Assert.assertNull(securityContext);
					}
				});
	}
}