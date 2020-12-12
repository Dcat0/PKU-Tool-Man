package edu.pku.PKUToolMan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.pku.PKUToolMan")
public class PkuToolManApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkuToolManApplication.class, args);
	}

}
