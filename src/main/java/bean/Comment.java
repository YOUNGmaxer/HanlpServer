package bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {
  private String content;

  public Comment() {

  }
  
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}