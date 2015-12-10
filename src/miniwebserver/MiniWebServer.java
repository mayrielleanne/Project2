/*
pananakot ni em 
kapag sa post na, 
ung sa external javascript, dapat may new line pa para di magerror
tapos dapat .read na lang

*/


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniwebserver;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Bermas
 */
public class MiniWebServer {

    /**
     * @param args the command line arguments
     */
	 ServerSocket s;
 
	 /**
	  * Creates and returns server socket.
	  * @param port Server port.
	  * @return created server socket
	  * @throws Exception Exception thrown, if socket cannot be created.
	  */
    protected ServerSocket getServerSocket(int port) throws Exception {
        return new ServerSocket(port);
    }
 
    /**
     * Starts web server and handles web browser requests.
     * @param port Server port(ex. 80, 8080)
     * @throws Exception Exception thrown, if server fails to start.
     */
    public void runServer(int port) throws Exception {
        s = getServerSocket(port);
 
        while (true) {
            try {
                Socket serverSocket = s.accept();
                handleRequest(serverSocket);
            } catch(IOException e) {
            	 System.out.println("Failed to start server: " + e.getMessage());
                System.exit(0);
                return;
            }
        }
    }
 
    /**
     * Handles web browser requests and returns a static web page to browser.
     * @param s socket connection between server and web browser.
     */
    public void handleRequest(Socket s) {
        //is variable reads request header from the client
        BufferedReader is;     // inputStream from web browser
        PrintWriter os;        // outputStream to web browser
        String request;        // Request from web browser
 
        try {
            String webServerAddress = s.getInetAddress().toString(  );
            System.out.println("Accepted connection from " + webServerAddress);
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
 
            request = is.readLine();
            String[] token = request.split(":");
            System.out.println("Server received request from client: " + request);
           // token[1]
            
            HashMap<String, String> ht = new HashMap<String, String>();
            while(!request.isEmpty()){
            System.out.println(request);
            token = request.split(" ");
            ht.put(token[0],token[1]);
            request = is.readLine();
            }
            
            //os variable pinupuntahan ng return ko sa client
            //dito isulat ung page na gusto ko ibalik 
            os = new PrintWriter(s.getOutputStream(), true);
            os.println("HTTP/1.0 200");
            os.println("Content-type: text/html");
            os.println("Server-name: myserver");
            String tableResult = "<table border=\"1\" style=\"width:100%\" >";
            for (Map.Entry me : ht.entrySet()) {
               tableResult = tableResult+"<tr><td>"+me.getKey()+"</td><td>"+me.getValue()+"</td></tr>";
            }
            String response = "<html><head>" +
                "<title>Simpl Web Page</title></head>\n" +
                "<h1>Congratulations!!!</h1>\n" +
                "<h3>This page was returned by " + webServerAddress + "</h3>\n" +
                "<p>This is the first page hosted by your web server.\n</p>" +
                "Visit <A HREF=\"http://www.techwiki.ordak.org\"> http://www.techwiki.ordak.org</A> for more sample codes.\n\n\n\n" + tableResult+
                "\n\n\n\n"+loadFile()+"</html>\n";
            os.println("Content-length: " + response.length(  ));
            os.println("");
            os.println(response);
            
            os.flush();
            os.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Failed to send response to client: " + e.getMessage());
        } finally {
        	if(s != null) {
        		try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return;
    }
    
    public char[] loadFile(){
    File file = new File("C://trial.html");
  //  String content = "";
    char content[] = new char[1024];
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.read(content);
            //content = content + "\n";
            br.close();
        }catch(Exception e){}
        return content;
    }
}	


