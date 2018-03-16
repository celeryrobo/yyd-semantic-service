package com.yyd.service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@MapperScan("com.yyd.service.*.mapper")
public class SemanticServiceAutoConfigure {
	private static final Logger LOG = Logger.getLogger(SemanticServiceAutoConfigure.class.getSimpleName());
	
	@PostConstruct
	public void init() {
		LOG.info("=====================================");
		LOG.info("Semantic Service Auto Configure ...");
		LOG.info("=====================================");
	}
}
