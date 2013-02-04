package com.eyeq.esp.etc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class TestSystemEnv {

	@Test
	public void test() {
		System.out.println(System.getenv("ESP_HOME"));
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("file.separator"));
	}
	
	@Test
	public void test2() throws IOException, URISyntaxException {
		String home = System.getProperty("user.home") + System.getProperty("file.separator") + ".esp" + System.getProperty("file.separator");
		System.out.println(home);
		home = StringUtils.cleanPath(home);
		System.out.println(home);
		URI uri = new URI("file:/");
		Path path = Paths.get(uri);
		System.out.println(Files.isWritable(path));
		System.out.println(Files.isExecutable(path));
		Files.createDirectory(path);
	}

}
