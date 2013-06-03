/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.util;

import javax.ejb.Local;

/**
 *
 * @author fahmi
 */
@Local
public interface BetaUtilLocal {

    public com.betapps.ejb.helper.TransactionHelper ConnectToPaymentChannel(com.betapps.ejb.helper.TransactionHelper helper);
    
}
