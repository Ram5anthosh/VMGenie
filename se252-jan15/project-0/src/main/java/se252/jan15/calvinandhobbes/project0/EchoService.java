package se252.jan15.calvinandhobbes.project0;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


@Path("echo")
public class EchoService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EchoMessage getWeatherAndTimeZone(
            @DefaultValue("") @QueryParam("nameOfVM") String nameOfVM,
            @DefaultValue("") @QueryParam("ramRequired") String ramRequired,
            @DefaultValue("") @QueryParam("diskSizeRequired") String diskSizeRequired,
            @DefaultValue("") @QueryParam("vcpusRequired") String vcpusRequired) {

        // Check if parameters are provided
        if (nameOfVM.isEmpty()) {
            return new EchoMessage("{\"error\": \"Name of the virtual machine is required.\"}");
        }
        if (ramRequired.isEmpty()) {
            return new EchoMessage("{\"error\": \"RAM of the virtual machine is required.\"}");
        }
        if (diskSizeRequired.isEmpty()) {
            return new EchoMessage("{\"error\": \"Storage of the virtual machine is required.\"}");
        }
        if (vcpusRequired.isEmpty()) {
            return new EchoMessage("{\"error\": \"Number of CPU cores of the virtual machine is required.\"}");
        }

        // Prepare response message
        String responseMessage = "<div style='font-family: Arial, sans-serif;'>" +
                "<style>" +
                ".section { margin-bottom: 20px; }" +
                ".section h3 { color: #333; margin-bottom: 10px; }" +
                ".section p { margin: 5px 0; }" +
                ".weather-forecast { margin-left: 20px; }" +
                ".weather-forecast h4 { margin-top: 10px; }" +
                ".weather-forecast table { border-collapse: collapse; width: 100%; }" +
                ".weather-forecast th, .weather-forecast td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                ".weather-forecast th { background-color: #f2f2f2; }" +
                ".time-difference p { margin: 5px 0; }" +
                ".time-difference strong { font-weight: bold; }" +
                "</style>" +
                "<div class='section'>" +
                "<h1>Virtual Machine</h1>"+
                "<h2>IEC2021019, IEC2021095, IEC2021085</h2>"+
                "</div>";

        // Create and return EchoMessage object with response message
        return new EchoMessage(responseMessage);
    }
}

