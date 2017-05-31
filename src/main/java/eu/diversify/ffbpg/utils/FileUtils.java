package eu.diversify.ffbpg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

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
    
    
    public static String readTextFile(File directory, String filename) {
        
        StringBuilder res = new StringBuilder();
        BufferedReader br = null;
        try {
        File initgraph = new File(directory, filename);
            br = new BufferedReader(new FileReader(initgraph));
            while(br.ready()) res.append(br.readLine());
            br.close();
            } catch (Exception ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(MainStatsCollector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res.toString();
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
