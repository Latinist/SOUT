package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoDogovors;
import artintech.dao.DaoPregerister;
import artintech.domain.DogovorDb;
import artintech.domain.User;
//import artintech.domain.VReeDocs;
//import au.com.bytecode.opencsv.CSVParser;
//import com.sun.xml.internal.ws.server.ServerRtException;
//import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.data.util.filter.Compare;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by Анатолий on 27.10.2015.
 */
public class ChoiceDogovor extends VerticalLayout implements IComponentContainer {
    final ComboBox request = new com.vaadin.ui.ComboBox();
    private HashMap<BigDecimal,BigDecimal> hashIdRequests = new HashMap<BigDecimal,BigDecimal>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
    DogovorUpdated dogovorUpdated = null;
    String linkDB;
    Integer idUser = null;
    User user = null;

    private BigDecimal currentIdRequest = null;
    private BigDecimal idDogovor = null;

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        user   = (User) VaadinSession.getCurrent().getAttribute("user");
        if (VaadinSession.getCurrent().getAttribute("idRequest") != null) {
            currentIdRequest = (BigDecimal) VaadinSession.getCurrent().getAttribute("idRequest");
        }
        if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
            idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
//            setNewUser(idDogovor);
        }

        request.setWidth("420px");
        request.setNullSelectionAllowed(false);
        request.setDescription("Выбор договора и (возможно) приложения для последующей загрузки документов");
        request.addStyleName("shadow");
        loadData(request);
        request.setScrollToSelectedItem(true);
        request.setTextInputAllowed(false);
        request.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                //if ((valueChangeEvent != null)) {
                if ((valueChangeEvent != null) && (getParent() != null)) {
                    idDogovor = (BigDecimal) request.getValue();
                    currentIdRequest = hashIdRequests.get(idDogovor);
                    // прописываем новое состояние пользователя (сменил договор)
                    setNewUser(idDogovor);
                    // меняем сосояние трека на новое в соответствии с новым договором
                    DaoDogovors.StateTrack stateTrack = getNewStateDogovor(idDogovor);
                    // выложим StateTrack  в доступ
                    VaadinSession vs = VaadinSession.getCurrent();
                    if (vs != null) {
                        vs.getCurrent().setAttribute("idRequest", currentIdRequest);
                        vs.getCurrent().setAttribute("idDogovor", idDogovor);
                        vs.getCurrent().setAttribute("stateTrack", stateTrack);
                    }

                    if ((stateTrack.getIdTrack() == null) ) {
                        VaadinSession.getCurrent().setAttribute("regreshSlider", true);
                        getUI().getNavigator().navigateTo("resource");    //("request");
                    } else {
                        if (dogovorUpdated != null) {
                            dogovorUpdated.doUpdateView();
                        }
                    }
                }
            }
        });
        if (idDogovor != null) {
            request.setValue(idDogovor);
        } else {
            if (request.size()>0){
                request.setNullSelectionAllowed(false);
                request.setValue(request.getItemIds().iterator().next());
            }
        }
        addComponent(request);
        setComponentAlignment(request, Alignment.TOP_CENTER);


        request.isImmediate();

        setMargin(new MarginInfo(false, false, true, false));

    }

    private DaoDogovors.StateTrack getNewStateDogovor(BigDecimal idDogovor) {
        DaoDogovors daoD = new DaoDogovors(linkDB);
        try {
            DaoDogovors.StateTrack stateTrack = daoD.getTrack(idDogovor);
            return  stateTrack;
        } catch (SQLException e) {
            Notification.show("Ошибка: "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Notification.show("Ошибка: " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        return null;
    }

    private void setNewUser(BigDecimal idDogovor) {
        String webState = idDogovor.toString();
        user.setWebState(webState);
        DaoPregerister daoP = new DaoPregerister(linkDB);
        if (!daoP.setWebState(webState,idUser)){
            Notification.show("Ошибка записи.", Notification.Type.ERROR_MESSAGE);
        }
    }

    public void setDogovorUpdated(DogovorUpdated dogovorUpdated){
        this.dogovorUpdated = dogovorUpdated;
    }

    private void loadData(ComboBox request) {
        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        BeanItemContainer<DogovorDb> biContainer = new BeanItemContainer<DogovorDb>(DogovorDb.class);
        biContainer.addAll(emTu.createQuery("select g from DogovorDb g where g.webId = :idUser").setParameter("idUser", idUser).getResultList());
        for (DogovorDb dog: biContainer.getItemIds()){
            request.addItem(dog.getId());
            hashIdRequests.put(dog.getId(), dog.getIdRequest());
            String sPril = "";
            if ((dog.getNumApp() != null) || (dog.getDtApp() !=null)) {
                sPril = " Приложение:" + ((dog.getNumApp() == null) ?  "-" : dog.getNumApp()) + "/" + ((dog.getDtApp() == null) ?  "-" : simpleDateFormat.format(dog.getDtApp()));
            }
            String sDog = "Договор:"+ dog.getRegNum()+" / "+simpleDateFormat.format(dog.getDtReg());
            request.setItemCaption(dog.getId(), sDog + sPril);
        }
        emTu.close();
    }

    public interface DogovorUpdated {
        void doUpdateView();
    }

    public void notifyUpdateDogovor(){
        BigDecimal newIdDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
        if (idDogovor.intValue() != newIdDogovor.intValue()){
            request.setValue(newIdDogovor);
        }

    }
}

