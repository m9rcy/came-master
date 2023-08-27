package com.example.camelmaster;

import org.apache.camel.CamelContext;
import org.apache.camel.cluster.CamelClusterService;
import org.apache.camel.component.kubernetes.cluster.KubernetesClusterService;
import org.apache.camel.impl.event.CamelContextStartedEvent;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.spi.EventNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class OptionalServiceStartupListener implements EventNotifier {

    @Autowired
    private ApplicationContext appContext;

    private void doAddService(CamelContext context, KubernetesClusterService kubernetesClusterService) throws Exception {
        var services = context.hasServices(CamelClusterService.class);
        // services.stream().filter(s-> s instanceof CamelClusterService).collect(Collectors.toList()).size()
        if (services.isEmpty() && kubernetesClusterService != null) {
            context.addService(kubernetesClusterService);
        }
    }

    @Override
    public void notify(CamelEvent event) throws Exception {
        if (event instanceof CamelContextStartedEvent) {
            var kubernetesClusterService = appContext.getBean("kubernetes-cluster-service", KubernetesClusterService.class);
            doAddService(((CamelContextStartedEvent) event).getContext(), kubernetesClusterService);
        }
    }

    @Override
    public boolean isEnabled(CamelEvent event) {
        return false;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean isIgnoreCamelContextInitEvents() {
        return false;
    }

    @Override
    public void setIgnoreCamelContextInitEvents(boolean ignoreCamelContextInitEvents) {

    }

    @Override
    public boolean isIgnoreCamelContextEvents() {
        return false;
    }

    @Override
    public void setIgnoreCamelContextEvents(boolean ignoreCamelContextEvents) {

    }

    @Override
    public boolean isIgnoreRouteEvents() {
        return false;
    }

    @Override
    public void setIgnoreRouteEvents(boolean ignoreRouteEvents) {

    }

    @Override
    public boolean isIgnoreServiceEvents() {
        return false;
    }

    @Override
    public void setIgnoreServiceEvents(boolean ignoreServiceEvents) {

    }

    @Override
    public boolean isIgnoreExchangeEvents() {
        return false;
    }

    @Override
    public void setIgnoreExchangeEvents(boolean ignoreExchangeEvents) {

    }

    @Override
    public boolean isIgnoreExchangeCreatedEvent() {
        return false;
    }

    @Override
    public void setIgnoreExchangeCreatedEvent(boolean ignoreExchangeCreatedEvent) {

    }

    @Override
    public boolean isIgnoreExchangeCompletedEvent() {
        return false;
    }

    @Override
    public void setIgnoreExchangeCompletedEvent(boolean ignoreExchangeCompletedEvent) {

    }

    @Override
    public boolean isIgnoreExchangeFailedEvents() {
        return false;
    }

    @Override
    public void setIgnoreExchangeFailedEvents(boolean ignoreExchangeFailureEvents) {

    }

    @Override
    public boolean isIgnoreExchangeRedeliveryEvents() {
        return false;
    }

    @Override
    public void setIgnoreExchangeRedeliveryEvents(boolean ignoreExchangeRedeliveryEvents) {

    }

    @Override
    public boolean isIgnoreExchangeSentEvents() {
        return false;
    }

    @Override
    public void setIgnoreExchangeSentEvents(boolean ignoreExchangeSentEvents) {

    }

    @Override
    public boolean isIgnoreExchangeSendingEvents() {
        return false;
    }

    @Override
    public void setIgnoreExchangeSendingEvents(boolean ignoreExchangeSendingEvents) {

    }

    @Override
    public boolean isIgnoreStepEvents() {
        return false;
    }

    @Override
    public void setIgnoreStepEvents(boolean ignoreStepEvents) {

    }

    @Override
    public void setIgnoreExchangeAsyncProcessingStartedEvents(boolean ignoreExchangeAsyncProcessingStartedEvents) {

    }

    @Override
    public boolean isIgnoreExchangeAsyncProcessingStartedEvents() {
        return false;
    }
}
