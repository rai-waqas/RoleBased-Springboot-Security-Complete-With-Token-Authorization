package com.Database.Project;

import com.Database.Project.Model.Role;
import com.Database.Project.Model.User;
import com.Database.Project.Repository.RoleRepository;
import com.Database.Project.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.Database.Project")
@EnableJpaRepositories(basePackages = "com.Database.Project.Repository")
//@ComponentScan("com.Database.Project")
public class Application{


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



}