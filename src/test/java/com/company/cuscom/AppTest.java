package com.company.cuscom;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit test for simple App.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/config-test.xml")
@MockEndpoints("*")
public class AppTest {
    @Autowired
    CamelContext context;

    @EndpointInject(uri = "mock:jms:queue:APPROVAL")
    MockEndpoint mockNeedsApproval;

    @EndpointInject(uri = "mock:seda:transform")
    MockEndpoint mockTransform;

    @Produce
    ProducerTemplate template;

    @DirtiesContext
    @Test
    public void testNeedsApprovalMessage() throws Exception {
        mockNeedsApproval.expectedMessageCount(1);
        mockTransform.expectedMessageCount(0);

        template.sendBodyAndHeader("direct:start", "message", "NeedsApproval", true);
        mockNeedsApproval.assertIsSatisfied();
        mockTransform.assertIsSatisfied();
    }

    @DirtiesContext
    @Test
    public void testDirectTransformMessage() throws InterruptedException {
        mockNeedsApproval.expectedMessageCount(0);
        mockTransform.expectedMessageCount(1);

        context.createProducerTemplate().sendBodyAndHeader("direct:start", "message", "NeedsApproval", false);

        mockNeedsApproval.assertIsSatisfied();
        mockTransform.assertIsSatisfied();
    }

    @Before
    public void replaceFileInbound() throws Exception {
        context.getRouteDefinitions().get(0).adviceWith(context,
        new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");
            }
        });
    }
}
