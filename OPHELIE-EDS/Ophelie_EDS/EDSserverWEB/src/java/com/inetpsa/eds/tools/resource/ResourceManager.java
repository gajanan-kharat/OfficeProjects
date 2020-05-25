package com.inetpsa.eds.tools.resource;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import java.util.HashMap;

/**
 * This class provide resources required for application.
 * 
 * @author Geometric Ltd.
 */
public class ResourceManager {
    // PUBLIC
    /**
     * This method returns the instance of ResourceManager class.
     * 
     * @return instance of ResourceManager
     */
    public static ResourceManager getInstance() {
        return ResourceManagerHolder.INSTANCE;
    }

    /**
     * This method returns resource associated with given resource path
     * 
     * @param resourcePath Path of resource
     * @return Resource to be displayed on UI
     */
    public Resource getThemeIconResource(String resourcePath) {
        if (!resources.containsKey(resourcePath)) {
            resources.put(resourcePath, new ThemeResource(resourcePath));
        }
        return resources.get(resourcePath);
    }

    // PRIVATE
    /**
     * Variable to hold map of resource path with resource
     */
    private HashMap<String, Resource> resources;

    /**
     * Initialize Resource manager
     */
    private void init() {
        this.resources = new HashMap<String, Resource>();
    }

    /**
     * Private default constructor
     */
    private ResourceManager() {
        init();
    }

    /**
     * This class provide instance of ResourceManager
     * 
     * @author Geometric Ltd.
     */
    private static class ResourceManagerHolder {
        /**
         * Constant to hold instance of ResourceManager
         */
        private static final ResourceManager INSTANCE = new ResourceManager();
    }
}
