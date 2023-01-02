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
    // Purpose:
    //-------------------------------------------------------------------
    public void send(String content) throws IOException {
        OutputStream socketOutput = Maja.socket.getOutputStream();
        socketOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
        socketOutput.write(("ContentType: text/html\r\n").getBytes());
        socketOutput.write("\r\n".getBytes());
        socketOutput.write(content.getBytes());
        socketOutput.write("\r\n\r\n".getBytes());
        socketOutput.flush();
        Maja.socket.close();
    }

    //-------------------------------------------------------------------
    // Purpose: Renders an HTML response to the client.
    //-------------------------------------------------------------------
    public void render(String location) throws IOException {
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

}
