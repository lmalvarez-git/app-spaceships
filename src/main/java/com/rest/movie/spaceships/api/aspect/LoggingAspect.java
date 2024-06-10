package com.rest.movie.spaceships.api.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Log4j2
public class LoggingAspect {
	
	
	 @After("execution(* com.rest.movie.spaceships.api.controller.SpaceshipController.getSpaceshipById(..)) && args(id)")
	    public void logNegativeId(Long id) {
	        if (id < 0) {
	            log.error("Requested spaceship with negative ID: {}", id);
	        }
	    }

}
