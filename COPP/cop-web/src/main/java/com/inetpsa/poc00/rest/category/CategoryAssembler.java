package com.inetpsa.poc00.rest.category;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;

import com.inetpsa.poc00.domain.category.Category;
import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszFactory;
import com.inetpsa.poc00.rest.clasz.ClaszAssembler;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;

/**
 * The Class CategoryAssembler.
 */
public class CategoryAssembler extends
		BaseAssembler<Category, CategoryRepresentation> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate
	 * (java.lang.Object, org.seedstack.business.domain.AggregateRoot)
	 */
	@Inject
	private ClaszAssembler claszAssembler;
	@Inject
	private ClaszFactory claszFactory;

	@Override
	public void doAssembleDtoFromAggregate(CategoryRepresentation targetDto,
			Category sourceAggregate) {

		targetDto.setEntityId(sourceAggregate.getEntityId());
		targetDto.setLabel(sourceAggregate.getLabel());
		targetDto.setDescription(sourceAggregate.getDescription());
		if (!sourceAggregate.getClasz().isEmpty()
				|| sourceAggregate.getClasz() != null) {
			Set<ClaszRepresentation> claszRepresentset = new HashSet<ClaszRepresentation>();

			for (Clasz obj : sourceAggregate.getClasz()) {
				ClaszRepresentation claszRepresent = new ClaszRepresentation();
				claszAssembler.doAssembleDtoFromAggregate(claszRepresent, obj);
				claszRepresentset.add(claszRepresent);
			}

			targetDto.setClaszRepresentation(claszRepresentset);

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto
	 * (org.seedstack.business.domain.AggregateRoot, java.lang.Object)
	 */
	@Override
	public void doMergeAggregateWithDto(Category targetAggregate,
			CategoryRepresentation sourceDto) {

		targetAggregate.setEntityId(sourceDto.getEntityId());
		targetAggregate.setLabel(sourceDto.getLabel());
		targetAggregate.setDescription(sourceDto.getDescription());
		if (!sourceDto.getClaszRepresentation().isEmpty()
				|| sourceDto.getClaszRepresentation() != null) {
			Set<Clasz> claszset = new HashSet<Clasz>();

			for (ClaszRepresentation obj : sourceDto.getClaszRepresentation()) {
				Clasz claszObj = claszFactory.createClasz();
				claszAssembler.doMergeAggregateWithDto(claszObj, obj);
				claszset.add(claszObj);

			}
			targetAggregate.setClasz(claszset);

		}

	}

}
