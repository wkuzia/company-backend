package com.czarny.company_backend;

import org.springframework.boot.SpringApplication;

public class TestCompanyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(CompanyBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
