/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech.domain;

import artintech.Digester;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Анатолий
 */
@Entity
@Table(name = "WEB_PREREGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebPreregister.findAll", query = "SELECT w FROM WebPreregister w"),
    @NamedQuery(name = "WebPreregister.findByNameOrg", query = "SELECT w FROM WebPreregister w WHERE w.nameOrg = :nameOrg"),
    @NamedQuery(name = "WebPreregister.findByEmail", query = "SELECT w FROM WebPreregister w WHERE w.email = :email"),
    @NamedQuery(name = "WebPreregister.findByTelephone", query = "SELECT w FROM WebPreregister w WHERE w.telephone = :telephone"),
    @NamedQuery(name = "WebPreregister.findByLogin", query = "SELECT w FROM WebPreregister w WHERE w.login = :login"),
    @NamedQuery(name = "WebPreregister.findByPassword", query = "SELECT w FROM WebPreregister w WHERE w.password = :password"),
    @NamedQuery(name = "WebPreregister.findByUrlTmp", query = "SELECT w FROM WebPreregister w WHERE w.urlTmp = :urlTmp"),
    @NamedQuery(name = "WebPreregister.findByDateContact", query = "SELECT w FROM WebPreregister w WHERE w.dateContact = :dateContact"),
    @NamedQuery(name = "WebPreregister.findByDateRegisters", query = "SELECT w FROM WebPreregister w WHERE w.dateRegisters = :dateRegisters"),
    @NamedQuery(name = "WebPreregister.findByStopped", query = "SELECT w FROM WebPreregister w WHERE w.stopped = :stopped"),
    @NamedQuery(name = "WebPreregister.findByIdGlobal", query = "SELECT w FROM WebPreregister w WHERE w.idGlobal = :idGlobal"),
    @NamedQuery(name = "WebPreregister.findById", query = "SELECT w FROM WebPreregister w WHERE w.id = :id")})
public class WebPreregister implements Serializable {
//    @OneToMany(mappedBy = "idWebUser")
//    private Collection<Requests> requestsCollection;
//    private static final long serialVersionUID = 1L;
    @Size(max = 250)
    @Column(name = "NAME_ORG")
    private String nameOrg;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Недопустимый адрес электронной почты")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 20)
    @Column(name = "TELEPHONE")
    private String telephone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 200)
    @Column(name = "URL_TMP")
    private String urlTmp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_CONTACT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateContact;
    @Column(name = "DATE_REGISTERS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegisters;
    @Column(name = "STOPPED")
    private BigInteger stopped;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    @GeneratedValue(generator="PREREGISTER_SEQ")
    @SequenceGenerator(name="PREREGISTER_SEQ",sequenceName="PREREGISTER_SEQ", allocationSize=1)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "ID_GLOBAL")
    @NotNull
    private BigDecimal idGlobal;
    @Column(name = "WEB_STATE")
    @Basic
    private String webState;
    @Transient
    private String passwordOriginal;
    @Transient
    private String passwordControl;

    public WebPreregister() {
    }

    public WebPreregister(BigDecimal id) {
        this.id = id;
    }

    public WebPreregister(BigDecimal id, String email, String login, String password, Date dateContact, String nameOrg) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.dateContact = dateContact;
        this.nameOrg = nameOrg;
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlTmp() {
        return urlTmp;
    }

    public void setUrlTmp(String urlTmp) {
        this.urlTmp = urlTmp;
    }

    public Date getDateContact() {
        return dateContact;
    }

    public void setDateContact(Date dateContact) {
        this.dateContact = dateContact;
    }

    public Date getDateRegisters() {
        return dateRegisters;
    }

    public void setDateRegisters(Date dateRegisters) {
        this.dateRegisters = dateRegisters;
    }

    public BigInteger getStopped() {
        return stopped;
    }

    public void setStopped(BigInteger stopped) {
        this.stopped = stopped;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public void setIdGlobal(BigDecimal idGlobal) {
        this.idGlobal = idGlobal;
    }
    public BigDecimal getIdGlobal(){return idGlobal;}

    public String getPasswordControl(){
        return passwordControl;
    };
    public void setPasswordControl(String passwordControl){
        this.passwordControl = passwordControl;
    }

    public String getPasswordOriginal(){
        return passwordOriginal;
    };
    public void setPasswordOriginal(String passwordOriginal){
        try {
            this.password = Digester.getDigest(passwordOriginal);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.passwordOriginal = passwordOriginal;
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
        if (!(object instanceof WebPreregister)) {
            return false;
        }
        WebPreregister other = (WebPreregister) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.WebPreregister[ id=" + id + " ]";
    }

    public String getWebState() {
        return webState;
    }

    public void setWebState(String webState) {
        this.webState = webState;
    }

/*
    @XmlTransient
    public Collection<Requests> getRequestsCollection() {
        return requestsCollection;
    }

    public void setRequestsCollection(Collection<Requests> requestsCollection) {
        this.requestsCollection = requestsCollection;
    }
*/
    
}
