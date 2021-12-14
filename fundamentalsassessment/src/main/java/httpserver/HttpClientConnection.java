package httpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class HttpClientConnection {
  /*
   * private int serverPort;
   * private ServerSocket serverSocket;
   * private Socket socket;
   * private ExecutorService threadPool;
   * 
   * // constructor
   * public HttpClientConnection() {
   * 
   * }
   * 
   * @Override
   * public void run() {
   * try {
   * // initiliase output stream to be used to send responses to the client later
   * OutputStream os = socket.getOutputStream();
   * BufferedOutputStream bos = new BufferedOutputStream(os);
   * DataOutputStream dos = new DataOutputStream(bos);
   * 
   * // get input stream from the socket
   * InputStream is = socket.getInputStream();
   * BufferedInputStream bis = new BufferedInputStream(is);
   * DataInputStream dis = new DataInputStream(bis);
   * 
   * // Checks if there is anything to read from client and acts accordingly based
   * on
   * // processRequest()
   * while (is.available() == 0) { // .available() returns an estimate of the
   * number of bytes that can be read from
   * // this input stream without blocking by the next invocation fo a method for
   * // this input stream
   * String messageFromClient = dis.readUTF();
   * processRequest(dos, messageFromClient);
   * }
   * } catch (IOException e) {
   * System.err.println("Client " + this.socket + " has disconnected.");
   * }
   * }
   * 
   * private void processRequest(DataOutputStream dos, String messageFromClient) {
   * }
   */
}
