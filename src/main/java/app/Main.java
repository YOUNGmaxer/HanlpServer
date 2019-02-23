package app;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;
import java.io.IOException;

/**
 * Main class
 */
public class Main {
  public static final String BASE_URI = "http://localhost:3002/hanlp";

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {
    // create a resource config that scans for JAX-RS resources and providers in app
    final ResourceConfig rc = new ResourceConfig().packages("app");

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }

  /**
   * Main method.
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format("App started with WADL available at %s/application.wadl\nHit enter to stop it...", BASE_URI));
    System.in.read();
    server.shutdownNow();
  }
}