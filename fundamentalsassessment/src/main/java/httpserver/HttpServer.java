package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

  private int serverPort;
  private ServerSocket serverSocket;
  private Socket socket;
  private ExecutorService threadPool;
  private ArrayList<String> pathStrings = new ArrayList<>();

  // constructor, server is initiliased with constructor
  public HttpServer(int serverPort, String paths) {
    try {
      this.serverPort = serverPort;
      this.serverSocket = new ServerSocket(this.serverPort);
      System.out.println("Server listening on: " + this.serverPort);
      // verifies directories
      this.pathStrings = verifyDirectories(paths);
    } catch (IOException e) {
      System.err.println("Unable to connect and listen from " + serverPort);
      System.exit(1);
    }

  }

  // verifies directories and add them to array of Path
  public ArrayList<String> verifyDirectories(String paths) {
    ArrayList<String> tempArray = new ArrayList<>();
    String[] directories = paths.split(":");
    for (String dir : directories) {
      Path dirPath = Paths.get(dir);
      System.out.println("Working Directory = " + System.getProperty("user.dir"));
      System.out.println("Path: " + dirPath.toString());
      // verify that path exists and is a directory
      if (Files.exists(dirPath) && Files.isDirectory(dirPath) && Files.isReadable(dirPath)) {
        tempArray.add(dir);
        System.out.println("Directory can be found.");

      } else {
        if (!Files.exists(dirPath)) {
          System.out.println("Directory does not exist.");
          System.exit(1);
        } else if (!Files.isDirectory(dirPath)) {
          System.out.println("Argument is not a directory.");
          System.exit(1);
        } else if (!Files.isReadable(dirPath)) {
          System.out.println("Directory is not readable by server.");
          System.exit(1);
        } else {
          System.out.println("Invalid directory stated in docRoot.");
          System.exit(1);
        }

      }

    }
    return tempArray;
  }

  // create ThreadPool
  public void createThreadPool() {
    try {
      // a threadpool with 3 threads is created
      this.threadPool = Executors.newFixedThreadPool(3);
    } catch (Exception e) {
      System.err.println("Problems creating threadPool.");
    }

  }

  // establish connection
  public void acceptConnections() {
    // while loop is used as you want the serverSocket to constantly accept new
    // sockets that are incoming

    try {

      while (true) {
        this.socket = this.serverSocket.accept();
        System.out.println("Relevant information on this socket: " + this.socket);
        threadPool.submit(new HttpClientConnection(this.socket, this.pathStrings));
      }

    } catch (Exception e) {
      System.err.println("Problems with Socket.");
      System.exit(1);
    }

  }

}
