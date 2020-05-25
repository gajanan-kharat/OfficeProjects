package com.inetpsa.eds.application.content.eds;

import java.util.Arrays;

/**
 * This class provide cation path
 * 
 * @author Geometric Ltd.
 */
public class CaptionPath {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param path Varargs of String
     */
    public CaptionPath(String... path) {
        this.path = path;
        init();
    }

    /**
     * Returns array of string
     * 
     * @return array of String
     */
    public String[] getStringPath() {
        return path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaptionPath other = (CaptionPath) obj;
        return hashCode() == other.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(path);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold Array of String containing path
     */
    private String[] path;

    /**
     * Initialize Content path
     */
    private void init() {
    }
}
