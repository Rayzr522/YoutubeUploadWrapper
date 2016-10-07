/**
 * 
 */
package com.rayzr522.youtubeuploadwrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * @author Rayzr
 *
 */
public class Config {

    public static final String folder = "$HOME/.ytup/";

    static {
        File base = new File(expandPath(folder));
        if (!base.exists()) {
            try {
                base.mkdirs();
            } catch (Exception e) {
                System.err.println("Failed to create basic directory! Exiting...");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private Properties properties;
    private String     path;

    public Config(String path) {

        this.path = path;

        File file = getFile(path);
        if (!file.exists()) {
            saveDefault(path);
        }

        properties = new Properties();
        try {
            FileReader reader = new FileReader(file);
            properties.load(reader);
            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load config file with path '" + path + "'");
            e.printStackTrace();
        }

    }

    /**
     * @param path
     */
    private void saveDefault(String path) {
        URL url = getClass().getClassLoader().getResource(("./" + path).replace("/", File.separator));
        if (url == null) {
            throw new IllegalArgumentException("The resource at path '" + path + "' could not be found!");
        }

        File file = new File(url.getPath());

        if (!file.exists()) {
            throw new IllegalArgumentException("The resource at path '" + path + "' could not be found!");
        }

        File destination = getFile(path);

        try {
            if (!destination.exists()) {
                destination.createNewFile();
            }
            Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to save default file!");
            e.printStackTrace();
        }

    }

    public File getFile(String path) {
        return new File(expandPath(folder + path));
    }

    public String get(String path) {
        String property = properties.getProperty(path);
        System.out.println(property);
        return property;
    }

    public void set(String path, String value) {
        properties.setProperty(path, value);
    }

    public void save() {
        try {
            File file = getFile(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "This is the config file for YoutubeUploadWrapper");
            fos.close();
        } catch (Exception e) {
            System.err.println("Failed to save config file at path '" + path + "'");
            e.printStackTrace();
        }

    }

    public static String expandPath(String path) {
        return path.replace("/", File.separator).replace("$HOME", System.getProperty("user.home"));
    }

}
