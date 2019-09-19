package com.stroganova.movielandapp.web.interceptor;

import com.stroganova.movielandapp.entity.Role;

import java.util.Arrays;
import java.util.List;

public enum SecurityMapping {

    ADD_MOVIE_REVIEW("/review", "POST", new Role[]{Role.USER_ROLE, Role.ADMIN_ROLE}),
    ADD_MOVIE("/movie", "POST", new Role[]{Role.ADMIN_ROLE}),
    UPDATE_MOVIE("/movie/{id}", "PUT", new Role[]{Role.ADMIN_ROLE}),
    ALLOWED("*", "default", new Role[0]);

    private String mappingName;
    private String method;
    private List<Role> roles;


    SecurityMapping(String mappingName, String method, Role[] role) {
        this.mappingName = mappingName;
        this.method = method;
        this.roles = Arrays.asList(role);
    }


    public static SecurityMapping get(String mappingName, String method) {
        SecurityMapping[] values = values();
        for (SecurityMapping value : values) {
            if (value.getMappingName().equalsIgnoreCase(mappingName) && value.getMethod().equalsIgnoreCase(method)) {
                return value;
            }
        }
        return ALLOWED;
    }

    public String getMappingName() {
        return mappingName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getMethod() {
        return method;
    }
}
