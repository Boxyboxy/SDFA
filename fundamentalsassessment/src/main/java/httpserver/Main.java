package httpserver;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        int serverPort = 3000;
        String paths = "./static";

        if (args != null && args.length >= 1) {
            if (args.length < 3) {
                if (args[0] == "--port") {
                    // port number as specified
                    // check if docRoot command is passed
                    // if so directories will be as specified
                    serverPort = Integer.parseInt(args[1]);
                    paths = "./static";

                } else if (args[0] == "--docRoot") {
                    serverPort = 3000;
                    paths = args[1];

                }
            } else if (args.length > 2) {
                serverPort = Integer.parseInt(args[1]);
                paths = args[3];

            }
        } else {
            // nothing passed in arguments
            // server starts on port 3000, docRoot directory is ./static

        }
        System.out.println(serverPort);
        System.out.println(paths);
        // HttpServer server = new HttpServer(serverPort, paths);
    }
}
