package com.eyeq.esp.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eyeq.esp.exception.RoomNotFoundException;
import com.eyeq.esp.exception.UserNotFoundException;
import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.Place;
import com.eyeq.esp.model.StudyRoom;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.PlaceManager;
import com.eyeq.esp.service.StudyRoomManager;
import com.eyeq.esp.service.UserManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:40
 * @revision $LastChangedRevision: 6044 $
 * @date $LastChangedDate: 2013-02-15 11:33:19 +0900 (금, 15 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
public class StudyContorller {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StudyRoomManager roomManager;

	@Autowired
	private UserManager userManager;

	@Autowired
	private PlaceManager placeManager;

	@InitBinder
	public void initDateBinder(WebDataBinder dataBinder, Locale locale) {
		String dateformat = this.messageSource.getMessage("date.format", null,
				locale);
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		sdf.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,
				false));
	}

	@RequestMapping(value = "/study")
	public String studyPageHandler(Model model, HttpSession httpSession,
			@RequestParam("studyRoomId") Integer studyRoomId)
			throws RoomNotFoundException, UserNotFoundException {
		/*
		 * User user = (User) httpSession.getAttribute("user"); if (user ==
		 * null) { throw new UserNotFoundException(); }
		 */

		StudyRoom room = roomManager.getStudyRoom(studyRoomId);
		/*
		 * if (room == null) { throw new RoomNotFoundException(); }
		 */

		if (studyRoomId == null) {
			studyRoomId = 1;
		}
		model.addAttribute("studyRoom", room);
		model.addAttribute("studyRoomId", studyRoomId);
		return "/pages/study-main";
	}

	@RequestMapping(value = "/study/reg")
	public String studyRegPageHandler(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		model.addAttribute("currentUser", user);
		return "/pages/study-reg";
	}

	@RequestMapping(value = { "/study/add" }, method = { RequestMethod.POST })
	public String createHandler(
			@ModelAttribute("studyRoom") StudyRoom studyRoom,
			HttpSession session) {
		User owner = (User) session.getAttribute("user");
		Image image = (Image) session.getAttribute("image");
		Place place = (Place) session.getAttribute("studyPlace");

		List<StudyRoom> rooms = roomManager.getStudyRooms();
		Boolean existEnabledRoom = false;
		if (rooms != null) {
			for (StudyRoom room : rooms) {
				if (room.getEnabled()) {
					existEnabledRoom = true;
					break;
				}
			}
		}

		studyRoom.setStudyImage(image);
		studyRoom.setStudyPlace(place);
		studyRoom.setOwner(owner);
		studyRoom.addMember(owner);
		if (existEnabledRoom) {
			studyRoom.setEnabled(false);
		} else {
			studyRoom.setEnabled(true);
		}

		roomManager.updateStudyRoom(studyRoom);
		session.removeAttribute("image");
		session.removeAttribute("studyPlace");
		return "redirect:/";
	}

	@RequestMapping(value = "/study/place")
	public String studyPlacePageHandler(Model model, HttpSession session,
			@RequestParam("studyRoomId") Integer studyRoomId) {
		StudyRoom room = roomManager.getStudyRoom(studyRoomId);
		if (room != null) {
			model.addAttribute("studyRoom", room);
		}
		model.addAttribute("studyRoomId", studyRoomId);
		return "/pages/study-place";
	}

	@RequestMapping(value = "/study/place/add", method = { RequestMethod.POST })
	@ResponseBody
	public String studyPlaceAddHandler(Model model, HttpServletRequest request,
			HttpSession session) {
		String placeName = request.getParameter("placeName");
		String longitude = request.getParameter("placeLongitude");
		String latitude = request.getParameter("placeLatitude");
		String studyAddr = request.getParameter("placeAddr");

		Place studyPlace = new Place();
		studyPlace.setName(placeName);
		studyPlace.setLatitude(Double.parseDouble(latitude));
		studyPlace.setLongitude(Double.parseDouble(longitude));
		studyPlace.setAddress(studyAddr);
		placeManager.createPlace(studyPlace);
		session.setAttribute("studyPlace", studyPlace);

		return "success";
	}

	@RequestMapping(value = "/study/join", method = { RequestMethod.POST })
	@ResponseBody
	public String studyJoinsHandler(Model model, HttpServletRequest request,
			HttpSession httpSession) {
		String uId = request.getParameter("uid");
		String studyId = request.getParameter("studyId");
		Integer sId = Integer.parseInt(studyId);
		StudyRoom studyRoom = roomManager.getStudyRoom(sId);
		User member = (User) httpSession.getAttribute("user");
		if (member == null) {
			member = userManager.getUser(uId);
		}

		if (studyRoom != null && member != null) {
			Set<User> members = studyRoom.getMembers();
			for (User mem : members) {
				if (mem.getUid().equals(uId)) {
					return "already";
				}
			}
			studyRoom.addMember(member);
			member.addStudyRoom(studyRoom);
			roomManager.updateStudyRoom(studyRoom);
			return "success";
		}
		return "error";
	}
}
