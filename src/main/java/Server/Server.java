package Server;

import network.TCPConnection;
import network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Accept the client Sockets and put it to the socket's list
 * then handle all of them by creating new TCP connections
 *
 * @throws IOException if connection failed
 */
public class Server implements TCPConnectionListener {
    private ArrayList<TCPConnection> listConnections = new ArrayList<TCPConnection>();
    private Server(){
        System.out.println("Server.Server running...");
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            while (true) {
                TCPConnection connection= new TCPConnection(this, serverSocket.accept());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

    //handlers are  synchronized. There can be more than one connection in a moment
    @Override
    public synchronized void connectionHandling(TCPConnection connection) {
        listConnections.add(connection);
        sendToAllConnections(connection, "Connected "+connection);
    }

    @Override
    public synchronized void disconnectionHandling(TCPConnection connection) {
        listConnections.remove(connection);
        sendToAllConnections(connection, "Disconnected "+connection);
    }

    @Override
    public synchronized void exceptionHandling(TCPConnection connection, Exception e) {
        System.out.println(connection+": "+e);
    }

    @Override
    public synchronized void receiveHandling(TCPConnection connection, String msg) {
        sendToAllConnections(connection, msg);
    }

    private void sendToAllConnections(TCPConnection connection, String msg) {
        for(TCPConnection con:listConnections){
            con.sendMsg(msg);
        }
    }
}
