package com.company.cuscom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.company.cusdec.CusdecBundle;

import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/config-test.xml")
public class NetValueBeanSpringTest {

  @Autowired
  NetValueBean bean;

  Unmarshaller u;

    @Test
    public void testFileParsedLower() throws FileNotFoundException, JAXBException {
      CusdecBundle bundle = (CusdecBundle)u.unmarshal(new FileInputStream("src/test/data/cusdec1.xml"));
      bean.checkNetValueForApproval(bundle);
      Assert.assertFalse(bean.checkNetValueForApproval(bundle));
    }

    @Test
    public void testFileParsedHigher() throws FileNotFoundException, JAXBException {
      CusdecBundle bundle = (CusdecBundle)u.unmarshal(new FileInputStream("src/test/data/cusdec2.xml"));
      Assert.assertTrue(bean.checkNetValueForApproval(bundle));
    }

    @Before
    public void setUp() throws JAXBException {
      JAXBContext context = JAXBContext.newInstance(CusdecBundle.class);
      u = context.createUnmarshaller();
    }
}
