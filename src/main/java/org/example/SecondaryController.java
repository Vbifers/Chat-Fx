package org.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import network.TCPConnection;
import network.TCPConnectionListener;

/**
 * Describes customer interface and controller for main chat window
 */
public class SecondaryController implements TCPConnectionListener {

    private TCPConnection tcpConnection;
    String host = "127.0.0.1";
    int port = 8189;
    private User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textInput;

    @FXML
    private Button signOutButton;

    @FXML
    void initialize() {
        TCPConnection tcpConnection;
        try {
            tcpConnection = new TCPConnection(this, host, port);
        } catch (IOException e) {
            System.out.println("Connection to server failed\n");
            return;
        }

        textInput.setOnAction(actionEvent -> {
            printMsg(user.getUsername()+": "+textInput.getText());
            textInput.setText("");
        });

        signOutButton.setOnAction(actionEvent -> {
            tcpConnection.disconnect();
            signOutButton.getScene().getWindow().hide();
        });
    }

    @Override
    public void connectionHandling(TCPConnection connection) {
        printMsg("Connect to server");
    }

    @Override
    public void disconnectionHandling(TCPConnection connection) {
        printMsg("Disconnect from server");
    }

    @Override
    public void exceptionHandling(TCPConnection connection, Exception e) {
        printMsg("Connection error: "+e);
    }

    @Override
    public void receiveHandling(TCPConnection connection, String msg) {
        printMsg(msg);
    }

    private void printMsg(String msg){
        if(!msg.equals("")){
            textArea.appendText(msg+"\n\r");
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
