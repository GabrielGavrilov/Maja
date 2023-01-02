import Maja.Maja;

import java.io.IOException;

public class RunningTheServer {
    public static void main(String[] args) throws IOException {

        // Initializes Maja.
        Maja maja = new Maja();

        // Starts the server on localhost with port 3000.
        maja.run(null, 3000, ()-> {
            System.out.println("Listening on port 3000...");
        });

        /*
            Note:
                Set the host to "0.0.0.0" if you'd like to run Maja publicly from your machine.
         */
    }
}
