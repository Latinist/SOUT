package artintech.dao;

import artintech.domain.SprDocs;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.validation.OverridesAttribute;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Анатолий on 02.02.2015.
 */
public class DaoSprDocs {
    private String linkDB;
    private EntityManager em;
    private BigDecimal idRequests = null;
    public DaoSprDocs(String linkDB){
        this.linkDB = linkDB;
        em = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
    }

    public Collection<SprDocs> getLispDoc(){
        Collection<SprDocs> sprDocses = em.createQuery("select g from SprDocs g where g.id >100 and g.id < 1000 order by g.id").getResultList();
        em.close();
        return sprDocses;
    }

}
