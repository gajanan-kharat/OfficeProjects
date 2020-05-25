/*
 * Creation : 28 juin 2016
 */
package com.psa.plm.te.jobtypes.up;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.psa.plm.te.jobtypes.PSAGenericJob;
import com.psa.plm.te.tools.PSATEGenericfunctions;
import com.psa.plm.te.tools.PSATEPatternMatcher;

public class PSAMatrixPosJob extends PSAGenericJob {

    private static final String strExaSubfolder = "MATRIXPOS";

    private String strOutputXML = "";

    private String strGlobalStatus = "";

    private String strHashValue = "";

    public PSAMatrixPosJob(String strjobID) {
        super(strjobID);
        strJobType = "plm_te_posttraitement_MATRIXPOS";
    }

    @Override
    public void initJobDetails(String strExaPath, StringBuilder rapportFileContent) {
        // 1. Build the EXALEAD PATH
        strReportPath = strExaPath + File.separator + strExaSubfolder + File.separator + strJobID + File.separator + "exalead";

        // 2. Get the details of the job
        getjobReportDetails(rapportFileContent);
    }

    @Override
    public void processIndexedData() {
        // 1. Get the Diagonal Input xml for association with correct diagonal job.
        getDiagonalInputXML();

        // 2. Get the global status from the output xml
        getGlobalDetails();

        // 3. Use XML parsing for reading the XML, as this is a nested XML with Object node inside parent Object node
        pareMatPosXML();
    }

    public final String getMatrixHash() {
        return strHashValue;
    }

    private void getDiagonalInputXML() {
        String strPattern = "^PSAVpmInterfacePosUpdateBatch_\\d+_\\d+\\.xml";
        String[] listfiles = PSATEGenericfunctions.getDirectoryChild(strReportPath, strPattern, true);
        if (listfiles != null && listfiles.length > 0) {
            strOutputXML = listfiles[0];
        }
    }

    private void getGlobalDetails() {
        String strGlobalProcessStatus = "(?:\\<GlobalStatus\\s\\w+\\s=\\s\"(\\w+)\"[/]\\>)";
        StringBuilder strBufferData = PSATEGenericfunctions.getFileContent(strReportPath + File.separator + strOutputXML);

        strGlobalStatus = PSATEPatternMatcher.execPattern(strGlobalProcessStatus, strBufferData.toString(), 1);
    }

    private void pareMatPosXML() {
        String strXmlPath = strReportPath + File.separator + strOutputXML;
        String strXMLData = "";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder;
        try {
            dbuilder = dbf.newDocumentBuilder();

            InputSource in = new InputSource(new FileReader(new File(strXmlPath)));
            in.setEncoding("ISO-8859-1");

            Document doc = dbuilder.parse(in);

            NodeList listMainChildren = doc.getChildNodes().item(0).getChildNodes();
            Node nodeObjStatus = null;

            for (int i = 0; i < listMainChildren.getLength(); i++) {
                if (listMainChildren.item(i).getNodeName().equals("ObjectStatus")) {
                    nodeObjStatus = listMainChildren.item(i);
                    break;
                }
            }

            if (nodeObjStatus != null) {
                ArrayList<String> listPAValues = new ArrayList<String>();
                NodeList listObject = nodeObjStatus.getChildNodes();
                for (int i = 0; i < listObject.getLength(); i++) {
                    Node objNode = listObject.item(i);
                    if (objNode.getNodeName().equals("Object")) {
                        String strName = "";
                        String strRevision = "";
                        String strObjectStatus = "";
                        NamedNodeMap listAttribute = objNode.getAttributes();
                        Node attObjectSatuts = listAttribute.getNamedItem("Status");
                        if (attObjectSatuts != null) {
                            strObjectStatus = attObjectSatuts.getTextContent();
                        }
                        Node attName = listAttribute.getNamedItem("Identifier");
                        if (attName != null) {
                            String strAttValue = attName.getTextContent();
                            String[] splitVal = strAttValue.split("\\|");
                            if (splitVal.length == 2) {
                                if (!listPAValues.contains(splitVal[0]))
                                    listPAValues.add(splitVal[0]);
                                strName = splitVal[0];
                                strRevision = splitVal[1] != null ? splitVal[1] : "";
                            } else if (splitVal.length == 1) {
                                if (!listPAValues.contains(splitVal[0]))
                                    listPAValues.add(splitVal[0]);
                                strName = splitVal[0];
                                strRevision = "";
                            }
                        }
                        if (strObjectStatus.equals("KO")) {
                            setErrorDetails(strName, strRevision, objNode);
                        }
                    }
                }
                if (listPAValues.size() > 0) {
                    Collections.sort(listPAValues);
                    for (String value : listPAValues) {
                        strXMLData += value;
                    }
                }
                if (!strXMLData.equals("")) {
                    strHashValue = PSATEGenericfunctions.getHashValue(strXMLData);
                }
            }

        } catch (ParserConfigurationException e) {
            System.out.println("Exception in file : " + strXmlPath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Exception in file : " + strXmlPath);
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("Exception in file : " + strXmlPath);
            e.printStackTrace();
        }
    }

    private void setErrorDetails(String strName, String strRevision, Node objNode) {
        String strErrorMessage = "KO";
        NodeList listObject = objNode.getChildNodes();
        if (listObject == null)
            return;
        int nTotal = listObject.getLength();
        for (int i = 0; i < nTotal; i++) {
            Node child = listObject.item(i);
            if (child.getNodeName().equals("statusDetailInfo")) {
                NamedNodeMap listAttribute = objNode.getAttributes();
                Node nodeType = listAttribute.getNamedItem("Type");
                Node nodeLevel = listAttribute.getNamedItem("Level");
                if (nodeType.getTextContent().equals("Global") && nodeLevel.getTextContent().equals("Error")) {
                    strErrorMessage = child.getTextContent();
                    break;
                }
            }
        }

        if (mapErrorDeta.containsKey(strErrorMessage)) {
            mapErrorDeta.get(strErrorMessage).add(strName + "|" + strRevision);
        } else {
            ArrayList<String> listObjects = new ArrayList<String>();
            listObjects.add(strName + "|" + strRevision);
            mapErrorDeta.put(strErrorMessage, listObjects);
        }
    }
}
