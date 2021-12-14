package httpserver;

public class Main {
    public static void main(String[] args) {
        int serverPort = 3000;
        String paths = "./static";

        if (args != null && args.length >= 1) {

            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("--port")) {
                    serverPort = Integer.parseInt(args[i + 1]);
                } else if (args[i].equals("--docRoot")) {
                    paths = args[i + 1];
                }
            }
        }

        System.out.println(serverPort);
        System.out.println(paths);
        HttpServer server = new HttpServer(serverPort, paths);
        server.createThreadPool();
        server.acceptConnections();
    }
}