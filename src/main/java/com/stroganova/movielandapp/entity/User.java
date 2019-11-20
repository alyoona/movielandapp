package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.view.View;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class User {
    @JsonView(View.MovieDetail.class)
    private long id;
    @JsonView(View.MovieDetail.class)
    private String nickname;
    private String email;
    private String password;
    private Role role;

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.nickname = userBuilder.nickname;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.role = userBuilder.role;
    }

    public static class UserBuilder {
        private long id;
        private String nickname;
        private String email;
        private String password;
        private Role role;


        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
