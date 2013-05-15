package com.eyeq.esp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.eyeq.esp.model.Article;
import com.eyeq.esp.model.User;
import com.eyeq.esp.system.config.SpringAppConfig;
import com.eyeq.esp.system.config.SpringWebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringAppConfig.class, SpringWebConfig.class })
@TransactionConfiguration
@Transactional
public class ArticleControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void editFormTest() throws Exception {
		User user = new User();
		user.setUid("guest");
		user.setName("게스트");
		user.setEmail("voyaging@eyeq.co.kr");
		user.setRole("ROLE_ADMIN,ROLE_USER");
		Article article = new Article();
		this.mockMvc
				.perform(
						get("/study-article/edit-form?studyRoomId=4")
								.sessionAttr("user", user).sessionAttr(
										"article", article))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("studyRoom"));

		// result.getRequest().getAttribute("studyRoom");
	}

	@Test
	public void editFormSubmitTest() throws Exception {
		User user = new User();
		user.setUid("guest");
		user.setName("게스트");
		user.setEmail("voyaging@eyeq.co.kr");
		user.setRole("ROLE_ADMIN,ROLE_USER");

		Article article = new Article();
		article.setTitle("정묵테스트");
		article.setContent("과연 나올까");

		this.mockMvc
				.perform(
						post("/study-article/edit-form-submit?studyRoomId=4")
								.sessionAttr("user", user).sessionAttr(
										"article", article))
				.andExpect(status().isOk())
				.andExpect(redirectedUrl("study-article/list?studyRoomId=4"));
	}
}
