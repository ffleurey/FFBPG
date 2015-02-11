package eu.diversify.ffbpg;

import eu.diversify.ffbpg.evolution.ApplicationLinksEvolutionScenario;
import eu.diversify.ffbpg.evolution.BalanceApplicationLinksEvolutionScenario;
import eu.diversify.ffbpg.evolution.BalancePlatformServicesEvolutionScenario;
import eu.diversify.ffbpg.evolution.InitializationEvolutionScenario;
import eu.diversify.ffbpg.evolution.PlatformServicesEvolutionScenario;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class Simulation {
    protected ArrayList<BPGraph> steps;
    protected Hashtable<BPGraph, ExtinctionSequence[]> extinctionSequences;
    
    protected BPGraph initial;
    protected String initial_parameters;
    protected String sim_parameters;

    public int getExtinction_sequence_runs() {
        return extinction_sequence_runs;
    }

    public void setExtinction_sequence_runs(int extinction_sequence_runs) {
        this.extinction_sequence_runs = extinction_sequence_runs;
    }

    public int getExtinction_sequence_percentage() {
        return extinction_sequence_percentage;
    }

    public void setExtinction_sequence_percentage(int extinction_sequence_percentage) {
        this.extinction_sequence_percentage = extinction_sequence_percentage;
    }
    
    /**
     * SIMULATION STRATEGIES
     */
    
    ApplicationLinksEvolutionScenario link_s;    
    PlatformServicesEvolutionScenario serv_s;
    InitializationEvolutionScenario init_s = null;
    
    BalanceApplicationLinksEvolutionScenario balance_links = null;
    BalancePlatformServicesEvolutionScenario balance_services = null;

    int extinction_sequence_runs = 50;
    int extinction_sequence_percentage = 100;
    
    
    public void run_init() {
        
        extinctionSequences = new Hashtable<BPGraph, ExtinctionSequence[]>();
                
        StringBuffer simulation_parameters = new StringBuffer("# SIMULATION PARAMETERS:\n");
        simulation_parameters.append("# Applications evolution strategy : " + link_s.getName() + "\n");
        simulation_parameters.append("# Platforms evolution strategy : " + serv_s.getName() + "\n");
        simulation_parameters.append("# Initialization strategy : " + init_s.getName() + "\n");

        simulation_parameters.append("# Drop services from staurated platforms : ");
        if (balance_services != null) simulation_parameters.append("YES (" + balance_services.getName() + ")\n");
        else simulation_parameters.append("NO\n");

        simulation_parameters.append("# Drop useless links : ");
        if (balance_links != null) simulation_parameters.append("YES (" + balance_links.getName() + ")\n");
        else simulation_parameters.append("NO\n");
        
        sim_parameters = simulation_parameters.toString();
        
                // run the simulation;
        System.out.println("Initializing simulations... ");
        steps = new  ArrayList<BPGraph>();
        BPGraph current = initial;
        steps.add(current);

        System.out.println("Running initialization step...");

        if (init_s != null) {
            current = current.deep_clone(); // Create the new bp graph
            current.clearAllCachedData();
            init_s.step(current);
            steps.add(current);
        }
    }
    
    public BPGraph run_step() {
        int i = (steps.size()-1);
        //System.out.println("Running Simuation step #" + i);
        BPGraph current = steps.get(i).deep_clone(); // Create the new bp graph
        current.setlabel("[Simulation step " + (i+1) + "]");

        current.clearAllCachedData();
        if (serv_s != null) serv_s.step(current);
        current.clearAllCachedData();
        if (link_s != null) link_s.step(current);
       current.clearAllCachedData();
        if (balance_services != null) balance_services.step(current);
        current.clearAllCachedData();
        if (balance_links != null) balance_links.step(current);

        steps.add(current);
        return current;
    }
    
    public int computeRobustnessExtinctionSequences(BPGraph model) {
        ExtinctionSequence[] seq = model.performRandomExtinctionSequences(extinction_sequence_runs, (model.getPlatforms().size() * extinction_sequence_percentage) / 100);
        extinctionSequences.put(model, seq);
        double[] res = ExtinctionSequence.averageExtinctionSequences(seq);
        int result = (int) (1000.0 * ExtinctionSequence.averageRobustnessIndex(res, res.length));
        return result;
    }
    
    
    
    
    public ArrayList<BPGraph> getSteps() {
        return steps;
    }

    public BPGraph getInitial() {
        return initial;
    }

    public void setInitial(BPGraph initial) {
        this.initial = initial;
    }

    public String getInitial_parameters() {
        return initial_parameters;
    }

    public void setInitial_parameters(String initial_parameters) {
        this.initial_parameters = initial_parameters;
    }

    public String getSim_parameters() {
        return sim_parameters;
    }

    public ApplicationLinksEvolutionScenario getApplicationLinksEvolutionScenario() {
        return link_s;
    }

    public void setApplicationLinksEvolutionScenario(ApplicationLinksEvolutionScenario link_s) {
        this.link_s = link_s;
    }

    public PlatformServicesEvolutionScenario getPlatformServicesEvolutionScenario() {
        return serv_s;
    }

    public void setPlatformServicesEvolutionScenario(PlatformServicesEvolutionScenario serv_s) {
        this.serv_s = serv_s;
    }

    public InitializationEvolutionScenario getInitializationEvolutionScenario() {
        return init_s;
    }

    public void setInitializationEvolutionScenario(InitializationEvolutionScenario init_s) {
        this.init_s = init_s;
    }

    public BalanceApplicationLinksEvolutionScenario getBalanceApplicationLinksEvolutionScenario() {
        return balance_links;
    }

    public void setBalanceApplicationLinksEvolutionScenario(BalanceApplicationLinksEvolutionScenario balance_links) {
        this.balance_links = balance_links;
    }

    public BalancePlatformServicesEvolutionScenario getBalancePlatformServicesEvolutionScenario() {
        return balance_services;
    }

    public void setBalancePlatformServicesEvolutionScenario(BalancePlatformServicesEvolutionScenario balance_services) {
        this.balance_services = balance_services;
    }

    public ExtinctionSequence[] getExtinctionSequences(BPGraph g) {
        return extinctionSequences.get(g);
    }
}
