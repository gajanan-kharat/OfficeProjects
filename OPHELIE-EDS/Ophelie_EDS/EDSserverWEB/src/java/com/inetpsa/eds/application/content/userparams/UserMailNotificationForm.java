package com.inetpsa.eds.application.content.userparams;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.dao.model.UserConfig;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

/**
 * This class represents the user details while sending mail notifications.
 * 
 * @author Geometric Ltd.
 */
public class UserMailNotificationForm extends A_UserForm {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param name User name.
     * @param config User configuration details.
     * @param controller EDS application controller object.
     */
    public UserMailNotificationForm(String name, UserConfig config, EdsApplicationController controller) {
        super(name, config);

        this.controller = controller;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#isValid()
     */
    @Override
    public boolean isValid() {
        String mailValue = (String) vTFmailValue.getValue();
        if (mailValue != null && !MAIL_PATTERN.matcher((String) vTFmailValue.getValue()).matches()) {
            controller.showError(controller.getBundle().getString("mail-notif-no-valide-adress"));
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#commitChanges()
     */
    @Override
    public boolean commitChanges() {
        getConfig().setMailAddress((String) vTFmailValue.getValue());
        getConfig().setPreferredLanguage((String) vOGlanguageValue.getValue());
        getConfig().setNotifyModification(
                ((Set) vOGnotificationConditionValues.getValue()).contains(controller.getBundle().getString("Param-title-all-modification")));
        getConfig().setNotifyStatus(
                ((Set) vOGnotificationConditionValues.getValue()).contains(controller.getBundle().getString("Param-title-new-statu")));
        getConfig().setNotifyVersionning(
                ((Set) vOGnotificationConditionValues.getValue()).contains(controller.getBundle().getString("Param-title-new-vers")));
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#getAllItemsToDelete()
     */
    @Override
    public Collection getAllItemsToDelete() {
        return Collections.EMPTY_SET;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.content.eds.A_EdsEditForm#discardChanges()
     */
    @Override
    public void discardChanges() {
        vTFmailValue.setValue(getConfig().getMailAddress());

        vOGlanguageValue.setValue(getConfig().getPreferredLanguage());
        if (getConfig().getPreferredLanguage() == null) {
            vOGlanguageValue.setValue(controller.getApplication().getLocale().getLanguage());
        }

        Set<String> notifyValues = new HashSet<String>();
        if (getConfig().isNotifyModification()) {
            notifyValues.add(controller.getBundle().getString("Param-title-all-modification"));
        }
        if (getConfig().isNotifyStatus()) {
            notifyValues.add(controller.getBundle().getString("Param-title-new-statu"));
        }
        if (getConfig().isNotifyVersionning()) {
            notifyValues.add(controller.getBundle().getString("Param-title-new-vers"));
        }
        vOGnotificationConditionValues.setValue(notifyValues);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Pattern to validate email address.
     */
    private static Pattern MAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    /**
     * Language in which mail needs to be sent.
     */
    private OptionGroup vOGlanguageValue;
    /**
     * Options for notification condition value.
     */
    private OptionGroup vOGnotificationConditionValues;
    /**
     * Text field for email address.
     */
    private TextField vTFmailValue;
    /**
     * Variable to store EDS application controller object.
     */
    private EdsApplicationController controller;

    /**
     * Initialization method. This method is used to form for mail notification.
     */
    private void init() {
        setSpacing(true);
        setMargin(true);

        vTFmailValue = new TextField();
        vTFmailValue.setWidth("400");
        vTFmailValue.setImmediate(true);
        vTFmailValue.setNullRepresentation("");

        vOGlanguageValue = new OptionGroup();
        vOGlanguageValue.setImmediate(true);
        vOGlanguageValue.setStyleName("horizontal");
        for (Locale locale : controller.getAvailableLocale()) {
            vOGlanguageValue.addItem(locale.getLanguage());
        }

        vOGnotificationConditionValues = new OptionGroup();
        vOGnotificationConditionValues.setImmediate(true);
        vOGnotificationConditionValues.setMultiSelect(true);

        vOGnotificationConditionValues.addItem(controller.getBundle().getString("Param-title-all-modification"));
        vOGnotificationConditionValues.addItem(controller.getBundle().getString("Param-title-new-statu"));
        vOGnotificationConditionValues.addItem(controller.getBundle().getString("Param-title-new-vers"));

        addComponent(new Label(controller.getBundle().getString("Param-title-mail") + " :"));
        addComponent(vTFmailValue);
        addComponent(new Label(controller.getBundle().getString("Param-title-lang") + " :"));
        addComponent(vOGlanguageValue);
        addComponent(new Label(controller.getBundle().getString("Param-title-action") + " :"));
        addComponent(vOGnotificationConditionValues);
    }
}
