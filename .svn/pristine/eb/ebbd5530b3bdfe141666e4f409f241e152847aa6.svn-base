/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Анатолий
 */
@Entity
@Table(name = "SPR_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprStatus.findAll", query = "SELECT s FROM SprStatus s"),
    @NamedQuery(name = "SprStatus.findById", query = "SELECT s FROM SprStatus s WHERE s.id = :id"),
    @NamedQuery(name = "SprStatus.findByStatusName", query = "SELECT s FROM SprStatus s WHERE s.statusName = :statusName"),
    @NamedQuery(name = "SprStatus.findByStVid", query = "SELECT s FROM SprStatus s WHERE s.stVid = :stVid"),
    @NamedQuery(name = "SprStatus.findByColorBg", query = "SELECT s FROM SprStatus s WHERE s.colorBg = :colorBg"),
    @NamedQuery(name = "SprStatus.findByColorText", query = "SELECT s FROM SprStatus s WHERE s.colorText = :colorText"),
    @NamedQuery(name = "SprStatus.findByColorStext", query = "SELECT s FROM SprStatus s WHERE s.colorStext = :colorStext"),
    @NamedQuery(name = "SprStatus.findByDtCreate", query = "SELECT s FROM SprStatus s WHERE s.dtCreate = :dtCreate"),
    @NamedQuery(name = "SprStatus.findByDtEdit", query = "SELECT s FROM SprStatus s WHERE s.dtEdit = :dtEdit")})
public class SprStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 150)
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Size(max = 10)
    @Column(name = "ST_VID")
    private String stVid;
    @Column(name = "COLOR_BG")
    private BigInteger colorBg;
    @Column(name = "COLOR_TEXT")
    private BigInteger colorText;
    @Column(name = "COLOR_STEXT")
    private BigInteger colorStext;
    @Column(name = "DT_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCreate;
    @Column(name = "DT_EDIT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEdit;
//    @OneToMany(mappedBy = "idStatus")
//    private Collection<Requests> requestsCollection;

    public SprStatus() {
    }

    public SprStatus(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStVid() {
        return stVid;
    }

    public void setStVid(String stVid) {
        this.stVid = stVid;
    }

    public BigInteger getColorBg() {
        return colorBg;
    }

    public void setColorBg(BigInteger colorBg) {
        this.colorBg = colorBg;
    }

    public BigInteger getColorText() {
        return colorText;
    }

    public void setColorText(BigInteger colorText) {
        this.colorText = colorText;
    }

    public BigInteger getColorStext() {
        return colorStext;
    }

    public void setColorStext(BigInteger colorStext) {
        this.colorStext = colorStext;
    }

    public Date getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Date getDtEdit() {
        return dtEdit;
    }

    public void setDtEdit(Date dtEdit) {
        this.dtEdit = dtEdit;
    }

//    @XmlTransient
//    public Collection<Requests> getRequestsCollection() {
//        return requestsCollection;
//    }

  //  public void setRequestsCollection(Collection<Requests> requestsCollection) {
//        this.requestsCollection = requestsCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SprStatus)) {
            return false;
        }
        SprStatus other = (SprStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.SprStatus[ id=" + id + " ]";
    }
    
}
