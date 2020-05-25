package com.inetpsa.eds.tools.doublelist;

import com.inetpsa.eds.tools.resource.ResourceManager;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents.DoubleClickEvent;
import com.vaadin.event.MouseEvents.DoubleClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class represent double list
 * 
 * @author Geometric Ltd.
 */
public class DoubleList extends HorizontalLayout {
    // PUBLIC
    /**
     * Default constructor
     */
    public DoubleList() {
        init();
    }

    /**
     * This method set left column caption
     * 
     * @param caption Caption for left column
     */
    public void setLeftColumnCaption(String caption) {
        optionList.setCaption(caption);
    }

    /**
     * This method set right column caption
     * 
     * @param caption Caption for right column
     */
    public void setRightColumnCaption(String caption) {
        selectList.setCaption(caption);
    }

    /**
     * This method add item in Option list
     * 
     * @param itemID Item id to add in option list
     */
    public void addOption(Object itemID) {
        optionList.addItem(itemID);
    }

    /**
     * This method add item in Select list
     * 
     * @param itemID Item id to add in select list
     */
    public void addSelection(Object itemID) {
        selectList.addItem(itemID);
    }

    /**
     * This method unselect item specified.
     * 
     * @param itemId Item id to be unselected
     */
    public void unselect(Object itemId) {
        optionList.unselect(itemId);
        selectList.unselect(itemId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.ui.AbstractComponent#setImmediate(boolean)
     */
    @Override
    public void setImmediate(boolean immediate) {
        super.setImmediate(immediate);
        optionList.setImmediate(immediate);
        selectList.setImmediate(immediate);
    }

    /**
     * This method return size of ListSelect
     * 
     * @return size of ListSelect
     */
    public int size() {
        return optionList.size() + selectList.size();
    }

    /**
     * This method set null selection allowed
     * 
     * @param nullSelectionAllowed check if null selection allowed
     */
    public void setNullSelectionAllowed(boolean nullSelectionAllowed) {
        optionList.setNullSelectionAllowed(nullSelectionAllowed);
        selectList.setNullSelectionAllowed(nullSelectionAllowed);
    }

    /**
     * This method set multiple select
     * 
     * @param multiSelect Check if multiple select allowed
     */
    public void setMultiSelect(boolean multiSelect) {
        optionList.setMultiSelect(multiSelect);
        selectList.setMultiSelect(multiSelect);
    }

    /**
     * This method set item caption
     * 
     * @param itemId Item id to which caption is given
     * @param caption Caption for item
     */
    public void setItemCaption(Object itemId, String caption) {
        if (optionList.containsId(itemId)) {
            optionList.setItemCaption(itemId, caption);
        }
        if (selectList.containsId(itemId)) {
            selectList.setItemCaption(itemId, caption);
        }
    }

    /**
     * This method select the item
     * 
     * @param itemId Item id to be selected
     */
    public void select(Object itemId) {
        if (optionList.containsId(itemId)) {
            optionList.select(itemId);
        }
        if (selectList.containsId(itemId)) {
            selectList.select(itemId);
        }
    }

    /**
     * This method Move Up the item of the selection list
     * 
     * @param itemId Item id to be moved up
     */
    public void moveUp(Object itemId) {
        if (selectList.containsId(itemId)) {
            Container.Indexed container = (Indexed) selectList.getContainerDataSource();
            int itemIndex = container.indexOfId(itemId);
            if (itemIndex > 0) {
                container.removeItem(itemId);
                container.addItemAt(itemIndex - 1, itemId);
            }
        }
    }

    /**
     * This method Move Down the item of the selection list
     * 
     * @param itemId item id to be moved down
     */
    public void moveDown(Object itemId) {
        if (selectList.containsId(itemId)) {
            Container.Indexed container = (Indexed) selectList.getContainerDataSource();
            int itemIndex = container.indexOfId(itemId);
            if (itemIndex < container.size() - 1) {
                container.removeItem(itemId);
                container.addItemAt(itemIndex + 1, itemId);
            }
        }
    }

    /**
     * This method removes the item
     * 
     * @param itemId Item id to be removed
     * @return check if item is removed
     * @throws UnsupportedOperationException
     */
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        return optionList.removeItem(itemId) || selectList.removeItem(itemId);
    }

    /**
     * This method removes all the item
     * 
     * @return Check if all items are removed
     * @throws UnsupportedOperationException exception is thrown when removes operation is not supported
     */
    public boolean removeAllItems() throws UnsupportedOperationException {
        return optionList.removeAllItems() && selectList.removeAllItems();
    }

    /**
     * This method check if item is selected
     * 
     * @param itemId Item id to be checked
     * @return Check if item is selected
     */
    public boolean isSelected(Object itemId) {
        return optionList.isSelected(itemId) || selectList.isSelected(itemId);
    }

    /**
     * This method check if null selection is allowed
     * 
     * @return Check if null selection is allowed
     */
    public boolean isNullSelectionAllowed() {
        return optionList.isNullSelectionAllowed();
    }

    /**
     * This method check if new item is allowed
     * 
     * @return Check if new item is allowed
     */
    public boolean isNewItemsAllowed() {
        return optionList.isNewItemsAllowed();
    }

    /**
     * This method check if multiple selection is possible
     * 
     * @return check if multiple selection is possible
     */
    public boolean isMultiSelect() {
        return optionList.isMultiSelect();
    }

    /**
     * This method return the option value
     * 
     * @return Option value
     */
    public Object getOptionValue() {
        return optionList.getValue();
    }

    /**
     * This method returns collection of options
     * 
     * @return Collection of options
     */
    public Collection<?> getAllOptions() {
        return optionList.getItemIds();
    }

    /**
     * This method returns selected value
     * 
     * @return selected value
     */
    public Object getSelectedValue() {
        return selectList.getValue();
    }

    /**
     * This method returns collection of selected items
     * 
     * @return collection of selected options
     */
    public Collection getAllSelections() {
        return selectList.getItemIds();
    }

    /**
     * This method returns collection of item ids
     * 
     * @return Collection of item ids
     */
    public Collection getItemIds() {
        return optionList.getItemIds();
    }

    /**
     * This method returns caption for given item id
     * 
     * @param itemId Item id
     * @return Caption for given item id
     */
    public String getItemCaption(Object itemId) {
        if (optionList.containsId(itemId)) {
            return optionList.getItemCaption(itemId);
        }
        if (selectList.containsId(itemId)) {
            return selectList.getItemCaption(itemId);
        }
        return "";
    }

    /**
     * This method check if given item id is present in list
     * 
     * @param itemId Item id
     * @return Check if item is present
     */
    public boolean containsId(Object itemId) {
        return optionList.containsId(itemId) || selectList.containsId(itemId);
    }

    /**
     * This method returns the index of given item id in list
     * 
     * @param itemId Item id
     * @return Index of item in list
     */
    public int indexOfItem(Object itemId) {
        int index = -1;
        if (optionList.containsId(itemId)) {
            Container.Indexed container = (Indexed) optionList.getContainerDataSource();
            index = container.indexOfId(itemId);
        } else if (selectList.containsId(itemId)) {
            Container.Indexed container = (Indexed) selectList.getContainerDataSource();
            index = container.indexOfId(itemId);
        }
        return index;
    }

    /**
     * This method set row in list
     * 
     * @param rows number of rows
     */
    public void setRows(int rows) {
        optionList.setRows(rows);
        selectList.setRows(rows);
    }

    /**
     * This method returns the number of rows in list
     * 
     * @return number of rows
     */
    public int getRows() {
        return optionList.getRows();
    }

    /**
     * This method adds the option when option in list is double clicked
     * 
     * @param listener Double click listener
     */
    public void addOptionDoubleClickListener(DoubleClickListener listener) {
        optionsDoubleClickListener.add(listener);
    }

    /**
     * This method removes the option when option in list is double clicked
     * 
     * @param listener Double click listener
     */
    public void removeOptionDoubleClickListener(DoubleClickListener listener) {
        optionsDoubleClickListener.remove(listener);
    }

    /**
     * This method removes all options after double click
     */
    public void removeAllOptionDoubleClickListener() {
        optionsDoubleClickListener.clear();
    }

    /**
     * This method returns collection of optionsDoubleClickListener
     * 
     * @return collection of optionsDoubleClickListener
     */
    public Collection<DoubleClickListener> getAllOptionDoubleClickListener() {
        return optionsDoubleClickListener;
    }

    /**
     * This method adds the selected options in list when selected option double clicked
     * 
     * @param listener Double click listener
     */
    public void addSelectionDoubleClickListener(DoubleClickListener listener) {
        selectionsDoubleClickListener.add(listener);
    }

    /**
     * This method removes the selected option when selected option in list is double clicked
     * 
     * @param listener Double click listener
     */
    public void removeSelectionDoubleClickListener(DoubleClickListener listener) {
        selectionsDoubleClickListener.remove(listener);
    }

    /**
     * This method removes all selections after double click
     */
    public void removeAllSelectionDoubleClickListener() {
        selectionsDoubleClickListener.clear();
    }

    /**
     * This method returns collection of selectionsDoubleClickListener
     * 
     * @return collection of selectionsDoubleClickListener
     */
    public Collection<DoubleClickListener> getAllSelectionDoubleClickListener() {
        return selectionsDoubleClickListener;
    }

    /**
     * This method adds button.
     * 
     * @param button Button to add
     */
    public void addButton(Button button) {
        buttonsLayout.addComponent(button);
        buttonsLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to hold value of ListSelect of optional items
     */
    private ListSelect optionList;
    /**
     * Variable to hold value of ListSelect of selected items
     */
    private ListSelect selectList;
    /**
     * Variable to hold value of collection of DoubleClickListener for optional items
     */
    private Collection<DoubleClickListener> optionsDoubleClickListener;
    /**
     * Variable to hold value of collection of DoubleClickListener for selected items
     */
    private Collection<DoubleClickListener> selectionsDoubleClickListener;
    /**
     * Variable to hold value of VerticalLayout for buttons
     */
    private VerticalLayout buttonsLayout;

    /**
     * Initialize Double list
     */
    private void init() {
        this.optionsDoubleClickListener = new LinkedHashSet<DoubleClickListener>();
        this.selectionsDoubleClickListener = new LinkedHashSet<DoubleClickListener>();

        this.optionList = new ListSelect();
        this.selectList = new ListSelect();

        buttonsLayout = new VerticalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);

        Button addButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (isMultiSelect()) {
                    for (Object itemId : (Set) optionList.getValue()) {
                        selectList.addItem(itemId);
                        selectList.setItemCaption(itemId, optionList.getItemCaption(itemId));
                    }
                    for (Object itemId : (Set) optionList.getValue()) {
                        optionList.removeItem(itemId);
                    }
                } else {
                    Object itemId = optionList.getValue();
                    if (itemId != null) {
                        selectList.addItem(itemId);
                        selectList.setItemCaption(itemId, optionList.getItemCaption(itemId));
                        optionList.removeItem(itemId);
                    }
                }
            }
        });
        addButton.setImmediate(true);
        addButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/right.png"));
        addButton.setStyleName(BaseTheme.BUTTON_LINK);
        buttonsLayout.addComponent(addButton);

        Button removeButton = new Button(null, new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (isMultiSelect()) {
                    for (Object itemId : (Set) selectList.getValue()) {
                        optionList.addItem(itemId);
                        optionList.setItemCaption(itemId, selectList.getItemCaption(itemId));
                    }
                    for (Object itemId : (Set) selectList.getValue()) {
                        selectList.removeItem(itemId);
                    }
                } else {
                    Object itemId = selectList.getValue();
                    if (itemId != null) {
                        optionList.addItem(itemId);
                        optionList.setItemCaption(itemId, selectList.getItemCaption(itemId));
                        selectList.removeItem(itemId);
                    }
                }
            }
        });
        removeButton.setImmediate(true);
        removeButton.setIcon(ResourceManager.getInstance().getThemeIconResource("icons/left.png"));
        removeButton.setStyleName(BaseTheme.BUTTON_LINK);
        buttonsLayout.addComponent(removeButton);
        buttonsLayout.setSizeUndefined();

        optionList.setWidth("100%");
        selectList.setWidth("100%");

        addComponent(optionList);
        addComponent(buttonsLayout);
        setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        addComponent(selectList);
        setExpandRatio(optionList, .5f);
        setExpandRatio(selectList, .5f);

        // Adding clickable layout
        addListener(new LayoutClickListener() {
            public void layoutClick(LayoutClickEvent event) {
                if (event.getChildComponent() == optionList) {
                    selectList.unselect(selectList.getValue());
                    if (event.isDoubleClick()) {
                        for (DoubleClickListener l : optionsDoubleClickListener) {
                            l.doubleClick(new DoubleClickEvent(optionList));
                        }
                    }
                } else if (event.getChildComponent() == selectList) {
                    optionList.unselect(optionList.getValue());
                    if (event.isDoubleClick()) {
                        for (DoubleClickListener l : selectionsDoubleClickListener) {
                            l.doubleClick(new DoubleClickEvent(selectList));
                        }
                    }
                }
            }
        });
    }
}
