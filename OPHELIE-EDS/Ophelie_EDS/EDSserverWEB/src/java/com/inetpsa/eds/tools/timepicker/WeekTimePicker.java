package com.inetpsa.eds.tools.timepicker;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import java.util.Calendar;
import java.util.Date;

/**
 * Component for selecting a time range for week. The date returned by week selected is the first day of the week start. In contrast, the date
 * returned by the end of week selected matches first day of the following week. Thus, select 2 times the same week will return a range of one week.
 * It is possible to disable one of the two dates to select an unbounded interval.
 * 
 * @author Geometric Ltd.
 */
public class WeekTimePicker extends HorizontalLayout {
    // PUBLIC
    /**
     * Default constructor
     */
    public WeekTimePicker() {
        this(null, null);
    }

    /**
     * Parameterized constructor
     * 
     * @param initStartDate Start date of week
     * @param initEndDate End date of week
     */
    public WeekTimePicker(Date initStartDate, Date initEndDate) {
        this.initStartDate = initStartDate;
        this.initEndDate = initEndDate;
        init();
    }

    /**
     * Returns the date corresponding to the first day of the week start. If this date is disabled, returns null.
     * 
     * @return Date correspondent to start week.
     */
    public Date getSelectedStartDate() {
        if (!vCBstartDate.booleanValue()) {
            return null;
        }

        return (Date) vTXTstartDate.getValue();
    }

    /**
     * Returns the date corresponding to the first day of the week following the week-end. If this date is disabled, returns null.
     * 
     * @return Date correspondent to the week of departure.
     */
    public Date getSelectedEndDate() {
        if (!vCBendDate.booleanValue()) {
            return null;
        }

        return (Date) vTXTendDate.getValue();
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold start date value
     */
    private Date initStartDate;
    /**
     * Variable to hold end date value
     */
    private Date initEndDate;
    /**
     * Variable to hold start date text value
     */
    private PopupDateField vTXTstartDate;
    /**
     * Variable to hold start date check box value
     */
    private CheckBox vCBstartDate;
    /**
     * Variable to hold start date label value
     */
    private Label vLBLstartDate;
    /**
     * Variable to hold and label value
     */
    private Label vLBLand;
    /**
     * Variable to hold end date text value
     */
    private PopupDateField vTXTendDate;
    /**
     * Variable to hold end date check box value
     */
    private CheckBox vCBendDate;
    /**
     * Variable to hold end date label value
     */
    private Label vLBLendDate;

    /**
     * Initialize Week time picker
     */
    private void init() {
        Calendar calendar = Calendar.getInstance();
        if (initStartDate == null) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        this.setSizeUndefined();
        this.setSpacing(true);

        Label vLBLcaption = new Label("Laps de temps :");
        this.addComponent(vLBLcaption);

        vCBstartDate = new CheckBox(null, new CheckBox.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (event.getButton().booleanValue()) {
                    vLBLstartDate.setEnabled(true);
                    vTXTstartDate.setEnabled(true);
                    vCBendDate.setEnabled(true);
                    vLBLand.setEnabled(true);
                } else {
                    vLBLstartDate.setEnabled(false);
                    vTXTstartDate.setEnabled(false);
                    vCBendDate.setEnabled(false);
                    vLBLand.setEnabled(false);
                }
            }
        });
        vCBstartDate.setValue(true);
        vCBstartDate.setImmediate(true);

        vLBLstartDate = new Label("après");

        if (initStartDate == null) {
            vTXTstartDate = new PopupDateField(null, calendar.getTime());
        } else {
            vTXTstartDate = new PopupDateField(null, initStartDate);
        }
        vTXTstartDate.setInvalidAllowed(false);
        vTXTstartDate.setInvalidCommitted(false);
        vTXTstartDate.setResolution(PopupDateField.RESOLUTION_DAY);
        vTXTstartDate.setShowISOWeekNumbers(true);
        vTXTstartDate.setDateFormat("dd/MM/yyyy");
        vTXTstartDate.addValidator(new Validator() {
            public void validate(Object value) throws InvalidValueException {
                if (value == null || !(value instanceof Date)) {
                    throw new InvalidValueException("Le format de date n'est pas valide.");
                } else if (((Date) value).after((Date) vTXTendDate.getValue())) {
                    throw new InvalidValueException("La date de départ ne peut pas se situer après la date de fin.");
                }
            }

            public boolean isValid(Object value) {
                if (value == null || !(value instanceof Date)) {
                    return false;
                } else if (((Date) value).after((Date) vTXTendDate.getValue())) {
                    return false;
                }

                return true;
            }
        });
        vTXTstartDate.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (vTXTstartDate.isValid()) {
                    vTXTstartDate.setComponentError(null);
                }
            }
        });
        vTXTstartDate.setImmediate(true);

        this.addComponent(vCBstartDate);
        this.addComponent(vLBLstartDate);
        this.addComponent(vTXTstartDate);

        if (initEndDate == null) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        vLBLand = new Label("et");
        vLBLand.setSizeUndefined();
        this.addComponent(vLBLand);

        vCBendDate = new CheckBox(null, new CheckBox.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (event.getButton().booleanValue()) {
                    vLBLendDate.setEnabled(true);
                    vTXTendDate.setEnabled(true);
                    vCBstartDate.setEnabled(true);
                    vLBLand.setEnabled(true);
                } else {
                    vLBLendDate.setEnabled(false);
                    vTXTendDate.setEnabled(false);
                    vCBstartDate.setEnabled(false);
                    vLBLand.setEnabled(false);
                }
            }
        });
        vCBendDate.setValue(true);
        vCBendDate.setImmediate(true);

        vLBLendDate = new Label("avant");

        if (initEndDate == null) {
            vTXTendDate = new PopupDateField(null, calendar.getTime());
        } else {
            vTXTendDate = new PopupDateField(null, initEndDate);
        }
        vTXTendDate.setInvalidAllowed(false);
        vTXTendDate.setInvalidCommitted(false);
        vTXTendDate.setDateFormat("dd/MM/yyyy");
        vTXTendDate.setResolution(PopupDateField.RESOLUTION_DAY);
        vTXTendDate.setShowISOWeekNumbers(true);
        vTXTendDate.addValidator(new Validator() {
            public void validate(Object value) throws InvalidValueException {
                if (value == null || !(value instanceof Date)) {
                    throw new InvalidValueException("Le format de date n'est pas valide.");
                } else if (((Date) value).before((Date) vTXTstartDate.getValue())) {
                    throw new InvalidValueException("La date de fin ne peut pas se situer avant la date de départ.");
                }
            }

            public boolean isValid(Object value) {
                if (value == null || !(value instanceof Date)) {
                    return false;
                } else if (((Date) value).before((Date) vTXTstartDate.getValue())) {
                    return false;
                }

                return true;
            }
        });

        vTXTendDate.addListener(new ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                if (vTXTendDate.isValid()) {
                    vTXTendDate.setComponentError(null);
                }
            }
        });
        vTXTendDate.setImmediate(true);
        vTXTendDate.setImmediate(true);

        this.addComponent(vCBendDate);
        this.addComponent(vLBLendDate);
        this.addComponent(vTXTendDate);

        this.setComponentAlignment(vLBLcaption, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vCBstartDate, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vLBLstartDate, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vTXTstartDate, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vLBLand, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vCBendDate, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vLBLendDate, Alignment.MIDDLE_LEFT);
        this.setComponentAlignment(vLBLendDate, Alignment.MIDDLE_LEFT);
    }
}
