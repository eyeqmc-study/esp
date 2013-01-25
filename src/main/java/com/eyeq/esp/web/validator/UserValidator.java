package com.eyeq.esp.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import com.eyeq.esp.model.User;
import com.eyeq.esp.utils.PatternUtils;

/**
 * @author Hana Lee
 * @since 0.0.2 2013. 1. 21. 오전 7:16:46
 * @revision $LastChangedRevision: 5808 $
 * @date $LastChangedDate: 2013-01-21 07:20:31 +0900 (월, 21 1월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public class UserValidator {

	/**
	 * @param owner
	 * @param errors
	 */
	public void validate(User owner, Errors errors) {
		if (StringUtils.isBlank(owner.getName())) {
			errors.rejectValue("name", "msg.error.required", "is required");
		}

		if (StringUtils.isBlank(owner.getPassword())) {
			errors.rejectValue("password", "msg.error.required", "is required");
		}

		String email = owner.getEmail();
		if (StringUtils.isBlank(email)) {
			errors.rejectValue("email", "msg.error.required", "is required");
		} else {
			Pattern pattern = Pattern.compile(PatternUtils.EMAIL);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				errors.rejectValue("email", "msg.error.unmatched",
						"is unmatched");
			}
		}

	}

}
