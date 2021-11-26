package com.company.cuscom;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 * Hello world!
 *
 */
public class App extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file:inbound")
      .choice()
      .when(header("NeedsApproval").isEqualTo(true))
      .to("jms:queue:APPROVAL")
      .otherwise()
      .to("seda:transform");
  }
}
