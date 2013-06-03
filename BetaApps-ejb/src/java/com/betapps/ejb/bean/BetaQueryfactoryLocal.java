/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.bean;

import javax.ejb.Local;

/**
 *
 * @author fahmi
 */
@Local
public interface BetaQueryfactoryLocal {

    public com.betaapps.dto.AdminChannel getAdminChannel(org.hibernate.criterion.Criterion... criterion);

    public com.betaapps.dto.Merchant getMerchant(org.hibernate.criterion.Criterion... criterion);
    
}
