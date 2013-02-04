package com.eyeq.esp.system;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author Hana Lee
 * @since 0.1.1 2013. 2. 2. 오후 3:20:29
 * @revision $LastChangedRevision: 5912 $
 * @date $LastChangedDate: 2013-02-03 01:27:27 +0900 (일, 03 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
@Service(value = "systemSetUp")
@Order(value = 0)
public class SystemSetUp {

	private final String ESP_HOME_ENV = "esp_home";

	private final String FILE_SEPARATOR = System.getProperty("file.separator");

	private String espHomeDir;

	private Log log = LogFactory.getLog(SystemSetUp.class);

	@PostConstruct
	public void initialize() {
		if (log.isInfoEnabled()) {
			log.info("ESP System 초기화 시작");
		}

		makeHomeResourcesDirectories();

		if (StringUtils.isNotBlank(espHomeDir)) {
			System.setProperty("esp.home", espHomeDir);
		}

		if (log.isInfoEnabled()) {
			log.info("ESP System 초기화 완료");
		}
	}

	protected void makeHomeResourcesDirectories() {
		espHomeDir = System.getenv(ESP_HOME_ENV);
		if (log.isInfoEnabled()) {
			log.info("esp_home 설정이 되어 있지 않아 기본 설정으로 처리 합니다.");
		}
		if (StringUtils.isBlank(espHomeDir)) {
			StringBuffer sb = new StringBuffer();
			sb.append(System.getProperty("user.home"));
			sb.append(FILE_SEPARATOR);
			sb.append(".esp");
			sb.append(FILE_SEPARATOR);

			espHomeDir = sb.toString();
		}

		File homeDir = new File(espHomeDir);
		if (!homeDir.exists()) {
			homeDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("esp home 디렉토리 생성 : " + espHomeDir);
			}
		}

		makeUploadedImagesDir();
		makeUploadedFilesDir();
		makeTempDir();
	}

	private void makeTempDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(espHomeDir);
		sb.append("temp");
		sb.append(FILE_SEPARATOR);

		File tempDir = new File(sb.toString());
		if (!tempDir.exists()) {
			tempDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Temp 디렉토리 생성 : " + sb.toString());
			}
		}
	}

	private void makeUploadedImagesDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(espHomeDir);
		sb.append("upload");
		sb.append(FILE_SEPARATOR);
		sb.append("images");
		sb.append(FILE_SEPARATOR);
		
		File imagesDir = new File(sb.toString());
		if (!imagesDir.exists()) {
			imagesDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Images upload 디렉토리 생성 : " + sb.toString());
			}
		}
	}

	private void makeUploadedFilesDir() {
		StringBuffer sb = new StringBuffer();
		sb.append(espHomeDir);
		sb.append("upload");
		sb.append(FILE_SEPARATOR);
		sb.append("files");
		sb.append(FILE_SEPARATOR);

		File filesDir = new File(sb.toString());
		if (!filesDir.exists()) {
			filesDir.mkdirs();
			if (log.isDebugEnabled()) {
				log.debug("Files upload 디렉토리 생성 : " + sb.toString());
			}
		}
	}

}
