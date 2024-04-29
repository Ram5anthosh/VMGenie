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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Path("web")
public class EchoWebform {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getEchoForm(
            @DefaultValue("") @QueryParam("nameOfVM") String nameOfVM,
            @DefaultValue("") @QueryParam("ramRequired") String ramRequired,
            @DefaultValue("") @QueryParam("diskSizeRequired") String diskSizeRequired,
            @DefaultValue("") @QueryParam("vcpusRequired") String vcpusRequired) {

        if (nameOfVM.isEmpty() || ramRequired.isEmpty() || diskSizeRequired.isEmpty() || vcpusRequired.isEmpty()) {
            // Initial case when no input parameters are passed. Show web form.
            String freeOutput = executeCommand("free -h");
            String dfOutput = executeCommand("df -h");
 
            return "<html><head><title>VM-Create</title><style>" +
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
                    "<span style=\"color: red;\">Note: Enter values in MB.</span><br><br>" +
                    "<strong><label for=\"diskSizeRequired\">Enter the required Disk size :</label></strong><br>" +
                    "<input type=\"text\" id=\"diskSizeRequired\" name=\"diskSizeRequired\"><br><br>" +
                    "<span style=\"color: red;\">Use integer to denote the number of GB required storage.</span><br><br>" +
                    "<strong><label for=\"vcpusRequired\">Enter the number of CPU cores required:</label></strong><br>" +
                    "<input type=\"text\" id=\"vcpusRequired\" name=\"vcpusRequired\"><br>" +
                    "<span style=\"color: red;\">Note: Please enter an integer value.</span><br><br>" +
                    "<input type=\"submit\" value=\"Submit\"><br><br><br>" +
                    "<h3> Centos 7.0 VM will be created. </h3>" +
                    "<p>RAM available on server:<br>" + freeOutput + "</p>" +
                    "<p>Disk Information:<br>" + dfOutput + "</p>" +
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
    private String executeCommand(String command) {
    StringBuilder output = new StringBuilder();
    Process process;
    try {
        process = Runtime.getRuntime().exec(command);
        process.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        output.append("<style>");
        output.append("table { border-collapse: collapse; width: 50%; margin: 0 auto; }");
        output.append("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        output.append("th { background-color: #f2f2f2; }");
        output.append("</style>");

        output.append("<table>");
        String line;
        boolean isHeader = true;
        while ((line = reader.readLine()) != null) {
            output.append("<tr>");
            String[] columns = line.split("\\s+");
            for (String column : columns) {
                if (isHeader) {
                    output.append("<th>").append(column).append("</th>");
                } else {
                    output.append("<td>").append(column).append("</td>");
                }
            }
            isHeader = false;
            output.append("</tr>");
        }
        output.append("</table>");

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
    return output.toString();
}





}
