/*
 * Creation : May 24, 2016
 */
package com.inetpsa.pv2.infrastructure.finders;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.pv2.rest.download.DownloadAIWorkRepresentation;

/**
 * The Class DownloadAIWorkFinder.
 */
public class DownloadAIWorkFinder {

    /** The l download AI work representation. */
    List<DownloadAIWorkRepresentation> downloadAIWorkRepresentation = new ArrayList<DownloadAIWorkRepresentation>();

    /**
     * Gets the l download AI work representation.
     *
     * @return the l download AI work representation
     */
    public List<DownloadAIWorkRepresentation> getDownloadAIWorkRepresentation() {
        return downloadAIWorkRepresentation;
    }

    /**
     * Sets the l download AI work representation.
     *
     * @param downloadAIWorkRepresentation the new l_ download ai work representation
     */
    public void setDownloadAIWorkRepresentation(List<DownloadAIWorkRepresentation> downloadAIWorkRepresentation) {
        this.downloadAIWorkRepresentation = downloadAIWorkRepresentation;
    }
}
