/**
 * 
 */
package com.rayzr522.youtubeuploadwrapper;

import java.io.IOException;

/**
 * @author Rayzr
 *
 */
public class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Main main = new Main();
        try {
            main.start();
        } catch (IOException e) {
            System.err.println("Failed to start Main! Exiting...");
            e.printStackTrace();
            System.exit(1);
        }

    }

}
