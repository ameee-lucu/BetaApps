/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betaapps.dto;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author fahmi
 */
@Entity
@Table(name = "admin_channel")
@SequenceGenerator(name = "seq_admin_channel_id_name", sequenceName = "seq_admin_channel_id", allocationSize = 1)
public class AdminChannel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_admin_channel_id_name")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "pwd")
    private String pwd;
    @Column(name = "address")
    private String address;
    @Column(name = "additional_info1")
    private String additionalInfo1;
    @Column(name = "additional_info2")
    private String additionalInfo2;
    @Column(name = "additional_info3")
    private String additionalInfo3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adminChannelId", fetch = FetchType.LAZY)
    private Set<Transactions> transactionsCollection;
    @JoinColumn(name = "user_group_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserGroup userGroupId;

    public AdminChannel() {
    }

    public AdminChannel(Long id) {
        this.id = id;
    }

    public AdminChannel(Long id, String login, String pwd) {
        this.id = id;
        this.login = login;
        this.pwd = pwd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalInfo1() {
        return additionalInfo1;
    }

    public void setAdditionalInfo1(String additionalInfo1) {
        this.additionalInfo1 = additionalInfo1;
    }

    public String getAdditionalInfo2() {
        return additionalInfo2;
    }

    public void setAdditionalInfo2(String additionalInfo2) {
        this.additionalInfo2 = additionalInfo2;
    }

    public String getAdditionalInfo3() {
        return additionalInfo3;
    }

    public void setAdditionalInfo3(String additionalInfo3) {
        this.additionalInfo3 = additionalInfo3;
    }

    public Set<Transactions> getTransactionsCollection() {
        return transactionsCollection;
    }

    public void setTransactionsCollection(Set<Transactions> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    public UserGroup getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(UserGroup userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdminChannel)) {
            return false;
        }
        AdminChannel other = (AdminChannel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.betaapps.dto.AdminChannel[id=" + id + "]";
    }

}
