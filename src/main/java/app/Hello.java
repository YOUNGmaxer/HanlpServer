package app;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @Produces 指定返回 MIME 的格式
 * @Consumes 指定接收 MIME 的格式（只有符合指定格式的请求才能执行）
 */

@Path("/hello")
public class Hello {
  public static final String MESSAGE = "Hello World";

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHello() {
    return MESSAGE;
  }

  @GET @Path("/sub1")
  @Produces(MediaType.TEXT_PLAIN)
  public String getSub1() {
    return "hello sub1";
  }

  @GET @Path("/ids/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public String getIds(@PathParam("id") String id) {
    return "hello id: " + id;
  }

  @POST @Path("/post")
  @Produces(MediaType.TEXT_PLAIN)
  public String getPost() {
    return "hello post";
  }
}