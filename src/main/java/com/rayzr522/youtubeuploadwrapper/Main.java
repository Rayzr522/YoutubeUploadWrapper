/**
 * 
 */
package com.rayzr522.youtubeuploadwrapper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 * @author Rayzr
 *
 */
public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -6478546758792633984L;

    private TextField         name;
    private TextField         category;
    private TextField         tags;

    private TextArea          description;

    private File              videoFile;
    private File              thumbnailFile;

    private JLabel            missing;

    private File              logFile;

    private Config            config;

    public Main() {

        config = new Config("settings.conf");

        setSize(600, 800);

        // Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        p.add(name = new TextField("Name: "));
        p.add(category = new TextField("Category: "));
        p.add(tags = new TextField("Tags: "));

        JLabel label = new JLabel("Description: ");
        description = new TextArea("Arial", 11);
        description.setText("Some description here");

        label.setLabelFor(description);
        p.add(label);
        p.add(description);

        p.add(new Button("Upload", this::startUpload));

        p.add(missing = new JLabel(""));

        missing.setFont(new Font("Arial", Font.BOLD, 15));
        missing.setMinimumSize(new Dimension(400, 50));

        // Lay out the panel.
        SpringUtilities.makeCompactGrid(p, 7, 1, // rows, cols
                6, 6,        // initX, initY
                6, 10);       // xPad, yPad

        // Now let's set the defaults
        category.setText(config.get("defaultCategory"));
        tags.setText(config.get("defaultTags"));

        add(p);

    }

    public void start() throws IOException {

        validateInput();
        setLocationRelativeTo(null);
        setVisible(true);

        logFile = new File(System.getProperty("user.home"), "YoutubeUploadWrapper.log");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        System.out.println(logFile.getAbsolutePath());

    }

    public void startUpload(ActionEvent e) {

        if (validateInput()) {

            // Let's get this baby rolling!
            alert("Press OK to select a video & thumbnail");

            // Have them select some files
            videoFile = getFile("Video File", "mp4", "mov");
            thumbnailFile = getFile("Thumbnail File", "png", "jpg", "jpeg");

            // Make sure they actually chose stuff...
            if (!validateFiles()) {
                alert("You have to select valid files!");
                resetFiles();
                return;
            }

            ProcessBuilder builder = new ProcessBuilder("youtube-upload", "--title=\"" + name.getText() + "\"", "--description=\"" + description.getText() + "\"", "--category=" + category.getText(),
                    "--tags=\"" + tags.getText() + "\"", "--thumbnail=\"" + thumbnailFile.getAbsolutePath() + "\"", "\"" + videoFile.getAbsolutePath() + "\"");

            alert("Press OK to get the command:");

            JFrame frame = new JFrame("Command");
            frame.add(new JTextArea(concat(builder.command(), " ")));
            frame.setMinimumSize(new Dimension(400, 400));

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            /*
             * builder.redirectInput(logFile);
             * builder.redirectErrorStream(true);
             * 
             * try { builder.start(); } catch (IOException e1) {
             * e1.printStackTrace(); alert(
             * "Something horrible went wrong and the program was unable to start the CLI command!"
             * ); alert("The program will now exit"); System.exit(1); }
             */

        } else {

            // Yup, you failed
            alert("Please fill in all fields!");
        }

    }

    private void resetFiles() {
        videoFile = null;
        thumbnailFile = null;
    }

    /**
     * @return
     */
    private boolean validateFiles() {
        return videoFile != null && thumbnailFile != null;
    }

    private File getFile(String title, String... extensions) {
        FileDialog dialog = new FileDialog(this, title, FileDialog.LOAD);
        dialog.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().matches(".*\\.(" + concat(extensions, "|") + ")");
            }
        });
        dialog.setVisible(true);
        if (dialog.getFile() == null) {
            return null;
        }
        return new File(dialog.getDirectory(), dialog.getFile());
    }

    private void alert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public boolean validateInput() {
        StringBuilder needed = new StringBuilder();

        if (!check(name)) {
            needed.append("name, ");
        }

        if (!check(category)) {
            needed.append("category, ");
        }

        if (!check(tags)) {
            needed.append("tags, ");
        }

        if (!check(description)) {
            needed.append("description, ");
        }

        String out = needed.toString();
        if (!check(out)) {
            missing.setText("Nothing missing");
            missing.setForeground(Color.BLACK);
            return true;
        }

        out = out.substring(0, out.lastIndexOf(","));
        missing.setText("Missing: " + out);
        missing.setForeground(Color.RED);
        return false;

    }

    private boolean check(TextArea comp) {
        return check(comp.getText());
    }

    private boolean check(TextField comp) {
        return check(comp.getText());
    }

    private boolean check(String text) {
        return text.trim().length() > 0;
    }

    /**
     * Concatenates all objects in the list with the given filler. Example:<br>
     * <br>
     * <code>ArrayUtils.concat(new String[] {"Hello", "world!", "How", "are", "you?"}, "_");</code>
     * <br>
     * <br>
     * Would return {@code "Hello_world!_How_are_you?"}
     *
     * @param list the list
     * @param filler the String to concatenate the objects with
     *
     * @return The concatenated String
     */
    public static String concat(List<?> list, String filler) {
        return concat(list.toArray(), filler);
    }

    public static String concat(Object[] arr, String filler) {
        return Arrays.stream(arr).map(Object::toString).collect(Collectors.joining(filler));
    }

}
