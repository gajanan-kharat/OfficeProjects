package com.psa.PSATEStructCompare.Batch;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.psa.PSATEStructCompare.PSAFilterDBHandler;
import com.psa.PSATEStructCompare.PSAFilterModel;
import com.psa.tools.PSAErrorHandler;

/**
 * This class needs to be called form Server Script for further processing
 */
public class PSAStructCompareBatch {
    private static Logger logger = Logger.getLogger("PSAStructCompareBatch");
    private static PSAFilterDBHandler filterDBHandler = new PSAFilterDBHandler(1);
    private static List<PSAFilterModel> filterList;

    public static void main(String[] args)
    {
        logger.info("[PSAStructCompareBatch:main] START");
        try {
            filterList = getFilterDBHandler().getFiltersForNextLaunchDate();
            createTextFileForEachFilter();

        } catch (PSAErrorHandler e) {
            logger.info("Exception occured while retriving filter data from the data base:" + e.getMessage());
        } catch (FileNotFoundException e) {
            logger.info("Failed to create file!");
        }

        logger.info("[PSAStructCompareBatch:main] END");
    }

    /**
     * To create file for each filter at given location
     * 
     * @throws FileNotFoundException
     */
    private static void createTextFileForEachFilter() throws FileNotFoundException
    {
        logger.info("[PSAStructCompareBatch:createTextFileForEachFilter] START");
        int counter = 1;
        for (PSAFilterModel psaFilterModel : filterList) {
            StringBuilder folderPath = new StringBuilder(getFolderPath());
            StringBuilder filePath = folderPath.append(File.separator + "StructCmpFilter_" + String.format("%02d", counter));
            filePath.append(".txt");
            logger.info("Creating file: " + filePath);
            PrintWriter writer = new PrintWriter(filePath.toString());
            writer.write(psaFilterModel.getName());
            writer.write("\n");
            writer.write(psaFilterModel.getRevision());
            writer.write("\n");
            writer.write(psaFilterModel.getProject());
            writer.write("\n");
            writer.write(psaFilterModel.getDesignation());
            writer.write("\n");
            writer.write(psaFilterModel.getType());
            writer.flush();
            writer.close();
            counter++;
        }
        updateLaunchAndModifDate();

        logger.info("[PSAStructCompareBatch:createTextFileForEachFilter] Files created at location: " + getFolderPath());
        logger.info("[PSAStructCompareBatch:createTextFileForEachFilter] END");
    }

    /**
     * To update the NEXTLAUNCHDATE and MODIFDATE for all processed filters
     */
    private static void updateLaunchAndModifDate()
    {
        logger.info("[PSAStructCompareBatch:updateLaunchAndModifDate] START");
        try {
            getFilterDBHandler().updateNextLaunchDate(filterList);
        } catch (PSAErrorHandler e) {
            logger.info("[PSAStructCompareBatch:updateLaunchAndModifDate] Failed to update Filter data!");
        }
        logger.info("[PSAStructCompareBatch:updateLaunchAndModifDate] END");
    }

    /**
     * To get folder path where files need to be created.
     * 
     * @return String
     */
    private static String getFolderPath()
    {
        logger.info("[PSAStructCompareBatch:getFolderPath] START");
        String folderPath = System.getenv("OUTPUT_PATH");
        if (folderPath == null || folderPath.isEmpty()) {
            folderPath = System.getenv("UNXTMP");
            logger.info("[PSAStructCompareBatch:getFolderPath] ENV VARIABLE: OUTPUT_PATH is not defined So creating files at location =>>>> " + folderPath);
        }
        logger.info("[PSAStructCompareBatch:getFolderPath] END");
        return folderPath;

    }

    public static PSAFilterDBHandler getFilterDBHandler()
    {
        return filterDBHandler;
    }

    public static void setFilterDBHandler(PSAFilterDBHandler filterDBHandler)
    {
        PSAStructCompareBatch.filterDBHandler = filterDBHandler;
    }

}
