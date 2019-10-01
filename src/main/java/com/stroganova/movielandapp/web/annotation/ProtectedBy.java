package com.stroganova.movielandapp.web.annotation;


import com.stroganova.movielandapp.entity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProtectedBy {

    Role role();
}
