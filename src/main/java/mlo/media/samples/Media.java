/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlo.media.samples;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Tim Boudreau
 */
public enum Media {

    MPEG_4("000B.mp4"),
    MPEG_1("awful-mpeg1.mpg"),
    PNG("ball-unc.png"),
    GIF("gif.gif"),
    GARAGE_BAND_M4A("inergy-2.m4a"),
    TIFF("MildredScarcliff1980.tiff"),
    AVI_MS_VIDEO_1("msvideo1-avi.avi"),
    WAV("Mama-Lolo.wav");

    private final String name;
    private Path file;

    Media(String name) {
        this.name = name;
    }

    public synchronized Path toPath() throws IOException {
        if (file != null) {
            return file;
        }
        file = new File(System.getProperty("java.io.tmpdir")).toPath().resolve(name);
        if (!Files.exists(file)) {
            System.out.println("COPY TO " + file);
            copyBytesFromClasspath(file);
        }
        return file;
    }

    public String toString() {
        return name;
    }

    private void copyBytesFromClasspath(Path file) throws IOException {
        try (InputStream in = Media.class.getResourceAsStream(name)) {
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void main(String... args) throws IOException {
        Path p = Media.PNG.toPath();
        System.out.println("GOT " + p);
    }
}
