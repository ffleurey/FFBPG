package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ffl
 */
public class SGHSystem {
    
    public static SGHSystem generateSGHSystem(int max_clients, int max_servers) {
        
        SGHSystem result = new SGHSystem();
        
        SGHModel m = SGHModel.getInstance();
        
        result.clients = new ArrayList<SGHClientApp>();
        for (int i=0; i<max_clients; i++) {
            SGHClientApp c = m.createRandomClient();
            result.clients.add(c);
        }
        
        result.servers = new ArrayList<SGHServer>();
        for (int i=0; i<max_servers; i++) {
            SGHServer s = m.createRandomServer();
            result.servers.add(s);
        }
        
        result.createRandomLinks();
        
        return result;
    }
    
    ArrayList<SGHClientApp> clients;
    ArrayList<SGHServer> servers;
    
    
    private SGHSystem() {}
    
    private void createRandomLinks() {
        // Clone the server list to randomize its order for each client
        ArrayList<SGHServer> _servers = (ArrayList<SGHServer>)servers.clone();
        // For each client, add a set of links to satistfy its dependencies
        
        for (SGHClientApp c : clients) {
            Collections.shuffle(_servers, RandomUtils.getRandom());
            c.getLinks().clear(); // remove any existing links
            
            ArrayList reqs = c.getRequests();
            for (SGHServer s : _servers) {
                ArrayList nreqs = s.filterRequestsWhichCanHandle(reqs);
                if (nreqs.size() < reqs.size()) {
                    // the server is useful
                    c.getLinks().add(s);
                    reqs = nreqs;
                }
                if (reqs.isEmpty()) break;
            }
        }
    }
    
    public String dumpClients() {
        StringBuffer b = new StringBuffer();
        
        for (SGHClientApp c : clients) {
            b.append(c.getOneLineString()); b.append("\n");
        }
        return b.toString();
    }
    
    
}
