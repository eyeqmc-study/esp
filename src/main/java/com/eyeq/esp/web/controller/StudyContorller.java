package com.eyeq.esp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.StudyRoomManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:40
 * @revision $LastChangedRevision: 5913 $
 * @date $LastChangedDate: 2013-02-03 01:29:19 +0900 (일, 03 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
public class StudyContorller {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StudyRoomManager roomManager;

	@InitBinder
	public void initDateBinder(WebDataBinder dataBinder, Locale locale) {
		String dateformat = this.messageSource.getMessage("date.format", null,
				locale);
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		sdf.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,
				false));
	}

	@RequestMapping(value = { "/study/reg" }, method = { RequestMethod.POST })
	public String createHandler(
			@ModelAttribute("studyRoom") StudyRoom studyRoom,
			HttpSession session) {
		User owner = (User) session.getAttribute("user");
		Image image = (Image) session.getAttribute("image");
		studyRoom.setStudyImage(image);
		studyRoom.setOwner(owner);
		studyRoom.addMember(owner);
		studyRoom.setEnabled(true);

		roomManager.updateStudyRoom(studyRoom);
		session.removeAttribute("image");
		return "redirect:/";
	}
}
