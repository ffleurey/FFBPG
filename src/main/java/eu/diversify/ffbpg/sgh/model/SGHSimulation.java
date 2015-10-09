
package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.Simulation;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class SGHSimulation {
    
    public final int NEIGHBORHOOD_SIZE = 15; // 15% of the servers are randomly visible to clients at each simulation step
    
    public final int NB_EXTINCTIONS = 100; // Number of random extinction sequences to calculate robustness at each steps
    
    boolean smart_servers;
    boolean smart_clients;
    
    boolean evolve_servers;
    boolean evolve_clients;
    
    
    SGHSystem system;
    ArrayList<SGHSimulationStep> steps;
    
    int current_step = 0;
    int delta_links = 0;
    int delta_features = 0;
    
    public SGHSimulation(SGHSystem system, boolean evolve_servers, boolean evolve_clients, boolean smart_servers, boolean smart_clients) {
        this.system = system.deep_clone();
        this.smart_clients = smart_clients;
        this.smart_servers = smart_servers;
        this.evolve_servers = evolve_servers;
        this.evolve_clients = evolve_clients;
    }
    
    
    public void startSimulation(int total_steps) {
        initialize_simulation();
        while(current_step < total_steps) {
            SGHSimulationStep step = run_step();
            steps.add(step);
            System.out.println(step.toString());
        }
    }
    
    private void initialize_simulation() {
        steps = new ArrayList<SGHSimulationStep>();
        current_step = 0;
        SGHSimulationStep step = new SGHSimulationStep(current_step);
        step.extinctions = system.computeRandomExtinctionSequence(NB_EXTINCTIONS);
        step.avg_seq = SGHExtinctionSequence.averageExtinctionSequences(step.extinctions);
        step.robustness = SGHExtinctionSequence.averageRobustnessIndex(step.avg_seq);
        steps.add(step);
        System.out.println(step.toString());
    }
    
    
    private SGHSimulationStep run_step() {
        current_step++;
        SGHSimulationStep step = new SGHSimulationStep(current_step);
        
        ArrayList<SGHServer> randomized_servers = (ArrayList<SGHServer>)system.servers.clone();
        ArrayList<SGHClientApp> randomized_clients = (ArrayList<SGHClientApp>)system.clients.clone();
        
        // Calculate a random neighborhood for each client
        Hashtable<SGHClientApp, ArrayList<SGHServer>> neighborhood = new Hashtable<SGHClientApp, ArrayList<SGHServer>>();
        for (SGHClientApp client : system.clients) {
            // Compute a random neigborhood of platforms
            ArrayList<SGHServer> neigbors = new ArrayList<SGHServer>();
            Collections.shuffle(randomized_servers);
            for (int i=0; i<(randomized_servers.size()*NEIGHBORHOOD_SIZE)/100; i++) {
                neigbors.add(randomized_servers.get(i));
            }
            neighborhood.put(client, neigbors);
        }
        
        // First adaptation step for clients: swap or remove links
        Collections.shuffle(randomized_clients);
        for (SGHClientApp client : randomized_clients) {
            delta_links += run_step1_for_client(step, client, neighborhood.get(client));
        }
        
        // Calulate server connections
        Hashtable<SGHServer, ArrayList<SGHClientApp>> links = new Hashtable<SGHServer, ArrayList<SGHClientApp>>();
        for (SGHClientApp client : system.clients) {
            for (SGHServer s : client.getLinks()) {
                if (!links.containsKey(s)) {
                    links.put(s, new ArrayList<SGHClientApp>());
                }
                links.get(s).add(client);
            }
        }
        // Add some empty lists for servers with no connections
        for (SGHServer s : system.servers) {
            if (!links.containsKey(s)) links.put(s, new ArrayList<SGHClientApp>());
        }
        
        // First adaptation step for servers: Drop features (consider servers in a random order)
        Collections.shuffle(randomized_servers);
        for (SGHServer s : randomized_servers) {
            delta_features += run_step1_for_server(step, s, links.get(s));
        }
        
         // Second adaptation step for servers: Add features (consider servers in a random order)
        Collections.shuffle(randomized_servers);
        for (SGHServer s : randomized_servers) {
            if(delta_features >= 0) break; // only add as many features as we have removed (keep cost constant)
            delta_features += run_step2_for_server(step, s, links.get(s));
        }
        
        // Second adaptation step for clients: Add as many links as we have removed earlier (keep cost constant)
        Collections.shuffle(randomized_clients);
        for (SGHClientApp client : randomized_clients) {
            if(delta_links >= 0) break; 
            delta_links += run_step2_for_client(step, client, neighborhood.get(client));
        }
        
        step.delta_features = delta_features;
        step.delta_links = delta_links;
        
        // Caluculate the robustness of the graph
        step.extinctions = system.computeRandomExtinctionSequence(NB_EXTINCTIONS);
        step.avg_seq = SGHExtinctionSequence.averageExtinctionSequences(step.extinctions);
        step.robustness = SGHExtinctionSequence.averageRobustnessIndex(step.avg_seq);
     
        return step;
    }
    
    private int run_step1_for_server(SGHSimulationStep step, SGHServer server, ArrayList<SGHClientApp> connections) {
        if (!evolve_servers) return 0;
        ArrayList<SGHServerAdaptation> candidates = server.all_valid_remove_feature_adaptations(connections);
        step.server_adaptation_space += candidates.size();

         if (!candidates.isEmpty()) {             
             step.removed_features++;
             SGHServerAdaptation selected;
             if (smart_servers) selected =SGHServerAdaptation.pickAdaptationWeightedByFeaturesPopularity(candidates);
             else selected =SGHServerAdaptation.pickRandomAdaptation(candidates);
             return selected.execute();
         }
         return 0;
    }
    
    private int run_step2_for_server(SGHSimulationStep step, SGHServer server, ArrayList<SGHClientApp> connections) {
        if (!evolve_servers) return 0;
        ArrayList<SGHServerAdaptation> candidates = server.all_valid_add_feature_adaptations(connections);
        step.server_adaptation_space += candidates.size();
         // select a random adaptation
         Collections.sort(candidates);
         if (!candidates.isEmpty()) {
             step.added_features++;
             SGHServerAdaptation selected;
             if (smart_servers) selected =SGHServerAdaptation.pickAdaptationWeightedByFeaturesPopularity(candidates);
             else selected =SGHServerAdaptation.pickRandomAdaptation(candidates);
             return selected.execute();
         }
         return 0;
    }
    
    private int run_step1_for_client(SGHSimulationStep step, SGHClientApp client, ArrayList<SGHServer> neigbors) {
        if (!evolve_clients) return 0;
        // consider only adaptationw which do not increase the number of links
        ArrayList<SGHClientAdaptation> candidates = client.all_valid_swap_link_adaptations(neigbors);
        candidates.addAll(client.all_valid_remove_link_adaptations());
        step.client_adaptation_space += candidates.size();
        // select a random adaptation
         Collections.shuffle(candidates);
         if (!candidates.isEmpty()) {
             
             SGHClientAdaptation selected;
             if (smart_clients) selected = SGHClientAdaptation.pickAdaptationWeightedByFitness(candidates);
             else selected =SGHClientAdaptation.pickRandomAdaptation(candidates);
             int result = 0;
             if (selected != null) {
                 result = selected.execute();
                 step.added_links += result+1;
                 step.removed_links += 1;
             }

             return result;

         }
         
        return 0;
    }
    
    private int run_step2_for_client(SGHSimulationStep step, SGHClientApp client, ArrayList<SGHServer> neigbors) {
        if (!evolve_clients) return 0;
        // consider only adaptationw which do not increase the number of links
        ArrayList<SGHClientAdaptation> candidates = client.all_valid_add_link_adaptations(neigbors);
        step.client_adaptation_space += candidates.size();
        // select a random adaptation
         Collections.shuffle(candidates);
         if (!candidates.isEmpty()) {
             SGHClientAdaptation selected;
             if (smart_clients) selected = SGHClientAdaptation.pickAdaptationWeightedByFitness(candidates);
             else selected =SGHClientAdaptation.pickRandomAdaptation(candidates);
             int result = 0;
             if (selected != null) {
                 result = selected.execute();
                 step.added_links += 1;
             }
             return result;
         }
         
        return 0;
    }
    
    public void exportRobustnessData(String name, File folder) {
        double[] robustness = new double[steps.size()];
        for(int i=0; i<steps.size(); i++) {
            robustness[i] = steps.get(i).robustness*100;
        }
        DataExportUtils.writeGNUPlotScriptForDouble(robustness, folder, name);
    }
    
    
}
