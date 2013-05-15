package com.eyeq.esp.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.eyeq.esp.model.ArticleReply;

public class ArticleReplyValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		if (ArticleReply.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "content", "required");
	}

}
