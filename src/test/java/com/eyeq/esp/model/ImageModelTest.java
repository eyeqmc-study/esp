package com.eyeq.esp.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.eyeq.esp.service.ImageManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/esp-context.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class ImageModelTest {

	@Autowired
	private ImageManager imageManager;

	@Test
	public void testCreateImage() {
		Image img = new Image();
		img.setName("hanalee_" + System.currentTimeMillis() + ".png");
		img.setRealName("HanaLee.png");
		img.setSize(123123123L);

		imageManager.createImage(img);

		img.setUrl("/uploaded/images/" + img.getId());
		System.out.println(img.getId());
	}

}
