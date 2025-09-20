package com.security.sga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SgaApplication{

	public static void main(String[] args) {
		SpringApplication.run(SgaApplication.class, args);
	}

	//Implement CommnadLineRunner para correr algun metodo al iniciar por consola
	
	// Logger log = LoggerFactory.getLogger(SgaApplication.class);
	
	// @Override
	// public void run(String... args) throws Exception {
	// 	log.info("Estos es Log Info");
	// 	log.warn("Esto es una Advertencia");
	// 	log.error("Aqui hubo un error");	
	
	// }

}
