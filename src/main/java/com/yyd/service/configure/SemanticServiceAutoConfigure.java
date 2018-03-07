package com.yyd.service.configure;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.yyd.service" })
public class SemanticServiceAutoConfigure {
	@PostConstruct
	public void init() {
		System.out.println("=====================================");
		System.out.println("Semantic Service Auto Configure ...");
		System.out.println("=====================================");
	}
}
