package network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPConnection {

    private final Socket socket;
    private final Thread thread;
    private final BufferedReader br;
    private final BufferedWriter bw;
    private final TCPConnectionListener listener;
    /**
     * Creating new TCP connections with the incoming parameters and
     * handling events
     *
     * @param listener instance of TCPConnectionListener
     * @param host host of the Server
     * @param port port of the Server
     *
     * @throws IOException if input or output streams failed
     */
    public TCPConnection(TCPConnectionListener listener, String host, int port) throws IOException {
        this(listener, new Socket(host, port));
    }
    public TCPConnection(final TCPConnectionListener listener, Socket socket) throws IOException {
        this.socket = socket;
        this.listener = listener;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                listener.connectionHandling(TCPConnection.this);
                while (!thread.isInterrupted())
                    listener.receiveHandling(TCPConnection.this, br.readLine());
                } catch (IOException e) {
                    listener.exceptionHandling(TCPConnection.this, e);
                } finally {
                    listener.disconnectionHandling(TCPConnection.this);
                }
            }
        });

        thread.start();
    }

    public synchronized void sendMsg(String msg){
        try {
            bw.write(msg+"\r\n");
            bw.flush();
        } catch (IOException e) {
           listener.exceptionHandling(this, e);
           disconnect();
        }
    }
    public synchronized void disconnect(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.exceptionHandling(this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection ip: " + socket.getInetAddress()+" port: "+socket.getPort();
    }
}

