package Maja;

//===================== Maja, All rights reserved. ======================//
//
// Purpose: User request class for Maja.
//
//=======================================================================

public class MajaRequest extends Maja {

    //-------------------------------------------------------------------
    // Purpose: Returns client's method.
    //-------------------------------------------------------------------
    public String method() {
        String method, temp = Maja.clientRequest.get(0);
        String[] tempArray = temp.split(" ");
        method = tempArray[0];

        return method;
    }

    //-------------------------------------------------------------------
    // Purpose: Returns client's ip.
    //-------------------------------------------------------------------
    public String ip() {
        String ip, temp = socket.getRemoteSocketAddress().toString();
        ip = temp.substring(1);
        return ip;
    }

    //-------------------------------------------------------------------
    // Purpose: Returns client's post payload.
    //-------------------------------------------------------------------
    public String[] payload() {
        String rawPayload = Maja.clientPayload;
        String[] payload = rawPayload.split("&");
        return payload;
    }

}
