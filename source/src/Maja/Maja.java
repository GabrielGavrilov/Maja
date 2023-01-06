package Maja;

//===================== Maja, All rights reserved. ======================//
//
// Maja, a minimal and flexible web server framework for Java.
//
// Authors:
//      Gabriel Gavrilov <gabriel.gavrilov02@gmail.com>
//
//=======================================================================

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Maja {

    static ServerSocket maja;
    static Socket socket;
    static MajaClient client;
    static ArrayList<String> clientRequest;
    static String clientPayload = "";

    //-------------------------------------------------------------------
    // Purpose: Starts the Maja web server.
    //-------------------------------------------------------------------
    public void run(String host, int port, Runnable callback) throws IOException {
        callback.run();
        if(host == null) {
            maja = new ServerSocket(port);
        } else {
            InetAddress hostAddress = InetAddress.getByName(host);
            maja = new ServerSocket(port, 0, hostAddress);
        }

        while(true) {
            try {

                socket = maja.accept();
                client = new MajaClient();
                clientRequest = client.connect(socket);
                handleRoutes();
                handleStaticRoutes();

            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    //-------------------------------------------------------------------
    // Purpose: Sets the static folder for Maja.
    //-------------------------------------------------------------------
    public static void setStatic(String location) {
        MajaSettings.staticFolder = location;
    }

    //-------------------------------------------------------------------
    // Purpose: Adds a route to the web server
    //-------------------------------------------------------------------
    public void route(String route, MajaRequest request, MajaResponse response, Runnable callback) {
        int temp = 0;

        MajaSettings.routes.add(route);
        for(int i = 0; i < MajaSettings.routes.size(); i++) {
            if(MajaSettings.routes.get(i).equals(route)) {
                temp = i;
            }
        }

        MajaSettings.routeCallbacks.add(temp, callback);
    }

    //-------------------------------------------------------------------
    // Purpose: If requested route is defined, then call the Runnable callback method for that route.
    //-------------------------------------------------------------------
    private static void handleRoutes() {
        String clientRoute = client.getClientRoute(clientRequest);
        int position = -1;
        for(int i = 0; i < MajaSettings.routes.size(); i++) {
            if(clientRoute.equals(MajaSettings.routes.get(i))) {
                position = i;
                break;
            } else {
                position = -1;
            }
        }

        if(position != -1) {
            MajaSettings.routeCallbacks.get(position).run();
        }
        else {
            clientRoute = "*";
            for(int i = 0; i < MajaSettings.routes.size(); i++) {
                if(clientRoute.equals(MajaSettings.routes.get(i))) {
                    MajaSettings.routeCallbacks.get(i).run();
                    break;
                }
            }
        }

    }

    //-------------------------------------------------------------------
    // Purpose:
    //-------------------------------------------------------------------
    private static void handleStaticRoutes() throws Exception {
        Path staticFilePath = Path.of(MajaSettings.staticFolder + client.getClientRoute(clientRequest));
        File staticFile = new File(staticFilePath.toUri());
        boolean exists = staticFile.exists();

        if(exists && !staticFilePath.equals(MajaSettings.staticFolder)) {
            byte[] fileBytes = Files.readAllBytes(staticFilePath);
            OutputStream socketOutput = socket.getOutputStream();

            socketOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            socketOutput.write(("ContentType: text/html\r\n").getBytes());
            socketOutput.write("\r\n".getBytes());
            socketOutput.write(fileBytes);
            socketOutput.write("\r\n\r\n".getBytes());
            socketOutput.flush();
            Maja.socket.close();
        }

    }

}
