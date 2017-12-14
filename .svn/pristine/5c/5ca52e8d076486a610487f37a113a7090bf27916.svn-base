package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoPregerister;
import artintech.domain.WebPreregister;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 20.01.2015.
 */
public class Kabinet extends VerticalLayout implements IComponentContainer {
    private Panel pan = new Panel();
    private VerticalLayout contentPan = new VerticalLayout();
    private Button btnChangePassword = new Button("Смена пароля");
    private FieldGroup fieldGroup = new FieldGroup();
    private BeanItemContainer<WebPreregister> biContainer;
    private BeanItem item;
    private Integer idUser;
    private String linkDB;
    private HorizontalLayout tools = new HorizontalLayout();
    private Integer idGlobal = null;

    public Kabinet(){};
    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idUser = (Integer) VaadinSession.getCurrent().getAttribute("iduser");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        addStyleName("Light");
//        mailPropertyes = (MailPropertyes) VaadinSession.getCurrent().getAttribute("mailPropertyes");

        VerticalLayout outPanel = new VerticalLayout();
        outPanel.setWidth("95%");
        outPanel.setHeight("600px");
        outPanel.addStyleName("semitransparentlayout");
        outPanel.setMargin(true);
        VerticalLayout contentOutPanel = new VerticalLayout();
        contentOutPanel.setWidth("100%");
        contentOutPanel.setHeightUndefined();



        String s ="На этой форме Вы можете откорректировать свои контактные данные и поменять пароль, при необходимости. ";
        Label header = new Label("<b><h3><p align=\"center\">"+s+"</p></h1><b>", ContentMode.HTML);
        header.setWidth("60%");
        btnChangePassword.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //------------ создадим модальное окно-----------------------------
                String login = (String) fieldGroup.getField("login").getValue();
                //String password = (String) fieldGroup.getField("login").getValue();
                String password = "";
                final Integer timeout = (Integer) VaadinSession.getCurrent().getAttribute("timeout");

                final String eMail =  (String) fieldGroup.getField("email").getValue();
                MyLoginWindow logSubWindow = new MyLoginWindow(login,password,
                        new MyLoginWindow.LoginRecipient() {

                            @Override
                            public Boolean testLogin(String input) {
                                DaoPregerister dao = new DaoPregerister(linkDB);
                                return (dao.isLoginfree( eMail, input, timeout));
/*
                                EntityManager em1 = Persistence
                                        .createEntityManagerFactory(linkDB)
                                        .createEntityManager();
                                Collection emps1 = em1.createQuery("SELECT g FROM WebPlat g WHERE g.login = :login").setParameter("login", input).setMaxResults(1).getResultList();
                                Boolean loginExists = !emps1.isEmpty();
                                em1.close();
                                if (loginExists) {
                                    //throw new UnsupportedOperationException("Not supported yet.");
                                    return false;
                                } else {
                                    return true;
                                }
*/
                            }

                            @Override
                            public void gotInput(String login,String password) {
                                DaoPregerister dao = new DaoPregerister(linkDB);
                                if (dao.changeLoginPassword(idUser, login, password)){
                                    TextField field = (TextField) fieldGroup.getField("login");
                                    field.setReadOnly(false); //Это поле только на чтение
                                    field.setValue(login);
                                    field.setReadOnly(true);
                                    //field = (TextField) fieldGroup.getField("password");
                                    //field.setValue(password);
                                    Notification.show("Ваши новые логин и пароль успешно сохранены");
                                }else{
                                    Notification.show("Ошибка при сохранении данных",Notification.Type.ERROR_MESSAGE);
                                }

                            /*
                                lb2.setValue(login);
                                EntityManager em2 = Persistence
                                        .createEntityManagerFactory(linkRes)
                                        .createEntityManager();
                                Collection emps2 = em2.createQuery("SELECT g FROM WebPlat g WHERE g.idPlat = :idplat").setParameter("idplat", idplat).setMaxResults(1).getResultList();
                                Boolean platexists = !emps2.isEmpty();
                                if (platexists) {
                                    WebPlat e2 = (WebPlat) emps2.iterator().next();
                                    em2.getTransaction().begin();
                                    e2.setLogin(login);
                                    e2.setPw(pasword);
                                    em2.persist(e2);
                                    em2.getTransaction().commit();
                                }
                                em2.close();
                                //throw new UnsupportedOperationException("Not supported yet.");
                             */
                            }
                        }
                );
                //-------------------- активизируем окно ------------------------
                //getWindow().addWindow(logSubWindow);
                getUI().addWindow(logSubWindow);








            }
        });
        fieldGroup.setReadOnly(true);
        final Button reset = new Button("Отмена");
        reset.setWidth("130px");
        reset.setEnabled(false);
        reset.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (fieldGroup.isModified())
                    fieldGroup.discard();
            }
        });
        final Button save = new Button("Сохранить");
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                DaoPregerister dao = new DaoPregerister(linkDB);
                if (!fieldGroup.isValid()){
                    Notification.show("Поля формы заполнены неверно",Notification.Type.WARNING_MESSAGE);
                } else if (fieldGroup.isModified()) {
                    try {
                        fieldGroup.commit();
                        if (dao.updatePregister((WebPreregister) item.getBean()))
                            Notification.show("Ваши изменения успешно сохранены");
                        else
                            Notification.show("Ваши изменения не сохранены", Notification.Type.ERROR_MESSAGE);
                    } catch (FieldGroup.CommitException e) {
                        Notification.show("Ошибка при сохранении данных", Notification.Type.ERROR_MESSAGE);
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при сохранении данных");
                        e.printStackTrace();
                    }
                }
            }
        });
        save.setWidth("130px");
        save.setEnabled(false);

        final Button edit = new Button("Исправить");
        edit.setWidth("130px");
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (fieldGroup.isModified()){
                    Notification.show("Вы не сохранили внесенные изменения.",Notification.Type.WARNING_MESSAGE);
                } else {
                    fieldGroup.setReadOnly(!fieldGroup.isReadOnly());
                    fieldGroup.getField("email").setReadOnly(true);
                    fieldGroup.getField("login").setReadOnly(true);
                    edit.setCaption(fieldGroup.isReadOnly() ? "Исправить" : "Просмотр");
                    save.setEnabled(!fieldGroup.isReadOnly());
                    reset.setEnabled(!fieldGroup.isReadOnly());
                }
            }
        });


        tools.addComponent(reset);
        tools.addComponent(edit);
        tools.addComponent(save);


        TextField nameOrg = new TextField ("Название организации:");
        nameOrg.setStyleName("fieldstyle");
        TextField eMail = new TextField ("Ваш EMail:");
        eMail.setStyleName("fieldstyle");
        eMail.setReadOnly(true);
        TextField kontaktTel = new TextField ("Контактный телефон:");
        kontaktTel.setStyleName("fieldstyle");
        TextField login = new TextField ("Логин:");
        login.setStyleName("fieldstyle");
        DaoPregerister dao = new DaoPregerister(linkDB);
        biContainer = new BeanItemContainer<WebPreregister>(WebPreregister.class);
        WebPreregister pojo = dao.getPreregistr(idUser);
        item =biContainer.addBean(dao.getPreregistr(idUser));
        fieldGroup.setItemDataSource(item);
        fieldGroup.bind(nameOrg, "nameOrg");
        fieldGroup.bind(eMail,"email");
        fieldGroup.bind(kontaktTel,"telephone");
        fieldGroup.bind(login,"login");
//        fieldGroup.bind(password,"password");
        fieldGroup.setBuffered(true);
        initEditor();

        FormLayout fl = new FormLayout();
        fl.addComponent(login);
        fl.addComponent(eMail);
        fl.addComponent(nameOrg);
        fl.addComponent(kontaktTel);
        fl.setWidthUndefined();

        HorizontalLayout buttons = new HorizontalLayout();
        btnChangePassword.setWidth("150px");
        buttons.addComponent(btnChangePassword);
        buttons.setWidthUndefined();

//        addComponent(new SeparatorL("Учетные данные пользователя"));
        pan.setWidth("500px");
        pan.setHeight("400px");
        pan.addStyleName(Reindeer.PANEL_LIGHT);
        pan.addStyleName("greylayout");
        pan.addStyleName("shadow");
        pan.addStyleName("paddingFull");

//        Ruler ruler1 = new Ruler();
//        Ruler ruler2 = new Ruler();

        contentPan.setMargin(true);
        contentPan.addComponent(fl);
        contentPan.setComponentAlignment(fl, Alignment.TOP_CENTER);
        contentPan.addComponent(new Ruler());
        contentPan.addComponent(tools);
        contentPan.setComponentAlignment(tools, Alignment.MIDDLE_CENTER);
        contentPan.addComponent(new Ruler());
        contentPan.addComponent(buttons);
        contentPan.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        pan.setContent(contentPan);

        //contentPan.setStyleName("greylayout");
        //addComponent(new SeparatorL("При необходимости, нажмите кнопку \"Смена пароля\""));
//        addComponent(new SeparatorL("...."));

        //setSizeFull();


        contentOutPanel.addComponent(header);
        contentOutPanel.setComponentAlignment(header, Alignment.TOP_CENTER);
        contentOutPanel.addComponent(pan);
        contentOutPanel.setComponentAlignment(pan, Alignment.TOP_CENTER);

        outPanel.addComponent(contentOutPanel);
        addComponent(outPanel);
        setComponentAlignment(outPanel, Alignment.TOP_CENTER);

        setImmediate(true);

    }

    private void initEditor() {
        Property.ValueChangeListener listner = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                // пустой обработчик, но без него не идут проверки на валидность
            }
        };

        Field field;
        for (Object propertyId : fieldGroup.getBoundPropertyIds()) {
            Field f = (fieldGroup.getField(propertyId));
            if (f instanceof TextField) ((TextField) f).setNullRepresentation("");
        }
        fieldGroup.getField("nameOrg").focus();

        field =fieldGroup.getField("email");
        field.setReadOnly(true);

        field = fieldGroup.getField("login");
        field.setReadOnly(true);
        //field.addValidator(new StringLengthValidator("Длина строки должна быть от 3 до 30 символов",3,30,false));
        //field.addValueChangeListener(listner);

    }

}
