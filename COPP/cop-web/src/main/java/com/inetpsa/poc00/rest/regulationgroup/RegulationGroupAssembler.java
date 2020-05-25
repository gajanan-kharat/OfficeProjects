package com.inetpsa.poc00.rest.regulationgroup;

import java.util.ArrayList;
import java.util.List;

import org.seedstack.business.assembler.BaseAssembler;

import com.google.inject.Inject;
import com.inetpsa.poc00.domain.es.EmissionStandard;
import com.inetpsa.poc00.domain.es.EmissionStandardFactory;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatisticalCalculationRule;
import com.inetpsa.poc00.domain.statisticalcalculationrule.StatsCalcltnRuleFactory;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaFactory;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataFactory;
import com.inetpsa.poc00.emsdeptcl.EmsDepTCLFinder;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardAssembler;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.EmsDepTCtoRGValuedTCAssembler;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependantAssembler;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.GenericTCtoRGVAluedTC;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValdTestConditionRepresentation;
import com.inetpsa.poc00.rest.regulationgroupvaluedtc.RegGrpValuedTCAssembler;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleAssembler;
import com.inetpsa.poc00.rest.statisticalcalculationrule.StatisticalCalculationRuleRepresentation;
import com.inetpsa.poc00.rest.status.StatusFinder;
import com.inetpsa.poc00.rest.status.StatusRepresentation;
import com.inetpsa.poc00.rest.status.StatusRepresentationAssembler;
import com.inetpsa.poc00.rest.tvv.TvvAssembler;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaAssembler;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;
import com.inetpsa.poc00.rest.wtlp.WLTPVLowHighDataAssembler;
import com.inetpsa.poc00.rest.wtlp.WLTPVLowHighDataRepresentation;

/**
 * The Class RegulationGroupAssembler.
 */
public class RegulationGroupAssembler extends BaseAssembler<RegulationGroup, RegulationGroupRepresentation> {

    /** The status assembler. */
    @Inject
    private StatusRepresentationAssembler statusAssembler;

    /** The type aproval assembler. */
    @Inject
    private TypeApprovalAreaAssembler typeAprovalAssembler;

    /** The emissionfactory. */
    @Inject
    EmissionStandardFactory emissionfactory;

    /** The typeapproval factory. */
    @Inject
    TypeApprovalAreaFactory typeApprovalFactory;

    /** The emission assembler. */
    @Inject
    EmissionStandardAssembler emissionAssembler;

    /** The ems dep tcl finder. */
    @Inject
    EmsDepTCLFinder emsDepTclFinder;

    /** The ems to rgvld assembler. */
    @Inject
    EmsDepTCtoRGValuedTCAssembler depToValuedConditionAssembler;

    /** The generic test condition finder. */
    @Inject
    GenericTestConditionFinder genericTestConditionFinder;

    /** The generic tcto rg valued tc. */
    @Inject
    GenericTCtoRGVAluedTC genericTctoRgValuedTc;

    /** The reg grp valued tc assembler. */
    @Inject
    RegGrpValuedTCAssembler regGrpValuedTcAssembler;

    /** The rg valued esd dependant assembler. */
    @Inject
    RGValuedESDependantAssembler rgValuedEsdDependantAssembler;

    /** The wltp assembler. */
    @Inject
    private WLTPVLowHighDataAssembler wltpAssembler;

    /** The wltp factory. */
    @Inject
    private WLTPVLowHighDataFactory wltpFactory;

    /** The tvv assembler. */
    @Inject
    TvvAssembler tvvAssembler;

    /** The status finder. */
    @Inject
    private StatusFinder statusFinder;

    @Inject

    private StatsCalcltnRuleFactory statisticalFactory;

    @Inject
    private StatisticalCalculationRuleAssembler statisticalRuleAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(RegulationGroupRepresentation targetDto, RegulationGroup sourceEntity) {

        targetDto.setEntityId(sourceEntity.getEntityId());
        targetDto.setLabel(sourceEntity.getLabel());
        targetDto.setDescription(sourceEntity.getLabel());
        targetDto.setVersion(sourceEntity.getVersion());
        targetDto.setCreationDate(sourceEntity.getCreationDate());
        targetDto.setModificationDate(sourceEntity.getModificationDate());
        if (sourceEntity.getRegulationgroupstatus() != null) {
            StatusRepresentation statusRepresent = new StatusRepresentation();
            statusAssembler.doAssembleDtoFromAggregate(statusRepresent, sourceEntity.getRegulationgroupstatus());
            targetDto.setRegulationgroupstatus(statusRepresent);
        }
        if (sourceEntity.getEmissionStandardforRg() != null) {
            EmissionStandardRepresentation emissionRepresent = new EmissionStandardRepresentation();

            emissionAssembler.doAssembleDtoFromAggregate(emissionRepresent, sourceEntity.getEmissionStandardforRg());
            targetDto.setEmissionStandardforRg(emissionRepresent);
        }
        if ((sourceEntity.getTypeApprovalArea() != null)) {
            TypeApprovalAreaRepresentation typeApprovalRepresent = new TypeApprovalAreaRepresentation();
            typeAprovalAssembler.doAssembleDtoFromAggregate(typeApprovalRepresent, sourceEntity.getTypeApprovalArea());
            targetDto.setTypeApprovalArea(typeApprovalRepresent);
        }
        if (sourceEntity.getRgValuedEsDepTCL() != null) {
            List<RGValuedESDependentTCL> regTclEntityList = sourceEntity.getRgValuedEsDepTCL();
            if (regTclEntityList.size() != 0) {
                List<RGValuedESDependentTCLRepresentation> rgValuedEsdTcrepresentlList = new ArrayList<RGValuedESDependentTCLRepresentation>();
                for (RGValuedESDependentTCL rgValdtc : regTclEntityList) {
                    RGValuedESDependentTCLRepresentation rgValuedEsdRepresent = new RGValuedESDependentTCLRepresentation();
                    rgValuedEsdDependantAssembler.doAssembleDtoFromAggregate(rgValuedEsdRepresent, rgValdtc);

                    rgValuedEsdTcrepresentlList.add(rgValuedEsdRepresent);

                }
                targetDto.setRgValuedEsDepTCL(rgValuedEsdTcrepresentlList);
            }

        }
        if (sourceEntity.getWltpVLowHighData() != null) {
            WLTPVLowHighData wltpLowHIghEntity = sourceEntity.getWltpVLowHighData();
            if (wltpLowHIghEntity != null) {
                WLTPVLowHighDataRepresentation wltpLowHighDataRepresent = new WLTPVLowHighDataRepresentation();
                wltpAssembler.doAssembleDtoFromAggregate(wltpLowHighDataRepresent, wltpLowHIghEntity);

                targetDto.setWltpRepresentation(wltpLowHighDataRepresent);
            }
        }
        if (sourceEntity.getStatisticalCalculationRule() != null) {
            StatisticalCalculationRuleRepresentation statisticalRuleRepresent = new StatisticalCalculationRuleRepresentation();
            statisticalRuleAssembler.doAssembleDtoFromAggregate(statisticalRuleRepresent, sourceEntity.getStatisticalCalculationRule());
            targetDto.setStatisticalRuleRepresentation(statisticalRuleRepresent);
            targetDto.setOldStatisticalObject(statisticalRuleRepresent);

        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    public void doMergeAggregateWithDto(RegulationGroup regulationGroup, RegulationGroupRepresentation sourceDto) {
        regulationGroup.setEntityId(sourceDto.getEntityId());
        regulationGroup.setLabel(sourceDto.getLabel());
        regulationGroup.setDescription(sourceDto.getDescription());
        regulationGroup.setVersion(sourceDto.getVersion());
        regulationGroup.setCreationDate(sourceDto.getCreationDate());
        regulationGroup.setModificationDate(sourceDto.getModificationDate());
        if (sourceDto.getRegulationgroupstatus() != null) {
            Status status = statusFinder.getStatusForEmissionStandard(sourceDto.getRegulationgroupstatus().getLabel());
            regulationGroup.setRegulationgroupstatus(status);
        }
        if (sourceDto.getTypeApprovalArea() != null) {
            TypeApprovalArea typeApproval = new TypeApprovalArea();
            typeAprovalAssembler.doMergeAggregateWithDto(typeApproval, sourceDto.getTypeApprovalArea());
            regulationGroup.setTypeApprovalArea(typeApproval);
        }
        if (sourceDto.getEmissionStandardforRg() != null) {
            EmissionStandard emission = emissionfactory.createEmissonStandard();
            emissionAssembler.doMergeAggregateWithDto(emission, sourceDto.getEmissionStandardforRg());
            regulationGroup.setEmissionStandardforRg(emission);
        }
        List<RGValuedESDependentTCLRepresentation> rgvaluedesdRepresentationList = sourceDto.getRgValuedEsDepTCL();
        if (rgvaluedesdRepresentationList != null) {
            List<RGValuedESDependentTCL> rgValuedEsdDependantTclList = new ArrayList<RGValuedESDependentTCL>();
            for (RGValuedESDependentTCLRepresentation rgvrepresentation : rgvaluedesdRepresentationList) {
                List<RegGrpValdTestConditionRepresentation> regValuedTcRepresentation = rgvrepresentation.getRgValuedGenericTestConditionrepresent();

                RGValuedESDependentTCL rgValuedEsdDependantTcl = new RGValuedESDependentTCL();
                rgValuedEsdDependantAssembler.doMergeAggregateWithDto(rgValuedEsdDependantTcl, rgvrepresentation);
                /*
                 * List<RegGrpValdTestCondition> regGrpValuedTCList = new ArrayList<RegGrpValdTestCondition>(); for
                 * (RegGrpValdTestConditionRepresentation regValuedTCRepresentation : regValuedTcRepresentation) {
                 * 
                 * RegGrpValdTestCondition regGrpValuedTc = new RegGrpValdTestCondition();
                 * regGrpValuedTcAssembler.doMergeAggregateWithDto(regGrpValuedTc, regValuedTCRepresentation);
                 * regGrpValuedTc.setRgValuedEsDepTCL(rgValuedEsdDependantTcl); regGrpValuedTCList.add(regGrpValuedTc); }
                 * rgValuedEsdDependantTcl.setRgValuedGenericTestCondition(regGrpValuedTCList);
                 */
                rgValuedEsdDependantTcl.setRegulationGroup(regulationGroup);
                rgValuedEsdDependantTclList.add(rgValuedEsdDependantTcl);

            }
            regulationGroup.setRgValuedEsDepTCL(rgValuedEsdDependantTclList);
        }
        WLTPVLowHighDataRepresentation wltpDto = sourceDto.getWltpRepresentation();
        if (wltpDto != null) {
            WLTPVLowHighData wltpEntity = wltpFactory.createWLTPVLowHighData();
            wltpAssembler.doMergeAggregateWithDto(wltpEntity, wltpDto);
            regulationGroup.setWltpVLowHighData(wltpEntity);
        }
        StatisticalCalculationRuleRepresentation statisticalCalcRuleDto = sourceDto.getStatisticalRuleRepresentation();
        if (statisticalCalcRuleDto != null) {
            StatisticalCalculationRule statisticalRuleEntity = statisticalFactory.createStatCalculationRule();
            statisticalRuleAssembler.doMergeAggregateWithDto(statisticalRuleEntity, statisticalCalcRuleDto);
            regulationGroup.setStatisticalCalculationRule(statisticalRuleEntity);
        }
    }

}
