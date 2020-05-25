package com.inetpsa.eds.application.header;

import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.TextField;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Search field for page header of EDS application.
 * 
 * @author Geometric Ltd.
 */
public class SearchField extends TextField implements Handler {
    // PUBLIC
    /**
     * This interface is used to listen to enter command.
     * 
     * @author Geometric Ltd.
     */
    public static interface EnterHitListener extends Serializable {
        /**
         * This method is called when enter is being hit.
         * 
         * @param text Text in the text field is passed as parameter.
         */
        public void enterHit(String text);
    }

    /**
     * Default constructor.
     */
    public SearchField() {
        init();
    }

    /**
     * This method is used to add hit listener.
     * 
     * @param listener Enter hit listener to be added.
     */
    public void addEnterHitListener(EnterHitListener listener) {
        this.enterListeners.add(listener);
    }

    /**
     * This method is used to remove hit listener.
     * 
     * @param listener Enter hit listener to be removed.
     */
    public void removeEnterHitListener(EnterHitListener listener) {
        this.enterListeners.remove(listener);
    }

    /**
     * Gets the list of actions applicable to this handler.
     * 
     * @param target the target handler to list actions for. For item containers this is the item id.
     * @param sender the party that would be sending the actions. Most of this is the action container.
     * @return the list of Action
     * @see com.vaadin.event.Action.Handler#getActions(java.lang.Object, java.lang.Object)
     */
    public Action[] getActions(Object target, Object sender) {
        return new Action[] { enterKeyShortcutAction };
    }

    /**
     * Handles an action for the given target. The handler method may just discard the action if it's not suitable.
     * 
     * @param action the action to be handled.
     * @param sender the sender of the action. This is most often the action container.
     * @param target the target of the action. For item containers this is the item id.
     * @see com.vaadin.event.Action.Handler#handleAction(com.vaadin.event.Action, java.lang.Object, java.lang.Object)
     */
    public void handleAction(Action action, Object sender, Object target) {
        if (action == enterKeyShortcutAction) {
            // If target is a UI component, we search the form that embeds it and commit (submit) that form:
            if (target == this) {
                for (EnterHitListener listener : enterListeners) {
                    listener.enterHit((String) this.getValue());
                }
            }
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Variable to store enter key short-cut action.
     */
    private static final Action enterKeyShortcutAction = new ShortcutAction(null, ShortcutAction.KeyCode.ENTER, null);
    /**
     * List of Enter hit listeners.
     */
    private ArrayList<EnterHitListener> enterListeners;

    /**
     * Initialization method.
     */
    private void init() {
        this.enterListeners = new ArrayList<EnterHitListener>();
    }
}
