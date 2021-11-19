package com.company.cuscom;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {
        CamelContext ctx = new DefaultCamelContext();
        ctx.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").to("log:end?level=INFO");
            }
        });
        ctx.start();
        ctx.createProducerTemplate().sendBody("direct:start", "Hello, world!");
        ctx.stop();
    }
}
