package com.company.cuscom;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {
        Main m = new Main();
        m.addRouteBuilder(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:input")
                .to("log:end?level=INFO")
                .to("file:output");
            }
        });
        m.run();
    }
}
