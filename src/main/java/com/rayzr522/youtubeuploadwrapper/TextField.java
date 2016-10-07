
package com.rayzr522.youtubeuploadwrapper;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class TextField extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1076222345645773786L;

    private JLabel            label;
    private JTextField        field;

    public TextField(String label) {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(this.label = new JLabel(label, JLabel.TRAILING));
        add(this.field = new JTextField());

        this.label.setLabelFor(this.field);

    }

    /**
     * @param offs
     * @param len
     * @return
     * @throws BadLocationException
     * @see javax.swing.text.JTextComponent#getText(int, int)
     */
    public String getText(int offs, int len) throws BadLocationException {
        return field.getText(offs, len);
    }

    /**
     * @return
     * @see javax.swing.text.JTextComponent#getText()
     */
    public String getText() {
        return field.getText();
    }

    /**
     * @param t
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    public void setText(String t) {
        field.setText(t);
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label.getText();
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label.setText(label);
    }

}
