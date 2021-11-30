package com.company.cuscom;

import java.io.File;
import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.FileCopyUtils;

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

        FileCopyUtils.copy(new File("src/test/data/cusdec2.xml"), new File("inbound/cusdec2.xml"));

        mockNeedsApproval.assertIsSatisfied();
        mockTransform.assertIsSatisfied();
    }

    @DirtiesContext
    @Test
    public void testDirectTransformMessage() throws Exception {
        mockNeedsApproval.expectedMessageCount(0);
        mockTransform.expectedMessageCount(1);

        FileCopyUtils.copy(new File("src/test/data/cusdec1.xml"), new File("inbound/cusdec.xml"));

        mockNeedsApproval.assertIsSatisfied();
        mockTransform.assertIsSatisfied();
    }

    @Before
    public void setUp() {
        MockEndpoint.resetMocks(context);
    }

    @BeforeClass
    public static void clearInbound() throws IOException {
        FileUtils.deleteDirectory(new File("inboud"));
        FileUtils.forceMkdir(new File("inbound"));
    }
}
