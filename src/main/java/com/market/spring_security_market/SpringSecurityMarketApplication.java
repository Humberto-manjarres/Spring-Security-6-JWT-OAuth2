package com.market.spring_security_market;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityMarketApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner createPasswordsCommand(PasswordEncoder passwordEncoder){
		return (args) -> {
			System.out.println(passwordEncoder.encode("clave123"));
			System.out.println(passwordEncoder.encode("clave456"));
			System.out.println(passwordEncoder.encode("clave789"));
		};
	}*/

}
