package com.inetpsa.poc00.rest.tirebrand;

import java.util.List;

/**
 * The Class ManageTireBrandRequestDto.
 */
public class ManageTireBrandRequestDto {

    /** The tire brand representations list. */
    private List<TireBrandRepresentation> tireBrandRepresentationsList;

    /**
     * Gets the tire brand representations list.
     *
     * @return the tire brand representations list
     */
    public List<TireBrandRepresentation> getTireBrandRepresentationsList() {
        return tireBrandRepresentationsList;
    }

    /**
     * Sets the tire brand representations list.
     *
     * @param tireBrandRepresentationsList the new tire brand representations list
     */
    public void setTireBrandRepresentationsList(List<TireBrandRepresentation> tireBrandRepresentationsList) {
        this.tireBrandRepresentationsList = tireBrandRepresentationsList;
    }

}
