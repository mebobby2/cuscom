package com.company.cuscom;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/config.xml")
@MockEndpoints("*")
public class AppTest {
    @Autowired
    CamelContext context;

    @EndpointInject(uri = "mock:file:test")
    MockEndpoint mock;

    @Test()
    public void testAppRoute() throws Exception {
        String testMessage = "This is a test message!";
        mock.expectedBodiesReceived(testMessage);
        context.createProducerTemplate().sendBody("direct:in", testMessage);
        MockEndpoint.assertIsSatisfied(mock);
    }

    @Before
    public void replaceStreamIn() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context,
        new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:in");
            }
        });
    }
}
