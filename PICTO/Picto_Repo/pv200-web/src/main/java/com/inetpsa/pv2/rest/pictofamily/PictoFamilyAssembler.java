package com.inetpsa.pv2.rest.pictofamily;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.seedstack.business.assembler.BaseAssembler;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;
import org.seedstack.pv2.domain.type.Type;

import com.inetpsa.pv2.rest.category.CategoryAssembler;
import com.inetpsa.pv2.rest.category.CategoryRepresentation;
import com.inetpsa.pv2.rest.color.ColorAssembler;
import com.inetpsa.pv2.rest.color.ColorRepresentation;
import com.inetpsa.pv2.rest.picto.CreatePictoRepresentation;
import com.inetpsa.pv2.rest.picto.PictoAssembler;
import com.inetpsa.pv2.rest.picto.PictoRepresentation;
import com.inetpsa.pv2.rest.ruleofuses.RuleOfUsesAssembler;
import com.inetpsa.pv2.rest.ruleofuses.RuleOfUsesRepresentation;
import com.inetpsa.pv2.rest.specificdrawing.SpecificDrawingAssembler;
import com.inetpsa.pv2.rest.specificdrawing.SpecificDrawingRepresentation;
import com.inetpsa.pv2.rest.type.TypeAssembler;
import com.inetpsa.pv2.rest.type.TypeRepresentation;

/**
 * The Class PictoFamilyAssembler.
 */
public class PictoFamilyAssembler extends BaseAssembler<PictoFamily, PictoFamilyRepresentation> {

    /** The type assembler. */
    @Inject
    private TypeAssembler typeAssembler;

    /** The color assembler. */
    @Inject
    private ColorAssembler colorAssembler;

    /** The picto assembler. */
    @Inject
    private PictoAssembler pictoAssembler;

    /** The rule of uses assembler. */
    @Inject
    private RuleOfUsesAssembler ruleOfUsesAssembler;

    /** The specific drawing assembler. */
    @Inject
    private SpecificDrawingAssembler specificDrawingAssembler;

    /** The category assembler. */
    @Inject
    private CategoryAssembler categoryAssembler;

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doAssembleDtoFromAggregate(java.lang.Object, org.seedstack.business.domain.AggregateRoot)
     */
    @Override
    public void doAssembleDtoFromAggregate(PictoFamilyRepresentation targetDto, PictoFamily sourceEntity) {
        PictoFamilyAssemblerHelper.doAssembleDtoFromAggregate(targetDto, sourceEntity);

        Type type = sourceEntity.getTypeID();
        if (type != null) {
            TypeRepresentation typeRepresentation = new TypeRepresentation();
            typeAssembler.doAssembleDtoFromAggregate(typeRepresentation, type);
            targetDto.setTypeID(typeRepresentation);
        }

        Category category = sourceEntity.getCategoryID();
        if (category != null) {
            CategoryRepresentation categoryRepresentation = new CategoryRepresentation();
            categoryAssembler.doAssembleDtoFromAggregate(categoryRepresentation, category);
            targetDto.setCategoryID(categoryRepresentation);
        }

        List<Color> witnessActive = sourceEntity.getWitnessActive();
        List<ColorRepresentation> colorActive = new ArrayList<ColorRepresentation>();
        if (!witnessActive.isEmpty()) {
            for (Color color : witnessActive) {
                ColorRepresentation colorRepresentation = new ColorRepresentation();
                colorAssembler.doAssembleDtoFromAggregate(colorRepresentation, color);
                colorActive.add(colorRepresentation);

            }
        }
        targetDto.setWitnessActive(colorActive);

        List<Color> witnessAlert = sourceEntity.getWitnessAlert();
        List<ColorRepresentation> colorAlert = new ArrayList<ColorRepresentation>();
        if (!witnessAlert.isEmpty()) {
            for (Color color : witnessAlert) {
                ColorRepresentation colorRepresentation = new ColorRepresentation();
                colorAssembler.doAssembleDtoFromAggregate(colorRepresentation, color);
                colorAlert.add(colorRepresentation);
            }
        }
        targetDto.setWitnessAlert(colorAlert);

        List<Color> witnessFailure = sourceEntity.getWitnessFailure();
        List<ColorRepresentation> colorFailure = new ArrayList<ColorRepresentation>();
        if (!witnessFailure.isEmpty()) {
            for (Color color : witnessFailure) {
                ColorRepresentation colorRepresentation = new ColorRepresentation();
                colorAssembler.doAssembleDtoFromAggregate(colorRepresentation, color);
                colorFailure.add(colorRepresentation);
            }
        }
        targetDto.setWitnessFailure(colorFailure);

        List<Picto> listPicto = sourceEntity.getPictos();
        List<PictoRepresentation> pictoRepresentation = new ArrayList<PictoRepresentation>();
        if (!listPicto.isEmpty()) {
            for (Picto picto : listPicto) {
                PictoRepresentation pictoRep = new PictoRepresentation();
                pictoAssembler.doAssembleDtoFromAggregate(pictoRep, picto);
                pictoRepresentation.add(pictoRep);

            }
        }
        targetDto.setPictos(pictoRepresentation);

        List<SpecificDrawing> listSpeDraw = sourceEntity.getSpecificDrawings();
        List<SpecificDrawingRepresentation> speDrawRepresentation = new ArrayList<SpecificDrawingRepresentation>();
        if (listSpeDraw != null && !listSpeDraw.isEmpty()) {
            for (SpecificDrawing speDraw : listSpeDraw) {
                SpecificDrawingRepresentation speDrawRep = new SpecificDrawingRepresentation();
                specificDrawingAssembler.doAssembleDtoFromAggregate(speDrawRep, speDraw);
                speDrawRepresentation.add(speDrawRep);

            }
        }
        targetDto.setSpecificDrawings(speDrawRepresentation);

        List<RuleOfUses> listRules = sourceEntity.getRules();
        List<RuleOfUsesRepresentation> ruleRepresentation = new ArrayList<RuleOfUsesRepresentation>();
        /* SN - GL - 230 - 17-Jul-16 - Start */
        if (listRules != null && !listRules.isEmpty()) {
            /* SN - GL - 230 - 17-Jul-16 - End */
            for (RuleOfUses rules : listRules) {
                RuleOfUsesRepresentation ruleRep = new RuleOfUsesRepresentation();
                ruleOfUsesAssembler.doAssembleDtoFromAggregate(ruleRep, rules);
                ruleRepresentation.add(ruleRep);

            }
        }
        targetDto.setRules(ruleRepresentation);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.seedstack.business.assembler.BaseAssembler#doMergeAggregateWithDto(org.seedstack.business.domain.AggregateRoot, java.lang.Object)
     */
    @Override
    protected void doMergeAggregateWithDto(PictoFamily targetEntity, PictoFamilyRepresentation sourceDto) {

        targetEntity.setName(sourceDto.getName());
        targetEntity.setInformationType(sourceDto.getInformationTypeLabel());
        targetEntity.setInformationLabelEN(sourceDto.getInformationEN());
        targetEntity.setInformationLabelFR(sourceDto.getInformationFR());
        targetEntity.setAdminInfo(sourceDto.getAdminInfo());
        targetEntity.setFunctionEN(sourceDto.getFunctionEN());
        targetEntity.setFunctionFR(sourceDto.getFunctionFR());
        targetEntity.setKeywordEN(sourceDto.getKeywordEN());
        targetEntity.setKeywordFR(sourceDto.getKeywordFR());

        targetEntity.setValidationLevel(sourceDto.getValidationLevel());

        CategoryRepresentation category = sourceDto.getCategoryID();
        if (category != null) {
            Category cat = new Category();
            categoryAssembler.mergeAggregateWithDto(cat, category);
            targetEntity.setCategoryID(cat);
        }

        TypeRepresentation typeRepresentation = sourceDto.getTypeID();
        if (typeRepresentation != null) {
            Type type = new Type();
            typeAssembler.doMergeAggregateWithDto(type, typeRepresentation);
            targetEntity.setTypeID(type);
        }

        List<PictoRepresentation> listPicto = sourceDto.getPictos();
        List<Picto> pictoList = new ArrayList<Picto>();
        if (!listPicto.isEmpty()) {
            for (PictoRepresentation picRepresentation : listPicto) {
                /* SN - GL - 318 - 19-Jul-16 - Start */
                picRepresentation.setLastModifiedUsr(sourceDto.getUser());
                /* SN - GL - 318 - 19-Jul-16 - End */

                if (!PictoConstants.TYPE_ICON.equals(sourceDto.getTypeID().getTypeLabel()) && sourceDto.getRefCharte() != null) {

                    picRepresentation.setImageLocation(sourceDto.getReferenceNum() + "_" + sourceDto.getRefCharte() + sourceDto.getName());
                } else {
                    picRepresentation.setImageLocation(sourceDto.getReferenceNum() + "_" + sourceDto.getName());
                }
                Picto picto = new Picto();
                pictoAssembler.doMergeAggregateWithDto(picto, picRepresentation);
                picto.setPictoFamilyID(targetEntity);
                pictoList.add(picto);
            }
        }
        targetEntity.getPictos().clear();
        targetEntity.getPictos().addAll(pictoList);

        List<SpecificDrawingRepresentation> listSpecficDraw = sourceDto.getSpecificDrawings();
        List<SpecificDrawing> spcDrawList = new ArrayList<SpecificDrawing>();
        if (!listSpecficDraw.isEmpty()) {
            for (SpecificDrawingRepresentation spdRepresentation : listSpecficDraw) {
                SpecificDrawing spcDrawing = new SpecificDrawing();
                specificDrawingAssembler.doMergeAggregateWithDto(spcDrawing, spdRepresentation);
                spcDrawing.setFamilyId(targetEntity);
                spcDrawList.add(spcDrawing);
            }

        }
        targetEntity.getSpecificDrawings().clear();
        targetEntity.getSpecificDrawings().addAll(spcDrawList);

        List<RuleOfUsesRepresentation> listRules = sourceDto.getRules();
        List<RuleOfUses> ruleUsesList = new ArrayList<RuleOfUses>();

        if (!listRules.isEmpty()) {
            for (RuleOfUsesRepresentation ruleRepresentation : listRules) {

                RuleOfUses rule = new RuleOfUses();
                ruleOfUsesAssembler.doMergeAggregateWithDto(rule, ruleRepresentation);
                rule.setFamilyId(targetEntity);
                ruleUsesList.add(rule);
            }
        }
        targetEntity.getRules().clear();
        targetEntity.getRules().addAll(ruleUsesList);

        if (!PictoConstants.TYPE_ICON.equals(sourceDto.getTypeID().getTypeLabel())) {
            targetEntity.setRefCharte(sourceDto.getRefCharte());
            targetEntity.setIsCommand(sourceDto.getCommand());
            if (sourceDto.getCommand()) {
                targetEntity.setCommandInformation(sourceDto.getCommandInformation());
            } else {
                targetEntity.setCommandInformation(null);
            }
            targetEntity.setIsRHNWitness(sourceDto.getIsRHNWitness());
            targetEntity.setRhnInfoEN(sourceDto.getRhnInfoEN());
            targetEntity.setRhnInfoFR(sourceDto.getRhnInfoFR());

            if (sourceDto.getIsRHNWitness()) {
                targetEntity.setIsRHNActive(sourceDto.getIsRHNActive());
                targetEntity.setIsRHNAlert(sourceDto.getIsRHNAlert());
                targetEntity.setIsRHNDefault(sourceDto.getIsRHNDefault());
                if (sourceDto.getIsRHNActive()) {
                    List<ColorRepresentation> listWitnessActive = sourceDto.getWitnessActive();
                    List<Color> colorActiveList = new ArrayList<Color>();
                    if (!listWitnessActive.isEmpty()) {
                        for (ColorRepresentation colorRepresenation : listWitnessActive) {
                            Color color = new Color();
                            if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                                colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                                colorActiveList.add(color);
                            }
                        }
                    }
                    targetEntity.getWitnessActive().clear();
                    targetEntity.getWitnessActive().addAll(colorActiveList);
                } else {
                    targetEntity.getWitnessActive().clear();
                }

                if (sourceDto.getIsRHNAlert()) {
                    List<ColorRepresentation> listWitnessAlert = sourceDto.getWitnessAlert();
                    List<Color> colorAlertList = new ArrayList<Color>();
                    if (!listWitnessAlert.isEmpty()) {
                        for (ColorRepresentation colorRepresenation : listWitnessAlert) {
                            Color color = new Color();
                            if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                                colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                                colorAlertList.add(color);
                            }
                        }
                    }
                    targetEntity.getWitnessAlert().clear();
                    targetEntity.getWitnessAlert().addAll(colorAlertList);
                } else {
                    targetEntity.getWitnessAlert().clear();
                }

                if (sourceDto.getIsRHNDefault()) {
                    List<ColorRepresentation> listWitnessFailure = sourceDto.getWitnessFailure();
                    List<Color> colorFailureList = new ArrayList<Color>();
                    if (!listWitnessFailure.isEmpty()) {
                        for (ColorRepresentation colorRepresenation : listWitnessFailure) {
                            Color color = new Color();
                            if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                                colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                                colorFailureList.add(color);
                            }
                        }
                    }
                    targetEntity.getWitnessFailure().clear();
                    targetEntity.getWitnessFailure().addAll(colorFailureList);
                } else {
                    targetEntity.getWitnessFailure().clear();
                }
            } else {
                targetEntity.setIsRHNActive(Boolean.FALSE);
                targetEntity.setIsRHNAlert(Boolean.FALSE);
                targetEntity.setIsRHNDefault(Boolean.FALSE);
                targetEntity.getWitnessActive().clear();
                targetEntity.getWitnessAlert().clear();
                targetEntity.getWitnessFailure().clear();

            }

        } else {
            targetEntity.setRefCharte(null);
            targetEntity.setIsCommand(Boolean.FALSE);
            targetEntity.setCommandInformation(null);
            targetEntity.setIsRHNWitness(Boolean.FALSE);
            targetEntity.setIsRHNActive(Boolean.FALSE);
            targetEntity.setIsRHNAlert(Boolean.FALSE);
            targetEntity.setIsRHNDefault(Boolean.FALSE);
            targetEntity.getWitnessActive().clear();
            targetEntity.getWitnessAlert().clear();
            targetEntity.getWitnessFailure().clear();
            targetEntity.setRhnInfoEN(null);
            targetEntity.setRhnInfoFR(null);

        }
    }

    /**
     * Do merge aggregate with dto create.
     *
     * @param targetEntity the p target entity
     * @param sourceDto the p source dto
     */
    protected void doMergeAggregateWithDtoCreate(PictoFamily targetEntity, CreatePicFamilyRepresentation sourceDto) {
        targetEntity.setReferenceNum(sourceDto.getReferenceNum());
        targetEntity.setName(sourceDto.getName());
        targetEntity.setInformationType(sourceDto.getInformationTypeLabel());
        targetEntity.setInformationLabelEN(sourceDto.getInformationEN());
        targetEntity.setInformationLabelFR(sourceDto.getInformationFR());
        targetEntity.setAdminInfo(sourceDto.getAdminInfo());
        targetEntity.setFunctionEN(sourceDto.getFunctionEN());
        targetEntity.setFunctionFR(sourceDto.getFunctionFR());
        targetEntity.setRefCharte(sourceDto.getRefCharte());
        if (sourceDto.getCommand() == null) {
            targetEntity.setIsCommand(Boolean.FALSE);
        } else {
            targetEntity.setIsCommand(sourceDto.getCommand());
        }
        targetEntity.setCommandInformation(sourceDto.getCommandInformation());

        if (sourceDto.getIsRHNWitness() == null) {
            targetEntity.setIsRHNWitness(Boolean.FALSE);
        } else {
            targetEntity.setIsRHNWitness(sourceDto.getIsRHNWitness());
        }

        if (sourceDto.getIsRHNActive() == null) {
            targetEntity.setIsRHNActive(Boolean.FALSE);

        } else {
            targetEntity.setIsRHNActive(sourceDto.getIsRHNActive());
        }

        if (sourceDto.getIsRHNAlert() == null) {
            targetEntity.setIsRHNAlert(Boolean.FALSE);
        } else {
            targetEntity.setIsRHNAlert(sourceDto.getIsRHNAlert());
        }

        if (sourceDto.getIsRHNDefault() == null) {
            targetEntity.setIsRHNDefault(Boolean.FALSE);
        } else {
            targetEntity.setIsRHNDefault(sourceDto.getIsRHNDefault());
        }

        targetEntity.setRhnInfoEN(sourceDto.getRhnInfoEN());
        targetEntity.setRhnInfoFR(sourceDto.getRhnInfoFR());
        targetEntity.setKeywordEN(sourceDto.getKeywordEN());
        targetEntity.setKeywordFR(sourceDto.getKeywordFR());
        targetEntity.setValidationLevel(sourceDto.getValidationLevel());

        CategoryRepresentation categoryRepresentation = sourceDto.getCategoryID();
        if (categoryRepresentation != null) {
            Category category = new Category();
            categoryAssembler.doMergeAggregateWithDto(category, categoryRepresentation);
            targetEntity.setCategoryID(category);
        }

        CreatePictoRepresentation listPicto = sourceDto.getPictos();
        List<Picto> pictoList = new ArrayList<Picto>();
        /* Fix for JIRA PRPO24006-150  Start */
        if (listPicto != null) {
            Picto picto = new Picto();
            pictoAssembler.doMergeAggregateWithDtoCreate(picto, listPicto, sourceDto.getReferenceNum(), sourceDto.getName(),
                    sourceDto.getRefCharte());
            picto.setPictoFamilyID(targetEntity);
            pictoList.add(picto);
        }
        targetEntity.setPictos(pictoList);

        TypeRepresentation typeRepresentation = sourceDto.getTypeID();
        if (typeRepresentation != null) {
            Type type = new Type();
            typeAssembler.doMergeAggregateWithDto(type, typeRepresentation);
            targetEntity.setTypeID(type);
        }

        List<ColorRepresentation> listWitnessActive = sourceDto.getWitnessActive();
        List<Color> colorActiveList = new ArrayList<Color>();
        if (!listWitnessActive.isEmpty()) {
            for (ColorRepresentation colorRepresenation : listWitnessActive) {
                Color color = new Color();
                if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                    colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                    colorActiveList.add(color);
                }

            }
        }
        targetEntity.setWitnessActive(colorActiveList);

        List<ColorRepresentation> listWitnessAlert = sourceDto.getWitnessAlert();
        List<Color> colorAlertList = new ArrayList<Color>();
        if (!listWitnessAlert.isEmpty()) {
            for (ColorRepresentation colorRepresenation : listWitnessAlert) {
                Color color = new Color();
                if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                    colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                    colorAlertList.add(color);
                }

            }
        }
        targetEntity.setWitnessAlert(colorAlertList);

        List<ColorRepresentation> listWitnessFailure = sourceDto.getWitnessFailure();
        List<Color> colorFailureList = new ArrayList<Color>();
        if (!listWitnessFailure.isEmpty()) {
            for (ColorRepresentation colorRepresenation : listWitnessFailure) {
                Color color = new Color();
                if (Boolean.TRUE.equals(colorRepresenation.isFlag())) {
                    colorAssembler.doMergeAggregateWithDto(color, colorRepresenation);
                    colorFailureList.add(color);
                }

            }
        }
        targetEntity.setWitnessFailure(colorFailureList);

    }
}
