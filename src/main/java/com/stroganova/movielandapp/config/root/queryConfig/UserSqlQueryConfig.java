package com.stroganova.movielandapp.config.root.queryConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSqlQueryConfig {
    @Bean
    public String getUserByEmailAndPasswordSql() {
        return "SELECT u.id, u.email, u.password, u.first_name, u.last_name, r.name role_name" +
                "        FROM movieland.users u" +
                "        LEFT JOIN movieland.user_roles ur ON u.id = ur.user_id" +
                "        JOIN movieland.roles r ON ur.role_id = r.id" +
                "        WHERE u.email = :email" +
                "        AND u.password = :password;";
    }

}
