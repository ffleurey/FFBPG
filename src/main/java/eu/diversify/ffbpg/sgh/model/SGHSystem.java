package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.Application;
import eu.diversify.ffbpg.BPGraph;
import static eu.diversify.ffbpg.BPGraph.average;
import static eu.diversify.ffbpg.BPGraph.dataFileWithAverage;
import eu.diversify.ffbpg.Platform;
import eu.diversify.ffbpg.Service;
import eu.diversify.ffbpg.collections.Population;
import eu.diversify.ffbpg.random.RandomUtils;
import eu.diversify.ffbpg.utils.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ffl
 */
public class SGHSystem {
    
    public static SGHSystem generateSGHSystem(int max_clients, int max_servers) {
        
        SGHSystem result = new SGHSystem();
        
        SGHModel m = SGHModel.getInstance();
        
        result.clients = new ArrayList<SGHClientApp>();
        int id=1;
        for (int i=0; i<max_clients; i++) {
            SGHClientApp c = m.createRandomClient();
            c.setName("C" + id);
            result.clients.add(c);
            id++;
        }
        
        result.servers = new ArrayList<SGHServer>();
        id=1;
        for (int i=0; i<max_servers; i++) {
            SGHServer s = m.createRandomServer();
            s.setName("S" + id);
            result.servers.add(s);
            id++;
        }
        
        // Add some random links
        result.createRandomLinks();
        
        // Cleanup the model
        result.removedDeadClients();
        result.removedUnusedServers();
        
        return result;
    }
    
    ArrayList<SGHClientApp> clients;
    ArrayList<SGHServer> servers;
    
    
    private SGHSystem() {}
    
    public SGHSystem deep_clone() {
        SGHSystem result = new SGHSystem();
        result.clients = new ArrayList<SGHClientApp>();
        result.servers = new ArrayList<SGHServer>();
        
        HashMap<SGHServer, SGHServer> old_new_map = new HashMap<SGHServer, SGHServer>();
        
        for (SGHServer s : servers) {
            SGHServer cs = s.deep_clone();
            old_new_map.put(s, cs);
            result.servers.add(cs);
        }
        
        for (SGHClientApp c : clients) {
            SGHClientApp cc = c.deep_clone(old_new_map);
            result.clients.add(cc);
        }
         
        return result;
        
    }
    
    private void createRandomLinks() {
        // Clone the server list to randomize its order for each client
        ArrayList<SGHServer> _servers = (ArrayList<SGHServer>)servers.clone();
        // For each client, add a set of links to satistfy its dependencies
        
        for (SGHClientApp c : clients) {
            Collections.shuffle(_servers, RandomUtils.getRandom());
            c.getLinks().clear(); // remove any existing links
            
            ArrayList reqs = c.getRequests();
            for (SGHServer s : _servers) {
                if (!s.hasCapacity()) continue;
                ArrayList nreqs = s.filterRequestsWhichCanHandle(reqs);
                if (nreqs.size() < reqs.size()) {
                    // the server is useful
                    c.getLinks().add(s);
                    s.increaseLoad();
                    reqs = nreqs;
                }
                if (reqs.isEmpty()) break;
            }
            if(!reqs.isEmpty()) {
                for (SGHServer s : c.getLinks()) {
                    s.decreaseLoad();
                }
                c.getLinks().clear();
            }
        }
    }
    
    private void removedDeadClients() {
        for (SGHClientApp c : (ArrayList<SGHClientApp>)clients.clone()) {
            if (!c.isAlive()) {
                for (SGHServer s : c.getLinks()) s.decreaseLoad(); // clear the load on the servers
                clients.remove(c);
            }
        }
    }
    
    private void removedUnusedServers() {
        HashSet<SGHServer> srvs = new HashSet<SGHServer>();
        for (SGHClientApp c : clients) {
            for (SGHServer s : c.getLinks()) {
                srvs.add(s);
            }
        }
        for (SGHServer s : (ArrayList<SGHServer>)servers.clone()) {
            if (!srvs.contains(s)) {
                servers.remove(s);
            }
        }
    }
    
    class ExecExtinctionSeq implements Callable<SGHExtinctionSequence[]> {
        
        int nb_seq = 10;
        
        public ExecExtinctionSeq(int nb_seq) {
            this.nb_seq = nb_seq;
        }
        
        public SGHExtinctionSequence[] call() {
            SGHExtinctionSequence[] result = computeRandomExtinctionSequence(nb_seq);
            return result;
        }
    }
    
    
    public SGHExtinctionSequence[] computeRandomExtinctionSequence(int seq, int threads) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        LinkedList<Future<?>> futures = new LinkedList<Future<?>>();
        SGHExtinctionSequence[] eseqs = new SGHExtinctionSequence[seq];
        
        for (int i = 0; i < threads; i++)
        {
            ExecExtinctionSeq task = new ExecExtinctionSeq(seq/threads);
            futures.add(executor.submit(task));
        }
        
        int idx = 0;
        
        for (Future<?> future:futures) {
            try {
                SGHExtinctionSequence[] res = (SGHExtinctionSequence[])future.get();
                
                for (int i=0; i<res.length; i++) {
                    eseqs[idx++] = res[i];
                }
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SGHSystem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(SGHSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        executor.shutdown();
        return eseqs;
    }
    
    
    private SGHExtinctionSequence[] computeRandomExtinctionSequence(int seq) {
        
        ArrayList<SGHServer> all_servers = (ArrayList<SGHServer>)servers.clone();

        SGHExtinctionSequence[] eseqs = new SGHExtinctionSequence[seq];
        
        for (int i=0; i<seq; i++) {
            HashSet<SGHServer> alive_servers = new HashSet<SGHServer>(all_servers);
             eseqs[i] = new SGHExtinctionSequence(servers.size(), clients.size());
            // Randomize the order
            Collections.shuffle(all_servers);
            for (SGHServer to_kill : all_servers) {
                
                int nb_cli_alive = 0;
                double percent_cli_alive = 0f;
                for(SGHClientApp c : clients) {
                    ArrayList<SGHRequest> reqs = c.getRequests();
                    for (SGHServer s : c.getLinks()) {
                        if (alive_servers.contains(s)) {
                            reqs = s.filterRequestsWhichCanHandle(reqs);
                        }
                    }
                    if (reqs.size() == 0) nb_cli_alive++;
                    percent_cli_alive += 1f - ((double)reqs.size() / ((double)c.getRequests().size()));
                }
                percent_cli_alive /= clients.size();
                eseqs[i].extinctionStep(servers.size() - alive_servers.size(), nb_cli_alive, percent_cli_alive);
                alive_servers.remove(to_kill);
            }
            eseqs[i].extinctionStep(servers.size(), 0, 0f);
        }
        return eseqs;
    }
    
    
    
    public String dumpData(boolean details) {
        StringBuffer b = new StringBuffer();
        
        Hashtable<SGHServer, Integer> srvs = new Hashtable<SGHServer, Integer>();
                
        
        int alive = 0;
        int dead = 0;
        for (SGHClientApp c : clients) {
            if (c.isAlive()) {
                if (details) b.append(c.getOneLineString());
                if (details) b.append("\n");
                alive++;
                for (SGHServer s : c.getLinks()) {
                    if (srvs.containsKey(s)) {
                        srvs.put(s, srvs.get(s)+1);
                    }
                    else {
                        srvs.put(s, 1);
                    }
                }
            }
            
        }
        
        for (SGHClientApp c : clients) {
            if (!c.isAlive()) {
                if (details)b.append(c.getOneLineString());
                if (details)b.append("\n");
                dead++;
            }
            
        }
        
        b.append("ALIVE CLIENTS: " + alive +", DEAD: " + dead + "\n");
        
        int s_alive = 0;
        int s_dead = 0;
        
        for (SGHServer s : servers) {
            if (srvs.containsKey(s)) {
                if (details) { b.append("#"); b.append(srvs.get(s)); b.append("->"); b.append(s.getOneLineString());
                b.append("\n"); }
                s_alive++;
            }
        }
        
        for (SGHServer s : servers) {
            if (!srvs.containsKey(s)) {
                if (details) { b.append("0 ->"); b.append(s.getOneLineString());
                b.append("\n");}
                s_dead++;
            }
        }
        b.append("ALIVE SERVERS: " + s_alive +", DEAD: " + s_dead + "\n");
        
        // Calculate some metrics on the network
        HashMap<String, ArrayList<SGHNode>> srv_pop = SGHNode.getPopulation(servers);
        Population srv_pop_stats = SGHNode.getPopulationStats(srv_pop);
        b.append("   SERVER POPULATION : "); b.append(srv_pop_stats.toString());b.append("\n");
        b.append("SERVER SPECIES COUNT : ");b.append(srv_pop_stats.getSpeciesCount());b.append("\n");
        b.append("   SERVER POPULATION : ");b.append(servers.size());b.append("\n");
        b.append("  SERVER SHANNON DIV : ");b.append(srv_pop_stats.getShannonIndex());b.append("\n");
        b.append(" SERVER EQUITABILITY : ");b.append(srv_pop_stats.getShannonEquitability());b.append("\n");
        b.append("    SERVER DISPARITY : ");b.append(SGHNode.disparityOfSpercies(srv_pop));b.append("\n");
        
        
        return b.toString();
    }
    
    
    public int[] distLinksPerClients() {
        int[] result = new int[servers.size()];
        for(SGHClientApp c : clients) {
            result[c.links.size()]++;
        }
        return result;
    }
    
    public int[] distLinksPerServers() {
        int[] result = new int[clients.size()];
        
        // Calulate server connections
        Hashtable<SGHServer, ArrayList<SGHClientApp>> links = new Hashtable<SGHServer, ArrayList<SGHClientApp>>();
        for (SGHClientApp client : clients) {
            for (SGHServer s : client.getLinks()) {
                if (!links.containsKey(s)) {
                    links.put(s, new ArrayList<SGHClientApp>());
                }
                links.get(s).add(client);
            }
        }
        
        for(SGHServer s : servers) {
            if (!links.containsKey(s)) result[0]++;
            else result[links.get(s).size()]++;
        }
        return result;
    }
    
    public int[] distNbFeaturesPerClients() {
        int[] result = new int[SGHModel.getInstance().totalNbFeatures()];
        for(SGHClientApp c : clients) {
            result[c.featureSet.size()]++;
        }
        return result;
    }
    
    public int[] distNbFeaturesPerServers() {
        int[] result = new int[SGHModel.getInstance().totalNbFeatures()];
        for(SGHServer s : servers) {
            result[s.featureSet.size()]++;
        }
        return result;
    }
    
    public int[] distFeaturesOnClients() {
        Hashtable<SGHFeature, Integer> count = new Hashtable<SGHFeature, Integer>();
        for (SGHClientApp c : clients) {
            for (SGHFeature f : c.featureSet) {
                if (!count.containsKey(f)) {
                    count.put(f, 1);
                }
                else {
                    count.put(f, count.get(f) + 1);
                }
            }
        }
        ArrayList<Integer> counts = new ArrayList<Integer>(count.values());
        Collections.sort(counts);
        int[] result = new int[counts.size()];
        for (int i=0; i<counts.size(); i++) result[counts.size()-i-1] = counts.get(i);
        return result;
    }
    
    public int[] distFeaturesOnServers() {
        Hashtable<SGHFeature, Integer> count = new Hashtable<SGHFeature, Integer>();
        for (SGHServer s : servers) {
            for (SGHFeature f : s.featureSet) {
                if (!count.containsKey(f)) {
                    count.put(f, 1);
                }
                else {
                    count.put(f, count.get(f) + 1);
                }
            }
        }
        ArrayList<Integer> counts = new ArrayList<Integer>(count.values());
        Collections.sort(counts);
        int[] result = new int[counts.size()];
        for (int i=0; i<counts.size(); i++) result[counts.size()-i-1] = counts.get(i);
        return result;
    }
    
    public void exportGraphStatistics(File folder) {
        DataExportUtils.writeGNUPlotScriptForIntArray(distNbFeaturesPerClients(), folder, "dist_nb_features_per_clients", "Size (in Features)", "# Clients");
        DataExportUtils.writeGNUPlotScriptForIntArray(distNbFeaturesPerServers(), folder, "dist_nb_features_per_servers", "Size (in Features)", "# Servers");
        
        DataExportUtils.writeGNUPlotScriptForIntArray(distFeaturesOnClients(), folder, "dist_feature_use_on_clients", "Feature", "# of usages in clients");
        DataExportUtils.writeGNUPlotScriptForIntArray(distFeaturesOnServers(), folder, "dist_feature_use_on_servers", "Feature", "# of usages in servers");
        
        DataExportUtils.writeGNUPlotScriptForIntArray(distLinksPerClients(), folder, "dist_nb_links_per_clients", "# Links (to Servers)", "# Clients");
        DataExportUtils.writeGNUPlotScriptForIntArray(distLinksPerServers(), folder, "dist_nb_links_per_servers", "# Links (from Clients)", "# Servers");
    }
    
    public void exportClientsToJSONFiles(File base_folder, String name, File hostlist) throws Exception {
        File outdir = new File(base_folder, name);
        outdir.mkdir();
        BufferedReader br = null;
        
        HashMap<SGHServer, String> serverids = new HashMap<SGHServer, String>();
        
        if (hostlist != null) {
            br = new BufferedReader(new FileReader(hostlist));
            for(SGHServer s : servers) {
                serverids.put(s, br.readLine());
            }
        }
        else {
            int srvid = 0;
        
            for(SGHServer s : servers) {
                serverids.put(s, "coreff3:153" + srvid);
                srvid++;
            }
        }

        int clientid = 0;
        // Export 1 JSON file per client
        for (SGHClientApp c : clients) {
            
            StringBuilder b = new StringBuilder();
            b.append("{\n");
            b.append("\"id\":\""+c.getName()+"\", \n");
            writeJSONForServices(c, b);
            
            b.append(",\n");
            b.append("\"platforms\":[\n");
            boolean first = true;
            for (SGHServer s : c.getLinks()) {
                if (first) { first = false; } else b.append(", ");
                b.append("{\n");
                b.append("\"id\":\""+s.getName()+"\", \n");
                b.append("\"host\":\"http://"+serverids.get(s)+"/\",\n");
                
                writeJSONForServices(s, b);
                
                b.append("}\n");
            }
            b.append("]\n");
            b.append("}\n");
            
            FileUtils.writeTextFile(outdir, "client" + clientid + ".json", b.toString());
            clientid++;
        }
        
    }
    
    public void writeJSONForServices(SGHNode n, StringBuilder b) {
        
         b.append("\"services\":[\n");
            boolean vfirst = true;
            for (SGHVariationPoint v : n.features.keySet()) {
                //if (n.features.get(v).isEmpty()) continue;
                if (vfirst) { vfirst = false; } else b.append(", ");
                b.append("{\"name\":\"");b.append(v.getName());b.append("\",\"alternatives\":[");
                boolean first = true;
                for (SGHFeature f : n.features.get(v)) {
                    if (first) { first = false; } else b.append(", ");
                    b.append("\"");b.append(f.getName());b.append("\"");
                }
                b.append("]}\n");
            }
            b.append("]\n");
    }
    
    public BPGraph toBPGraph() {
        
        // Create all the services and collect their indexes
        ArrayList<Service> services = new ArrayList<Service>();
        Hashtable<String, Integer> srvidx = new Hashtable<String, Integer>();
        Hashtable<String, SGHRequest> reqs = new Hashtable<String, SGHRequest>();
        
        for (SGHClientApp c : clients) {
            for (SGHRequest r : c.getRequests()) {
                String s = r.toString();
                if (!srvidx.containsKey(s)) {
                    srvidx.put(s, services.size());
                    reqs.put(s, r);
                    Service ns = new Service(s);
                    ns.incrementUsage();
                    services.add(ns);
                } else {
                    Service ns = services.get(srvidx.get(s));
                    ns.incrementUsage();
                }
            }
        }
        // Sort the list of services and update indexes
        Collections.sort(services);
        for (int i=0; i<services.size(); i++) {
            String s = services.get(i).getName();
            srvidx.put(s, i);
        }
        
        
        
        // Create all platforms
        Hashtable<SGHServer, Platform> platmap = new Hashtable<SGHServer, Platform>();
        ArrayList<Platform> plats= new ArrayList<Platform>();
        
        for (SGHServer s : servers) {
            Platform p = new Platform(s.getName(), s.getCapacity());
            for (String str : reqs.keySet()) {
                SGHRequest req = reqs.get(str);
                if (s.canHandle(req)) {
                    p.getProvidedServices().add(srvidx.get(str));
                }
            }
            platmap.put(s, p);
            plats.add(p);
        }
        
        // Create applications
        ArrayList<Application> apps= new ArrayList<Application>();
        for (SGHClientApp c : clients) {
            Application a = new Application(c.getName(), plats.size());
            for (SGHRequest req : c.getRequests()) {
                a.getRequiredServices().add(srvidx.get(req.toString()));
            }
            for (SGHServer s : c.getLinks()) {
                Platform p = platmap.get(s);
                p.incrementLoad();
                a.getLinkedPlatforms().add(p);
            }
            apps.add(a);
        }
        
        BPGraph result = new BPGraph(services);
        result.setPlatforms(plats);
        result.setApplications(apps);
        
        return result;
    }
    
}
