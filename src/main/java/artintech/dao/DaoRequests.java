package artintech.dao;

import artintech.domain.Requests;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Compare;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Анатолий on 09.01.2015.
 */
public class DaoRequests {
    private String linkDB;

    public DaoRequests(String linkDB) {
        this.linkDB = linkDB;
    }

    public Container getRequests(Integer idUser){
        BigDecimal bd = new BigDecimal(idUser);
        JPAContainer<Requests> requests = JPAContainerFactory.make(Requests.class, linkDB);
        requests.addContainerFilter(new Compare.Equal("idWebUser", bd));
        requests.applyFilters();
        return requests;
    }

}
