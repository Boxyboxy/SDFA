package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
  /*
   * private int serverPort;
   * private ServerSocket serverSocket;
   * private Socket socket;
   * private ExecutorService threadPool;
   * 
   * private ArrayList<Path> pathArray = new ArrayList<Path>();
   * 
   * // constructor
   * public HttpServer(int serverPort, String paths) {
   * this.serverPort = serverPort;
   * this.pathArray = verifyDirectories(paths);
   * }
   * 
   * public ArrayList<Path> verifyDirectories(String paths) {
   * ArrayList<Path> tempArray = new ArrayList<Path>();
   * String[] directories = paths.split(":");
   * for (String dir : directories) {
   * Path dirPath = Path.of(dir);
   * // verify that path exists and is a directory
   * if (dirPath.toFile().exists() && dirPath.toFile().isDirectory()) {
   * tempArray.add(dirPath);
   * } else {
   * System.out.println("Directory specified does not exist.");
   * System.exit(1);
   * }
   * }
   * return tempArray;
   * }
   * 
   * // start server
   * public void startServer() throws IOException {
   * this.serverSocket = new ServerSocket(this.serverPort);
   * // a threadpool with 3 threads is created
   * this.threadPool = Executors.newFixedThreadPool(3);
   * System.out.println("Server started on port: " + this.serverPort);
   * }
   * 
   * // establish connection
   * public void connectServer() throws IOException {
   * // while loop is used as you want the serverSocket to constantly accept new
   * // sockets that are incoming
   * 
   * try {
   * int count = 1;
   * while (true) {
   * System.out.println("Connecting fella " + count);
   * // Scans and accepts new socket connections.
   * // redefining new sockets in the same pointer is fine because the
   * // CookieClientHandler will be managing the sockets. You do not need the
   * pointer
   * // to assess the previous sockets introduced.
   * this.socket = this.serverSocket.accept();
   * System.out.println("Fella " + count + " connected");
   * System.out.println("Relevant information on this socket: " + this.socket);
   * 
   * threadPool.execute( HttpClientHandler );
   * count++;
   * }
   * 
   * } catch (Exception e) {
   * 
   * }
   * 
   * }
   */
}
