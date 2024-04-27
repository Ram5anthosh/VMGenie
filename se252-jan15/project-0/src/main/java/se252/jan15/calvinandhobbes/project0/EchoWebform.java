package se252.jan15.calvinandhobbes.project0;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("web")
public class EchoWebform {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getEchoForm(
            @DefaultValue("") @QueryParam("nameOfVM") String nameOfVM,
            @DefaultValue("") @QueryParam("ramRequired") String ramRequired,
            @DefaultValue("") @QueryParam("diskSizeRequired") String diskSizeRequired,
            @DefaultValue("") @QueryParam("vcpusRequired") String vcpusRequired) {

        if (locationOne.isEmpty() || locationTwo.isEmpty()) {
            // Initial case when no input parameters are passed. Show web form.
            return "<html><head><title>TravelBuddy</title><style>" +
                    "body { font-family: Arial, sans-serif; text-align: center; }" +
                    "form { margin: 0 auto; width: 50%; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f9f9f9; }" +
                    "input[type=\"text\"], input[type=\"submit\"] { padding: 10px; margin: 5px 0; width: 100%; box-sizing: border-box; }" +
                    "input[type=\"submit\"] { background-color: #4CAF50; color: white; border: none; cursor: pointer; }" +
                    "input[type=\"submit\"]:hover { background-color: #45a049; }" +
                    "</style></head><body>" +
                    "<h2>VirtualMachine</h2>" +
                    "<form action=\"web\" method=\"get\">" +
                    "<strong><label for=\"nameOfVM\">Enter the name of Virtual Machine:</label></strong><br>" +
                    "<input type=\"text\" id=\"nameOfVM\" name=\"nameOfVM\"><br>" +
                    "<strong><label for=\"ramRequired\">Enter the RAM required:</label></strong><br>" +
                    "<input type=\"text\" id=\"ramRequired\" name=\"ramRequired\"><br>" +
                    "<strong><label for=\"diskSizeRequired\">Enter the required Disk size :</label></strong><br>" +
                    "<input type=\"text\" id=\"diskSizeRequired\" name=\"diskSizeRequired\"><br><br>" +
                    "<strong><label for=\"vcpusRequired\">Enter the number of CPU cores required:</label></strong><br>" +
                    "<input type=\"text\" id=\"vcpusRequired\" name=\"vcpusRequired\"><br><br>" +
                    "<input type=\"submit\" value=\"Submit\">" +
                    "</form></body></html>";

        } else {
            // Case when input params are passed. Call REST service.
            Client c = ClientBuilder.newClient();
            WebTarget target = c.target(EchoWebformLauncher.restBaseUri);
            EchoMessage responseMsg = target.path("echo")
                    .queryParam("nameOfVM", nameOfVM)
                    .queryParam("ramRequired", ramRequired)
                    .queryParam("diskSizeRequired", diskSizeRequired)
                    .queryParam("vcpusRequired", vcpusRequired)
                    .request().get(EchoMessage.class);

            return "<html><body><h4>Cloud and Edge Computing Assignment:</h4>" +
                    "<p>" + responseMsg.getMessage() + "</p>" +
                    "</body></html>";
        }
    }
}
