package eu.diversify.ffbpg.sgh.model;

import eu.diversify.ffbpg.random.RandomUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by franck on 29.02.16.
 */
public class SGHExecSimulationStep {

    HashSet<SGHServer> servers_dead;

    public SGHExecSimulationStep(HashSet<SGHServer> dead) {
        servers_dead = dead;
    }

    public int total_requests = -1;
    public int failed_requests = -1;

    public void compute_for_all_requests(SGHSystem model) {
        total_requests = 0;
        failed_requests = 0;

        for (SGHClientApp c : model.clients) {
            ArrayList<SGHServer> links = new ArrayList<SGHServer>();
            for (SGHServer s : c.getLinks()) {
                if (!servers_dead.contains(s)) links.add(s);
            }

            //ArrayList<SGHRequest> reqs = c.getRequests();
            ArrayList<SGHRequest> reqs = new ArrayList<SGHRequest>();
            reqs.add(c.getRequests().get(RandomUtils.getUniform(c.getRequests().size()-1)));

            total_requests += reqs.size();
            for (SGHServer s : links) {
                reqs = s.filterRequestsWhichCanHandle(reqs);
            }
            failed_requests += reqs.size();
        }
    }

}
