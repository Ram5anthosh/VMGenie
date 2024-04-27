package se252.jan15.calvinandhobbes.project0;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class to launch echo REST web service
 *
 * @author Yogesh Simmhan
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 * 
 * (c) Yogesh Simmhan, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
public class EchoServiceLauncher {
	
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer(String baseUri) {
        // create a resource config that scans for JAX-RS resources and providers
        // in se252.jan15.calvinandhobbes.project0 package
        final ResourceConfig rc = new ResourceConfig().packages("se252.jan15.calvinandhobbes.project0");       

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        System.out.println("Trying to starting service at: " + baseUri);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
    }

    /**
     * Main method to start REST web service. Pass public IP/hostname and optional port of the local machine/VM to start service on.
     * e.g. EchoServiceLauncher host1
     * e.g. EchoServiceLauncher host1 1947
     * 
     * @param args Public IP or public hostname of the local VM, and an optional port number to start service on (default 8081)
     *   
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	if(args.length != 1 && args.length !=2){
    		System.out.println("Please pass the public IP or public hostname of the local machine/VM as parameter. e.g. ec2-54-254-184-72.ap-southeast-1.compute.amazonaws.com");
    		System.out.println("You can also optionally pass a port number for the REST service. 8081 is used by default.");
    		return;
    	}
    	
    	int port = args.length == 2 ? Integer.parseInt(args[1]) : 8081;
    	String baseUri = "http://" + args[0] + ":" + port + "/project0";
    	
        final HttpServer server = startServer(baseUri);
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%s/application.wadl\nHit enter to stop it...", baseUri));
        System.in.read();
        server.shutdownNow();
    }
}

