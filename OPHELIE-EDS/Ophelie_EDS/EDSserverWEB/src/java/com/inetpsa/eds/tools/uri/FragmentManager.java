package com.inetpsa.eds.tools.uri;

import com.inetpsa.eds.dao.model.EdsEds;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This is fragments Handler class. It contains a set of fragment handler allowing access to certain pages. All accepted fragments meet
 * 
 * <pre>
 * the regular expression [a-zA-Z0-9 \-_.] + (& [A-zA-Z0-9 \-_.] + (= [A -zA-Z0-9 \-_. * +%] +)?) *
 * </ pre>
 * 
 * Or
 * 
 * <pre>
 * [a-zA-Z0-9 \-_.] + = + (& [A-zA-Z0-9 \ [a-zA-Z0-9 \-_ * +%.] - _.] + (= [a-zA-Z0-9 \-_. * +%] +)?) *
 * </ pre>
 * 
 * . The values ​​can contain Special characters. Therefore, URLEncoder URLDecoder are used and these characters used in the remaining specification .
 * 
 * @author Geometric Ltd.
 * @see A_FragmentHandler
 */
public class FragmentManager implements Serializable {
    // PUBLIC
    /**
     * Default constructor
     */
    public FragmentManager() {
        init();
    }

    /**
     * This method add the key related to the fragment in handler.
     * 
     * @param handler Fragment handler
     */
    public void addFragmentHandler(A_FragmentHandler handler) {
        if (handler != null) {
            handlers.put(handler.getKey(), handler);
        }
    }

    /**
     * This method gets the fragment of URL after # key. Associate related handler with type of fragment.
     * 
     * @param fragment URL fragment
     * @throws IllegalArgumentException Invalid arguments
     */
    public void handleFragment(String fragment) throws IllegalArgumentException {
        if (fragment.length() == 0) {
            handlers.get(null).handleWithParameters(null);
        } else if (fragment.matches(NO_ID_FRAGMENT_REGEXP)) {
            A_FragmentHandler handler = handlers.get(null);
            handler.handleWithParameters(handler.parseFragmentResidue(fragment));
        } else if (fragment.matches(FRAGMENT_REGEXP)) {
            int andIndex = fragment.indexOf('&');
            if (andIndex != -1) {
                String fragmentKey = fragment.substring(0, andIndex);
                String fragmentResidue = fragment.substring(fragmentKey.length());
                A_FragmentHandler handler = handlers.get(fragmentKey);

                HashMap<String, String> parameters = handler.parseFragmentResidue(fragmentResidue);
                handler.handleWithParameters(parameters);
            } else {
                handlers.get(fragment).handleWithParameters(null);
            }
        } else {
            throw new IllegalArgumentException("URL introuvable.");
        }
    }

    /**
     * This method format URL fragment of the Project.
     * 
     * @param projectID Project ID
     * @return URL fragment with project id
     */
    public static String formatURLFragmentForProject(String projectID) {
        return A_FragmentHandler.PROJECT_FRAGMENT_KEY + "&id=" + projectID;
    }

    /**
     * This method format URL fragment of the EDS .
     * 
     * @param eds Eds details
     * @return URL fragment with Eds reference and version(if Eds has version)
     */
    public static String formatURLFragmentForEDS(EdsEds eds) {
        if (eds.getEdsState() == EdsEds.DEFAULT_STATE) {
            return A_FragmentHandler.EDS_FRAGMENT_KEY + "&ref=" + eds.getEdsRef();
        } else {
            return A_FragmentHandler.EDS_FRAGMENT_KEY + "&ref=" + eds.getEdsRef() + "&v=" + eds.getEdsMajorVersion() + '.' + eds.getEdsMinorVersion();
        }
    }

    // PROTECTED
    // PRIVATE
    /**
     * Map variable to hold fragment key with associated fragment handler.
     */
    private HashMap<String, A_FragmentHandler> handlers;
    /**
     * Constant variable to hold regular expression , combining the characters for the key.
     */
    private static final String AC_KEY = "[a-zA-Z0-9\\-_.]";
    /**
     * constant variable to hold Regular expression , combining the characters for the values.
     */
    private static final String AC_VALUE = "[a-zA-Z0-9\\-_.*+%]";
    /**
     * constant variable to hold Regular expression for fragment having identifier.
     */
    private static final String FRAGMENT_REGEXP = AC_KEY + "+(&" + AC_KEY + "+(=" + AC_VALUE + "+)?)*";
    /**
     * constant variable to hold Regular expression for fragment not having identifier.
     */
    private static final String NO_ID_FRAGMENT_REGEXP = AC_KEY + "+=" + AC_VALUE + "+(&" + AC_KEY + "+(=" + AC_VALUE + "+)?)*";

    /**
     * Initialize map for handlers.
     */
    private void init() {
        this.handlers = new HashMap<String, A_FragmentHandler>();
    }
}
