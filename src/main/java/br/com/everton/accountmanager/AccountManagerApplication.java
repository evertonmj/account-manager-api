package br.com.everton.accountmanager;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@RestController
@Tag(name="Endpoints")
public class AccountManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApplication.class, args);
	}
	
	@RequestMapping("/")
	@Hidden
	public String home() {
		return "Hello World!";
	}
	
	@Operation(summary = "Health check endpoint")
	@GetMapping(path="/health")
	public @ResponseBody String health() {
		return "It's working!" + new Date();
	}

}
