package org.uqam.naga;

import java.net.*;
import java.io.*;

public class NagaServer extends Thread{

    private final static int DEFAULT_PORT = 3000;
    private final static int DEFAULT_TIMEOUT = (int) 7.2e+6; // Time in milliseconds (two hours)
    private static boolean SERVER_STATE = false;
    private ServerSocket listenSocket;

    public NagaServer(int port, int timeOut) throws IOException {
        listenSocket = new ServerSocket(port);
        listenSocket.setSoTimeout(timeOut);
        SERVER_STATE = true;
    }

    public NagaServer() throws IOException
    {
        listenSocket = new ServerSocket(DEFAULT_PORT);
        listenSocket.setSoTimeout(DEFAULT_TIMEOUT);
        SERVER_STATE = true;
    }

    /**
     * A multi-threaded server
     * Each Thread is instantiated with a client socket
     * The measurement part is done by the Measurement class which extends Thread
     * This allow multiple scenarios connection to the server.
     */
    @Override
    public void run() {
        System.out.println("NAGA VIPER Server : Started");
        while (SERVER_STATE) // Less than the number of run passed as arg
        {
            try {
                // Waiting for a client connection with the server.
                Thread measurementRun = new Measurement(listenSocket.accept());
                measurementRun.start();

            }catch (SocketTimeoutException t)
            {
                SERVER_STATE = false;
                System.out.print("Time out");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
