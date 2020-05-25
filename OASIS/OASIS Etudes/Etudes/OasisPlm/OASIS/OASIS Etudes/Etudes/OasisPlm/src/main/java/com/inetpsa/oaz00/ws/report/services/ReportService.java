/*
 * Author: U224106
 * Creation: 3 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.services;

import java.io.IOException;

/**
 * The Report service.
 *
 * @author U224106
 */
public interface ReportService {

    /**
     * Generate report.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String generateReport() throws IOException;

}
