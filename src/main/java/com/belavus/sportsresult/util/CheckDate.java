package com.belavus.sportsresult.util;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
public @interface CheckDate {

    public String message() default "date should be in yyyy/MM/dd";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    boolean optional() default false;


}
