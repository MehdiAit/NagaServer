package org.uqam.naga;

import java.io.IOException;

/**
 * Created by overpex on 04/11/16.
 */
public class main {

    private static String [] serverParam = {"localhost", "3000"};

    public static void main(String[] args) {
        try {
            Thread naga = new NagaServer();
            naga.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
