package Maja;

//===================== Maja, All rights reserved. ======================//
//
// Purpose: Client class for Maja.
//
//=======================================================================

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MajaClient extends Maja {

    //-------------------------------------------------------------------
    // Purpose: Connects client to the server and returns the request.
    //-------------------------------------------------------------------
    protected ArrayList<String> connect(Socket socket) throws IOException {
        ArrayList<String> socketRequest = new ArrayList<>();
        String requestLine;
        BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while((requestLine = buff.readLine()).length() != 0) {
            socketRequest.add(requestLine);
        }

        StringBuilder payload = new StringBuilder();
        while(buff.ready()) {
            payload.append((char) buff.read());
        }

        if(MajaSettings.debug) {
            for(int i = 0; i < socketRequest.size(); i++) {
                System.out.println(socketRequest.get(i));
            }
        }

        Maja.clientPayload = payload.toString();
        return socketRequest;
    }


    //-------------------------------------------------------------------
    // Purpose: Returns client's route.
    //-------------------------------------------------------------------
    protected String getClientRoute(ArrayList<String> socketRequest) {
        String temp = socketRequest.get(0);
        String[] tempArray = temp.split(" ");
        String clientRoute = tempArray[1];

        return clientRoute;
    }

}
