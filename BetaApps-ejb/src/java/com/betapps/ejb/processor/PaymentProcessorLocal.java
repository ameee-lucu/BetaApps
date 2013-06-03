/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.processor;

import javax.ejb.Local;

/**
 *
 * @author fahmi
 */
@Local
public interface PaymentProcessorLocal {

    public com.betapps.ejb.helper.TransactionHelper doPaymentProcessor(com.betapps.ejb.helper.TransactionHelper transactionHelper);
    
}
