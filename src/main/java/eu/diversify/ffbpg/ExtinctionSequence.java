package eu.diversify.ffbpg;

import eu.diversify.ffbpg.utils.FileUtils;
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
public class ExtinctionSequence {

    // Data for this extinction sequence
    private int[] data;
    private Platform[] platforms;

    public ExtinctionSequence(int n_platforms, int n_applications) {
        this.data = new int[n_platforms + 1];
        this.data[0] = n_applications;
        this.platforms = new Platform[n_platforms];
    }

    public void extinctionStep(int killedPlatform, int aliveApplications, Platform killed) {
        data[killedPlatform] = aliveApplications;
        platforms[killedPlatform - 1] = killed;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("# Extinction sequence for " + data[0] + " applications  and " + (data.length - 1) + " platforms.\n");
        b.append("# This data corresponds to a single extiction sequences.\n");
        for (int i = 0; i < data.length; i++) {
            b.append(i);
            b.append("\t");
            b.append(data[i]);
            b.append("\n");
        }
        return b.toString();
    }

    public static double[] averageExtinctionSequences(ExtinctionSequence[] sequences) {
        assert sequences.length > 0; // There should be at least one sequence
        int data_size = sequences[0].data.length;
        for (int i = 0; i < sequences.length; i++) {
            assert sequences[i].data.length == data_size; // All should have the same size
            assert sequences[i].data[0] == sequences[0].data[0]; // all should have the same initial conditions
        }
        double[] result = new double[data_size];
        for (int i = 0; i < data_size; i++) {
            for (int j = 0; j < sequences.length; j++) {
                result[i] += sequences[j].data[i];
            }
            result[i] /= sequences.length;
        }
        return result;
    }

    public static String averageExtinctionSequencesToString(ExtinctionSequence[] sequences) {
        double[] average = averageExtinctionSequences(sequences);
        StringBuilder b = new StringBuilder();
        b.append("# Extinction sequence for " + sequences[0].data[0] + " applications  and " + (sequences[0].data.length - 1) + " platforms.\n");
        b.append("# This is an average for " + sequences.length + " different extinction sequences.\n");
        for (int i = 0; i < average.length; i++) {
            b.append(i);
            b.append("\t");
            b.append(average[i]);
            b.append("\n");
        }
        return b.toString();
    }

    public static String allExtinctionSequencesToString(ExtinctionSequence[] sequences) {
        double[] average = averageExtinctionSequences(sequences);
        StringBuilder b = new StringBuilder();
        b.append("# Extinction sequence for " + sequences[0].data[0] + " applications  and " + (sequences[0].data.length - 1) + " platforms.\n");
        b.append("# This is the data and average for " + sequences.length + " different extinction sequences.\n");
        for (int i = 0; i < average.length; i++) {
            b.append(i);
            for (int j = 0; j < sequences.length; j++) {
                b.append("\t");
                b.append(sequences[j].data[i]);
            }
            b.append("\t");
            b.append(average[i]);
            b.append("\t");
            b.append((sequences[0].data.length - 1 - i));
            b.append("\n");
        }
        return b.toString();
    }

    public static String gnuPlotScriptForAll(ExtinctionSequence[] sequences, String filename) {
        StringBuilder b = new StringBuilder();
        b.append("# Extinction sequence for " + sequences[0].data[0] + " applications  and " + (sequences[0].data.length - 1) + " platforms.\n");
        b.append("plot \\\n");
        for (int i = 0; i < sequences.length; i++) {
            b.append("\"" + filename + "\" using " + (i + 2) + " notitle with lines lc rgb 'grey', \\\n");
        }
        b.append("\"" + filename + "\" using " + (sequences.length + 2) + " title 'Average' with lines lw 2 lc rgb 'red', \\\n");
        b.append("\"" + filename + "\" using " + (sequences.length + 3) + " title 'Reference' with line lt 'dashed' lw 2 lc rgb 'black'");
        return b.toString();
    }

    public static void writeGNUPlotScriptForAll(ExtinctionSequence[] sequences, File out_dir, String filename) {
        if (!(out_dir != null && out_dir.exists() && out_dir.isDirectory())) {
            out_dir = FileUtils.createTempDirectory();
        }
        FileUtils.writeTextFile(out_dir, filename + ".dat" , allExtinctionSequencesToString(sequences));
        FileUtils.writeTextFile(out_dir, filename + ".plt", gnuPlotScriptForAll(sequences, filename+ ".dat"));
        
    }

    

}
