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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Path("echo")
public class EchoService {

@GET
@Produces(MediaType.APPLICATION_JSON)
public EchoMessage getWeatherAndTimeZone(
@DefaultValue("") @QueryParam("nameOfVM") String nameOfVM,
@DefaultValue("") @QueryParam("ramRequired") String ramRequired,
@DefaultValue("") @QueryParam("diskSizeRequired") String diskSizeRequired,
@DefaultValue("") @QueryParam("vcpusRequired") String vcpusRequired,
@DefaultValue("") @QueryParam("isoURL") String isoURL) {

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
if (isoURL.isEmpty()) {
return new EchoMessage("{\"error\": \"URL for the ISO file is required.\"}");
}

// Execute shell command to create virtual machine
String[] urlParts = isoURL.split("/");
String isoFileName = urlParts[urlParts.length - 1];
String diskFileName = nameOfVM + ".qcow2";
String[] command = {
"bash",
"-c",
"cd /var/lib/libvirt/boot/ && wget " + isoURL + " && virt-install --virt-type=kvm --name " + nameOfVM +
" --ram " + ramRequired + " --vcpus=" + vcpusRequired + " --cdrom=/var/lib/libvirt/boot/" + isoFileName +
" --network=bridge=br0,model=virtio --graphics vnc --disk path=/var/lib/libvirt/images/" + diskFileName + ",size=40,bus=virtio,format=qcow2"
};
String output = executeCommand(command);


// Execute shell command to get VNC details
String[] vncCommand = {
"bash",
"-c",
"virsh dumpxml " + nameOfVM + " | grep vnc"
};
String vncOutput = executeCommand(vncCommand);

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
"<h1>Virtual Machine</h1>" +
"<h2>IEC2021019, IEC2021095, IEC2021085</h2>" +
"</div>" +
"<div class='output'>" +
"<h3>Output of creating virtual machine:</h3>" +
"<p>" + output + "</p>" +
"<h3>VNC details:</h3>" +
"<p>" + vncOutput + "</p>" +
"</div>";

// Create and return EchoMessage object with response message
return new EchoMessage(responseMessage);
}

// Method to execute shell commands and capture output
private String executeCommand(String[] command) {
try {
Process process = new ProcessBuilder(command).start();
BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
StringBuilder output = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
output.append(line).append("\n");
}
process.waitFor();
return output.toString();
} catch (IOException | InterruptedException e) {
e.printStackTrace();
return "Error executing command: " + e.getMessage();
}
}
}
