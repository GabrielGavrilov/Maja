import Maja.Maja;
import Maja.MajaRequest;
import Maja.MajaResponse;

import java.io.IOException;

public class ErrorRoute {
    public static void main(String[] args) throws IOException {

        // Initializes Maja.
        Maja maja = new Maja();
        MajaRequest request = new MajaRequest();
        MajaResponse response = new MajaResponse();

        // Index route.
        maja.route("/", request, response, ()-> {
           try {

               if(request.method().equals("GET")) {
                   response.render("src/pages/index.html");
               }

           } catch(Exception e) {
               System.out.println(e);
           }
        });

        // Contact route.
        maja.route("/contact", request, response, ()-> {
            try {

                if(request.method().equals("GET")) {
                    response.render("src/pages/contact.html");
                }

            } catch(Exception e) {
                System.out.println(e);
            }
        });

        // Error route (only gets called when a user goes to an undefined route).
        maja.route("*", request, response, ()-> {
            try {

                if(request.method().equals("GET")) {
                    response.render("src/pages/404.html");
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
