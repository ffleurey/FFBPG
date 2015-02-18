package eu.diversify.ffbpg;

import eu.diversify.ffbpg.utils.FileUtils;
import eu.diversify.ffbpg.collections.SortedIntegerSet;
import eu.diversify.ffbpg.random.IntegerSetGenerator;
import eu.diversify.ffbpg.random.RandomUtils;
import eu.diversify.ffbpg.random.UniformIntegerSetGenerator;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    
    private String label = "";
    
    public void setlabel(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return "Bi-Partite Graph #" + id + " " + label;
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
    
    public void clearAllCachedData() {
        for (Application a : applications) {
            a.clearAllCachedData();
        }
        for (Platform p : platforms) {
            p.clearAllCachedData();
        }
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
        services = (ArrayList<Service>)other.services.clone();
        
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
    
    public SortedIntegerSet getAllRequiredServices() {
        SortedIntegerSet result = new SortedIntegerSet();
        for (Application a : applications) result.addAll(a.requiredServices);
        return result;
    }
    
    public SortedIntegerSet getAllProvidedServices() {
        SortedIntegerSet result = new SortedIntegerSet();
        for (Platform p : platforms) result.addAll(p.providedServices);
        return result;
    }
    
    public SortedIntegerSet getAllUsedServices() {
        SortedIntegerSet result = getAllRequiredServices();
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
    
    public int totalNumberOfSrvRequiredByApp() {
        int result = 0;
        for (Application a : applications) result += a.getRequiredServices().size();
        return result;
    }
    
    public double getAvgSrvCountPerApp() {
        double result = totalNumberOfSrvRequiredByApp();
        return result/applications.size();
    }
    
    public int totalNumberOfSrvProvidedByPlat() {
        int result = 0;
        for (Platform p : platforms) result += p.getProvidedServices().size();
        return result;
    }
    
    public double getAvgSrvCountPerPlatform() {
        double result = totalNumberOfSrvProvidedByPlat();
        return result/platforms.size();
    }

    public void createGraphWithOnePlatformPerApplicationAndSingleLink(SortedIntegerSet[] services_sets, int app_capacity, int srv_capacity) {
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
    
    public void createRandomizedGraphWithoutLinks(SortedIntegerSet[] apps, SortedIntegerSet[] plats, int app_capacity, int srv_capacity) {
       
        platforms = new ArrayList<Platform>();
        for (int i = 0; i < plats.length; i++) {
            Platform p = new Platform("P" + i, srv_capacity);
            p.getProvidedServices().addAll(plats[i]);
            platforms.add(p);
        }
        
        applications = new ArrayList<Application>();
        for (int i = 0; i < apps.length; i++) {
            Application a = new Application("A" + i, app_capacity);
            a.getRequiredServices().addAll(apps[i]);
            applications.add(a);
        }
    }
    
    public void addRandomLinksToSatisfyDeps() {
        
        ArrayList<Platform> plats = getPlatforms();
        
        for (Application a : getApplications()) {
            if (a.dependenciesSatisfied()) continue; // No problem
            if (!a.hasRemainingCapacity()) continue; // Cannot be repared by adding links
            SortedIntegerSet required = a.getRequiredServices();
            for (Platform p : a.getLinkedPlatforms()) {
                required = required.minus(p.getProvidedServices());
            }
            Collections.shuffle(plats, RandomUtils.getRandom());
            for(Platform p : plats) {
                if(a.getLinkedPlatforms().contains(p)) continue;
                if (p.hasRemainingCapacity() && p.getProvidedServices().containsSome(required)) {
                    a.getLinkedPlatforms().add(p);
                    if (!a.hasRemainingCapacity()) break;
                    p.incrementLoad();
                    required = required.minus(p.getProvidedServices());
                    if(required.size() == 0) break;
                }
            }
        }
    }
    
    public void purgeDeadApplications() {
        ArrayList<Application> toremove = new ArrayList<Application>();
        for (Application a : getApplications()) {
            if (!a.dependenciesSatisfied()) {
                toremove.add(a);
                // Clear any existing links
                for (Platform p : a.getLinkedPlatforms()) {
                    p.decrementLoad();
                }
            }
        }
        applications.removeAll(toremove);
    }
    
    
    public void addLinksFromApplicationsToPlatformsProvidingAtLeastOneSrv(int app_neighborhood_size) {
        ArrayList<AddLinkIfPossible> commands = new ArrayList<AddLinkIfPossible>();
                
        if (app_neighborhood_size >= platforms.size()) {
            for (Application a : applications) {
                a.addLinksToPlatformsProvidingAtLeastOneSrv(platforms, commands);
                //a.setNeighborhood(new HashSet<Platform>(getPlatforms()));
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
                //a.setNeighborhood(new HashSet<Platform>(neighborhood));
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
                //a.setNeighborhood(new HashSet<Platform>(getPlatforms()));
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
                //a.setNeighborhood(new HashSet<Platform>(neighborhood));
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

    public ExtinctionSequence performRandomExtinctionSequence(int steps) {
        assert steps >= 1 && steps <= platforms.size();
        int[] rseq = seq_generator.getRandomIntegerSet(platforms.size(), steps);
        ArrayList<Application> alive_applications = new ArrayList<Application>();
        for (Application a : applications) {
            a.startExtinctionSequence();
            if (a.isAlive()) {
                alive_applications.add(a);
            }
        }
        ExtinctionSequence result = new ExtinctionSequence(platforms.size(), alive_applications.size(), steps);

        for (int i = 0; i < steps; i++) {
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

    public ExtinctionSequence[] performRandomExtinctionSequences(int n_sequences, int steps) {
        ExtinctionSequence[] result = new ExtinctionSequence[n_sequences];
        for (int i = 0; i < result.length; i++) {
            result[i] = performRandomExtinctionSequence(steps);
        }
        return result;
    }

    public int[] servicesDistributionInApplications() {
        int[] result = new int[services.size()];
        for (Application a : applications) {
            SortedIntegerSet c = a.getRequiredServices();
            for (int i = 0; i < c.size(); i++) {
                result[c.get(i)]++;
            }
        }
        return result;
    }

    public int[] servicesDistributionInPlatforms() {
        int[] result = new int[services.size()];
        for (Platform a : platforms) {
            SortedIntegerSet c = a.getProvidedServices();
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
    
    public int[] platformsLinksCountsDistribution() {
        int[] result = new int[applications.size()+1];
        for (int i = 0; i < platforms.size(); i++) {
            result[platforms.get(i).getLoad()]++;
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
                if ( data[c].length > l) result[l] += data[c][l];
                // counts as 0 if the array is smaller
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
                if (data[c].length > l) b.append(data[c][l]);
                else b.append(0);
                b.append("\t");
            }
            b.append(average[l]);
            b.append("\n");
        }
        return b.toString();
    }
    
    public String serialize_txt() {
        StringBuffer buf = new StringBuffer();
        buf.append("PLATFORMS\n");
        for (Platform p : getPlatforms()) {
            buf.append(p.name + ";" + p.capacity + ";" + p.load + "\n");
            buf.append(p.getProvidedServices().toString() + "\n");
        }
        buf.append("APPLICATIONS\n");
        for (Application a : getApplications()) {
            buf.append(a.name + ";" + a.capacity + "\n");
            buf.append(a.getRequiredServices().toString()+"\n");
            buf.append(a.getLinkedPlatformNames()+"\n");
        }
        return buf.toString();
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
