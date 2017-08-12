package hellow;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ApplicationStart {
	static Logger log = Logger.getLogger(ApplicationStart.class.getName());
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		log.info("Starting application");
		SpringApplication.run(ApplicationStart.class, args);
		log.info("Application Started ......");
		log.info("Listening to Messages .......");
	}

}