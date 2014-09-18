package eu.diversify.ffbpg;

import eu.diversify.ffbpg.utils.FileUtils;
import eu.diversify.ffbpg.collections.SortedIntegerCollection;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 *
 * @author ffl
 */
public class BPGraph {

    ArrayList<Service> services;
    ArrayList<Application> applications;
    ArrayList<Platform> platforms;
    
    private static long graph_counter = 0;
    private long id;
    
    @Override
    public String toString() {
        return "Bi-Partite Graph #" + id;
    }

    public String getName() {
        return "BiPartiteGraph_" + id;
    }
    
    public ArrayList<Service> getServices() {
        return services;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
    
    public ArrayList<Application> getLinkedApplicationsForPlatform(Platform p) {
        ArrayList<Application> linked_apps = new ArrayList<Application>();
        for (Application a : this.getApplications()) {
            if (a.getLinkedPlatforms().contains(p)) linked_apps.add(a);
        }
        return linked_apps;
    }

    RandomGenerator randomGenerator = new RandomGenerator();

    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    public BPGraph(int n_services) {
        services = randomGenerator.createServices(n_services);
        id = graph_counter++;
    }
    
    private BPGraph(BPGraph other) {
        // Clone the services collection (Service objects are shared)
        services = (ArrayList<Service>)services.clone();
        
        // Clone the platforms
        Hashtable<String, Platform> new_platforms = new Hashtable<String, Platform>();
        platforms = new ArrayList<Platform>();
        for (Platform p : other.getPlatforms()) {
            Platform np = p.deep_clone();
            new_platforms.put(p.getName(), np);
            platforms.add(np);
        }
        // clone the applications (and re-link to the cloned platforms)
        applications = new ArrayList<Application>();
        for (Application a : other.getApplications()) {
            Application na = a.deep_clone(new_platforms);
            applications.add(na);
        }
    }
    
    public BPGraph deep_clone() {
        return new BPGraph(this);
    }
    
    public SortedIntegerCollection getAllRequiredServices() {
        SortedIntegerCollection result = new SortedIntegerCollection();
        for (Application a : applications) result.addAll(a.requiredServices);
        return result;
    }
    
    public SortedIntegerCollection getAllProvidedServices() {
        SortedIntegerCollection result = new SortedIntegerCollection();
        for (Platform p : platforms) result.addAll(p.providedServices);
        return result;
    }
    
    public SortedIntegerCollection getAllUsedServices() {
        SortedIntegerCollection result = getAllRequiredServices();
        result.addAll(getAllProvidedServices());
        return result;
    }
    
    public int getLinkCount() {
        int result = 0;
        for (Application a : applications) result += a.platforms.size();
        return result;
    }
    
    public double getAvgLinkCountPerApp() {
        double result = 0;
        for (Application a : applications) result += a.platforms.size();
        return result/applications.size();
    }
    
    public double getAvgSrvCountPerApp() {
        double result = 0;
        for (Application a : applications) result += a.getRequiredServices().size();
        return result/applications.size();
    }
    
    public double getAvgSrvCountPerPlatform() {
        double result = 0;
        for (Platform p : platforms) result += p.getProvidedServices().size();
        return result/applications.size();
    }

    public void createGraphWithOnePlatformPerApplicationAndSingleLink(SortedIntegerCollection[] services_sets, int app_capacity, int srv_capacity) {
        applications = new ArrayList<Application>();
        platforms = new ArrayList<Platform>();
        for (int i = 0; i < services_sets.length; i++) {
            Application a = new Application("A" + i, app_capacity);
            a.getRequiredServices().addAll(services_sets[i]);
            applications.add(a);
            Platform p = new Platform("P" + i, srv_capacity);
            p.getProvidedServices().addAll(services_sets[i]);
            platforms.add(p);
            // Add a single ling between the application and the platform
            a.updateLinkforAddedPlatform(p);
        }
    }
    
    
    public void addLinksFromApplicationsToPlatformsProvidingAtLeastOneSrv(int app_neighborhood_size) {
        ArrayList<AddLinkIfPossible> commands = new ArrayList<AddLinkIfPossible>();
                
        if (app_neighborhood_size >= platforms.size()) {
            for (Application a : applications) {
                a.addLinksToPlatformsProvidingAtLeastOneSrv(platforms, commands);
            }
        }
        else {
            // Compute a random neighborhood for each application
            ArrayList<Platform> neighborhood = new ArrayList<Platform>();
            UniformIntegerSetGenerator g = new UniformIntegerSetGenerator();
            
            for (Application a : applications) {
                neighborhood.clear();
                int[] indexes = g.getRandomIntegerSet(platforms.size()-1, app_neighborhood_size);
                for(int i : indexes) neighborhood.add(platforms.get(i));
                a.addLinksToPlatformsProvidingAtLeastOneSrv(neighborhood, commands);
            }
        }
        Collections.shuffle(commands); // Randomize the order of the commands (Important)
        for (AddLinkIfPossible c : commands) {
            c.execute();
        }
    }

    public void addLinksFromApplicationsToPlatformsProvidingAllSrv(int app_neighborhood_size) {
        ArrayList<AddLinkIfPossible> commands = new ArrayList<AddLinkIfPossible>();
        if (app_neighborhood_size >= platforms.size()) {
            for (Application a : applications) {
                a.addLinksToPlatformsProvidingAllSrv(platforms, commands);
            }
        }
        else {
            // Compute a random neighborhood for each application
            ArrayList<Platform> neighborhood = new ArrayList<Platform>();
            UniformIntegerSetGenerator g = new UniformIntegerSetGenerator();
            
            for (Application a : applications) {
                neighborhood.clear();
                int[] indexes = g.getRandomIntegerSet(platforms.size()-1, app_neighborhood_size);
                for(int i : indexes) neighborhood.add(platforms.get(i));
                a.addLinksToPlatformsProvidingAllSrv(neighborhood, commands);
            }
        }
        Collections.shuffle(commands); // Randomize the order of the commands (Important)
        for (AddLinkIfPossible c : commands) {
            c.execute();
        }
    }
    
    public void addLinksToAllPlatformsProvidingAtLeastOneSrv() {
        addLinksFromApplicationsToPlatformsProvidingAtLeastOneSrv(platforms.size());
    }
    
    IntegerSetGenerator seq_generator = new UniformIntegerSetGenerator();

    public ExtinctionSequence performRandomExtinctionSequence() {
        int[] rseq = seq_generator.getRandomIntegerSet(platforms.size(), platforms.size());
        ArrayList<Application> alive_applications = new ArrayList<Application>();
        for (Application a : applications) {
            a.startExtinctionSequence();
            if (a.isAlive()) {
                alive_applications.add(a);
            }
        }
        ExtinctionSequence result = new ExtinctionSequence(platforms.size(), alive_applications.size());

        for (int i = 0; i < rseq.length; i++) {
            Platform tokill = platforms.get(rseq[i]);
            ArrayList<Application> dead_applications = new ArrayList<Application>();
            for (Application a : alive_applications) {
                a.killPlatform(tokill);
                if (!a.isAlive()) {
                    dead_applications.add(a);
                }
            }
            alive_applications.removeAll(dead_applications);
            result.extinctionStep(i + 1, alive_applications.size(), tokill);
        }
        return result;
    }

    public ExtinctionSequence[] performRandomExtinctionSequences(int n_sequences) {
        ExtinctionSequence[] result = new ExtinctionSequence[n_sequences];
        for (int i = 0; i < result.length; i++) {
            result[i] = performRandomExtinctionSequence();
        }
        return result;
    }

    public int[] servicesDistributionInApplications() {
        int[] result = new int[services.size()];
        for (Application a : applications) {
            SortedIntegerCollection c = a.getRequiredServices();
            for (int i = 0; i < c.size(); i++) {
                result[c.get(i)]++;
            }
        }
        return result;
    }

    public int[] servicesDistributionInPlatforms() {
        int[] result = new int[services.size()];
        for (Platform a : platforms) {
            SortedIntegerCollection c = a.getProvidedServices();
            for (int i = 0; i < c.size(); i++) {
                result[c.get(i)]++;
            }
        }
        return result;
    }

    public int[] applicationsLinksCountsDistribution() {
        int[] result = new int[platforms.size()+1];
        for (int i = 0; i < applications.size(); i++) {
            result[applications.get(i).linksCount()]++;
        }
        return result;
    }

    public int[] applicationsServicesCountsDistribution() {
        int[] result = new int[services.size()+1];
        for (int i = 0; i < applications.size(); i++) {
            result[applications.get(i).getRequiredServices().size()]++;
        }
        return result;
    }

    public int[] platformsServicesCountsDistribution() {
        int[] result = new int[services.size()+1];
        for (int i = 0; i < platforms.size(); i++) {
            result[platforms.get(i).getProvidedServices().size()]++;
        }
        return result;
    }

    public static double[] average(int[][] data) {
        assert data.length > 0 && data[0].length > 0;
        double[] result = new double[data[0].length];
        for (int l = 0; l < result.length; l++) {
            for (int c = 0; c < data.length; c++) {
                result[l] += data[c][l];
            }
            result[l] /= data.length;
        }
        return result;
    }

    public static String dataFileWithAverage(int[][] data) {
        StringBuilder b = new StringBuilder();
        b.append("# Data for " + data.length + " runs. Last column is the average.\n");
        double[] average = average(data);
        for (int l = 0; l < average.length; l++) {
            b.append(l);
            b.append("\t");
            for (int c = 0; c < data.length; c++) {
                b.append(data[c][l]);
                b.append("\t");
            }
            b.append(average[l]);
            b.append("\n");
        }
        return b.toString();
    }
    
    public String exportToGraphViz() {
        StringBuilder b = new StringBuilder();
        b.append("digraph bipartite {\n");
        for (Application a : applications) {
            b.append("\t" + a.name + "[label=\"" + a.getName() + "\\n" + a.getRequiredServices().toString() + "\"];\n");
        }
        for (Platform p : platforms) {
            b.append("\t" + p.name + "[label=\"" + p.getName() + "\\n" + p.getProvidedServices().toString() + "\"];\n");
        }
        for (Application a : applications) {
            for (Platform p : a.platforms) {
                 b.append("\t" + a.name + " -> " + p.name + ";\n");
            }
        }
        b.append("}\n");
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

}
