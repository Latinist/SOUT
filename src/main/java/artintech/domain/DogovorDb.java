/**
 * Created by Анатолий on 19.06.2015.
 */

package artintech.domain;

        import javax.persistence.*;
        import javax.validation.constraints.NotNull;
        import javax.xml.bind.annotation.XmlRootElement;
        import java.io.Serializable;
        import java.math.BigDecimal;
        import java.util.Date;

/**
 * Created by Анатолий on 17.06.2015.
 */

@Entity
@Table(name = "V_DOGOVORS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "DogovorDb.findAll", query = "SELECT r FROM DogovorDb r"),
        @NamedQuery(name = "DogovorDb.findById", query = "SELECT r FROM DogovorDb r WHERE r.id = :id"),
        @NamedQuery(name = "DogovorDb.findByWebId", query = "SELECT r FROM DogovorDb r WHERE r.webId = :webId"),
        @NamedQuery(name = "DogovorDb.findByIdRequest", query = "SELECT r FROM DogovorDb r WHERE r.idRequest = :idRequest"),
        @NamedQuery(name = "DogovorDb.findByIdOrg", query = "SELECT r FROM DogovorDb r WHERE r.idOrg = :idOrg"),
        @NamedQuery(name = "DogovorDb.findByRegNum", query = "SELECT r FROM DogovorDb r WHERE r.regNum = :regNum"),
        @NamedQuery(name = "DogovorDb.findByRegNum2", query = "SELECT r FROM DogovorDb r WHERE r.regNum2 = :regNum2"),
        @NamedQuery(name = "DogovorDb.findByDtReg", query = "SELECT r FROM DogovorDb r WHERE r.dtReg = :dtReg"),
        @NamedQuery(name = "DogovorDb.findByNumApp", query = "SELECT r FROM DogovorDb r WHERE r.numApp = :numApp"),
        @NamedQuery(name = "DogovorDb.findByIdApp", query = "SELECT r FROM DogovorDb r WHERE r.idApp = :idApp"),
        @NamedQuery(name = "DogovorDb.findByDtApp", query = "SELECT r FROM DogovorDb r WHERE r.dtApp = :dtApp"),
        @NamedQuery(name = "DogovorDb.findByFullName", query = "SELECT r FROM DogovorDb r WHERE r.fullName = :fullName"),
        @NamedQuery(name = "DogovorDb.findByAddress", query = "SELECT r FROM DogovorDb r WHERE r.address = :address"),
        @NamedQuery(name = "DogovorDb.findByContact", query = "SELECT r FROM DogovorDb r WHERE r.contact = :contact")
})

public class DogovorDb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "WEB_ID")
    private BigDecimal webId;
    @Column(name = "ID_REQUEST")
    private BigDecimal idRequest;
    @Column(name = "ID_ORG")
    private BigDecimal idOrg;
    @Column(name = "REG_NUM")
    private String regNum;
    @Column(name = "REG_NUM2")
    private String regNum2;
    @Column(name = "DT_REG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtReg;
    @Column(name = "NUMAPP")
    private String numApp;
    @Column(name = "IDAPP")
    private BigDecimal idApp;
    @Column(name = "DT_APP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtApp;
    @Column(name = "FULLNAME")
    private String fullName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CONTACT")
    private String contact;

    public BigDecimal getId() {
        return id;
    }
    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getWebId() {
        return webId;
    }
    public void setWebId(BigDecimal webId) {
        this.webId = webId;
    }

    public BigDecimal getIdRequest() {
        return idRequest;
    }
    public void setIdRequest(BigDecimal idRequest) {
        this.idRequest = idRequest;
    }

    public BigDecimal getIdOrg() {
        return idOrg;
    }
    public void setIdOrg(BigDecimal idOrg) {
        this.idOrg = idOrg;
    }

    public String getRegNum() {
        return regNum;
    }
    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getRegNum2() {
        return regNum2;
    }
    public void setRegNum2(String regNum2) {
        this.regNum2 = regNum2;
    }

    public Date getDtReg() {
        return dtReg;
    }
    public void setDtReg(Date dtReg) {
        this.dtReg = dtReg;
    }

    public String getNumApp() {
        return numApp;
    }
    public void setNumApp(String numApp) {
        this.numApp = numApp;
    }

    public BigDecimal getIdApp() {
        return idApp;
    }
    public void setIdApp(BigDecimal idApp) {
        this.idApp = idApp;
    }

    public Date getDtApp() {
        return dtApp;
    }
    public void setDtApp(Date dtApp) {
        this.dtApp = dtApp;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public DogovorDb(){};

    public DogovorDb(BigDecimal id) {
        this.id = id;
    }

    public DogovorDb(BigDecimal id, BigDecimal webId,BigDecimal idRequest,BigDecimal idOrg, String regNum,String regNum2,Date dtReg,String numApp,BigDecimal idApp,Date dtApp,String fullName,String address,String contact) {
        this.id = id;
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
        if (!(object instanceof DogovorDb)) {
            return false;
        }
        DogovorDb other = (DogovorDb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.DogovorDb[ id=" + id + " ]";
    }

}
