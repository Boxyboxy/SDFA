package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;

public class HttpClientConnection implements Runnable {
  private Socket socket;
  private HttpWriter httpWriter;
  private BufferedReader reader;
  private ArrayList<String> pathStrings;

  // constructor
  public HttpClientConnection(Socket socket, ArrayList<String> pathStrings) {
    this.socket = socket;
    this.pathStrings = pathStrings;
  }

  @Override
  public void run() {
    try {
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.httpWriter = new HttpWriter(socket.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Unable to initiliase i/o streams");
    }

    // reads only one line from browser and storing arguments as method and
    // resourceName
    String fromBrowser = reader.readLine(); // IOException
    String[] arguments = fromBrowser.split(" ");
    String method = arguments[0];
    String resourceName = arguments[1];

    // NOT A GET METHOD
    if (!method.equals("GET")) {
      error405(method);
    }
    // RESOURCE DOES NOT EXIST
    if (!fileExists(resourceName)) {
      error404(resourceName);
    }

    // RESOURCE EXISTS

    // RESOURCE EXISTS AND IS A PNG

  }

  public boolean fileExists(String resourceName) {
    if (resourceName.equals("/")) {
      resourceName = "/index.html";
    }

    for (String dir : pathStrings) {
      String fileString = dir + resourceName;
      Path filePath = Path.of(fileString);
      if (filePath.toFile().exists()) {
        return true;
      }
    }
    return false;
  }

  // writing error messages
  // not a GET method
  public void error405(String method) {
    String response1 = "HTTP/1.1 405 Method Not Allowed";
    String response2 = "";
    String response3 = method + " not supported";
    try {
      this.httpWriter.writeString(response1);
      this.httpWriter.writeString(response2);
      this.httpWriter.writeString(response3);
    } catch (Exception e) {
      System.err.println("Unable to send response to browsers");
    }
  }

  public void error404(String resourceName) {
    String response1 = "HTTP/1.1 404 Method Not Found";
    String response2 = "";
    String response3 = resourceName + " not Found";
    try {
      this.httpWriter.writeString(response1);
      this.httpWriter.writeString(response2);
      this.httpWriter.writeString(response3);
    } catch (Exception e) {
      System.err.println("Unable to send response to browsers");
    }
  }

}
