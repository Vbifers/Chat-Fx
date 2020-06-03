package network;

public interface TCPConnectionListener {
    void connectionHandling(TCPConnection connection);
    void disconnectionHandling(TCPConnection connection);
    void exceptionHandling(TCPConnection connection, Exception e);
    void receiveHandling(TCPConnection connection, String msg);
}
