/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betapps.ejb.plugin;

import com.betaapps.dto.AdminChannel;
import com.betaapps.dto.Merchant;
import com.betaapps.dto.TransactionLog;
import com.betaapps.dto.Transactions;
import com.betapps.ejb.bean.BetaQueryfactoryLocal;
import com.betapps.ejb.helper.TransactionHelper;
import com.betapps.ejb.util.BetaUtilLocal;
import com.sorcier.caisse.lue.lite.enums.AlfaResponseCode;
import com.sorcier.caisse.lue.lite.enums.AlfaTrxType;
import com.sorcier.caisse.lue.lite.enums.TransactionState;
import com.sorcier.caisse.lue.lite.enums.TransactionStatus;
import com.sorcier.caisse.lue.lite.log.SimplelLogger;
import com.sorcier.caisse.lue.lite.request.PaymentRequest;
import com.sorcier.caisse.lue.lite.response.AlfaResponse;
import com.sorcier.caisse.lue.lite.util.SafeSimpleDateFormat;
import com.sorcier.caisse.lue.lite.util.SimpleProperties;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author fahmi
 */
@Stateless
public class DefaultPaymentPluginBean implements DefaultPaymentPluginLocal {

    private SimplelLogger log = SimplelLogger.getLogger(getClass().getName());
    @PersistenceContext(unitName = "CHECKOUT")
    protected EntityManager em;
    @EJB
    protected BetaQueryfactoryLocal queryFactory;
    @EJB
    protected BetaUtilLocal util;

    public TransactionHelper execute(TransactionHelper helper) {
        try {
            if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.CASH_IN.value())) {
                helper = doCashIn(helper);
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.CASH_IN_REMITT.value())) {
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.CASH_OUT_REMITT_CONFIRMATION.value())) {
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.CASH_OUT_REMITT_INQUIRY.value())) {
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.ECHO.value())) {
                helper = doEcho(helper);
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.PURCHASE_REQUEST.value())) {
                helper = doPurchase(helper);
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.PURCHASE_REQUEST_CASHOUT.value())) {
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.RETRIEVE_TRANSACTION_ID.value())) {
            } else if (helper.getAlfaRequest().getTrxType().equals(AlfaTrxType.REVERSAL.value())) {
                helper = doReversal(helper);
            } else {
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return helper;
    }

    protected TransactionHelper doCashIn(TransactionHelper helper) {
        helper.setAlfaResponse(new AlfaResponse());
        try {

            //nanti jangan lupa ditest yah


            log.info(": : : GETTING CASHIN REQUEST FROM ALFA, CHECKING USERNAME AND PASSWORD FIRST:.");

            Criterion crit1 = Restrictions.eq("login", helper.getAlfaRequest().getLogin());
            Criterion crit2 = Restrictions.eq("pwd", helper.getAlfaRequest().getPwd());

            AdminChannel channel = queryFactory.getAdminChannel(crit1, crit2);


            if (channel == null) {
                log.info(": : : LOGIN / PASSWORD WRONG, GIVE REPSONSE:.");
                helper.getAlfaResponse().setResponseCode(AlfaResponseCode.MERCHANT_TERMINAL_PASSWORD_TIDAK_VALID);
                helper.getAlfaResponse().setTransactionDate(new Date());
                helper.getAlfaResponse().setTransactionId(SimpleProperties.getSimpleConfig().getString("BASIC.ECHO.BETAAPPS"));
                return helper;
            }

            log.info(": : : LOGIN VALID, NOW PROCEED TRANSACTION:.");

            Transactions trans = new Transactions();
            trans.setAdminChannelId(channel);
            trans.setAmount(BigDecimal.valueOf(Double.valueOf(helper.getAlfaRequest().getTrxAmount())));
            trans.setCoAmount(BigDecimal.valueOf(Double.valueOf(helper.getAlfaRequest().getCoAmount())));
            trans.setCustomer(helper.getAlfaRequest().getCustomer());
            trans.setDatetime(new SafeSimpleDateFormat("yyyyMMddhhmmss").parse(helper.getAlfaRequest().getTrxDate()));

            Criterion c1 = Restrictions.eq("id", 1);
            Merchant merchant = queryFactory.getMerchant(c1);

            trans.setMerchantId(merchant);
            trans.setPromo(helper.getAlfaRequest().getPromo());
            trans.setSequenceId(helper.getAlfaRequest().getSequenceId());
            trans.setState(TransactionState.INITIATE.value().toString());
            trans.setStatus(TransactionStatus.PENDING.value().toString());
            trans.setTerminal(helper.getAlfaRequest().getTerminal());
            trans.setTransactionId(helper.getAlfaRequest().getTransactionId());
            trans.setType(helper.getAlfaRequest().getTrxType());

            Set<TransactionLog> listLog = new HashSet<TransactionLog>();


            TransactionLog transLog = new TransactionLog();
            transLog.setDatetime(new Date());
            transLog.setStatus(TransactionStatus.PENDING.value().toString());
            transLog.setTransactionId(trans);

            listLog.add(transLog);
            trans.setTransactionLogCollection(listLog);
            em.persist(trans);

            helper.setPaymentRequest(new PaymentRequest());
            helper.getPaymentRequest().setAmount(amount);
            helper.getPaymentRequest().setCart(cart);
            helper.getPaymentRequest().setCurrency(currency);
            helper.getPaymentRequest().setInvoice(invoice);
            helper.getPaymentRequest().setIp(ip);
            helper.getPaymentRequest().setMasterMerchantId(masterMerchantId);
            helper.getPaymentRequest().setMerchantId(merchantId);
            helper.getPaymentRequest().setPaymentType(paymentType);
            helper.getPaymentRequest().setResult(result);
            helper.getPaymentRequest().setSessionId(sessionId);
            helper.getPaymentRequest().setToken(token);

            helper = util.ConnectToPaymentChannel(helper);


            if (!(helper.getPaymentRequest().getResult().equalsIgnoreCase("Success"))) {
                log.info(": : : TRANSACTION FAILED:.");

                trans.setState(TransactionState.DONE.value().toString());
                trans.setStatus(TransactionStatus.FAIL.value().toString());

                transLog = new TransactionLog();
                transLog.setDatetime(new Date());
                transLog.setStatus(TransactionStatus.FAIL.value().toString());
                transLog.setTransactionId(trans);

                listLog.add(transLog);
                trans.setTransactionLogCollection(listLog);

                em.merge(trans);

                helper.getAlfaResponse().setResponseCode(AlfaResponseCode.KEY_TIDAK_VALID);
                helper.getAlfaResponse().setTransactionDate(new Date());
                helper.getAlfaResponse().setTransactionId(trans.getId().toString());
                return helper;
            }

            log.info(": : : TRANSACTION SUCCESS, UPDATE TRANSACTION TO SUCCESS:.");

            trans.setState(TransactionState.DONE.value().toString());
            trans.setStatus(TransactionStatus.SUCCESS.value().toString());

            transLog = new TransactionLog();
            transLog.setDatetime(new Date());
            transLog.setStatus(TransactionStatus.SUCCESS.value().toString());
            transLog.setTransactionId(trans);

            listLog.add(transLog);
            trans.setTransactionLogCollection(listLog);

            em.merge(trans);

            log.info(": : : SUCCESS TRANSACTION:.");


            helper.getAlfaResponse().setResponseCode(AlfaResponseCode.SUKSES);
            helper.getAlfaResponse().setTransactionDate(trans.getDatetime());
            helper.getAlfaResponse().setTransactionId(trans.getId().toString());

        } catch (Throwable e) {
            helper.getAlfaResponse().setResponseCode(AlfaResponseCode.SYSTEM_ERROR);
            helper.getAlfaResponse().setTransactionDate(new Date());
            helper.getAlfaResponse().setTransactionId(SimpleProperties.getSimpleConfig().getString("BASIC.ECHO.BETAAPPS"));
            e.printStackTrace();
        }

        return helper;
    }

    protected TransactionHelper doEcho(TransactionHelper helper) {
        try {
            log.info(": : : PREPARING TO DO ECHO:.");
            helper.setAlfaResponse(new AlfaResponse());
            helper.getAlfaResponse().setResponseCode(AlfaResponseCode.SUKSES);
            helper.getAlfaResponse().setTransactionDate(new Date());
            helper.getAlfaResponse().setTransactionId(SimpleProperties.getSimpleConfig().getString("BASIC.ECHO.BETAAPPS"));
            log.info(": : : DONE SETTING ECHO:.");

        } catch (Throwable e) {
            helper.setAlfaResponse(new AlfaResponse());
            helper.getAlfaResponse().setResponseCode(AlfaResponseCode.SYSTEM_ERROR);
            helper.getAlfaResponse().setTransactionDate(new Date());
            helper.getAlfaResponse().setTransactionId(SimpleProperties.getSimpleConfig().getString("BASIC.ECHO.BETAAPPS"));
            e.printStackTrace();
        }

        return helper;
    }

    protected TransactionHelper doPurchase(TransactionHelper helper) {
        try {
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return helper;
    }

    protected TransactionHelper doReversal(TransactionHelper helper) {
        try {
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return helper;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method" or "Web Service > Add Operation")
}
