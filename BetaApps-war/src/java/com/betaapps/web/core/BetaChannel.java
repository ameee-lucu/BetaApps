/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betaapps.web.core;

import com.betapps.ejb.helper.TransactionHelper;
import com.betapps.ejb.processor.PaymentProcessorLocal;
import com.sorcier.caisse.lue.lite.enums.AlfaTrxType;
import com.sorcier.caisse.lue.lite.log.SimplelLogger;
import com.sorcier.caisse.lue.lite.request.AlfaRequest;
import com.sorcier.caisse.lue.lite.struts.util.GeneralConstant;
import com.sorcier.caisse.lue.lite.util.ServiceLocator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;

/**
 *
 * @author fahmi
 */
public class BetaChannel extends HttpServlet {
   
    protected SimplelLogger log = SimplelLogger.getLogger(getClass().getName());
    private TransactionHelper helper;
    private String paramLogin = "login";
    private String paramPwd = "pwd";
    private String paramTerminal = "terminal";
    private String paramCustomer = "customer";
    private String paramTrxDate = "trx_date";
    private String paramTrxType = "trx_type";
    private String paramSequenceId = "sequence_id";
    private String paramTxAmount = "tx_amount";
    private String paramCoAmount = "co_amount";
    private String paramPromo = "promo";
    private String paramTransactionId = "transaction_id";
    private String login;
    private String pwd;
    private String terminal;
    private String customer;
    private String trxDate;
    private String trxType;
    private String sequenceId;
    private String trxAmount;
    private String coAmount;
    private String promo;
    private String transactionId;

    protected PaymentProcessorLocal payment=ServiceLocator.lookupAlfaLocal(PaymentProcessorLocal.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ESAPI.httpUtilities().setCurrentHTTP(request, response);
        HttpServletRequest req = ESAPI.httpUtilities().getCurrentRequest();
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        helper = new TransactionHelper();
        try {
            login = req.getParameter(paramLogin);
            pwd = req.getParameter(paramPwd);
            terminal = req.getParameter(paramTerminal);
            customer = req.getParameter(paramCustomer);
            trxDate = req.getParameter(paramTrxDate);
            trxType = req.getParameter(paramTrxType);
            sequenceId = req.getParameter(paramSequenceId);
            trxAmount = req.getParameter(paramTxAmount);
            coAmount = req.getParameter(paramCoAmount);
            promo = req.getParameter(paramPromo);
            transactionId = req.getParameter(paramTransactionId);


            log.info(GeneralConstant.LINE_HELPER);
            log.info(": : : GETTING REQUEST FROM ALFAMART:.");
            log.info(GeneralConstant.LINE_HELPER);
            log.info("login                    [" + login + "]");
            log.info("pwd                      [" + pwd + "]");
            log.info("terminal                 [" + terminal + "]");
            log.info("customer                 [" + customer + "]");
            log.info("trx_date                 [" + trxDate + "]");
            log.info("trx_type                 [" + trxType + "]");
            log.info("sequence_id              [" + sequenceId + "]");
            log.info("tx_amount                [" + trxAmount + "]");
            log.info("co_amount                [" + coAmount + "]");
            log.info("promo                    [" + promo + "]");
            log.info("transaction_id           [" + transactionId + "]");
            log.info("remote address           [" + request.getRemoteAddr() + "]");
            log.info(GeneralConstant.LINE_HELPER);

            if (trxType == null || trxType.trim().equalsIgnoreCase("")) {
                log.info(GeneralConstant.LINE_HELPER);
                log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                log.info(": : : PARAM [" + paramTrxType + "] is Null:.");
                log.info(GeneralConstant.LINE_HELPER);

            } else {
                log.info(": : : TRANSACTION TYPE EXIST:.");
                AlfaTrxType transactionType = AlfaTrxType.getLookup().get(trxType);

                if (transactionType.equals(AlfaTrxType.ECHO)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR TRX TYPE ECHO COMPLETE COPY ALL PARAMS:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper=payment.doPaymentProcessor(helper);

                    }
                } else if (transactionType.equals(AlfaTrxType.CASH_IN)||transactionType.equals(AlfaTrxType.CASH_IN_REMITT)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (terminal == null || terminal.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTerminal + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (customer == null || customer.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramCustomer + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (sequenceId == null || sequenceId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramSequenceId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxAmount == null || trxAmount.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTxAmount + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (promo == null || promo.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPromo + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR CASHIN COMPLETE, NOW COPY ALL PARAM:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTerminal(terminal);
                        helper.getAlfaRequest().setCustomer(customer);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper.getAlfaRequest().setSequenceId(sequenceId);
                        helper.getAlfaRequest().setTrxAmount(trxAmount);
                        helper.getAlfaRequest().setPromo(promo);
                        helper=payment.doPaymentProcessor(helper);


                    }
                } else if (transactionType.equals(AlfaTrxType.PURCHASE_REQUEST)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (terminal == null || terminal.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTerminal + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (customer == null || customer.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramCustomer + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (sequenceId == null || sequenceId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramSequenceId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxAmount == null || trxAmount.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTxAmount + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (promo == null || promo.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPromo + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR PURCHASE COMPLETE, NOW COPY ALL PARAM:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTerminal(terminal);
                        helper.getAlfaRequest().setCustomer(customer);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper.getAlfaRequest().setSequenceId(sequenceId);
                        helper.getAlfaRequest().setTrxAmount(trxAmount);
                        helper.getAlfaRequest().setPromo(promo);
                        helper=payment.doPaymentProcessor(helper);


                    }
                } else if (transactionType.equals(AlfaTrxType.PURCHASE_REQUEST_CASHOUT)||transactionType.equals(AlfaTrxType.CASH_OUT_REMITT_CONFIRMATION)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (terminal == null || terminal.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTerminal + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (customer == null || customer.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramCustomer + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (sequenceId == null || sequenceId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramSequenceId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxAmount == null || trxAmount.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTxAmount + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (promo == null || promo.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPromo + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (coAmount == null || coAmount.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramCoAmount + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR PURCHASE CASHOUT COMPLETE, NOW COPY ALL PARAM:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTerminal(terminal);
                        helper.getAlfaRequest().setCustomer(customer);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper.getAlfaRequest().setSequenceId(sequenceId);
                        helper.getAlfaRequest().setTrxAmount(trxAmount);
                        helper.getAlfaRequest().setPromo(promo);
                        helper.getAlfaRequest().setCoAmount(coAmount);
                        helper=payment.doPaymentProcessor(helper);

                    }
                } else if (transactionType.equals(AlfaTrxType.RETRIEVE_TRANSACTION_ID)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (terminal == null || terminal.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTerminal + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (customer == null || customer.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramCustomer + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (sequenceId == null || sequenceId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramSequenceId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxAmount == null || trxAmount.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTxAmount + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (promo == null || promo.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPromo + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (transactionId == null || transactionId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTransactionId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR RETRIEVE CASHOUT COMPLETE, NOW COPY ALL PARAM:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTerminal(terminal);
                        helper.getAlfaRequest().setCustomer(customer);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper.getAlfaRequest().setSequenceId(sequenceId);
                        helper.getAlfaRequest().setTrxAmount(trxAmount);
                        helper.getAlfaRequest().setPromo(promo);
                        helper.getAlfaRequest().setCoAmount(coAmount);
                        helper.getAlfaRequest().setTransactionId(transactionId);
                        helper=payment.doPaymentProcessor(helper);

                    }
                } else if (transactionType.equals(AlfaTrxType.CASH_OUT_REMITT_INQUIRY)) {
                    if (login == null || login.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramLogin + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (pwd == null || pwd.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramPwd + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (terminal == null || terminal.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTerminal + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (trxDate == null || trxDate.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramTrxDate + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else if (sequenceId == null || sequenceId.trim().equalsIgnoreCase("")) {
                        log.info(GeneralConstant.LINE_HELPER);
                        log.info(": : : GOT ERROR WHEN CHECKING PARAM:.");
                        log.info(": : : PARAM [" + paramSequenceId + "] is Null:.");
                        log.info(GeneralConstant.LINE_HELPER);
                    } else {
                        log.info(": : : PARAM FOR INQUIRY COMPLETE, NOW COPY ALL PARAM:.");
                        helper.setAlfaRequest(new AlfaRequest());
                        helper.getAlfaRequest().setLogin(login);
                        helper.getAlfaRequest().setPwd(pwd);
                        helper.getAlfaRequest().setTerminal(terminal);
                        helper.getAlfaRequest().setCustomer(customer);
                        helper.getAlfaRequest().setTrxDate(trxDate);
                        helper.getAlfaRequest().setTrxType(trxType);
                        helper.getAlfaRequest().setSequenceId(sequenceId);
                        helper.getAlfaRequest().setTrxAmount(trxAmount);
                        helper.getAlfaRequest().setPromo(promo);
                        helper.getAlfaRequest().setCoAmount(coAmount);
                        helper.getAlfaRequest().setTransactionId(transactionId);
                        helper=payment.doPaymentProcessor(helper);
                    }
                }
            }

        } catch (Throwable e) {
            log.info(": : : ERROR WHEN PROCESSING TRANSACTION:.");
            e.printStackTrace();
        } finally {
            log.info(": : : RESPONSE FROM TRANSACTION:.");
            log.info(": : : RESPONSE            ["+helper.getAlfaResponse().toString()+"]");
            out.println(helper.getAlfaResponse().toString());
            out.close();
            session.invalidate();
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
