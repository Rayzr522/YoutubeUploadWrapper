
package com.rayzr522.youtubeuploadwrapper;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {

    /**
     * 
     */
    private static final long serialVersionUID = 9160459947810025430L;

    public Button(String label, ActionListener listener) {
        super(label);
        addActionListener(listener);
    }

}
