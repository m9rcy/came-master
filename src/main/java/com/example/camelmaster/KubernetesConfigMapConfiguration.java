package com.example.camelmaster;

import org.apache.camel.CamelContext;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;
import org.apache.camel.component.kubernetes.cluster.LeaseResourceType;
import org.apache.camel.component.kubernetes.cluster.lock.KubernetesLockConfiguration;
import org.apache.camel.component.kubernetes.springboot.cluster.KubernetesClusterServiceAutoConfiguration;
import org.apache.camel.component.kubernetes.springboot.cluster.KubernetesClusterServiceConfiguration;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.apache.camel.spring.boot.cluster.ClusteredRouteControllerAutoConfiguration;
import org.apache.camel.spring.boot.util.CamelPropertiesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration(
//        proxyBeanMethods = false
//)
//@AutoConfigureBefore({ClusteredRouteControllerAutoConfiguration.class, CamelAutoConfiguration.class})
//@ConditionalOnProperty(
//        prefix = "camel.cluster.kubernetes",
//        name = {"enabled"}
//)
//@EnableConfigurationProperties({KubernetesClusterServiceConfiguration.class})
public class KubernetesConfigMapConfiguration extends KubernetesClusterServiceAutoConfiguration{

    @Autowired
    private CamelContext camelContext;
    @Autowired
    private KubernetesClusterServiceConfiguration configuration;

    @Override
    public CamelClusterService kubernetesClusterService() throws Exception {
        KubernetesClusterService service = new KubernetesClusterService();
        KubernetesLockConfiguration lockConfiguration = new KubernetesLockConfiguration();
        //To avoid the default behaviour of using k8 leases
        lockConfiguration.setLeaseResourceType(LeaseResourceType.ConfigMap);
        lockConfiguration.setKubernetesResourceName("master-cm");
        CamelPropertiesHelper.setCamelProperties(this.camelContext, service, CamelPropertiesHelper.getNonNullProperties(this.camelContext, this.configuration), false);
        CamelPropertiesHelper.setCamelProperties(this.camelContext, service, CamelPropertiesHelper.getNonNullProperties(this.camelContext, lockConfiguration), false);
        return service;
    }


}
