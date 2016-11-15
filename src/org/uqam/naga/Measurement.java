package org.uqam.naga;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by overpex on 14/11/16.
 */
public class Measurement extends Thread {

    private Socket calabashClient;

    public Measurement(Socket remote /*, Yocto Instance, Android Device */) {this.calabashClient = remote;}

    @Override
    public void run() {

        System.out.println(String.format("SERVER - Address :%s", calabashClient.getRemoteSocketAddress()));

        // Buffer size 1KB
        byte[] buffer = new byte[1024];
        int read = 0;

        try {
            while ((read = calabashClient.getInputStream().read(buffer)) > 0) {
                String signal = clientDataRead(buffer,read);

                if (signal.equals("START")){
                    System.out.println("Calabash Start");
                } else if(signal.equals("END")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("End the N run the next will start soon");

    }

    public String clientDataRead(byte[] buffer, int size)
    {
        byte[] redData = new byte[size];
        System.arraycopy(buffer, 0, redData, 0, size);
        try {
            return new String(redData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(-1);
            return "";
        }
    }
}
