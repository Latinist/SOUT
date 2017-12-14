package artintech;

import artintech.beans.IComponentContainer;
//import artintech.dao.DaoPregerister;
import artintech.dao.DaoPregerister;
import artintech.dao.DaoRequests;
import artintech.domain.DogovorDb;
import artintech.domain.Requests;
import artintech.domain.User;
import artintech.domain.WebPreregister;
import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
//import com.vaadin.ui.grid;

import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Анатолий on 29.12.2014.
 * Дизайн 2
 *-------------- Форма просмотра договоров и выбора текущего договора--------
 */
public class Request extends VerticalLayout implements IComponentContainer {
//    private VerticalLayout contentPan = new VerticalLayout();
    private JPAContainer<DogovorDb> container;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
    private BigDecimal idDogovor;
    private Grid grid = null;
    private BeanItemContainer<DogovorDb> biContainer = null;
//    private Table table;
//    private Integer idGlobal;
    String linkDB;
    Integer idUser = null;
    User user = null;
//    Button btnSave = new Button("Далее");

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        user   = (User) VaadinSession.getCurrent().getAttribute("user");
        if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
            idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
        }
        setMargin(false);
        addStyleName("Light");
//        setStyleName("DolgiWindow");
        BuildMain();
        setImmediate(true);
    }

    private void BuildMain() {
        VerticalLayout outPanel = new VerticalLayout();
        outPanel.setWidth("95%");
        outPanel.setHeight("600px");
        outPanel.addStyleName("semitransparentlayout");
        outPanel.setMargin(false);
        VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("100%");
        contentOutPanel.setHeightUndefined();

        HorizontalLayout dataPanel = new HorizontalLayout();
        VerticalLayout rPanel = new VerticalLayout();
        VerticalLayout lPanel = new VerticalLayout();
//        contentPan.setMargin(true);
//        Ruler ruler2 = new Ruler();

        BigDecimal bd = new BigDecimal(idUser);
        container = JPAContainerFactory.make(DogovorDb.class, linkDB);
        container.addContainerFilter(new Compare.Equal("webId",idUser));
        container.applyFilters();
        int ii = container.size();

//        table = new Table("Текущие договоры", container){
//            @Override
//            protected String formatPropertyValue(Object rowId, Object colId,
//                                Property property) {
//                Object v = property.getValue();
//                if (v instanceof Date) {
//                    Date dateValue = (Date) v;
//                    return new SimpleDateFormat("dd-MM-yy").format(dateValue);
//                }
//                return super.formatPropertyValue(rowId, colId, property);
//            }
//        };
//        table.setPageLength(0);
//        table.setWidth("100%");
//        table.setHeightUndefined(); // setHeight("100%");
//        table.setSelectable(true);
//        table.setColumnReorderingAllowed(true);
//        table.setColumnCollapsingAllowed(true);
//
//        table.setVisibleColumns(new Object[]{"regNum2","dtReg","numApp","dtApp"});
//        table.setColumnHeaders(new String[]{"Номер", "дата", "Номер", "дата"});
//        table.setFooterVisible(false);
//        table.setImmediate(true);
//        //table.addStyleName(Reindeer.T) //("Light");
//        Object id =  table.firstItemId();
//        table.select(id);
//        Item fItem = table.getItem(id);
//        table.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(final Property.ValueChangeEvent event) {
//
//                Object id=event.getProperty().getValue();
//            }
//        });
//        final Item item = null;

//        btnSave.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent clickEvent) {
//                getUI().getNavigator().navigateTo("loadTools");
//            }
//        });


        VerticalLayout vl0 = new VerticalLayout();
        vl0.setStyleName("greylayout");
        vl0.setCaption("Детали");
        vl0.setWidth("100%");

        GridLayout gridLayout = new GridLayout(2, 9);
        gridLayout.setWidthUndefined();
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        gridLayout.setColumnExpandRatio(0,1);
        gridLayout.setColumnExpandRatio(1,1.6f);

        gridLayout.addComponent(new Label("Номер договора"));
        final Label nomDog = new Label("Отсутствует");
        gridLayout.addComponent(nomDog);
        nomDog.addStyleName("whiteLabel");

        gridLayout.addComponent(new Label("Дата договора"));
        final Label dateDog = new Label("Отсутствует");
        dateDog.addStyleName("whiteLabel");
        gridLayout.addComponent(dateDog);

        gridLayout.addComponent(new Label("№ приложения"));
        final Label nomPril = new Label("Отсутствует");
        nomPril.addStyleName("whiteLabel");
        gridLayout.addComponent(nomPril);

        gridLayout.addComponent(new Label("Дата приложения"));
        final Label datePril = new Label("Отсутствует");
        datePril.addStyleName("whiteLabel");
        gridLayout.addComponent(datePril);

        gridLayout.addComponent(new Label("Название организации"));
        final Label nameOrg = new Label("Отсутствует");
        //nameOrg.setWidth("200px");
        nameOrg.addStyleName("whiteLabel");
        gridLayout.addComponent(nameOrg);

        final ListSelect listSelect = new ListSelect("Адреса мест проведения оценки");
        listSelect.setWidth("400px");
        listSelect.setHeight("100px");
        gridLayout.addComponent(listSelect, 0, 5, 1, 5);

        gridLayout.addComponent(new Label("Ф.И.О."));
        final Label fio = new Label("Отсутствует");
        fio.addStyleName("whiteLabel");
        gridLayout.addComponent(fio);

        gridLayout.addComponent(new Label("E-mail"));
        final Label email = new Label("Отсутствует");
        email.addStyleName("whiteLabel");
        gridLayout.addComponent(email);

        gridLayout.addComponent(new Label("Телефон"));
        final Label phone = new Label("Отсутствует");
        phone.addStyleName("whiteLabel");
        gridLayout.addComponent(phone);

        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkDB)
                .createEntityManager();
        biContainer = new BeanItemContainer<DogovorDb>(DogovorDb.class);
        biContainer.addAll(emTu.createQuery("select g from DogovorDb g where g.webId = :idUser").setParameter("idUser", idUser).getResultList());
        emTu.close();

//        OptionGroup lGrid = new OptionGroup("Текущие договора");
//        for (DogovorDb d : biContainer.getItemIds()) {
//            lGrid.addItem(d);
//            lGrid.setItemCaption(d,"dgdfgfdf");
////            lGrid.setItemCaption(d,d.getNumApp().toString());
//        }


//        GridLayout lGrid = new GridLayout(4,biContainer.size());
//        lGrid.setWidth("100%");
//        lGrid.setHeightUndefined();
//        for (DogovorDb d : biContainer.getItemIds()){
//            lGrid.addComponent(new CheckBox(d.getRegNum().toString()));
//            lGrid.addComponent(new Label(simpleDateFormat.format(d.getDtReg())));
//            if (d.getNumApp() !=null) {
//                lGrid.addComponent(new Label(d.getNumApp().toString()));
//            }else {
//                lGrid.addComponent(new Label(""));
//            }
//            lGrid.addComponent(new Label(simpleDateFormat.format(d.getDtApp())));
//        }


        grid =  new Grid("Текущие договоры",biContainer);
        grid.setWidth("100%");
        grid.setHeight("470px");
        grid.setFooterVisible(false);
        grid.removeAllColumns();
        grid.addColumn("regNum");
        grid.addColumn("dtReg");
        grid.addColumn("numApp");
        grid.addColumn("dtApp");
        grid.setColumnOrder("regNum", "dtReg", "numApp", "dtApp");
        // высота соответствует количеству строк
        //grid.setHeightByRows(biContainer.size());
        //grid.setHeightMode(HeightMode.ROW);
        Grid.Column n1 = grid.getColumn("regNum");
        n1.setHeaderCaption("Номер");
        n1.setSortable(false);
        Grid.Column n2 = grid.getColumn("numApp");
        n2.setHeaderCaption("Номер");
        n2.setSortable(false);
        Grid.Column dt1 = grid.getColumn("dtReg");
        dt1.setRenderer(new DateRenderer("%1$te-%1$tm-%1$ty", Locale.ENGLISH));
        dt1.setHeaderCaption("Дата");
        dt1.setSortable(false);
        Grid.Column dt2 = grid.getColumn("dtApp");
        dt2.setRenderer(new DateRenderer("%1$te-%1$tm-%1$ty", Locale.ENGLISH));
        dt2.setHeaderCaption("Дата");
        dt2.setSortable(false);
        Grid.HeaderRow groupHeader = grid.prependHeaderRow();
        Grid.HeaderCell dogovorCaption = groupHeader.join(groupHeader.getCell("regNum"),
                groupHeader.getCell("dtReg"));
        dogovorCaption.setText("Договор");
        Grid.HeaderCell prilogCaption = groupHeader.join(groupHeader.getCell("numApp"),
                groupHeader.getCell("dtApp"));
        prilogCaption.setText("Приложение");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
//                BeanItem<DogovorDb> item = (BeanItem<DogovorDb>) container.getItem(grid.getSelectedRow());
//                item.getBean().getId();
                DogovorDb itemRow = (DogovorDb) grid.getSelectedRow();
//                DogovorDb item1 = (DogovorDb) selectionEvent.getSelected().toArray()[0];
                if (itemRow == null){return;}
                //VaadinSession.getCurrent().setAttribute("idDogovor",itemRow.getId());
                idDogovor = itemRow.getId();
                VaadinSession.getCurrent().setAttribute("idDogovor", idDogovor);
                BigDecimal idRequest = itemRow.getIdRequest();
                VaadinSession.getCurrent().setAttribute("idRequest", idRequest);
                if (itemRow.getRegNum()!= null) {
                    nomDog.setValue(itemRow.getRegNum());
                } else{
                    nomDog.setValue("Отсутствует");
                }
                if (itemRow.getDtReg() !=null) {
                    dateDog.setValue(simpleDateFormat.format(itemRow.getDtReg()));
                } else{
                    dateDog.setValue("Отсутствует");
                }
                if (itemRow.getNumApp() !=null) {
                    nomPril.setValue(itemRow.getNumApp());
                }else{
                    nomPril.setValue("Отсутствует");
                }
                if(itemRow.getDtApp()!=null) {
                    datePril.setValue(simpleDateFormat.format(itemRow.getDtApp()));
                } else {
                    datePril.setValue("Отсутствует");
                }
                if(itemRow.getFullName() !=null) {
                    nameOrg.setValue(itemRow.getFullName());
                } else {
                    nameOrg.setValue("Отсутствует");
                }
                String adr = itemRow.getAddress();
                if (adr != null){
                    CSVParser parser =  new CSVParser();
                    if (adr != null) {
                        listSelect.removeAllItems();
                        listSelect.setNullSelectionAllowed(false);
                        try {
                            //adr = "\"203652,Орел,\"\"ул. Ленина\"\",\"\"дом. 22\"\",\",\"500231,Тула,Рабочая,1,\",\"100999,Выкша,Профсоюзная,15/2,1\"";
                            String[] ff = parser.parseLineMulti(adr);
                            for (String f : ff) {
                                String[] ff1 = parser.parseLine(f);
                                String ss = ""; String sep="";
                                for (String ff2 : ff1) {
                                    ss = ss+sep+ff2;
                                    sep = ",";
                                }
                                //ss.substring(1,ss.length()-1);
                                listSelect.addItem(ss);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        listSelect.removeAllItems();
//                        listSelect.setNullSelectionAllowed(false);
//                        listSelect.addItems(arr);
                        listSelect.setImmediate(true);
                    }
                }

                String contacts = itemRow.getContact();
                try {
                    setFIO(contacts, 0, fio, email, phone);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                fio.setValue("Отсутствует");
//                email.setValue("Отсутствует");
//                phone.setValue("Отсутствует");
//                if (contact != null) {
//                    contact = contact.substring(1, contact.length() - 1);
//                    if (contact != null) {
//                        String[] arr = contact.split("\",\"");
//                        String[] currFio = arr[0].split(",");
//                        if (currFio.length > 0) {
//                            fio.setValue(currFio[0].trim().equals("") ? "Отсутствует" : currFio[0]);
//                        }
//                        if (currFio.length > 1) {
//                            email.setValue(currFio[1].trim().equals("") ? "Отсутствует" : currFio[1]);
//                        }
//                        if (currFio.length > 2) {
//                            phone.setValue(currFio[2].trim().equals("") ? "Отсутствует" : currFio[2]);
//                        }
//                    }
//                }


                //fio.setValue(itemRow.getContact());

            }
        });
        boolean okok = false;
        for (DogovorDb item : biContainer.getItemIds()){
            if (item.getId().intValue() == idDogovor.intValue()){
                grid.select(item);
                okok = true;
            }
        }
        if (!okok) {
            grid.select(biContainer.getIdByIndex(0));
        }

        listSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                int numrec =  0;
                for (Iterator i = listSelect.getItemIds().iterator(); i.hasNext();){
                    Object iid = i.next();
                    if (listSelect.isSelected(iid)){
                        break;
                    }
                    numrec++;
                }
                DogovorDb itemRow = (DogovorDb) grid.getSelectedRow();
                String contacts = itemRow.getContact();
                try {
                    setFIO(contacts, numrec, fio, email, phone);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });










        gridLayout.addStyleName("shadow");

        vl0.addComponent(gridLayout);

//        HorizontalLayout buttons = new HorizontalLayout();
//        btnSave.setWidth("150px");
//        buttons.addComponent(btnSave);
//        buttons.setWidthUndefined();

        dataPanel.setWidthUndefined(); // setWidth("100%");
        dataPanel.addComponent(lPanel);
        dataPanel.addComponent(rPanel);
        lPanel.setWidth("400px");
        lPanel.setMargin(true);
        //rPanel.setWidthUndefined(); // setWidth("500px");
        rPanel.setMargin(true);

//        lPanel.addComponent(table);
//        lPanel.addComponent(lGrid);
        grid.addStyleName("shadow");
        lPanel.addComponent(grid);

        rPanel.addComponent(vl0);
        vl0.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);

//        rPanel.addComponent(ruler2);
//        rPanel.addComponent(buttons);
//        buttons.setMargin(true);
//        rPanel.setComponentAlignment(buttons, Alignment.TOP_CENTER);
        rPanel.setWidth("100%"); // setSizeFull();
        rPanel.setHeightUndefined();


        contentOutPanel.addComponent(dataPanel);
        contentOutPanel.setComponentAlignment(dataPanel, Alignment.MIDDLE_CENTER);


        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);

//        VerticalLayout prokladka3 = new VerticalLayout();
//        prokladka3.setHeight("10px");
//        addComponent(prokladka3);

//        Label podval = new Label("<p align=\"center\">Eсли Вы уже выбрали интересующий Вас договор - нажмите кнопку далее.</p>",ContentMode.HTML);
//        podval.setWidth("80%");
//        addComponent(podval);
//        setComponentAlignment(podval, Alignment.TOP_CENTER);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);
//        addComponent(buttons);
//        setComponentAlignment(buttons, Alignment.TOP_CENTER);

        //-----------------------------------------
        FactoryTrains factoryTrains =  new FactoryTrains();
//        HorizontalLayout podballogo = (HorizontalLayout) factoryTrains.makeTrain(new String[]{"Договоры", "Список документов","Сервис","Загрузка"},1);
//        addComponent(podballogo);
//        setComponentAlignment(podballogo,Alignment.TOP_CENTER);
        //-----------------------------------------

        setImmediate(true);

    }

    private void setFIO(String contacts, int i, Label fio, Label email, Label phone) throws IOException {
        fio.setValue("Отсутствует");
        email.setValue("Отсутствует");
        phone.setValue("Отсутствует");

        if (contacts != null) {
            CSVParser parser =  new CSVParser();
            String[] ff = parser.parseLineMulti(contacts);
            String ff1 = ff[i];
            String[] currFio = parser.parseLine(ff1);

            if (currFio.length > 0) {
                fio.setValue(currFio[0].trim().equals("") ? "Отсутствует" : currFio[0]);
            }
            if (currFio.length > 1) {
                email.setValue(currFio[1].trim().equals("") ? "Отсутствует" : currFio[1]);
            }
            if (currFio.length > 2) {
                phone.setValue(currFio[2].trim().equals("") ? "Отсутствует" : currFio[2]);
            }
        }
    }

    public void notifyUpdate(){
        BigDecimal newIdDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
        if (idDogovor.intValue() != newIdDogovor.intValue()){
            for (DogovorDb item : biContainer.getItemIds()){
                if (item.getId().intValue() == newIdDogovor.intValue()){
                    grid.select(item);
                    idDogovor = newIdDogovor;
                }
            }
        }

    }

}
