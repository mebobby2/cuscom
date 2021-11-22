package com.company.cuscom;

import org.apache.camel.Exchange;
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
        m.addRouteBuilder(new AppRoute());
        m.run();
    }

    static class AppRoute extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("stream:in").to("file:test");
        }
    }
}
