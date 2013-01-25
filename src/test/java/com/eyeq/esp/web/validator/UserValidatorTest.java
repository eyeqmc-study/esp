package com.eyeq.esp.web.validator;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.regex.Pattern;

import org.junit.Test;

import com.eyeq.esp.utils.PatternUtils;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:17:02
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public class UserValidatorTest {

	@Test
	public void emailPattern() {
		String[] emailStrArray = { "a1a23abc.cde12@gmail.com",
				"123.abc.123@123eyeq.co.kr", "123abc@eyeq.com" };

		String[] nagativeEmailStrArray = { "123.abc", "abc", ".b",
				"@a@eyeq.co.kr", "email@", "email@localhost", "이메일@gmail.com",
				"하나21@eyeq.co.kr", "21#h.b@eyeq.co.kr" };

		Pattern pattern = Pattern.compile(PatternUtils.EMAIL);
		for (String emailStr : emailStrArray) {
			assertThat(pattern.matcher(emailStr).matches(), is(true));
		}

		for (String nagativeEmailStr : nagativeEmailStrArray) {
			assertThat(pattern.matcher(nagativeEmailStr).matches(),
					is(not(true)));
		}
	}
}
