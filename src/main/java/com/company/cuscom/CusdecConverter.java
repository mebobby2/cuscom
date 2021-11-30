package com.company.cuscom;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.company.cusdec.CusdecBundle;

import org.apache.camel.Converter;

@Converter
public class CusdecConverter {
  private static Unmarshaller unmarshaller;

  @Converter
  public static CusdecBundle toCusdecBundle(File cusdecFile) throws JAXBException {
    if (unmarshaller == null) {
      JAXBContext jaxb = JAXBContext.newInstance(CusdecBundle.class);
      unmarshaller = jaxb.createUnmarshaller();
    }
    return (CusdecBundle)unmarshaller.unmarshal(cusdecFile);
  }
}
