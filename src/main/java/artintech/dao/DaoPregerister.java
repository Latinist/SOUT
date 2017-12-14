package artintech.dao;

import artintech.Digester;
import artintech.Request;
import artintech.domain.Requests;
import artintech.domain.SprOrgExp;
import artintech.domain.User;
import artintech.domain.WebPreregister;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Анатолий on 08.01.2015.
 */
public class DaoPregerister {
    private String linkDB;

    public DaoPregerister(String linkDB){
        this.linkDB = linkDB;
    }
    /* Пользователь полностью зарегистрирован */
    public Boolean isRegister(String email, Integer idGlobal) {
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.email = :email and g.idGlobal = :idGlobal and g.dateRegisters is not null").
                setParameter("email", email).
                setParameter("idGlobal",idGlobal).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()) {
            //"Потребитель с таким почтовым адресом уже зарегистрирован";
            emps.clear();
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }
    /* пользователь в состоянии регистрации. Ему отправлено мыло но ответа еще нет */
    public Boolean inStateRegister(String email,Integer idGlobal, Integer timeOutInHours){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        GregorianCalendar c = new GregorianCalendar();//календарь на текущую дату
        c.add(GregorianCalendar.HOUR, -timeOutInHours );
        Date waitdate = c.getTime();
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.email = :email and g.idGlobal = :idGlobal and g.dateRegisters is null and g.dateContact < :dateContact"  ).
                setParameter("email", email).
                setParameter("dateContact", waitdate).
                setParameter("idGlobal",idGlobal).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()){
            //  пользователь есть но его время регистрации не истекло. Отправим ему почту повторно и обновим время регистрации
            emps.clear();
            return true;
        } else{
            return false;
        }

    }
    /* Логин не захвачен */
    public Boolean isLoginfree(String email,String login, Integer timeOutInHours){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        GregorianCalendar c = new GregorianCalendar();//календарь на текущую дату
        c.add(GregorianCalendar.HOUR, -timeOutInHours );
        Date waitdate = c.getTime();
        /* Логин захвачен */
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.login = :login and g.email<>:email and"+
                            " ((g.dateRegisters is not null) or (g.dateRegisters is null and g.dateContact < :dateContact))"  ).
                setParameter("login", login).
                setParameter("email", email).
                setParameter("dateContact",waitdate).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()){
            // login Занят
            emps.clear();
            return false;
        } else{
            return true;
        }

    }
    // переписать объект регистрации
    public  void makePregister(WebPreregister ss){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
//        Collection emps;
        em.getTransaction().begin();
//        ss.setDateRegisters(new Date());
        em.persist(ss);
        em.getTransaction().commit();
        em.close();
    }

    // Прописываение даты регистрации
    public String makeFinishPregister(String email, Integer idGlobal){
        String url="";
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps= null;
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.email = :email and g.idGlobal = :idGlobal and g.dateRegisters is not null").
                setParameter("email", email).
                setParameter("idGlobal", idGlobal).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()){
            WebPreregister e2 = (WebPreregister) emps.iterator().next();
            url = e2.getUrlTmp();
            em.getTransaction().begin();
            e2.setDateContact(new Date());
            em.persist(e2);
            em.getTransaction().commit();
        }
        em.close();
        return url;
    }

    // Получить данные пользователя
    public User getUser(String login, String password,Integer idGlobal){
        EntityManager emUser = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection empsUser = null;
        empsUser = emUser.createQuery("SELECT g FROM  WebPreregister g WHERE g.login = :login and g.password = :password and g.idGlobal = :idGlobal and g.dateRegisters is not null" ).
                setParameter("login", login).
                setParameter("password", password).
                setParameter("idGlobal", new BigDecimal(idGlobal)).
                setMaxResults(1).
                getResultList();
        if (!empsUser.isEmpty()){
            WebPreregister pojo = (WebPreregister) empsUser.iterator().next();
            //return pojo.getId().toBigInteger().intValue();
            User user = new User();
            user.setId( pojo.getId().toBigInteger().intValue());
            user.seteMail(pojo.getEmail());
            user.setNameOrg(pojo.getNameOrg());
            user.setTelephone(pojo.getTelephone());
            user.setIdGlobal(pojo.getIdGlobal().toBigInteger().intValue());
            user.setWebState(pojo.getWebState());
            emUser.close();
            return user;
        } else {
            emUser.close();
            return null;
        }

    }

    // Получить данные пользователя
    public User getAlterUser(Integer idUser){
        EntityManager emUser = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection empsUser = null;
        empsUser = emUser.createQuery("SELECT g FROM  WebPreregister g WHERE g.id = :idUser and g.dateRegisters is not null" ).
                setParameter("idUser", idUser).
                setMaxResults(1).
                getResultList();
        if (!empsUser.isEmpty()){
            WebPreregister pojo = (WebPreregister) empsUser.iterator().next();
            //return pojo.getId().toBigInteger().intValue();
            User user = new User();
            user.setId( pojo.getId().toBigInteger().intValue());
            user.seteMail(pojo.getEmail());
            user.setNameOrg(pojo.getNameOrg());
            user.setTelephone(pojo.getTelephone());
            user.setIdGlobal(pojo.getIdGlobal().toBigInteger().intValue());
            emUser.close();
            return user;
        } else {
            emUser.close();
            return null;
        }

    }

    // получить объект регистрации
    public WebPreregister getPreregistr(Integer idUser){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.id = :idUser and g.dateRegisters is not null").
                setParameter("idUser", idUser).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()) {
            WebPreregister pojo = (WebPreregister) emps.iterator().next();
            em.close();
            return pojo;
        }
        em.close();
        return null;

    }

    // переписать объект регистрации
    public boolean updatePregister(WebPreregister ss){
        int i = 0;
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        em.getTransaction().begin();
        i = em.createQuery("UPDATE WebPreregister g set g.telephone= :telephone, g.nameOrg = :nameOrg  WHERE g.id = :idUser").
                setParameter("idUser", ss.getId()).
                setParameter("telephone",ss.getTelephone()).
               // setParameter("login",ss.getLogin()).
                setParameter("nameOrg", ss.getNameOrg()).executeUpdate();
//        em.persist(ss);
//        em.refresh(ss);
        em.getTransaction().commit();
        em.close();
        if (i == 1) return true;
        else return false;
    }

    public  Boolean changeLoginPassword(Integer idUser,String login, String password){
        int i = 0;
        String pw = null;
        try {
            pw = Digester.getDigest(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        em.getTransaction().begin();
        i = em.createQuery("UPDATE WebPreregister g set g.login= :login, g.password = :password WHERE g.id = :idUser").
                setParameter("idUser", idUser).
                setParameter("password", pw).
                setParameter("login", login).executeUpdate();
        em.getTransaction().commit();
        em.close();
        if (i == 1) return true;
        else return false;
    }

    // обновим логин и пароль

    public  Boolean updateLoginPassword(String eMail,String login, String password,Integer idGlobal){
        int i = 0;
        String pw = null;
        try {
            pw = Digester.getDigest(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        em.getTransaction().begin();
        i = em.createQuery("UPDATE WebPreregister g set g.login= :login, g.password = :password WHERE g.email = :eMail and g.idGlobal = :idGlobal").
                setParameter("eMail", eMail).
                setParameter("password", pw).
                setParameter("idGlobal",idGlobal).
                setParameter("login", login).executeUpdate();
        em.getTransaction().commit();
        em.close();
        if (i == 1) return true;
        else return false;
    }

    public Boolean idGlobalIsValid(Integer idGlobal){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        List sp = em.createQuery("select p from SprOrgExp p where p.idGlobal = :id", SprOrgExp.class).setParameter("id", idGlobal).



                setMaxResults(1).getResultList();
        if (sp.size() == 0) return false;
        else return true;
    }


    public String getEMailExpertOrg(Integer idGlobal){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        SprOrgExp sp = em.createQuery("select p from SprOrgExp p where p.idGlobal = :id", SprOrgExp.class).setParameter("id", idGlobal).
                setMaxResults(1).getSingleResult();
        em.close();
        if (sp == null) return null;
        else return sp.getEmail();
    }


    public String getPhoneExpOrg(Integer idGlobal) throws SQLException, IOException {
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();

        BigDecimal id = new BigDecimal(idGlobal);
        String ret = null;
//        this.idRequests = idRequests;
        em.getTransaction().begin();
        java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
        PreparedStatement ps = null;
        ps = connection.prepareStatement(
                "SELECT PHONE from  SPR_ORG_EXP where ID_GLOBAL= ?");
        ps.setBigDecimal(1, id);
        ResultSet result = ps.executeQuery();
        int ind = result.findColumn("PHONE");
        if (result.next()){
            ret = result.getString(ind);
        }
//        while (result.next()){
//            result.getBigDecimal(0);
//        }
        ps.close();
        em.getTransaction().commit();
        return ret;
    }

    public boolean setWebState(String webState, int idUser){
        int i = 0;
        EntityManager em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        em.getTransaction().begin();
        i = em.createQuery("UPDATE WebPreregister g set g.webState = :webState  WHERE g.id = :idUser").
                setParameter("idUser", idUser).
                setParameter("webState",webState).
                executeUpdate();
        em.getTransaction().commit();
        em.close();
        if (i == 1) return true;
        else return false;
    }

    public BigDecimal getIdRequests(int idUser){
        BigDecimal idU = new BigDecimal(idUser);
        EntityManager emUser = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Requests res = emUser.createQuery("SELECT g FROM  Requests g WHERE g.idWebUser = :idUser" ,Requests.class).
                setParameter("idUser", idU).
                setMaxResults(1).
                getSingleResult();
        if (res !=null){
            BigDecimal ret =  res.getId();
            emUser.close();
            return ret;
        } else {
            emUser.close();
            return null;
        }

    }

};
