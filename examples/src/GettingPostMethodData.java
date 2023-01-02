import Maja.Maja;
import Maja.MajaRequest;
import Maja.MajaResponse;

import java.io.IOException;

public class GettingPostMethodData {
    public static void main(String[] args) throws IOException {

        // Initializes Maja.
        Maja maja = new Maja();
        MajaRequest request = new MajaRequest();
        MajaResponse response = new MajaResponse();

        // Form route with a get and post.
        maja.route("/", request, response, ()-> {
            try {

                if(request.method().equals("GET")) {
                    response.render("src/pages/form.html");
                }

                else if(request.method().equals("POST")) {
                    String[] payload = request.payload();
                    String name, email;

                    name = payload[0].split("=")[1];
                    email = payload[1].split("=")[1];

                    System.out.println("Submitted name: " + name);
                    System.out.println("Submitted email: " + email);

                    System.out.println("\nRaw payload: ");
                    for(int i = 0; i < payload.length; i++) {
                        System.out.println(payload[i]);
                    }

                    response.render("src/pages/thankyou.html");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        });

        // Starts the server on localhost with port 3000.
        maja.run(null, 3000, ()-> {
            System.out.println("Listening on port 3000...");
        });

    }

}
