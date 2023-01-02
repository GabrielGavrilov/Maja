import Maja.Maja;
import Maja.MajaRequest;
import Maja.MajaResponse;

import java.io.IOException;

public class RoutingAndRenderingHTML {
    public static void main(String[] args) throws IOException {

        // Initializes Maja.
        Maja maja = new Maja();
        MajaRequest request = new MajaRequest(); // We use the MajaRequest class to get request information.
        MajaResponse response = new MajaResponse(); // We use the MajaResponse class to respond to the request.

        // Index route.
        maja.route("/", request, response, ()-> {
            try {

                if(request.method().equals("GET")) { // If it's a GET method.
                    response.send("src/pages/index.html"); // Then respond with the index.html file.
                }

            } catch(Exception e) {
                System.out.println(e);
            }
        });

        // Starts the server on localhost with port 3000.
        maja.run(null, 3000, ()-> {
            System.out.println("Listening on port 3000...");
        });

    }

}
