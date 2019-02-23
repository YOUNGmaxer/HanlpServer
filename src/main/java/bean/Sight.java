package bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sight {
  private String rid;

  public String getRid() {
    return rid;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }
}