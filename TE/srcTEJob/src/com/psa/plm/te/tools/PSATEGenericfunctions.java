/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class PSATEGenericfunctions {

    public static Date getDateFromString(String strDate, String strDateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat, Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format : " + e);
        }
        return new Date(date.getTime());
    }

    public static String[] getChildDirectory(String strDataFolder) {
        String[] strchildDir = null;
        File datafolder = new File(strDataFolder);
        File[] PSAFolder = datafolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile())
                    return false;
                return true;
            }
        });

        if (PSAFolder.length > 0) {
            strchildDir = new String[PSAFolder.length];
            int i = 0;
            for (File file : PSAFolder) {
                strchildDir[i] = file.getName();
                i++;
            }
        }
        return strchildDir;
    }

    public static String[] getDirectoryChild(String strDataFolder, final String strNamePattern, final boolean bcheckFile) {
        String[] strchildDir = null;
        File parent = new File(strDataFolder);
        if (parent.exists()) {
            File[] children = parent.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String strName = pathname.getName();
                    if ((bcheckFile == true) && (pathname.isFile()) && (PSATEPatternMatcher.isMatching(strNamePattern, strName)))
                        return true;
                    else if ((bcheckFile == false) && (pathname.isDirectory()) && (PSATEPatternMatcher.isMatching(strNamePattern, strName)))
                        return true;
                    return false;
                }
            });
            if (children != null) {
                strchildDir = new String[children.length];
                int i = 0;
                for (File file : children) {
                    strchildDir[i] = file.getName();
                    i++;
                }
            }
        }
        return strchildDir;
    }

    public static boolean hasChildFolder(String strParentDir, ArrayList<String> listChildFolders) {
        boolean bHasChild = false;
        String[] listChild = getChildDirectory(strParentDir);
        // All Children should match in the list
        if (listChild != null) {
            for (String dirName : listChild) {
                bHasChild |= listChildFolders.contains(dirName) ? true : false;
            }
        }
        return bHasChild;
    }

    public static StringBuilder getFileContent(String strFile) {
        StringBuilder strContent = null;
        try {
            File rptfile = new File(strFile);
            int nlength = (int) rptfile.length();
            strContent = new StringBuilder();
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(rptfile));
            while (reader.ready()) {
                strContent.append(reader.readLine() + "\n");
            }
        } catch (IOException e) {

        }
        return strContent;
    }

    public static int getIntendedJobCount(String strConfig) {
        int nCount = 0;
        // String splitPattern="^(?<JOBPATH>.*)\\|(?<FINDOPTION>\\w+)=(?<EXPRESSION>.*)";
        String[] params = PSATEPatternMatcher.execPattern(PSATEMufoConfig.STR_PATTERN_CONFIGPATH, strConfig);
        boolean bcheckFile = params[1].endsWith("f") ? true : true;
        nCount = getfileCount(params[0], params[2], bcheckFile);
        return nCount;
    }

    public static int getfileCount(String strPath, final String strPattern, final boolean bcheckFile) {
        int nFileCount = 0;
        File parent = new File(strPath);
        if (parent.exists()) {
            File[] children = parent.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String strName = pathname.getName();
                    if ((bcheckFile == true) && (pathname.isFile()) && (PSATEPatternMatcher.isMatching(strPattern, strName)))
                        return true;
                    else if ((bcheckFile == false) && (pathname.isDirectory()) && (PSATEPatternMatcher.isMatching(strPattern, strName)))
                        return true;
                    return false;
                }
            });
            nFileCount = (children != null) ? (int) children.length : 0;
        } else {
            nFileCount = -1;
        }
        return nFileCount;
    }

    public static boolean isFileExist(String strfile) {
        File file = new File(strfile);
        return file.exists();
    }

    public static String getHashValue(String strInput) {
        StringBuffer strHash = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(strInput.getBytes());
            byte byteData[] = md.digest();
            for (byte byt : byteData) {
                strHash.append(Integer.toString((0xff & byt) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return strHash.toString();
    }

    public static ArrayList<String> getUniqueList(ArrayList<String> arrJobID) {
        ArrayList<String> listUnique = new ArrayList<String>();
        for (String jobId : arrJobID) {
            if (listUnique.size() == 0 || !listUnique.contains(jobId))
                listUnique.add(jobId);
        }
        return listUnique;
    }

    public static ArrayList<String> getCSVKeyInOrder(Set<String> keys) {
        ArrayList<String> listOrderedKeys = new ArrayList<String>();
        for (String strConfKey : PSATEMufoConfig.listOrderforCSV) {
            if (keys.contains(strConfKey))
                listOrderedKeys.add(strConfKey);
        }
        return listOrderedKeys;
    }
}
