package eu.diversify.ffbpg.sgh.model;

import static eu.diversify.ffbpg.BPGraph.average;
import eu.diversify.ffbpg.utils.FileUtils;
import java.io.File;

/**
 *
 * @author ffl
 */
public class DataExportUtils {
    
    public static String dataFileWithAverage(int[][] data) {
        StringBuilder b = new StringBuilder();
        b.append("# Data for " + data.length + " runs. Last column is the average.\n");
        double[] average = average(data);
        for (int l = 0; l < average.length; l++) {
            b.append(l);
            b.append("\t");
            for (int c = 0; c < data.length; c++) {
                if (data[c].length > l) b.append(data[c][l]);
                else b.append(0);
                b.append("\t");
            }
            b.append(average[l]);
            b.append("\n");
        }
        return b.toString();
    }
    
    public static String gnuPlotScriptForData(int[][] data, String filename) {

        StringBuilder b = new StringBuilder();
        b.append("plot \\\n");
        for (int i = 0; i < data.length; i++) {
            b.append("\"" + filename + "\" using " + (i + 2) + " notitle with lines lc rgb 'grey', \\\n");
        }
        b.append("\"" + filename + "\" using " + (data.length + 2) + " title 'Average' with lines lw 2 lc rgb 'red', \\\n");
        return b.toString();
    }

    public static void writeGNUPlotScriptForData(int[][] data, File out_dir, String filename) {
        if (!(out_dir != null && out_dir.exists() && out_dir.isDirectory())) {
            out_dir = FileUtils.createTempDirectory();
        }
        FileUtils.writeTextFile(out_dir, filename + ".dat", dataFileWithAverage(data));
        FileUtils.writeTextFile(out_dir, filename + ".plt", gnuPlotScriptForData(data, filename + ".dat"));

    }
    
    public static void writeGNUPlotScriptForDouble(double[] data, File out_dir, String filename) {
        if (!(out_dir != null && out_dir.exists() && out_dir.isDirectory())) {
            out_dir = FileUtils.createTempDirectory();
        }
        double avg = 0;
        // Write the data to a file
        StringBuilder b = new StringBuilder();
        for (double d : data) {
            b.append(d); b.append("\n");
            avg += d;
        }
        avg /= data.length;
        FileUtils.writeTextFile(out_dir, filename + "_robustness.dat" , b.toString());
        
        // Write the gnuplot to a file
        b = new StringBuilder();
        b.append("set title '" + filename + ": Robustness index for " + data.length + " runs (avg = "+ avg + ")'\n");
        b.append("set xlabel 'Run'\n");
        b.append("set ylabel 'Robusness index (%)'\n");
        b.append("set xrange [0:"+ (data.length-1) +"]\n");
        b.append("set yrange [0:100]\n");
        b.append("plot " + "\"" + filename + "_robustness.dat\" using 1 notitle with line\n" );
        FileUtils.writeTextFile(out_dir, filename + "_robustness.plt", b.toString());
        
    }
}
