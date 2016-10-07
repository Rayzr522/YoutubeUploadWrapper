
package com.rayzr522.youtubeuploadwrapper;

import java.awt.Font;

import javax.swing.JTextArea;

public class TextArea extends JTextArea {

    /**
     * 
     */
    private static final long serialVersionUID = -3491322175164686748L;

    public TextArea(String font, int size) {

        setFont(new Font(font, 0, size));

    }

    public void print(Object msg) {
        setText(getText() + msg);
    }

    public void println(Object msg) {
        print(msg + "\n");
    }

}
