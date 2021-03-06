
package cn.id5.gboss.businesses.validator.service.app;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "QueryValidatorServicesService", targetNamespace = "http://app.service.validator.businesses.gboss.id5.cn", wsdlLocation = "http://gboss.id5.cn/services/QueryValidatorServices?wsdl")
public class QueryValidatorServicesService
    extends Service
{

    private final static URL QUERYVALIDATORSERVICESSERVICE_WSDL_LOCATION;
    private final static WebServiceException QUERYVALIDATORSERVICESSERVICE_EXCEPTION;
    private final static QName QUERYVALIDATORSERVICESSERVICE_QNAME = new QName("http://app.service.validator.businesses.gboss.id5.cn", "QueryValidatorServicesService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://gboss.id5.cn/services/QueryValidatorServices?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        QUERYVALIDATORSERVICESSERVICE_WSDL_LOCATION = url;
        QUERYVALIDATORSERVICESSERVICE_EXCEPTION = e;
    }

    public QueryValidatorServicesService() {
        super(__getWsdlLocation(), QUERYVALIDATORSERVICESSERVICE_QNAME);
    }

    public QueryValidatorServicesService(WebServiceFeature... features) {
        super(__getWsdlLocation(), QUERYVALIDATORSERVICESSERVICE_QNAME, features);
    }

    public QueryValidatorServicesService(URL wsdlLocation) {
        super(wsdlLocation, QUERYVALIDATORSERVICESSERVICE_QNAME);
    }

    public QueryValidatorServicesService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, QUERYVALIDATORSERVICESSERVICE_QNAME, features);
    }

    public QueryValidatorServicesService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public QueryValidatorServicesService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns QueryValidatorServices
     */
    @WebEndpoint(name = "QueryValidatorServices")
    public QueryValidatorServices getQueryValidatorServices() {
        return super.getPort(new QName("http://app.service.validator.businesses.gboss.id5.cn", "QueryValidatorServices"), QueryValidatorServices.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns QueryValidatorServices
     */
    @WebEndpoint(name = "QueryValidatorServices")
    public QueryValidatorServices getQueryValidatorServices(WebServiceFeature... features) {
        return super.getPort(new QName("http://app.service.validator.businesses.gboss.id5.cn", "QueryValidatorServices"), QueryValidatorServices.class, features);
    }

    private static URL __getWsdlLocation() {
        if (QUERYVALIDATORSERVICESSERVICE_EXCEPTION!= null) {
            throw QUERYVALIDATORSERVICESSERVICE_EXCEPTION;
        }
        return QUERYVALIDATORSERVICESSERVICE_WSDL_LOCATION;
    }

}
