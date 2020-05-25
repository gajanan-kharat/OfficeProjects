package com.inetpsa.eds.tools.docinfo;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;

/**
 * This class provide component for attaching documents and information of already attached documents.
 * 
 * @author Geometric Ltd.
 */
public class ComposantDocInfos extends GridLayout implements Button.ClickListener {
    // PUBLIC
    /**
     * Parameterized constructor
     * 
     * @param controller Controller of EDS application
     */
    public ComposantDocInfos(EdsApplicationController controller) {
        this(controller.getBundle().getString("comp-rob-doc-join") + " : ", controller);
    }

    /**
     * Parameterized constructor
     * 
     * @param title Title of document
     * @param controller Controller of EDS application
     */
    public ComposantDocInfos(String title, EdsApplicationController controller) {
        this.vLTitleText = title;
        this.controller = controller;
        urlList = new ArrayList<URL>();
        init();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of Title of document
     */
    private String vLTitleText;
    /**
     * Variable to hold value of label
     */
    private Label vLTitle;
    /**
     * Variable to hold value of TextField
     */
    private TextField vTFLien;
    /**
     * Variable to hold value of Button
     */
    private Button vBAdd;
    /**
     * Variable to hold value of VerticalLayout
     */
    private VerticalLayout vVLDocInfo;
    /**
     * Variable to hold value of LienDocInfo
     */
    private LienDocInfo docInfoLien;
    /**
     * Variable to hold list if URL
     */
    private ArrayList<URL> urlList;
    /**
     * Variable to hold value of Controller of EDS application
     */
    private EdsApplicationController controller;
    /**
     * Constant to hold value of MAX_URLs
     */
    private static int MAX_URLs = 3;

    /**
     * Initialize ComposantDocInfos
     */
    private void init() {
        setColumns(2);
        setRows(3);
        setSpacing(true);
        setSizeFull();

        vLTitle = new Label(vLTitleText);
        vLTitle.addStyleName("h2");
        addComponent(vLTitle, 0, 0, 1, 0);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSpacing(true);
        vTFLien = new TextField();
        vTFLien.setWidth("600");
        hl.addComponent(vTFLien);
        vBAdd = new Button();
        vBAdd.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/activate.png "));
        vBAdd.setStyleName(BaseTheme.BUTTON_LINK);
        vBAdd.setDescription(controller.getBundle().getString("eds-docinfo-btn"));
        vBAdd.addListener(this);

        hl.addComponent(vBAdd);
        addComponent(hl, 0, 1, 1, 1);

        vVLDocInfo = new VerticalLayout();
        addComponent(vVLDocInfo, 0, 2, 1, 2);

    }

    /**
     * This method is called when button is clicked
     * 
     * @param event An event containing information about the click (non-Javadoc)
     * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
     */
    public void buttonClick(ClickEvent event) {
        try {
            addURL(StrToUrl(vTFLien.getValue().toString()));
        } catch (Exception ex) {
        }
    }

    /**
     * This method add URL in list of URLs
     * 
     * @param url Url to be added in list
     */
    private void addURL(final URL url) {
        if (url == null) {
            return;
        }
        if (urlList.contains(url)) {
            showNotification("", controller.getBundle().getString("eds-docinfo-error"));
            return;
        }

        urlList.add(url);

        final LienDocInfo docInfo = new LienDocInfo(url, controller);
        docInfo.setRemovable(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                vVLDocInfo.removeComponent(docInfo);
                vBAdd.setEnabled(true);
                urlList.remove(url);
            }
        });
        vVLDocInfo.addComponent(docInfo);
        vTFLien.setValue("");

        if (urlList.size() >= MAX_URLs) {
            vBAdd.setEnabled(false);
        }
    }

    /**
     * This method convert string to URL
     * 
     * @param val String value
     * @return URL
     * @throws MalformedURLException Exception when URL is not proper
     */
    private URL StrToUrl(String val) throws MalformedURLException {
        URL url = null;
        if (isURL(val)) {
            url = new URL(val);
        } else {
            char[] tab = val.toCharArray();
            if (!"".equals(val) && java.lang.Character.isDigit(tab[0])) {
                val = "http://docinfogroupe.inetpsa.com/ead/doc/ref." + val + "/v.dp/fiche";
                url = new URL(val);
            } else {
                showNotification("", controller.getBundle().getString("Activ-profill-error-url"));

                return null;
            }
        }
        return url;
    }

    /**
     * This method check if given value is URL
     * 
     * @param val String value
     * @return check if value given is URL
     */
    private boolean isURL(String val) {
        try {
            URL uRL = new URL(val);
        } catch (MalformedURLException ex) {
            return false;
        }
        return true;
    }

    /**
     * This method show notification
     * 
     * @param title Title of notification
     * @param description Description of Notification
     */
    private void showNotification(String title, String description) {
        getWindow().showNotification(title, description, Notification.TYPE_ERROR_MESSAGE);
    }

    /**
     * This method returns the URL list
     * 
     * @return URL list
     */
    public String getValue() {
        if (urlList.isEmpty()) {
            return null;
        } else {
            return StringUtils.join(urlList, ";");
        }
    }

    /**
     * This method set value for URL List
     * 
     * @param val Value to be set
     */
    public void setValue(String val) {
        urlList.clear();
        vVLDocInfo.removeAllComponents();
        if (val == null || val.equals("")) {
            return;
        }
        for (String url : val.split(";")) {
            try {
                addURL(StrToUrl(url));
            } catch (MalformedURLException ex) {
            }
        }
    }
}
