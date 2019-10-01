package com.stroganova.movielandapp.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Role {
    GUEST_ROLE("GUEST_ROLE") {
        @Override
        public List<Role> getIncludedRights() {
            return Collections.singletonList(GUEST_ROLE);
        }
    },
    USER_ROLE("USER_ROLE") {
        @Override
        public List<Role> getIncludedRights() {
            return Arrays.asList(GUEST_ROLE, USER_ROLE);
        }
    },
    ADMIN_ROLE("ADMIN_ROLE") {
        @Override
        public List<Role> getIncludedRights() {
            return Arrays.asList(GUEST_ROLE, USER_ROLE, ADMIN_ROLE);
        }
    };

    private String name;

    Role(String name) {
        this.name = name;
    }

    public static Role getByName(String name) {
        Role[] roles = values();
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role for name: " + name);
    }

    public String getName() {
        return name;
    }

    public abstract List<Role> getIncludedRights();


}
