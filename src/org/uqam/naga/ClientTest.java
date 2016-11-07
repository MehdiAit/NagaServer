package org.uqam.naga;

import java.net.*;
import java.io.*;

import static java.lang.Thread.sleep;

/**
 * Created by overpex on 04/11/16.
 */

public class ClientTest {

    public void testCommunication(String [] args)
    {
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            System.out.println("CLIENT : Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("START");
            sleep(2000);
            client.close();
            // do something

        }catch(IOException e) {
            e.printStackTrace();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}