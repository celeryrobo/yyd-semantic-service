package com.yyd.service;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@MapperScan("com.yyd.service.*.mapper")
public class SemanticServiceAutoConfigure {
	@PostConstruct
	public void init() {
		System.out.println("=====================================");
		System.out.println("Semantic Service Auto Configure ...");
		System.out.println("=====================================");
	}
}
