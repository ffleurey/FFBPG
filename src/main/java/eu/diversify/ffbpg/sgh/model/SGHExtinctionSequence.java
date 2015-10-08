package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.*;
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
public class SGHExtinctionSequence {

    // Data for this extinction sequence
    private int[] data;
    private double[] percentage_data;

    public SGHExtinctionSequence(int n_platforms, int n_applications) {
        this.data = new int[n_platforms + 1];
        this.percentage_data = new double[n_platforms + 1];
        this.data[0] = n_applications;
    }

    public void extinctionStep(int extinction_step, int nb_alive, double percentage_alive) {
        data[extinction_step] = nb_alive;
        percentage_data[extinction_step] = percentage_alive;
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

    public static double[] averageExtinctionSequences(SGHExtinctionSequence[] sequences) {
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
    
    public static double averageRobustnessIndex(double[] average_extinction) {
        int steps = average_extinction.length;
        if (steps < 1 || steps > average_extinction.length) {
            System.err.println("ERROR PRE in averageRobustnessIndex steps = " + steps);
            return 0;
        }
        
        double na = average_extinction[0];
        double np = average_extinction[steps-1];

        double total = 0;

        for (int p=0; p<steps; p++) {
            total += average_extinction[p];
        }
        double result = total / (steps * na);
        return result;
    }

    public static String allExtinctionSequencesToString(SGHExtinctionSequence[] sequences) {
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

    public static String gnuPlotScriptForAll(SGHExtinctionSequence[] sequences, String filename) {
        StringBuilder b = new StringBuilder();
        b.append("# Extinction sequence for " + sequences[0].data[0] + " applications  and " + (sequences[0].data.length - 1) + " platforms.\n");
        
        b.append("set title '"+filename+": Extinction sequences (avg robustness = <AVG>%)'\n");
        b.append("set xlabel 'Platformed Killed'\n");
        b.append("set ylabel 'Applications Alive'\n");
        b.append("set xrange [0:"+(sequences[0].data.length - 1)+"]\n");
        b.append("set yrange [0:"+sequences[0].data[0]+"]\n");
        
        b.append("plot \\\n");
        for (int i = 0; i < sequences.length; i++) {
            b.append("\"" + filename + "\" using " + (i + 2) + " notitle with lines lc rgb 'grey', \\\n");
        }
        b.append("\"" + filename + "\" using " + (sequences.length + 2) + " title 'Average' with lines lw 2 lc rgb 'red', \\\n");
        //b.append("\"" + filename + "\" using " + (sequences.length + 3) + " title 'Reference' with line lt 'dashed' lw 2 lc rgb 'black'");
        return b.toString();
    }

    public static void writeGNUPlotScriptForAll(SGHExtinctionSequence[] sequences, File out_dir, String filename) {
        if (!(out_dir != null && out_dir.exists() && out_dir.isDirectory())) {
            out_dir = FileUtils.createTempDirectory();
        }
        FileUtils.writeTextFile(out_dir, filename + ".dat" , allExtinctionSequencesToString(sequences));
        FileUtils.writeTextFile(out_dir, filename + ".plt", gnuPlotScriptForAll(sequences, filename+ ".dat"));
        
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
        b.append("set yrange [-100:100]\n");
        b.append("plot " + "\"" + filename + "_robustness.dat\" using 1 notitle with line\n" );
        FileUtils.writeTextFile(out_dir, filename + "_robustness.plt", b.toString());
        
    }

    

}
