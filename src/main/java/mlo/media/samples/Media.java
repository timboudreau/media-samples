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
 * Enum of media file samples for use in selenium tests.
 *
 * @author Tim Boudreau
 */
public enum Media {

    MPEG_4("000B.mp4", 8720308),
    MPEG_1("awful-mpeg1.mpg", 161177),
    PNG("ball-unc.png", 89707),
    GIF("gif.gif", 1512),
    GARAGE_BAND_M4A("inergy-2.m4a", 7698985),
    TIFF("MildredScarcliff1980.tiff", 3571332),
    OGG_AUDIO("mil.oga", 873954),
    AVI_MS_VIDEO_1("msvideo1-avi.avi", 962334),
    WAV("Mama-Lolo.wav", 19553064);

    private final String name;
    private final int length;
    private Path file;

    Media(String name, int length) {
        this.name = name;
        InputStream in = toInputStream();
        try {
            if (in == null) {
                throw new Error(name + " is not on the classpath relative to : " + Media.class.getName());
            }
        } finally {
            try {
                in.close();
            } catch (Exception ex) {
                throw new Error(ex);
            }
        }
        this.length = length;
    }

    public long length() {
        return length;
    }

    public String fileName() {
        return name;
    }

    public synchronized Path toPath() throws IOException {
        if (file != null) {
            return file;
        }
        file = new File(System.getProperty("java.io.tmpdir")).toPath().resolve(name);
        if (!Files.exists(file)) {
            copyBytesFromClasspath(file);
        }
        return file;
    }

    public synchronized InputStream toInputStream() {
        return Media.class.getResourceAsStream(name);
    }

    public String toString() {
        return fileName();
    }

    private void copyBytesFromClasspath(Path file) throws IOException {
        try (InputStream in = toInputStream()) {
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void main(String... args) throws IOException {
        for (Media m : Media.values()) {
            System.out.println(m + " -> " + m.toPath());
        }
    }
}
