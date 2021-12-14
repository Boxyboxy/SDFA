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

  private int serverPort;
  private ServerSocket serverSocket;
  private Socket socket;
  private ExecutorService threadPool;
  private ArrayList<String> pathStrings = new ArrayList<String>();

  // constructor, server is initiliased with constructor
  public HttpServer(int serverPort, String paths) {
    try {
      this.serverPort = serverPort;
      this.serverSocket = new ServerSocket(this.serverPort);
      System.out.println("Server listening on: " + this.serverPort);

      this.pathStrings = verifyDirectories(paths);
    } catch (IOException e) {
      System.err.println("Unable to connect and listen from " + serverPort);
      System.exit(1);
    }

  }

  // verifies directories and add them to array of Path
  public ArrayList<String> verifyDirectories(String paths) {
    ArrayList<String> tempArray = new ArrayList<String>();
    String[] directories = paths.split(":");
    for (String dir : directories) {
      Path dirPath = Path.of(dir);
      // verify that path exists and is a directory
      if (Files.isReadable(dirPath) && Files.isDirectory(dirPath)) {
        tempArray.add(dir);
      } else {
        System.err.println("Invalid directory stated in docRoot.");
        System.exit(1);
      }
    }
    return tempArray;
  }

  // create ThreadPool
  public void createThreadPool() throws IOException {
    // a threadpool with 3 threads is created
    this.threadPool = Executors.newFixedThreadPool(3);

  }

  // establish connection
  public void acceptConnections() throws IOException {
    // while loop is used as you want the serverSocket to constantly accept new
    // sockets that are incoming

    try {

      while (true) {
        this.socket = this.serverSocket.accept();
        System.out.println("Relevant information on this socket: " + this.socket);
        threadPool.submit(new HttpClientConnection(this.socket, this.pathArray));
      }

    } catch (Exception e) {
      System.err.println("Problems with Socker.");
      System.exit(1);
    }

  }

}
