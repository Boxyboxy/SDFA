package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;

public class HttpClientConnection implements Runnable {
  private Socket socket;
  private HttpWriter httpWriter;
  private BufferedReader reader;
  private ArrayList<String> pathStrings;
  private Path pathOfFile;

  // constructor
  public HttpClientConnection(Socket socket, ArrayList<String> pathStrings) {
    this.socket = socket;
    this.pathStrings = pathStrings;
  }

  @Override
  public void run() {

    try {
      // initialise reader and writer
      this.initReaderWriter();
      // reading line by browser
      String[] arguments = firstLineByBrowser();
      String method = arguments[0];
      String resourceName = arguments[1];

      // NOT A GET METHOD
      if (!method.equals("GET")) {
        error405(method);
        this.socket.close();
      }
      // RESOURCE DOES NOT EXIST
      if (!fileExists(resourceName)) {
        error404(resourceName);
        this.socket.close();
      }
      // RESOURCE EXISTS
      if (fileExists(resourceName)) {
        // IF resource is PNG
        String[] pngChecker = resourceName.split(".");
        if (pngChecker.length == 2 && pngChecker[1].equalsIgnoreCase("png")) {
          // SENDING MESSAGE AND RESOURCE AS BYTE
          ok200PNG(this.pathOfFile);
          this.socket.close();
        } else {
          // IF RESOURCE IS A NON-PNG FILE
          ok200(this.pathOfFile);
          // SENDING MESSAGE AND RESOURCE AS BYTE
          this.socket.close();
        }

      }

    } catch (IOException e) {
      System.err.println("Unable to read request from browser.");
    }
  }

  // initialising reader and writer
  public void initReaderWriter() {
    try {
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.httpWriter = new HttpWriter(socket.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Unable to initiliase i/o streams");
    }
  }

  // input from first line by browser
  public String[] firstLineByBrowser() throws IOException {
    // reads only one line from browser and storing arguments as method and
    // resourceName
    String fromBrowser = this.reader.readLine(); // IOException
    String[] arguments = fromBrowser.split(" ");
    return arguments;
  }

  // checking if file exists, returns an Optional<String>
  public boolean fileExists(String resourceName) {
    if (resourceName.equals("/")) {
      resourceName = "/index.html";
    }
    for (String dir : pathStrings) {
      String fileString = dir + resourceName;
      Path filePath = Path.of(fileString);
      if (filePath.toFile().exists()) {
        this.pathOfFile = filePath;
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

  public void ok200(Path filePath) {
    String response1 = "HTTP/1.1 200 OK";
    String response2 = "";

    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      this.httpWriter.writeString(response1);
      this.httpWriter.writeString(response2);
      this.httpWriter.writeBytes(fileBytes, 0, fileBytes.length);
      this.httpWriter.close();
    } catch (IOException e) {
      System.err.println("Problems reading or sending bytes.");
    } catch (Exception e) {
      System.err.println("Unable to send response to browsers");
    }
  }

  public void ok200PNG(Path filePath) {
    String response1 = "HTTP/1.1 200 OK";
    String response2 = "image/png";
    String response3 = "";
    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      this.httpWriter.writeString(response1);
      this.httpWriter.writeString(response2);
      this.httpWriter.writeString(response3);
      this.httpWriter.writeBytes(fileBytes, 0, fileBytes.length);
      this.httpWriter.close();
    } catch (IOException e) {
      System.err.println("Problems reading or sending bytes.");
    } catch (Exception e) {
      System.err.println("Unable to send response to browsers");
    }
  }

}
