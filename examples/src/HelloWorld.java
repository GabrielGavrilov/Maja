import Maja.Maja;
import Maja.MajaRequest;
import Maja.MajaResponse;

import java.io.IOException;

public class HelloWorld {
    public static void main(String[] args) throws IOException {

        // Initialized Maja.
        Maja maja = new Maja();
        MajaRequest request = new MajaRequest();
        MajaResponse response = new MajaResponse();

        // Index route.
        maja.route("/", request, response, ()-> {
            try {

                if(request.method().equals("GET")) {
                    response.send("<h2>Hello, World!</h2>");
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
