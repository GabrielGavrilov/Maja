package Maja;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

//===================== Maja, All rights reserved. ======================//
//
// Purpose: User response class for Maja.
//
//=======================================================================

public class MajaResponse extends Maja {

    //-------------------------------------------------------------------
    // Purpose: Writes to the client the HTML page.
    //-------------------------------------------------------------------
    public void send(String location) throws IOException {
        Path fileLocation = Path.of(location);
        byte[] fileBytes = Files.readAllBytes(fileLocation);
        OutputStream socketOutput = Maja.socket.getOutputStream();

        socketOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        socketOutput.write(("ContentType: text/html\r\n").getBytes());
        socketOutput.write("\r\n".getBytes());
        socketOutput.write(fileBytes);
        socketOutput.write("\r\n\r\n".getBytes());
        socketOutput.flush();
        Maja.socket.close();
    }

    public void readPost() throws IOException {
        InputStream is = Maja.socket.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isReader);

        String headerLine = null;
        while((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        StringBuilder payload = new StringBuilder();
        while(br.ready()) {
            payload.append((char) br.read());
        }

        System.out.println("Payload data: " + payload.toString());

    }

}
