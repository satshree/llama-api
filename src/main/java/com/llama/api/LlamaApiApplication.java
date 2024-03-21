package com.llama.api;

import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LlamaApiApplication {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${llama.admin.username}")
    String adminUsername;

    @Value("${llama.admin.password}")
    String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(LlamaApiApplication.class, args);
    }

    public void start() {
        if (!userService.usernameExists("admin")) {
            UserDTO user = new UserDTO("", "", adminUsername, "");
            UserProfileDTO profile = new UserProfileDTO("", "");

            userService.addSuperUser(
                    user,
                    profile,
                    passwordEncoder.encode(adminPassword)
            );
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            this.start();
        };
    }
}
