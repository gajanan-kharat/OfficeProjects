/*
 * This class was automatically generated with 
 * <a href="http://xml.inetpsa.com/ToolboxXML/">The ToolboxXML</a>.
 */
package com.inetpsa.oaz00.ws.server.invoker;

import java.lang.reflect.Method;

import com.inetpsa.cxl.core.fault.ServiceException;
import com.inetpsa.cxl.core.message.MessageContext;
import com.inetpsa.cxl.core.service.invoker.Invoker;
import com.inetpsa.xml.services.oasisplm.interfaceoasis.InterfaceOasisPortType;

/**
 * Cette classe permet de controller l'appel du service metier. Si vous devez placer du code avant ou apres l'appel d'une methode c'est dans cette
 * classe que vous devez le faire.
 * 
 * @author Geometric Ltd.
 */
public class InterfaceOasisInvoker extends Invoker {

    /**
     * Instance du service
     */
    private InterfaceOasisPortType interfaceoasis;

    /**
     * Constructeur de l'invoker
     */
    public InterfaceOasisPortType getService() {
        return interfaceoasis;
    }

    /**
     * Setter pour l'instance du service
     * 
     * @param interfaceoasis interfaceoasis
     */
    public void setService(InterfaceOasisPortType interfaceoasis) {
        this.interfaceoasis = interfaceoasis;
    }

    /**
     * Methode d'invocation du service
     * 
     * @param method methode du service a executer
     * @param params parametre de la methode a executer
     * @param messageContext context du message
     * @throws ServiceExceptionR Exception generee lors de l'appel
     * @return retourne le resultat de la methode execute
     */
    public Object invoke(Method method, Object[] params, MessageContext messageContext) throws ServiceException {
        try {
            return method.invoke(interfaceoasis, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

}
