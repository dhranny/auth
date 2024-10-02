package com.project.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.auth.services.LdapService;

@SpringBootApplication
public class AuthApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
