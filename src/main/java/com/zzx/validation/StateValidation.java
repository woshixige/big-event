package com.zzx.validation;

import com.zzx.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-09 9:18
 */

public class StateValidation implements ConstraintValidator<State, String> {//注解名称，参数的使用类型

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //值为空，不合法
        if (value == null) {
            return false;
        }
        if ("已发布".equals(value) || "草稿".equals(value)) {
            return true;
        }
        return false;
    }
}
