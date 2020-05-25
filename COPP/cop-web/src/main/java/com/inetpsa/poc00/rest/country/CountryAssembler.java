package com.inetpsa.poc00.rest.country;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.country.Country;

/**
 * The Class CountryAssembler.
 */
public class CountryAssembler extends BaseAssembler<Country, CountryRepresentation> {

    /*
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(CountryRepresentation targetDto, Country sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setCountryLable(sourceEntity.getLabel());

    }

    /*
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(Country targetEntity, CountryRepresentation sourceDto) {
        targetEntity.setEntityId(sourceDto.getEntityId());
        targetEntity.setLabel(sourceDto.getCountryLable());

    }

}
