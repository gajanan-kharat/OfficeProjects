package com.inetpsa.eds.ws.resources;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hibernate.Hibernate;

import com.inetpsa.atimonitoring.ATIMonitoring;
import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.content.eds.EdsFormFactory;
import com.inetpsa.eds.application.content.eds.I_FormBuilder;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.I_FormData;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsEdsAdmin;
import com.inetpsa.eds.dao.model.EdsHighValidationFormData;
import com.inetpsa.eds.dao.model.EdsLowValidationFormData;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.dao.model.EdsUser;
import com.inetpsa.eds.dao.model.EdsValidation;
import com.inetpsa.eds.ws.model.EdsAdminResponse;
import com.inetpsa.eds.ws.model.EdsResponse;
import com.inetpsa.eds.ws.model.EdsWsSessionToken;

/**
 * This class get EDS data from EDS reference. It first checks if supplier or partner has access to EDS based on role, then returns the list of EDS
 * form data depending on the model sheet and user rights.
 * 
 * @author Geometric Ltd.
 */
@Path("/eds")
public class EdsResource {
    /**
     * This method returns the EDS data.
     * 
     * @param tokenId Token Id
     * @param edsRef reference to EDS
     * @param request Http servlet request
     * @return EDS response
     */
    @GET
    @Produces(MediaType.TEXT_XML)
    public EdsResponse getEdsData(@QueryParam("token-id") String tokenId, @QueryParam("eds-ref") String edsRef, @Context HttpServletRequest request) {
        EdsResponse response = null;
        if (tokenId != null && edsRef != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Control token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().before(new Date()))
                ;
            {
                Date startDate = new Date();
                response = getEdsResponseFrom(EDSdao.getInstance().getEdsByRef(edsRef), token);
                ATIMonitoring.log(request.getHeader("hostname"), token.getWstLogin(), request.getHeader("appid"), "", "", "", startDate, new Date(),
                        "EDS_WS_" + token.getWstSource());
            }

        }
        return response;
    }

    /**
     * This method returns list of forms depending on model sheet and user rights using EdsResponse
     * 
     * @param eds Eds details
     * @param token Web service session token
     * @return EdsResponse
     */
    public static EdsResponse getEdsResponseFrom(EdsEds eds, EdsWsSessionToken token) {
        EdsResponse response = new EdsResponse();

        if (eds != null) {
            // Control of user rights
            if (canAccessEds(token.getEdsUser(), eds)) {
                response.setEds(eds);
                // Traitement pour ne pas wrapper les collections vides
                // if ( response.getEds().getEdsNumber96ks().isEmpty() )
                // {
                // response.getEds().setEdsNumber96ks( null );
                // }
                // if ( response.getEds().getEdsPerimeters().isEmpty() )
                // {
                // response.getEds().setEdsPerimeters( null );
                // }
                // if ( response.getEds().getEdsProjectEdses().isEmpty() )
                // {
                // response.getEds().setEdsProjectEdses( null );
                // }

                // It retrieves the list of forms depending on the model sheet and user rights
                Set<String> formSetIds = eds.isBttbt() ? eds.getEdsComponentType().getAllBttbtbForms() : eds.getEdsComponentType()
                        .getAllNonBttbtbForms();
                ArrayList<Class<?>> formClassesToExport = new ArrayList<Class<?>>();

                for (String formId : formSetIds) {
                    I_FormBuilder builder = EdsFormFactory.getBuilder(formId);

                    if (EdsRight.hasSufficientRights(token.getEdsUser(), builder.getReadingRightId())) {
                        if (builder.getFormDataClasses() != null) {
                            formClassesToExport.addAll(builder.getFormDataClasses());
                        }
                    }
                }

                // Recovering forms to export.

                List<I_FormData> forms = new ArrayList<I_FormData>();
                for (Class<?> clazz : formClassesToExport) {
                    I_FormData form = (I_FormData) EDSdao.getInstance().getFormData(response.getEds().getEdsId(), clazz);
                    if (form != null) {
                        forms.add(form);
                    }
                }
                if (!forms.isEmpty()) {
                    response.setFormDatas(forms);
                }
            }
        }
        return response;
    }

    // PROTECTED
    // PRIVATE

    /**
     * This method check if given user can access EDS based on role.
     * 
     * @param user Eds User details
     * @param eds Eds details
     * @return check if user can access EDS
     */
    // Restricted access for suppliers and partners to the form.

    private static boolean canAccessEds(EdsUser user, EdsEds eds) {
        boolean canAccess = true;
        if (user.getEdsRole().isSupplier()) {
            if (!user.getEdsSupplier().equals(eds.getEdsSupplier())) {
                canAccess = false;
            }
        } else if (user.getEdsRole().isPerimeter()) {
            Hibernate.initialize(eds.getEdsPerimeters());
            boolean isInPerimeter = EDSdao.getInstance().isEdsIn(eds, user.getEdsPerimeter());
            if (!isInPerimeter) {
                canAccess = false;
            }
        }
        return canAccess;
    }

    /**
     * This method returns EDS data for admin user
     * 
     * @param tokenId Token ID
     * @param edsRef EDS reference
     * @param request Http servlet Request
     * @return EdsAdminResponse
     */
    @Path("/admin")
    @GET
    @Produces(MediaType.TEXT_XML)
    public EdsAdminResponse getEdsAdminData(@QueryParam("token-id") String tokenId, @QueryParam("eds-ref") String edsRef,
            @Context HttpServletRequest request) {
        EdsAdminResponse response = null;
        if (tokenId != null && edsRef != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Control token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().before(new Date()))
                ;
            {
                EdsEds edsAdminByRef = EDSdao.getInstance().getEdsByRef(edsRef);
                String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                EdsEdsAdmin edsAdmin = convertEdstoEdsAdmin(edsAdminByRef, baseURL);
                response = getEdsAdminResponseFrom(edsAdmin, token);
            }

        }
        return response;
    }

    /**
     * This method returns list of forms depending on model sheet using EdsAdminResponse for admin
     * 
     * @param eds EDS Admin
     * @param token Token Id
     * @return EdsAdminResponse
     */
    public static EdsAdminResponse getEdsAdminResponseFrom(EdsEdsAdmin eds, EdsWsSessionToken token) {
        EdsAdminResponse response = new EdsAdminResponse();

        if (eds != null) {
            // Control of user rights
            if (canAccessEds(token.getEdsUser(), eds)) {
                response.setEds(eds);
                // Traitement pour ne pas wrapper les collections vides
                // if ( response.getEds().getEdsNumber96ks().isEmpty() )
                // {
                // response.getEds().setEdsNumber96ks( null );
                // }
                // if ( response.getEds().getEdsPerimeters().isEmpty() )
                // {
                // response.getEds().setEdsPerimeters( null );
                // }
                // if ( response.getEds().getEdsProjectEdses().isEmpty() )
                // {
                // response.getEds().setEdsProjectEdses( null );
                // }

                // It retrieves the list of forms depending on the model sheet and user rights
                Set<String> formSetIds = eds.isBttbt() ? eds.getEdsComponentType().getAllBttbtbForms() : eds.getEdsComponentType()
                        .getAllNonBttbtbForms();
                ArrayList<Class<?>> formClassesToExport = new ArrayList<Class<?>>();

                for (String formId : formSetIds) {
                    I_FormBuilder builder = EdsFormFactory.getBuilder(formId);

                    if (EdsRight.hasSufficientRights(token.getEdsUser(), builder.getReadingRightId())) {
                        if (builder.getFormDataClasses() != null) {
                            formClassesToExport.addAll(builder.getFormDataClasses());
                        }
                    }
                }

                // Recovering forms to export.

                List<I_FormData> forms = new ArrayList<I_FormData>();
                for (Class<?> clazz : formClassesToExport) {
                    I_FormData form = (I_FormData) EDSdao.getInstance().getFormData(response.getEds().getEdsId(), clazz);
                    if (form != null) {
                        forms.add(form);
                    }
                }
                if (!forms.isEmpty()) {
                    response.setFormDatas(forms);
                }
            }
        }
        return response;
    }

    /**
     * Copy all attributes from EdsEds model to EdsEdsAdmin model
     * 
     * @param eds EdsAdmin
     * @return EdsEdsAdmin
     */
    private EdsEdsAdmin convertEdstoEdsAdmin(EdsEds eds, String baseURL) {
        EdsEdsAdmin edsAdmin = new EdsEdsAdmin();
        Method[] methods = EdsEds.class.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("get")) {
                String split = name.substring(3);
                Object object;
                try {
                    object = method.invoke(eds);
                    EdsEdsAdmin.class.getMethod("set" + split, method.getReturnType()).invoke(edsAdmin, object);
                } catch (Exception e) {
                    // Left empty deliberately.
                }
            }
        }
        edsAdmin.setUrl(baseURL + "#eds&ref=" + eds.getEdsRef() + "&v=" + eds.getVersionShort());
        return edsAdmin;
    }

    /**
     * This method returns Eds data at robust stage for admin user when multiple stages are validated.
     * 
     * @param tokenId Token Id
     * @param edsRef Eds reference
     * @param request Http servlet Request
     * @return EdsAdminRespose
     */
    @Path("/robustStageValidation")
    @GET
    @Produces(MediaType.TEXT_XML)
    public EdsAdminResponse getEdsRobustStageValidationData(@QueryParam("token-id") String tokenId, @QueryParam("eds-ref") String edsRef,
            @Context HttpServletRequest request) {
        EdsAdminResponse response = null;
        if (tokenId != null && edsRef != null) {

            EdsWsSessionToken token = EDSdao.getInstance().getEdsTokenByID(tokenId);
            // Control token
            if (token != null && request.getRemoteAddr().equals(token.getWstRemoteAddress()) && token.getWstExpirationDate().before(new Date()))
                ;
            {
                EdsEds edsAdminByRef = EDSdao.getInstance().getEdsByRef(edsRef);
                String baseURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                EdsEdsAdmin edsAdmin = convertEdstoEdsAdmin(edsAdminByRef, baseURL);
                response = getEdsRobustStageValidationResponseFrom(edsAdmin, token);
            }

        }
        return response;
    }

    /**
     * This method returns list of forms depending on model sheet at Robust Stage for Eds Admin
     * 
     * @param eds EdsEdsAdmin
     * @param token Token Id
     * @return EdsAdminResponse
     */
    public static EdsAdminResponse getEdsRobustStageValidationResponseFrom(EdsEdsAdmin eds, EdsWsSessionToken token) {
        EdsAdminResponse response = new EdsAdminResponse();

        if (eds != null) {
            // Control of user rights
            if (canAccessEds(token.getEdsUser(), eds)) {
                response.setEds(eds);
                // Traitement pour ne pas wrapper les collections vides
                // if ( response.getEds().getEdsNumber96ks().isEmpty() )
                // {
                // response.getEds().setEdsNumber96ks( null );
                // }
                // if ( response.getEds().getEdsPerimeters().isEmpty() )
                // {
                // response.getEds().setEdsPerimeters( null );
                // }
                // if ( response.getEds().getEdsProjectEdses().isEmpty() )
                // {
                // response.getEds().setEdsProjectEdses( null );
                // }

                // It retrieves the list of forms depending on the model sheet and user rights
                Set<String> formSetIds = eds.isBttbt() ? eds.getEdsComponentType().getAllBttbtbForms() : eds.getEdsComponentType()
                        .getAllNonBttbtbForms();
                ArrayList<Class<?>> formClassesToExport = new ArrayList<Class<?>>();

                for (String formId : formSetIds) {
                    I_FormBuilder builder = EdsFormFactory.getBuilder(formId);

                    if (EdsRight.hasSufficientRights(token.getEdsUser(), builder.getReadingRightId())) {
                        if (builder.getFormDataClasses() != null) {
                            formClassesToExport.addAll(builder.getFormDataClasses());
                        }
                    }
                }

                // Recovering forms to export.

                List<I_FormData> forms = new ArrayList<I_FormData>();
                for (Class<?> clazz : formClassesToExport) {
                    I_FormData form = (I_FormData) EDSdao.getInstance().getFormData(response.getEds().getEdsId(), clazz);
                    if (form != null) {
                        if (form instanceof EdsLowValidationFormData) {

                            ((EdsLowValidationFormData) form).setEdsValidationByLvfdClosedId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsLowValidationFormData) form).setEdsValidationByLvfdConsolidatedId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsLowValidationFormData) form).setEdsValidationByLvfdSupplierDataId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsLowValidationFormData) form).setLvfdStage(EdsApplicationController.SOLID_STAGE);
                        }
                        if (form instanceof EdsHighValidationFormData) {

                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdDbeesClosedId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdCadeClosedId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdDbeedClosedId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));

                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdDbeesConsolidateId(new EdsValidation(UUID.randomUUID()
                                    .toString(), EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdCadeConsolidateId(new EdsValidation(
                                    UUID.randomUUID().toString(), EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdDbeedConsolidateId(new EdsValidation(UUID.randomUUID()
                                    .toString(), EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setEdsValidationByHvfdSupplierDataId(new EdsValidation(UUID.randomUUID().toString(),
                                    EdsValidation.VALIDATION_NONE));
                            ((EdsHighValidationFormData) form).setHvfdStage(EdsApplicationController.SOLID_STAGE);
                        }
                        forms.add(form);

                    }
                }
                if (!forms.isEmpty()) {
                    response.setFormDatas(forms);
                }
            }
        }
        return response;
    }

}
