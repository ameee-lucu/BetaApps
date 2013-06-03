/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.betaapps.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author fahmi
 */
@Entity
@Table(name = "transactions")
@SequenceGenerator(name = "seq_transactions_id_name", sequenceName = "seq_transactions_id", allocationSize = 1)
public class Transactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction_log_id_name")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "terminal")
    private String terminal;
    @Column(name = "customer")
    private String customer;
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Column(name = "status")
    private String status;
    @Column(name = "state")
    private String state;
    @Column(name = "sequence_id")
    private String sequenceId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "co_amount")
    private BigDecimal coAmount;
    @Column(name = "promo")
    private String promo;
    @Column(name = "transaction_id")
    private String transactionId;
    @JoinColumn(name = "admin_channel_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AdminChannel adminChannelId;
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Merchant merchantId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transactionId", fetch = FetchType.LAZY)
    private Set<TransactionLog> transactionLogCollection;

    public Transactions() {
    }

    public Transactions(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCoAmount() {
        return coAmount;
    }

    public void setCoAmount(BigDecimal coAmount) {
        this.coAmount = coAmount;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public AdminChannel getAdminChannelId() {
        return adminChannelId;
    }

    public void setAdminChannelId(AdminChannel adminChannelId) {
        this.adminChannelId = adminChannelId;
    }

    public Merchant getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Merchant merchantId) {
        this.merchantId = merchantId;
    }

    public Set<TransactionLog> getTransactionLogCollection() {
        return transactionLogCollection;
    }

    public void setTransactionLogCollection(Set<TransactionLog> transactionLogCollection) {
        this.transactionLogCollection = transactionLogCollection;
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
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.betaapps.dto.Transactions[id=" + id + "]";
    }

}
