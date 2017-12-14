package artintech.dao;

import artintech.domain.DogovorDb;
import artintech.domain.Requests;
import au.com.bytecode.opencsv.CSVParser;
import com.vaadin.data.util.BeanItemContainer;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Анатолий on 18.06.2015.
 */
public class DaoDogovors {
    private  String linkDb;
    private EntityManager em;

    public DaoDogovors(String linkDb){
        this.linkDb = linkDb;
        em = Persistence.createEntityManagerFactory(linkDb).createEntityManager();
    }

    public void setContractToReady(BigDecimal id_contract) throws SQLException, IOException {
        em.getTransaction().begin();
        java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
        PreparedStatement ps = null;
        ps = connection.prepareStatement(
                "UPDATE CONTRACTS SET ID_STATUS= 5 where ID = ?");
        ps.setBigDecimal(1, id_contract);
        int count = ps.executeUpdate();
        ps.close();
        em.getTransaction().commit();
    }

    public void setStateTrack(BigDecimal idDogovor, StateTrack stateTrack) throws SQLException {
        String str = stateTrack.toString();
        em.getTransaction().begin();
        java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
        PreparedStatement ps = null;
        ps = connection.prepareStatement(
                "UPDATE CONTRACTS SET WEB_STATE= ? where ID = ?");
        ps.setString(1, str);
        ps.setBigDecimal(2, idDogovor);
        int count = ps.executeUpdate();
        ps.close();
        em.getTransaction().commit();
    }

    public StateTrack getTrack(BigDecimal idDogovor) throws SQLException, IOException {
        //String str = stateTrack.toString();
        em.getTransaction().begin();
        java.sql.Connection connection = em.unwrap(java.sql.Connection.class);
        PreparedStatement ps = null;
        ps = connection.prepareStatement(
                "SELECT WEB_STATE FROM CONTRACTS where ID = ?");
        ps.setBigDecimal(1, idDogovor);
        ResultSet res = ps.executeQuery();
        String webState = null;
        if (res.next()) {
            webState = res.getString("WEB_STATE");
        }
        ps.close();
        em.getTransaction().commit();

        StateTrack ret = new StateTrack(webState);
        return ret;
    }


    public BigDecimal getFirstDogovor(Integer idUser){
        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkDb)
                .createEntityManager();
//            BeanItemContainer<DogovorDb> biContainer = new BeanItemContainer<DogovorDb>(DogovorDb.class);
        DogovorDb dd = emTu.createQuery("select g from DogovorDb g where g.webId = :idUser", DogovorDb.class).setParameter("idUser", idUser).setMaxResults(1).getSingleResult();
        BigDecimal ret = dd.getId();
        emTu.close();
        return ret;
    }


    public class StateTrack{
        private Integer idTrack;
        private Integer activeLength;
        private Integer currentstate;

        public StateTrack(String webState) throws IOException {
            if ((webState == null) || webState.trim().equals("")) {
                idTrack = null;
                activeLength = 1;
                currentstate = 1;
            } else {
                CSVParser csvParser = new CSVParser();
                String[] arr = csvParser.parseLine(webState);
                if (arr[0].trim().equals("")){
                    idTrack = null;
                } else {
                    idTrack = Integer.parseInt(arr[0]);
                }
                activeLength = Integer.parseInt(arr[1]);
                currentstate = Integer.parseInt(arr[2]);
            }
        }

        public Integer getIdTrack() {
            return idTrack;
        }

        public void setIdTrack(Integer idTrack) {
            this.idTrack = idTrack;
        }

        public Integer getActiveLength() {
            return activeLength;
        }

        public void setActiveLength(Integer activeLength) {
            this.activeLength = activeLength;
        }

        @Override
        public  String toString(){
            return (idTrack == null) ? "" : idTrack.toString()+","+ activeLength.toString()+","+currentstate.toString();
        }

        public Integer getCurrentstate() {
            return currentstate;
        }

        public void setCurrentstate(Integer currentstate) {
            this.currentstate = currentstate;
        }
    }

}
