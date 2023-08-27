package com.example.camelmaster;

import org.apache.camel.CamelContext;
import org.apache.camel.DeferredContextBinding;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TimerRoute extends RouteBuilder {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void configure() throws Exception {

//        var camelContext = getCamelContext();
//        var kubernetesClusterService = appContext.getBean("kubernetes-cluster-service", KubernetesClusterService.class);
//        var services = camelContext.hasServices(CamelClusterService.class);
//
//        if (services.isEmpty()) {
//            camelContext.addService(kubernetesClusterService);
//        }


        // This route is configured to be local (see application.properties)
        // so it will be started regardless of the leadership status if
        // this node.
        from("timer:heartbeat?period=10000")
                .routeId("heartbeat")
                .log("HeartBeat route (timer) {{node.id}} ...");

        // This route is configured to be clustered so it will be started
        // by the controller only when this node is leader
        from("master:test:timer:clustered?period=5000")
                .routeId("clustered")
                .log("Clustered route (timer) {{node.id}} ...");

        from("master:test2:timer:clustered2?period=4000")
                .routeId("clustered2")
                .log("Clustered2 route (timer) {{node.id}} ...");
    }
}
