package se252.jan15.calvinandhobbes.project0;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se252.jan15.calvinandhobbes.project0.EchoServiceLauncher;
import static org.junit.Assert.assertEquals;

/**
 * JUnit code to test the Echo REST web service
 *
 * @author Yogesh Simmhan
 * @version 1.0
 * @see <a href="http://www.serc.iisc.ernet.in/~simmhan/SE252/">IISc SERC 'SE252:Intro to Cloud Computing' Course Webpage</a>
 *
 * (c) Yogesh Simmhan, 2015
 * This work is licensed under a Attribution 4.0 International (CC BY 4.0).
 * http://creativecommons.org/licenses/by/4.0/
 */
public class EchoServiceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {

    	String baseUri = System.getProperty("serviceUrl");

        // start the server
        server = EchoServiceLauncher.startServer(baseUri);
        // create the client
        Client c = ClientBuilder.newClient();

        System.out.println("Trying to contact service at: " + baseUri);
        target = c.target(baseUri);
    }

    @After
    public void tearDown() throws Exception {
        if(server != null){
            server.shutdownNow();
        }
    }

    /**
     * Test to see that the message "Hello World!" is sent in the response EchoMessage.
     */
    @Test
    public void testEchoService() {
    	EchoMessage responseMsg = target.path("echo").queryParam("msg","Hello World!").request().get(EchoMessage.class);
        assertEquals("I heard you say 'Hello World!'", responseMsg.getMessage());
    }
}
