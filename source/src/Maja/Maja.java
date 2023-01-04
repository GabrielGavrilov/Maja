package Maja;

//===================== Maja, All rights reserved. ======================//
//
// Maja, a minimal and flexible web server framework for Java.
//
// Authors:
//      Gabriel Gavrilov <gabriel.gavrilov02@gmail.com>
//
//=======================================================================

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    //-------------------------------------------------------------------
    // Purpose: Adds a get route to the web server
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
        int position = 0;
        for(int i = 0; i < MajaSettings.routes.size(); i++) {
            if(clientRoute.equals(MajaSettings.routes.get(i))) {
                position = i;
                break;
            } else {
                position = -1;
            }
        }

        if(position >= 0) {
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

}
