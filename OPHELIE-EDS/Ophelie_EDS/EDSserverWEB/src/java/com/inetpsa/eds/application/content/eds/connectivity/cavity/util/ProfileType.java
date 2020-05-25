/*
 * Creation : 29/09/2015
 */
package com.inetpsa.eds.application.content.eds.connectivity.cavity.util;

/**
 * Profile types for Cavity Definition
 * 
 * @author jcosta @ Alter Frame
 */
public enum ProfileType {
    INOM(0, "chs-cavdef-profile-inom"), // Easy Reading
    IMAX(1, "chs-cavdef-profile-imax"), //
    TMAX(2, "chs-cavdef-profile-tmax"), //
    VMIN(3, "chs-cavdef-profile-vmin"); //

    private final String name;
    private final int index;

    /**
     * @param index - Number to identify the property
     * @param name - Resource Bundle Name
     */
    ProfileType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    /**
     * @param id - type of profile
     * @return ProfileType or null if it doesn't exist
     */
    public static ProfileType getProfileFromType(int id) {
        for (ProfileType profile : ProfileType.values()) {
            if (profile.getIndex() == id) {
                return profile;
            }
        }
        return null;
    }
}
