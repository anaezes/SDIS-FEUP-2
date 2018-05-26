package xet.server.handler;

import xet.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by ana on 5/24/18.
 */

public class MessageHandler implements HttpHandler {
    private Server server;

    public MessageHandler(Server server) {
        this.server = server;
    }

    public void handle(HttpExchange t) throws IOException {
        InputStreamReader isr =  new InputStreamReader(t.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        System.out.println(query);
        byte [] response = "Got your xet.message".getBytes();
        t.sendResponseHeaders(200, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();


        String[] parts = query.split("&");

        String username = parts[0];
        String[] user = username.split("=");

        String room = parts[1];
        String[] r = room.split("=");

        String message = parts[2];
        String[] msg = message.split("=");

        System.out.println("username: " + user[1]);
        System.out.println("room: " + r[1]);
        System.out.println("message: " + msg[1]);

        server.updateRooms(user[1], r[1], msg[1]);
    }
}

