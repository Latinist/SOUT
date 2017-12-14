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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Анатолий
 */
@Entity
@Table(name = "REQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requests.findAll", query = "SELECT r FROM Requests r"),
    @NamedQuery(name = "Requests.findById", query = "SELECT r FROM Requests r WHERE r.id = :id"),
    @NamedQuery(name = "Requests.findByNumRequests", query = "SELECT r FROM Requests r WHERE r.numRequests = :numRequests"),
    @NamedQuery(name = "Requests.findByNameOrg", query = "SELECT r FROM Requests r WHERE r.nameOrg = :nameOrg"),
    @NamedQuery(name = "Requests.findByAddressEval", query = "SELECT r FROM Requests r WHERE r.addressEval = :addressEval"),
    @NamedQuery(name = "Requests.findByContacts", query = "SELECT r FROM Requests r WHERE r.contacts = :contacts"),
    @NamedQuery(name = "Requests.findByWorkplaceCount", query = "SELECT r FROM Requests r WHERE r.workplaceCount = :workplaceCount"),
    @NamedQuery(name = "Requests.findByWorkplaceAnalog", query = "SELECT r FROM Requests r WHERE r.workplaceAnalog = :workplaceAnalog"),
    @NamedQuery(name = "Requests.findByDateCreate", query = "SELECT r FROM Requests r WHERE r.dateCreate = :dateCreate"),
    @NamedQuery(name = "Requests.findByIdGlobal", query = "SELECT r FROM Requests r WHERE r.idGlobal = :idGlobal"),
    @NamedQuery(name = "Requests.findByNote", query = "SELECT r FROM Requests r WHERE r.note = :note")})
public class Requests implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator="REQUESTS_SEQ")
    @SequenceGenerator(name="REQUESTS_SEQ",sequenceName="REQUESTS_SEQ", allocationSize=1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "NUM_REQUESTS")
    private String numRequests;
    @Size(max = 250)
    @Column(name = "NAME_ORG")
    private String nameOrg;
    @Size(max = 250)
    @Column(name = "ADDRESS_EVAL")
    private String addressEval;
    @Size(max = 250)
    @Column(name = "CONTACTS")
    private String contacts;
    @Column(name = "WORKPLACE_COUNT")
    private Integer workplaceCount;
    @Column(name = "WORKPLACE_ANALOG")
    private Integer workplaceAnalog;
    @Column(name = "DATE_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;
    @Column(name = "ID_GLOBAL")
    private Integer idGlobal;
    @Size(max = 250)
    @Column(name = "NOTE")
    private String note;
    @JoinColumn(name = "ID_STATUS", referencedColumnName = "ID", insertable=false, updatable=false)
    @ManyToOne
    private SprStatus status; //idStatus;

    @JoinColumn(name = "ID_GLOBAL",referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne
    private SprOrgExp global;

    @Column(name = "ID_STATUS")
    private  Integer idStatus;

//    @JoinColumn(name = "ID_WEB_USER", referencedColumnName = "ID")
//    @ManyToOne
//    private WebPreregister idWebUser;
    @Column(name = "ID_WEB_USER")
    private BigDecimal idWebUser;

    @Transient
    private String statusName;

    @Transient
    private  String nameOrgGlobal;


    public Requests() {
        setDateCreate(new Date());
    }

    public Requests(BigDecimal id) {
        this.id = id;
        setDateCreate(new Date());
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
        setNumRequests(id.toString());

    }

    public String getNumRequests() {
        return numRequests;
    }

    public void setNumRequests(String numRequests) {
        this.numRequests = numRequests;
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public String getAddressEval() {
        return addressEval;
    }

    public void setAddressEval(String addressEval) {
        this.addressEval = addressEval;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Integer getWorkplaceCount() {

        if (workplaceCount == null)
        //    return new BigInteger(String.valueOf(0));
 return new Integer(0);

        else
            return workplaceCount;
    }

    public void setWorkplaceCount(Integer workplaceCount) {
        this.workplaceCount = workplaceCount;
    }

    public Integer getWorkplaceAnalog() {
        return workplaceAnalog;
    }

    public void setWorkplaceAnalog(Integer workplaceAnalog) {
        this.workplaceAnalog = workplaceAnalog;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Integer getIdGlobal() {
        return idGlobal;
    }

    public void setIdGlobal(Integer idGlobal) {
        this.idGlobal = idGlobal;
    }

    public String getNote() {
        if (note == null)
            return "";
        else
            return note;
    }

    public void setNote(String note) {
        this.note = note;
    }





    public SprStatus getStatus() {
        return status;
    }
    public void setStatus(SprStatus status) {
        this.status = status;
    }

    public SprOrgExp getGlobal() {
        return global;
    };
    public void setGlobal(SprOrgExp global){
        this.global = global;
    }



    public Integer getIdStatus(){
        return idStatus;
    };
    public void  setIdStatus(Integer idStatus){
        this.idStatus = idStatus;
    };

    public String getStatusName(){
        if (status == null){
          return null;
        } else{
            return status.getStatusName();
        }
    };

    public String getNameOrgGlobal(){
        if (global == null){
            return null;
        } else{
            return global.getNameOrg();
        }
    }


/*
    public WebPreregister getIdWebUser() {
        return idWebUser;
    }

    public void setIdWebUser(WebPreregister idWebUser) {
        this.idWebUser = idWebUser;
    }
*/
public BigDecimal getIdWebUser() {
    return idWebUser;
}

    public void setIdWebUser(BigDecimal idWebUser) {
        this.idWebUser = idWebUser;
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
        if (!(object instanceof Requests)) {
            return false;
        }
        Requests other = (Requests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.Requests[ id=" + id + " ]";
    }
    
}
