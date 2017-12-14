/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Анатолий
 */
@Entity
@Table(name = "SPR_DOCS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SprDocs.findAll", query = "SELECT s FROM SprDocs s"),
    @NamedQuery(name = "SprDocs.findById", query = "SELECT s FROM SprDocs s WHERE s.id = :id"),
    @NamedQuery(name = "SprDocs.findByNm", query = "SELECT s FROM SprDocs s WHERE s.nm = :nm")})
public class SprDocs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "NM")
    private String nm;

    public SprDocs() {
    }

    public SprDocs(Integer id) {
        this.id = id;
    }

    public SprDocs(Integer id, String nm) {
        this.id = id;
        this.nm = nm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
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
        if (!(object instanceof SprDocs)) {
            return false;
        }
        SprDocs other = (SprDocs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "artintech.domain.SprDocs[ id=" + id + " ]";
    }
    
}
