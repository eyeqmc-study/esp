package com.eyeq.esp.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.eyeq.esp.model.Image;
import com.eyeq.esp.model.User;
import com.eyeq.esp.service.ImageManager;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:20
 * @revision $LastChangedRevision: 5974 $
 * @date $LastChangedDate: 2013-02-08 03:37:09 +0900 (금, 08 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Controller
@SessionAttributes(value = { "image" }, types = { Image.class })
public class ImageController {

	private final String FILE_SEPARATOR = System.getProperty("file.separator");

	@Autowired
	private ImageManager imageManager;

	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public @ResponseBody
	Object uploadHandler(Model model, @RequestParam("file") MultipartFile file,
			HttpSession httpSession) throws IllegalStateException, IOException {
		String originalFilename = file.getOriginalFilename();
		String storeFileName = getStoreFileName(originalFilename);
		String fileStorePath = getFileStorePath();

		file.transferTo(new File(fileStorePath + storeFileName));

		Image image = new Image();
		image.setRealName(originalFilename);
		image.setName(storeFileName);
		image.setSize(file.getSize());
		image.setPath(fileStorePath);
		image.setContentType(file.getContentType());

		User owner = (User) httpSession.getAttribute("user");
		image.setOwner(owner);
		imageManager.createImage(image);

		image.setUrl(getImageUrl(image));
		imageManager.updateImage(image);

		model.addAttribute("image", image);

		List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
		Map<String, Object> fileInfo = new HashMap<String, Object>();
		fileInfo.put("name", image.getRealName());
		fileInfo.put("size", image.getSize());
		fileInfo.put("url", image.getUrl());
		fileInfo.put("thumbnail_url", image.getUrl());
		files.add(fileInfo);
		Map<String, List<Map<String, Object>>> info = new HashMap<String, List<Map<String, Object>>>();
		info.put("files", files);
		return info;
	}

	@RequestMapping(value = "/image/uploaded", method = RequestMethod.GET)
	@ResponseBody
	public void imageShowHandler(@RequestParam("imageId") Integer imageId,
			HttpServletResponse response) throws IOException {
		Image image = imageManager.getImage(imageId);
		String path = image.getPath() + image.getName();
		InputStream in = new FileInputStream(path);
		response.setContentType(image.getContentType());
		IOUtils.copyLarge(in, response.getOutputStream());
	}

	protected String getImageUrl(Image image) {
		StringBuffer sb = new StringBuffer();
		sb.append("/esp/image/uploaded/");
		sb.append("?");
		sb.append("imageId");
		sb.append("=");
		sb.append(image.getId());
		return sb.toString();
	}

	protected String getFileStorePath() {
		String espHomePath = System.getProperty("esp.home");
		StringBuffer filePath = new StringBuffer();
		filePath.append(espHomePath);
		filePath.append("upload");
		filePath.append(FILE_SEPARATOR);
		filePath.append("images");
		filePath.append(FILE_SEPARATOR);
		return filePath.toString();
	}

	protected String getStoreFileName(String originalFilename) {
		if (StringUtils.isNotBlank(originalFilename)) {
			String fileType = StringUtils.substringAfterLast(originalFilename,
					".");
			String fileName = StringUtils.substringBeforeLast(originalFilename,
					".");
			StringBuffer storeFile = new StringBuffer();
			storeFile.append(fileName);
			storeFile.append("_");
			storeFile.append(System.currentTimeMillis());
			storeFile.append(".");
			storeFile.append(fileType);
			return storeFile.toString();
		}
		return null;
	}
}
