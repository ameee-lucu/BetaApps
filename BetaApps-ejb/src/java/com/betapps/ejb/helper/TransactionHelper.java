/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.helper;

import com.sorcier.caisse.lue.lite.request.AlfaRequest;
import com.sorcier.caisse.lue.lite.request.PaymentRequest;
import com.sorcier.caisse.lue.lite.response.AlfaResponse;

/**
 *
 * @author fahmi
 */
public class TransactionHelper {

    private AlfaRequest alfaRequest;
    private AlfaResponse alfaResponse;
    private PaymentRequest paymentRequest;

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    


    public AlfaRequest getAlfaRequest() {
        return alfaRequest;
    }

    public void setAlfaRequest(AlfaRequest alfaRequest) {
        this.alfaRequest = alfaRequest;
    }

    public AlfaResponse getAlfaResponse() {
        return alfaResponse;
    }

    public void setAlfaResponse(AlfaResponse alfaResponse) {
        this.alfaResponse = alfaResponse;
    }


    

}
