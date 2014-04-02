package eu.diversify.ffbpg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ffl
 */
public class FileUtils {
    
    
    public static void writeTextFile(File directory, String filename, String content) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new File(directory, filename));
            out.println(content);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }
    
    public static File createTempDirectory() {
        File temp = null;

        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

            if (!(temp.delete())) {
                throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
            }

            if (!(temp.mkdir())) {
                throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
            }
            temp.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (temp);
    }
}
