/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.util;

import com.betapps.ejb.bean.BetaQueryfactoryLocal;
import com.betapps.ejb.helper.TransactionHelper;
import com.sorcier.caisse.lue.lite.log.SimplelLogger;
import com.sorcier.caisse.lue.lite.util.Connection;
import com.sorcier.caisse.lue.lite.util.SimpleProperties;
import java.net.URLEncoder;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fahmi
 */
@Stateless
public class BetaUtilBean implements BetaUtilLocal {
    
    private SimplelLogger log = SimplelLogger.getLogger(getClass().getName());
    @PersistenceContext(unitName = "BETAAPPS")
    protected EntityManager em;
    @EJB
    protected BetaQueryfactoryLocal queryFactory;

    public TransactionHelper ConnectToPaymentChannel(TransactionHelper helper){
        try {

            String url=SimpleProperties.getSimpleConfig().getString("SIMPLE.ALFA.URL");

            String param =
                    URLEncoder.encode("INVOICE", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getInvoice() + "", "UTF-8") + "&" +
                    URLEncoder.encode("CART", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getCart(), "UTF-8") + "&" +
                    URLEncoder.encode("MASTERMERCHANTID", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getMasterMerchantId(), "UTF-8") + "&" +
                    URLEncoder.encode("MERCHANTID", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getMerchantId(), "UTF-8") + "&" +
                    URLEncoder.encode("AMOUNT", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getAmount(), "UTF-8") + "&" +
                    URLEncoder.encode("CURRENCY", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getCurrency(), "UTF-8") + "&" +
                    URLEncoder.encode("SESSIONID", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getSessionId(), "UTF-8") + "&" +
                    URLEncoder.encode("TOKEN", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getToken(), "UTF-8") + "&" +
                    URLEncoder.encode("PAYMENTTYPE", "UTF-8") + "=" + URLEncoder.encode(helper.getPaymentRequest().getPaymentType(), "UTF-8");

            Connection conn = new Connection();

            String result = conn.connect(url, param);

            if (result != null && !(result.trim().equalsIgnoreCase(""))) {
                if (result.trim().equalsIgnoreCase("Success")) {
                    helper.getPaymentRequest().setResult(result);
                } else {
                    helper.getPaymentRequest().setResult(result);
                }
            }

        } catch (Throwable th) {
            th.printStackTrace();
            helper.getPaymentRequest().setResult("Failure");
        }
        return helper;
    }

    
 
}
