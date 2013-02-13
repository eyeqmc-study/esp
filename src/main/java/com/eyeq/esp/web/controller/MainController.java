package com.eyeq.esp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ArticleManager;
import com.eyeq.esp.service.StudyRoomManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:33
 * @revision $LastChangedRevision: 5997 $
 * @date $LastChangedDate: 2013-02-12 01:11:31 +0900 (화, 12 2월 2013) $
 * @by $LastChangedBy: samkwang.na $
 */
@Controller
public class MainController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ArticleManager articleManager;

	@Autowired
	private UserManager ownerManager;

	@Autowired
	private StudyRoomManager roomManager;

	@ModelAttribute("allStudyRooms")
	public List<StudyRoom> populateStudyRooms() {
		return this.roomManager.getStudyRooms();
	}

	@ModelAttribute("enabledStudyRoom")
	public StudyRoom enabledStudyRoom() {
		List<StudyRoom> rooms = this.roomManager.getStudyRooms();
		StudyRoom studyRoom = null;
		if (rooms != null) {
			for (StudyRoom room : rooms) {
				if (room.getEnabled()) {
					studyRoom = room;
					break;
				}
			}
			if (studyRoom == null) {
				studyRoom = new StudyRoom();
			}
		}

		return studyRoom;
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

	@RequestMapping(value = "/")
	public String welcomeHandler(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		model.addAttribute("currentUser", user);
		return "main";
	}

}
