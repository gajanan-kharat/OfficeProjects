/*
 * Author: U224106
 * Creation: 18 déc. 2014
 */
package com.inetpsa.oaz00.ws.report.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.inetpsa.oaz00.ws.report.utils.ReportUtility;

/**
 * The Class ReportDownloadServlet.
 * 
 * @author U224106
 */
public class ReportDownloadServlet extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1036131014702451661L;

    /** The logger. */
    private static Logger logger = Logger.getLogger(ReportDownloadServlet.class);

    /**
     * {@inheritDoc}
     * 
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportId = request.getParameter("report");

        // Matching example : MonteCarlo_20141209-110113.xlsx -> ^\w+_(\d+)-\d+\.xlsx$
        Pattern pattern = Pattern.compile("^\\w+_(\\d+)-\\d+\\.xlsx$");
        Matcher matcher = pattern.matcher(reportId);
        String executionDate = null;
        // check all occurance
        if (matcher.matches())
            executionDate = matcher.group(1);

        String dataFilePrefix = System.getenv("TMPDIR") + File.separator + executionDate + File.separator + ReportUtility.REPORT;
        File report = new File(dataFilePrefix + File.separator + reportId);

        if (report.exists()) {

            response.setContentType(getServletContext().getMimeType(report.getAbsolutePath()));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + report.getName() + "\"");
            response.setContentLength((int) report.length());

            InputStream fis = new FileInputStream(report);
            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();
        } else
            logger.error("Error - No report available... Report expected is " + dataFilePrefix);
    }
}