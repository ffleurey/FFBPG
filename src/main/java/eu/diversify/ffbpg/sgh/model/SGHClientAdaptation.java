/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.diversify.ffbpg.sgh.model;

/**
 *
 * @author ffl
 */
public class SGHClientAdaptation {
    
    SGHClientApp client;
    
    SGHServer link_to_remove = null;
    SGHServer link_to_add = null;

    public SGHClientApp getClient() {
        return client;
    }

    public SGHServer getLink_to_remove() {
        return link_to_remove;
    }

    public void setLink_to_remove(SGHServer link_to_remove) {
        this.link_to_remove = link_to_remove;
    }

    public SGHServer getLink_to_add() {
        return link_to_add;
    }

    public void setLink_to_add(SGHServer link_to_add) {
        this.link_to_add = link_to_add;
    }
    
    public SGHClientAdaptation(SGHClientApp client) {
        this.client = client;
    }
    
    public SGHClientAdaptation(SGHClientApp client, SGHServer link_to_remove, SGHServer link_to_add) {
        this.client = client;
        this.link_to_add = link_to_add;
        this.link_to_remove = link_to_remove;
    }
    
    public int execute() {
        int size = client.getLinks().size();
        if (link_to_remove != null) client.getLinks().remove(link_to_remove);
        if (link_to_add != null) client.getLinks().add(link_to_add);
        return client.getLinks().size() - size;
    }
    
}
