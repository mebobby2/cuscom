package com.company.cuscom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetValueBean {
  protected double approvalThreshold;

  protected static final Pattern totalNetAmountRE = Pattern.compile("<TOTAL_NET_AMOUNT>(.*)</TOTAL_NET_AMOUNT>");

  public boolean checkNetValueForApproval(String document) {
    Matcher m = totalNetAmountRE.matcher(document);
    if (m.find()) {
      String amount = m.group(1);
      return Double.parseDouble(amount) > approvalThreshold;
    } else {
      return false;
    }
  }

  public double getApprovalThreshold() {
    return approvalThreshold;
  }

  public void setApprovalThreshold(double approvalThreshold) {
    this.approvalThreshold = approvalThreshold;
  }

}
