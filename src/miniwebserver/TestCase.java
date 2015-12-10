/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniwebserver;

/**
 *
 * @author Bermas
 */
public class TestCase{
	/**
	 * Main Method
	 */
	public static void main(String[] args) {
	        MiniWebServer webServer = new MiniWebServer();
	        try {
			webServer.runServer(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
}