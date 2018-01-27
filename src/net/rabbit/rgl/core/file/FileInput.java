package net.rabbit.rgl.core.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public final class FileInput {

    private FileInput() {
    }

    public static String loadString(File file) {
        InputStream in;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
		}

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static BufferedImage loadImage(File file) {
        InputStream in;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
		}

        try {
            BufferedImage image = ImageIO.read(in);

            return image;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}