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
import javax.persistence.Lob;
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
@Table(name = "REE_DOCS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReeDocs.findAll", query = "SELECT r FROM ReeDocs r"),
    @NamedQuery(name = "ReeDocs.findById", query = "SELECT r FROM ReeDocs r WHERE r.id = :id"),
    @NamedQuery(name = "ReeDocs.findByIdRequests", query = "SELECT r FROM ReeDocs r WHERE r.idRequests = :idRequests"),
    @NamedQuery(name = "ReeDocs.findByIdPreregister", query = "SELECT r FROM ReeDocs r WHERE r.idPreregister = :idPreregister"),
    @NamedQuery(name = "ReeDocs.findByDoctype", query = "SELECT r FROM ReeDocs r WHERE r.doctype = :doctype"),
    @NamedQuery(name = "ReeDocs.findByFilename", query = "SELECT r FROM ReeDocs r WHERE r.filename = :filename"),
    @NamedQuery(name = "ReeDocs.findByIdContract", query = "SELECT r FROM ReeDocs r WHERE r.idContract = :idContract"),
    @NamedQuery(name = "ReeDocs.findByFileSize", query = "SELECT r FROM ReeDocs r WHERE r.fileSize = :fileSize"),
    @NamedQuery(name = "ReeDocs.findByDttm", query = "SELECT r FROM ReeDocs r WHERE r.dttm = :dttm")})
public class ReeDocs implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REQUESTS")
    private BigInteger idRequests;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PREREGISTER")
    private BigInteger idPreregister;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DOCTYPE")
    private BigInteger doctype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FILENAME")
    private String filename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DTTM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dttm;
    @NotNull
    @Column(name = "ID_CONTRACT")
    private BigDecimal idContract;
    @Column(name = "FILESIZE")
    private  BigDecimal fileSize;

//    @Lob
//    @Column(name = "BODY")
//    private Serializable body;

    public ReeDocs() {
    }

    public ReeDocs(BigDecimal id) {
        this.id = id;
    }

    public ReeDocs(BigDecimal id, BigInteger idRequests, BigInteger idPreregister, BigInteger doctype, String filename, Date dttm) {
        this.id = id;
        this.idRequests = idRequests;
        this.idPreregister = idPreregister;
        this.doctype = doctype;
        this.filename = filename;
        this.dttm = dttm;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getIdRequests() {
        return idRequests;
    }

    public void setIdRequests(BigInteger idRequests) {
        this.idRequests = idRequests;
    }

    public BigInteger getIdPreregister() {
        return idPreregister;
    }

    public void setIdPreregister(BigInteger idPreregister) {
        this.idPreregister = idPreregister;
    }

    public BigInteger getDoctype() {
        return doctype;
    }

    public void setDoctype(BigInteger doctype) {
        this.doctype = doctype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getDttm() {
        return dttm;
    }

    public void setDttm(Date dttm) {
        this.dttm = dttm;
    }

//    public Serializable getBody() {
//        return body;
//    }

//    public void setBody(Serializable body) {
//        this.body = body;
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
        if (!(object instanceof ReeDocs)) {
            return false;
        }
        ReeDocs other = (ReeDocs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.ReeDocs[ id=" + id + " ]";
    }

    public BigDecimal getIdContract() {
        return idContract;
    }

    public void setIdContract(BigDecimal idContract) {
        this.idContract = idContract;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }
}
