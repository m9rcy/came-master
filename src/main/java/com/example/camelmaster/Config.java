package com.example.camelmaster;

import org.apache.camel.CamelContext;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    private ApplicationContext appContext;

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                var kubernetesClusterService = appContext.getBean("kubernetes-cluster-service", KubernetesClusterService.class);

                try {
                    doAddService(camelContext, kubernetesClusterService);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {

            }
        };
    }

    private void doAddService(CamelContext context, KubernetesClusterService kubernetesClusterService) throws Exception {
        var services = context.hasServices(CamelClusterService.class);
        // services.stream().filter(s-> s instanceof CamelClusterService).collect(Collectors.toList()).size()
        if (services.isEmpty() && kubernetesClusterService != null) {
            context.addService(kubernetesClusterService);
        }
    }
}
