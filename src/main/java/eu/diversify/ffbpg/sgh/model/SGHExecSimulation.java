package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by franck on 29.02.16.
 */
public class SGHExecSimulation {

    SGHSystem system;
    ArrayList<SGHExecSimulationStep> steps;
    int nb_failures;

    int total_request = 0;
    int failed_requests = 0;
    double failure_rate = 0;

    public SGHExecSimulation(SGHSystem init_system, int nb_failures, int nb_steps) {

        this.nb_failures = nb_failures;
        this.system = init_system.deep_clone();
        steps = new ArrayList<SGHExecSimulationStep>();
        ArrayList<SGHServer> all_servers = (ArrayList<SGHServer>)system.servers.clone();
        for (int i = 0; i<nb_steps; i++) {
            Collections.shuffle(all_servers);
            HashSet<SGHServer> dead = new HashSet<SGHServer>();
            for(int j=0; j<nb_failures;j++) {
                dead.add(all_servers.get(j));
            }
            steps.add(new SGHExecSimulationStep(dead));
        }
    }

    public void execute() {

        for (SGHExecSimulationStep step : steps) {
            step.compute_for_all_requests(system);
            total_request += step.total_requests;
            failed_requests += step.failed_requests;
        }

        failure_rate = failed_requests * 100;
        failure_rate /= total_request;

        System.out.println("SIM " + failed_requests + " / " + total_request + " = " + failure_rate + "% (" + steps.size() + " steps with " + nb_failures + " failures)");

    }
    
    public String toString() {
         return "" + nb_failures + "\t" + system.servers.size() + "\t" + failure_rate + "\t" + failed_requests + "\t" + total_request;
    }
    
    
    public static void writeResults(File folder, ArrayList<SGHExecSimulation> sims) {
        StringBuilder sb = new StringBuilder();
        sb.append("#srv_dead\t#srv\t%fail\t#fail\t#requests\n");
        for (SGHExecSimulation s : sims) {
            sb.append(s.toString());
            sb.append("\n");
        }
        FileUtils.writeTextFile(folder, "ExecutionSim.txt", sb.toString());
    }

}
