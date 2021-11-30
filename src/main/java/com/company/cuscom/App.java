package com.company.cuscom;

import javax.xml.bind.JAXBContext;

import com.company.cusdec.CusdecBundle;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;

/**
 * Hello world!
 *
 */
public class App extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    DataFormat jaxb = new JaxbDataFormat(JAXBContext.newInstance(CusdecBundle.class));

    from("file:inbound")
      .unmarshal(jaxb)
      .setHeader(
        "NeedsApproval",
        method("netValueBean", "checkNetValueForApproval")
      )
      .choice()
      .when(header("NeedsApproval").isEqualTo(true))
      .to("jms:queue:APPROVAL")
      .otherwise()
      .to("seda:transform")
      .endChoice();
  }
}
