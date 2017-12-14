package artintech.dao;

import artintech.domain.ReeDocs;
import artintech.domain.ReeXml;
import artintech.domain.Requests;
import com.google.common.io.ByteStreams;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Notification;
import org.eclipse.persistence.sessions.Session;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by Анатолий on 29.01.2015.
 */
public class DaoDocs {
    private String linkDB;
//    private EntityManager em;
    private BigDecimal idRequests = null;
    public DaoDocs(String linkDB){
        this.linkDB = linkDB;
//        em = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
    }

    private Boolean XmlExists(String filename, BigDecimal idContract, BigDecimal DocType){
      boolean ret = false;
      EntityManager em = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
        em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        emps = em.createQuery("SELECT g FROM  ReeXml g WHERE g.idContract = :idContract and g.doctype=:doctype").
                setParameter("idContract", idContract).
                setParameter("doctype",DocType).
//                setParameter("filename",filename).
                setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()) {
            //"Потребитель с таким почтовым адресом уже зарегистрирован";
            emps.clear();
            em.close();
            ret = true;
        } else {
            em.close();
            ret = false;
        }
      return ret;
    }


    private Boolean DocExists(String filename, BigDecimal idContract, BigDecimal DocType){
        boolean ret = false;
        EntityManager em = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
        em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        Collection emps = null;
        emps = em.createQuery("SELECT g FROM  ReeDocs g WHERE g.filename = :filename and g.idContract = :idContract and g.doctype=:doctype").
                setParameter("idContract", idContract).
                setParameter("doctype",DocType).
                setParameter("filename",filename).
        setMaxResults(1).
                getResultList();
        if (!emps.isEmpty()) {
            //"Потребитель с таким почтовым адресом уже зарегистрирован";
            emps.clear();
            em.close();
            ret = true;
        } else {
            em.close();
            ret = false;
        }
        return ret;
    }

    private String noteOnDoc(String filename, BigDecimal idContract, BigDecimal DocType){
        String note = null;
                EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
                em1.getTransaction().begin();
                java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
                PreparedStatement ps = null;
                try {
                    ps = connection.prepareStatement(
                            "SELECT NOTE from REE_DOCS where ID_CONTRACT =? and FILENAME = ? and DOCTYPE = ?");
                    int i =1;  ps.setBigDecimal(i, idContract);
                    i =2;  ps.setString(i, filename);
                    i =3;  ps.setBigDecimal(i, DocType);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        //is = rs.getBinaryStream("BODYB");
                        note = rs.getString("NOTE");
                        break;
                    }
                    ps.close();
                    em1.getTransaction().commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return note;
    }


    //-------- это резервный расчет на случай если не будет работать триггер ------------------
    private String executeImportXLS(String filename, BigDecimal idContract, BigDecimal DocType){
        String note = null;
        EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
        em1.getTransaction().begin();
        java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(
                    "SELECT mkvCreateXls.ExtInfoIn(ID_CONTRACT, BODY) NOTE from REE_DOCS where ID_CONTRACT =? and FILENAME = ? and DOCTYPE = ?");
            int i =1;  ps.setBigDecimal(i, idContract);
            i =2;  ps.setString(i, filename);
            i =3;  ps.setBigDecimal(i, DocType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                //is = rs.getBinaryStream("BODYB");
                note = rs.getString("NOTE");
                break;
            }
            ps.close();
            //em1.getTransaction().commit();


            //em1.getTransaction().begin();
            ps = connection.prepareStatement(
               "UPDATE REE_DOCS SET NOTE= ? where DOCTYPE = ? and FILENAME = ? and ID_CONTRACT= ?");
            ps.setString(1, note);
            ps.setBigDecimal(2, DocType);
            ps.setString(3, filename);
            ps.setBigDecimal(4, idContract);
            int count = ps.executeUpdate();
            ps.close();
            em1.getTransaction().commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return note;
    }


    public void setDocToDb(InputStream inputStream, String filename,Integer idUser,BigDecimal idRequests, BigDecimal DocType, BigDecimal idContract, BigDecimal fileSize) throws SQLException, IOException {
        BigDecimal idPreregieter = new BigDecimal(idUser);
        //BigDecimal DocType = new BigDecimal(100);
        this.idRequests = idRequests;
        //  Вынести сюда нельзя поскольку транзакция закрывается раньше времени
        if (XmlExists(filename, idContract, DocType)){
            EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
            em1.getTransaction().begin();
            java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);

            PreparedStatement ps = null;
            ps = connection.prepareStatement(
                    "UPDATE REE_XML SET BODYB= ? where DOCTYPE = ? and FILENAME = ? and ID_CONTRACT= ?");
            ps.setBlob(1, inputStream);
            ps.setBigDecimal(2, DocType);
            ps.setString(3, filename);
            ps.setBigDecimal(4, idContract);
            int count = ps.executeUpdate();
            inputStream.close();
            ps.close();
            em1.getTransaction().commit();

        } else {
            EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
            em1.getTransaction().begin();
            java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
            PreparedStatement ps = null;
            ps = connection.prepareStatement(
                    "INSERT INTO REE_XML (ID_REQUESTS, ID_PREREGISTER, DOCTYPE, FILENAME,BODYB, ID_CONTRACT, FILESIZE) VALUES (?,?,?,?,?,?,?)");
            ps.setBigDecimal(1, idRequests);
            ps.setBigDecimal(2, idPreregieter);
            ps.setBigDecimal(3, DocType);
            ps.setString(4, filename);
//-----------------------------------------------
//        ps.setAsciiStream(5,inputStream);
            ps.setBlob(5, inputStream);
            ps.setBigDecimal(6, idContract);
            ps.setBigDecimal(7, fileSize);
            int count = ps.executeUpdate();
            inputStream.close();
            ps.close();
            em1.getTransaction().commit();
        }
/*
        ReeXml reeXml = new ReeXml();
        reeXml.setFilename(filename);
        Serializable ff = new ByteArrayInputStream(filename);
        Serializable dd = new String;

        Blob blob =  em.get;

        reeXml.setBody(dd);
*/
    }

    public String uploadDocToDb(InputStream inputStream, String filename,Integer idUser,BigDecimal idRequests, BigDecimal DocType, BigDecimal idContract, BigDecimal fileSize) throws SQLException, IOException {
        BigDecimal idPreregieter = new BigDecimal(idUser);
        this.idRequests = idRequests;
        //  Вынести сюда нельзя поскольку транзакция закрывается раньше времени
//        if (XmlExists(filename,idContract, DocType)){
          if (DocExists(filename,idContract, DocType)){
            EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
            em1.getTransaction().begin();
            java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);

            PreparedStatement ps = null;
            ps = connection.prepareStatement(
                    "UPDATE REE_DOCS SET BODY= ? where DOCTYPE = ? and FILENAME = ? and ID_CONTRACT= ?");
            ps.setBlob(1, inputStream);
            ps.setBigDecimal(2, DocType);
            ps.setString(3, filename);
            ps.setBigDecimal(4, idContract);
            int count = ps.executeUpdate();
            inputStream.close();
            ps.close();
            em1.getTransaction().commit();
        } else {
            EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();

            em1.getTransaction().begin();
            java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
            PreparedStatement ps = null;
            ps = connection.prepareStatement(
                    "INSERT INTO REE_DOCS (ID_REQUESTS, ID_PREREGISTER, DOCTYPE, FILENAME,BODY, ID_CONTRACT, FILESIZE) VALUES (?,?,?,?,?,?,?)");
            ps.setBigDecimal(1, idRequests);
            ps.setBigDecimal(2, idPreregieter);
            ps.setBigDecimal(3, DocType);
            ps.setString(4, filename);
//-----------------------------------------------
            ps.setBlob(5, inputStream);
            ps.setBigDecimal(6, idContract);
            ps.setBigDecimal(7, fileSize);
            int count = ps.executeUpdate();
            inputStream.close();
            //connection.commit();
            //connection.close();
            ps.close();
            em1.getTransaction().commit();
        }

//        executeImportXLS(filename, idContract, DocType);

        String note =  noteOnDoc(filename, idContract, DocType);
        //Notification.show("Получено сообщение: " + note, Notification.Type.WARNING_MESSAGE);
        return note;

    }


    public void deleteDoc(BigDecimal id, boolean isXml){
        EntityManager em = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
        em = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        if (isXml) {
            ReeXml doc = em.find(ReeXml.class, id);
            em.getTransaction().begin();
            em.remove(doc);
            em.getTransaction().commit();
        } else {
            em.getTransaction().begin();
            ReeDocs doc = em.find(ReeDocs.class, id);
            em.remove(doc);
            em.getTransaction().commit();
        }
        em.close();
    }

    public Resource getExelStruktureFile(final BigDecimal idContract) throws SQLException {
        StreamResource.StreamSource  streamSource =  new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
                em1.getTransaction().begin();
                java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
                PreparedStatement ps = null;
                InputStream is =  null;
                Blob bl = null;
                InputStream is1 = null;
                try {
                    ps = connection.prepareStatement(
                            "SELECT mkvCreateXls.StructInfoOut(?) BODYB from dual");
                    int i =1;
                    ps.setBigDecimal(i, idContract);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        //is = rs.getBinaryStream("BODYB");
                        bl = rs.getBlob("BODYB");
                        break;
                    }
                    ps.close();
                    em1.getTransaction().commit();
                    is1 = bl.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return is1;
            }
        };
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String fileName = "Структура предприятия "+idContract+"_"+timeStamp+".xls";
        Resource ff = new StreamResource(streamSource, fileName);
        return ff;
    }

    public Resource getExelFile(final BigDecimal idContract) throws SQLException {
        StreamResource.StreamSource  streamSource =  new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                EntityManager em1 = Persistence.createEntityManagerFactory(linkDB).createEntityManager();
                em1.getTransaction().begin();
                java.sql.Connection connection = em1.unwrap(java.sql.Connection.class);
                PreparedStatement ps = null;
                InputStream is =  null;
                Blob bl = null;
                InputStream is1 = null;
                try {
                    ps = connection.prepareStatement(
                       "SELECT mkvCreateXls.ExtInfoOut(?, 0) BODYB from dual");
//                    "SELECT mkvCreateXls.ExtInfoOut(69) BODYB from dual");
//                            "SELECT BODYB, FILENAME from REE_XML where ID = 16");
//                    "SELECT BODYB, FILENAME from table(mkvCreateXls.ExtInfoOut (?))");
                    int i =1;
                    ps.setBigDecimal(i, idContract);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        //is = rs.getBinaryStream("BODYB");
                        bl = rs.getBlob("BODYB");
                        break;
                    }
                    ps.close();
                    em1.getTransaction().commit();
                    is1 = bl.getBinaryStream();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return is1;
            }
        };
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String fileName = "Расширенный_список_рабочих_мест_"+idContract+"_"+timeStamp+".xls";
        Resource ff = new StreamResource(streamSource, fileName);
        return ff;
    }

    public JPAContainer getReeDocs(BigDecimal idRequests){
//        BigDecimal bd = new BigDecimal(idRequests);
        JPAContainer<ReeDocs> requests = JPAContainerFactory.make(ReeDocs.class, linkDB);
        requests.addContainerFilter(new Compare.Equal("idRequests", idRequests));
        requests.applyFilters();
        return requests;
    }


}
