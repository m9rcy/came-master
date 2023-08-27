package com.example.camelmaster;

import org.apache.camel.component.kubernetes.springboot.cluster.KubernetesClusterServiceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication(exclude = KubernetesClusterServiceAutoConfiguration.class)
@SpringBootApplication()
public class CamelMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelMasterApplication.class, args);
	}

}
