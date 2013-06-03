/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.processor;

import com.betapps.ejb.bean.BetaQueryfactoryLocal;
import com.betapps.ejb.helper.BetaBase;
import com.betapps.ejb.helper.TransactionHelper;
import com.betapps.ejb.plugin.DefaultPaymentPluginLocal;
import com.sorcier.caisse.lue.lite.log.SimplelLogger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fahmi
 */
@Stateless
public class PaymentProcessorBean implements PaymentProcessorLocal {
    
    private SimplelLogger log = SimplelLogger.getLogger(getClass().getName());
    @PersistenceContext(unitName = "BETAAPPS")
    protected EntityManager em;
    @EJB
    protected BetaQueryfactoryLocal queryFactory;
    @Resource
    protected SessionContext ctx;

    @EJB
    protected DefaultPaymentPluginLocal payment;


    public TransactionHelper doPaymentProcessor(TransactionHelper transactionHelper){
        log.info(": : : PREPARE TO DO TRANSACTION:.");
        try {
            transactionHelper = payment.execute(transactionHelper);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        log.info(": : : END DO TRANSACTION:.");

        return transactionHelper;
    }
 
}
