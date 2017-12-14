/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Анатолий
 */
@Entity
@Table(name = "SPR_ORG_EXP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprOrgExp.findAll", query = "SELECT s FROM SprOrgExp s"),
    @NamedQuery(name = "SprOrgExp.findById", query = "SELECT s FROM SprOrgExp s WHERE s.id = :id"),

    @NamedQuery(name = "SprOrgExp.findByIdGlobal", query = "SELECT s FROM SprOrgExp s WHERE s.idGlobal = :idGlobal"),
    @NamedQuery(name = "SprOrgExp.findByNameOrg", query = "SELECT s FROM SprOrgExp s WHERE s.nameOrg = :nameOrg"),
    @NamedQuery(name = "SprOrgExp.findByAddress", query = "SELECT s FROM SprOrgExp s WHERE s.address = :address"),
    @NamedQuery(name = "SprOrgExp.findByFioLeader", query = "SELECT s FROM SprOrgExp s WHERE s.fioLeader = :fioLeader"),
    @NamedQuery(name = "SprOrgExp.findByPhone", query = "SELECT s FROM SprOrgExp s WHERE s.phone = :phone"),
    @NamedQuery(name = "SprOrgExp.findByFax", query = "SELECT s FROM SprOrgExp s WHERE s.fax = :fax"),
    @NamedQuery(name = "SprOrgExp.findByEmail", query = "SELECT s FROM SprOrgExp s WHERE s.email = :email"),
    @NamedQuery(name = "SprOrgExp.findByInn", query = "SELECT s FROM SprOrgExp s WHERE s.inn = :inn"),
    @NamedQuery(name = "SprOrgExp.findByOkpo", query = "SELECT s FROM SprOrgExp s WHERE s.okpo = :okpo"),
    @NamedQuery(name = "SprOrgExp.findByOkogu", query = "SELECT s FROM SprOrgExp s WHERE s.okogu = :okogu"),
    @NamedQuery(name = "SprOrgExp.findByOkved", query = "SELECT s FROM SprOrgExp s WHERE s.okved = :okved"),
    @NamedQuery(name = "SprOrgExp.findByOkato", query = "SELECT s FROM SprOrgExp s WHERE s.okato = :okato"),
    @NamedQuery(name = "SprOrgExp.findByOgrn", query = "SELECT s FROM SprOrgExp s WHERE s.ogrn = :ogrn"),
    @NamedQuery(name = "SprOrgExp.findByNumReg", query = "SELECT s FROM SprOrgExp s WHERE s.numReg = :numReg"),
    @NamedQuery(name = "SprOrgExp.findByDtReg", query = "SELECT s FROM SprOrgExp s WHERE s.dtReg = :dtReg")
//    @NamedQuery(name = "SprOrgExp.findByNumAtt", query = "SELECT s FROM SprOrgExp s WHERE s.numAtt = :numAtt"),
//    @NamedQuery(name = "SprOrgExp.findByDtBeg", query = "SELECT s FROM SprOrgExp s WHERE s.dtBeg = :dtBeg"),
//    @NamedQuery(name = "SprOrgExp.findByDtEnd", query = "SELECT s FROM SprOrgExp s WHERE s.dtEnd = :dtEnd"),
//    @NamedQuery(name = "SprOrgExp.findByNameReg", query = "SELECT s FROM SprOrgExp s WHERE s.nameReg = :nameReg")
    })
public class SprOrgExp implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 250)
    @Column(name = "NAME_ORG")
    private String nameOrg;
    @Size(max = 250)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 250)
    @Column(name = "FIO_LEADER")
    private String fioLeader;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Недопустимый формат номера телефона/факса (должен иметь формат xxx-xxx-xxxx)")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "PHONE")
    private String phone;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Недопустимый формат номера телефона/факса (должен иметь формат xxx-xxx-xxxx)")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 150)
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "INN")
    private BigInteger inn;
    @Column(name = "OKPO")
    private BigInteger okpo;
    @Column(name = "OKOGU")
    private BigInteger okogu;
    @Column(name = "OKVED")
    private BigInteger okved;
    @Column(name = "OKATO")
    private BigInteger okato;
    @Column(name = "OGRN")
    private BigInteger ogrn;
    @Size(max = 50)
    @Column(name = "REG_NUM")
    private String numReg;
    @Column(name = "ID_GLOBAL")
    private BigInteger idGlobal;
    @Column(name = "DT_REG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtReg;
//    @Size(max = 50)
//    @Column(name = "NUM_ATT")
//    private String numAtt;
//    @Column(name = "DT_BEG")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dtBeg;
//    @Column(name = "DT_END")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dtEnd;
//    @Size(max = 250)
//    @Column(name = "NAME_REG")
//    private String nameReg;

    public SprOrgExp() {
    }

    public SprOrgExp(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFioLeader() {
        return fioLeader;
    }

    public void setFioLeader(String fioLeader) {
        this.fioLeader = fioLeader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getInn() {
        return inn;
    }

    public void setInn(BigInteger inn) {
        this.inn = inn;
    }

    public BigInteger getOkpo() {
        return okpo;
    }

    public void setOkpo(BigInteger okpo) {
        this.okpo = okpo;
    }

    public BigInteger getOkogu() {
        return okogu;
    }

    public void setOkogu(BigInteger okogu) {
        this.okogu = okogu;
    }

    public BigInteger getOkved() {
        return okved;
    }

    public void setOkved(BigInteger okved) {
        this.okved = okved;
    }

    public BigInteger getOkato() {
        return okato;
    }

    public void setOkato(BigInteger okato) {
        this.okato = okato;
    }

    public BigInteger getOgrn() {
        return ogrn;
    }

    public void setOgrn(BigInteger ogrn) {
        this.ogrn = ogrn;
    }

    public String getNumReg() {
        return numReg;
    }

    public void setNumReg(String numReg) {
        this.numReg = numReg;
    }

    public Date getDtReg() {
        return dtReg;
    }

    public void setDtReg(Date dtReg) {
        this.dtReg = dtReg;
    }

    public BigInteger getIdGlobal() {
        return idGlobal;
    }
    public void setIdGlobal(BigInteger idGlobal) {
        this.idGlobal = idGlobal;
    }

/*
    public String getNumAtt() {
        return numAtt;
    }
    public void setNumAtt(String numAtt) {
        this.numAtt = numAtt;
    }

    public Date getDtBeg() {
        return dtBeg;
    }

    public void setDtBeg(Date dtBeg) {
        this.dtBeg = dtBeg;
    }

    public Date getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(String nameReg) {
        this.nameReg = nameReg;
    }
*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprOrgExp)) {
            return false;
        }
        SprOrgExp other = (SprOrgExp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.SprOrgExp[ id=" + id + " ]";
    }

}
