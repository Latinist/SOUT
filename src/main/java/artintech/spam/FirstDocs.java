package artintech.spam;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoRequests;
import artintech.spam.Dogovors;
import artintech.spam.ExportDoc;
import artintech.spam.UploadDocsBref;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by Анатолий on 29.01.2015.
 * Дизайн 1
 *--------- форма с закладками - устарела-------------
 */
public class FirstDocs extends VerticalLayout implements IComponentContainer {
    private Integer idGlobal = null;
    private TabSheet masterT;
    private ExportDoc exportDoc;
    private Dogovors dogovors;
    private UploadDocsBref uploadDocs;
    private NativeSelect requests;
    private Integer idUser = null;
    private String linkDB;
    private BigDecimal currentIdRequest = null;

    public FirstDocs(){

    };
    @Override
    public void init() {
        setMargin(true);
        setHeight("100%");
        setSizeFull();
        setStyleName("LispRaWindow");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        requests = new NativeSelect("Ваша заявка");
        requests.setWidth("330px");

        initChoiceRequest();

        exportDoc = new ExportDoc();
        exportDoc.init(currentIdRequest);
//        uploadDocs = new UploadDocs();
        uploadDocs = new UploadDocsBref();
        uploadDocs.init(currentIdRequest);
        dogovors = new Dogovors();
        dogovors.init();

        masterT = new TabSheet();
        masterT.addStyleName(ValoTheme.TABSHEET_FRAMED);
        masterT.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        masterT.addTab(new VerticalLayout(dogovors), "Договор");
        masterT.addTab(new VerticalLayout(uploadDocs), "Документы");
        masterT.addTab(exportDoc,"Импорт");
        masterT.setSelectedTab(0);

        FormLayout fl = new FormLayout();
        fl.setWidth("100%");
        fl.addComponent(requests);
        fl.setComponentAlignment(requests, Alignment.MIDDLE_CENTER);
        addComponent(fl);
        addComponent(masterT);
        isImmediate();
    }

    private void initChoiceRequest() {
        DaoRequests daoRequests = new DaoRequests(linkDB);
        Container container = daoRequests.getRequests(idUser);
        BigDecimal firstId =  null;
        if (container.size()>0){
            Collection<BigDecimal> idReqs = (Collection<BigDecimal>) container.getItemIds();
            firstId = idReqs.iterator().next();
            for (BigDecimal id : idReqs){
                Item item = (Item) container.getItem(id);
                requests.addItem(id);
                requests.setItemCaption(id, (String) item.getItemProperty("nameOrg").getValue());
            }
            requests.setNullSelectionAllowed(false);
            requests.setValue(firstId);
            currentIdRequest = firstId;
        }
        requests.setImmediate(true);
        requests.addValueChangeListener(new Property.ValueChangeListener(){
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty().getValue() != null){
                    currentIdRequest = (BigDecimal) event.getProperty().getValue();
                }
            }
        });
    }
}
