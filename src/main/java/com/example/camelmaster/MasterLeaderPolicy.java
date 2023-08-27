package com.example.camelmaster;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;
import org.apache.camel.support.RoutePolicySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class MasterLeaderPolicy extends RoutePolicySupport {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void onInit(Route route) {
        var kubernetesClusterService = appContext.getBean("kubernetes-cluster-service", KubernetesClusterService.class);
        try {
            doAddService(route.getCamelContext(), kubernetesClusterService);
            this.onInit(route);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAddService(CamelContext context, KubernetesClusterService kubernetesClusterService) throws Exception {
        var services = context.hasServices(CamelClusterService.class);
        // services.stream().filter(s-> s instanceof CamelClusterService).collect(Collectors.toList()).size()
        if (services.isEmpty() && kubernetesClusterService != null) {
            context.addService(kubernetesClusterService);
        }
    }
}
