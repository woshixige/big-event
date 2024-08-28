package com.zzx.anno;

import com.zzx.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author zzx
 * @version 1.0
 * @date 2024-08-09 9:12
 */
@Documented
@Target( ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})

public @interface State {
    //校验参数不合法的提示信息
    String message() default "{state参数只能是已发布或者草稿}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
