/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betapps.ejb.bean;

import com.betaapps.dto.AdminChannel;
import com.betaapps.dto.Merchant;
import com.sorcier.caisse.lue.lite.log.SimplelLogger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author fahmi
 */
@Stateless
public class BetaQueryfactoryBean implements BetaQueryfactoryLocal {
    
    private SimplelLogger log = SimplelLogger.getLogger(getClass().getName());
    @PersistenceContext(unitName = "BETAAPPS")
    protected EntityManager em;

    protected Session getSession(){
        return (Session) em.getDelegate();
    }


    public Merchant getMerchant(Criterion... criterion){
        try {

            Criteria criteria=getSession().createCriteria(Merchant.class);

            for(Criterion c:criterion){
                criteria.add(c);
            }

            return (Merchant) criteria.uniqueResult();

        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }


    }


    public AdminChannel getAdminChannel(Criterion... criterion){
        try {
            Criteria criteria=getSession().createCriteria(AdminChannel.class);
            criteria.createAlias("userGroupId", "userGroup");

            for(Criterion c:criterion){
                criteria.add(c);
            }

            return (AdminChannel) criteria.uniqueResult();


        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }


    }
 
}
