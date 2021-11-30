package com.company.cusdec;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "BUNDLE")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BUNDLE")
public class CusdecBundle {
  @XmlElement(name = "CUSDEC")
  protected ArrayList<CusdecDocument> documents = new ArrayList<CusdecDocument>();

  public void setDocuments(ArrayList<CusdecDocument> documents) {
    this.documents = documents;
  }

  public ArrayList<CusdecDocument> getDocuments() {
    return documents;
  }

}
