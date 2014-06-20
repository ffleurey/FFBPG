/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.diversify.ffbpg;

/**
 *
 * @author ffl
 */
public class AddLinkIfPossible {
    
    protected Platform platform;
    protected Application application;
    
    public AddLinkIfPossible(Application application, Platform platform) {
        this.platform = platform;
        this.application = application;
    }
    
    public void execute() {
        application.updateLinkforAddedPlatform(platform);
    }
    
}
