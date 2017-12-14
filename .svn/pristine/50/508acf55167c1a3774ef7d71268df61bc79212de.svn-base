package artintech.dao;

import artintech.domain.WebPreregister;
import com.vaadin.server.VaadinSession;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Анатолий on 15.01.2015.
 */
public class TestVirtualPage {
    private String linkDB;
    private String eMail;

    public TestVirtualPage(String linkDB){
        this.linkDB = linkDB;
    }

    public Integer isVirtualPage(String id) {
        Integer ret = 0;
        BigDecimal iduser = null;
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps;
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.urlTmp = :id").
                setParameter("id", id).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()) {
            WebPreregister e2 = (WebPreregister) emps.iterator().next();
            Date dateContact = e2.getDateContact();
            Date dateRegisters = e2.getDateRegisters();
            eMail = e2.getEmail();
            iduser = e2.getId();
            GregorianCalendar c = new GregorianCalendar();//календарь на текущую дату
            c.add(GregorianCalendar.DATE, -1);
            //Date waitdate = c.getTime();
            if (dateRegisters != null)
                ret = 2; // уже в работе
            else if (c.after(dateContact))
                ret = 3; // истек срок ожидания
        } else {
            ret =1; // Нет такой виртуальной страницы
        }
        if (ret ==0){
            WebPreregister e2 = (WebPreregister) emps.iterator().next();
            em.getTransaction().begin();
            e2.setDateRegisters(new Date());
            em.persist(e2);
            em.getTransaction().commit();
            Integer id1 =  Integer.valueOf(iduser.intValue());
            VaadinSession.getCurrent().setAttribute("iduser", id1);

        }
        em.close();
        return ret;
    }

    public String geteMail(){
        return eMail;
    }
}
