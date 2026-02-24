package com.hackers.freelancia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * class général.
 *
 * @author : <A HREF="mailto:karambiriarnold@gmail.com">Karambiri Lawatan Arnold Bily</A>
 * @version : 1.0
 * Copyright (c) 2021 All rights reserved.
 * @since : 19/01/2026 à 13:51
 */
@EnableJpaAuditing
@SpringBootApplication
public class FreelanciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelanciaApplication.class, args);
	}

}

