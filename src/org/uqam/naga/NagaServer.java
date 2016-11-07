package org.uqam.naga;

import java.net.*;
import java.io.*;

public class NagaServer extends Thread{

    private final static int DEFAULT_PORT = 3000;
    private final static int DEFAULT_TIMEOUT = 70000;
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

    @Override
    public void run() {
        while (SERVER_STATE) // Less than the number of run passed as arg
        {
            try {
                System.out.println("Naga Viper Server Start - Time out : " + listenSocket.getSoTimeout());

                // Waiting for a client connection with the server.
                Socket server = listenSocket.accept();
                System.out.println(String.format("SERVER - Address :%s", server.getRemoteSocketAddress()));

                // Get data from the clients
                DataInputStream inStream = new DataInputStream(server.getInputStream());
                String signal = inStream.readUTF();
                System.out.println(String.format("SERVER - Data received : %s", signal));

                if (signal.equals("START")){
                    System.out.println("Calabash Start");
                    Thread tmp = new Thread()
                    {
                        @Override
                        public void run() {
                            for(int i = 0;i < 20; i++)
                            {
                                System.out.println("Start Measurements");
                                try {
                                    sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    tmp.start();
                    tmp.join();
                }

                System.out.println("End the N run the next will start soon");


            }catch (SocketTimeoutException t)
            {
                SERVER_STATE = false;
                System.out.print("Time out");
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
