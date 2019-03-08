package com.ke.seckill.validator;
import  javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ke.seckill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;


public class MobileValidator implements ConstraintValidator<Mobile, String> {

	private boolean required = false;
	
	@Override
	public void initialize(Mobile constraintAnnotation) {
		required = constraintAnnotation.required();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required) {
			return ValidatorUtil.isMobile(value);
		}else {
			if(StringUtils.isEmpty(value)) {
				return true;
			}else {
				return ValidatorUtil.isMobile(value);
			}
		}
	}

}
