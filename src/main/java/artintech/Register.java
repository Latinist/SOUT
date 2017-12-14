package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoPregerister;
//import artintech.domain.MailPropertyes;
import artintech.domain.WebPreregister;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.xml.bind.ValidationEvent;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 05.01.2015.
 */
public class Register extends VerticalLayout implements IComponentContainer {
    private Panel pan = new Panel();
    private VerticalLayout contentPan = new VerticalLayout();
    private Button btnRegistr = new Button("Регистрация");
    private FieldGroup fieldGroup = new FieldGroup();
    private BeanItemContainer<WebPreregister> biContainer;
    private BeanItem item;
    private String linkDB;
    private Integer timeout;
    private Integer idGlobal;
//    private MailPropertyes mailPropertyes;

    @Override
    public void init() {
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        timeout = (Integer) VaadinSession.getCurrent().getAttribute("timeout");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
//        mailPropertyes = (MailPropertyes) VaadinSession.getCurrent().getAttribute("mailPropertyes");

        String s ="<h1 align=\"center\">\"На этой форме Вы можете ввести свои контактные данные и зарегистрироваться на ресурсе.</h1> ";
        s=s+"После заполнения учетной формы  и нажатия кнопки \"Регистрация\" Вам будет выслано почтовое сообщение на вашу почту. ";
        s=s+"В сообщении будет указана ссылка, перейдя по которой, вы активизируете свою учетную запись.";
        Label header = new Label("<b><p align=\"center\">"+s+"</p><b>",ContentMode.HTML);
        btnRegistr.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                getUI().getNavigator().navigateTo("request");
//                sendMail("tomat56@mail.ru", "dfgf----------hfghd");

                if (makeRegister()){
                    getUI().getNavigator().navigateTo("");                                      }
            }
        });

        contentPan.setMargin(true);
        contentPan.setStyleName("greylayout");
        //contentPan.addComponent(btnRegistr);
        Ruler ruler1 = new Ruler();
        Ruler ruler2 = new Ruler();


        TextField nameOrg = new TextField ("Название организации");
        TextField eMail = new TextField ("Ваш EMail");
        TextField kontaktTel = new TextField ("Контактный телефон");
        TextField login = new TextField ("Логин");
        PasswordField password = new PasswordField ( "Пароль");
        PasswordField passwordControl = new PasswordField ( "Подтвердите пароль");

        biContainer = new BeanItemContainer<WebPreregister>(WebPreregister.class);
        item = biContainer.addBean(new WebPreregister());
        Property hh = item.getItemProperty("idGlobal");
        item.getItemProperty("idGlobal").setValue(new BigDecimal(idGlobal));
        fieldGroup.setItemDataSource(item);
        fieldGroup.bind(nameOrg, "nameOrg");
        fieldGroup.bind(eMail,"email");
        fieldGroup.bind(kontaktTel,"telephone");
        fieldGroup.bind(login,"login");
//        fieldGroup.bind(password,"password");
        fieldGroup.bind(password,"passwordOriginal");
        fieldGroup.bind(passwordControl, "passwordControl");
        fieldGroup.setBuffered(false);
        initEditor();

        FormLayout fl = new FormLayout();
        fl.addComponent(nameOrg);
        fl.addComponent(eMail);
        fl.addComponent(kontaktTel);
        fl.addComponent(login);
        fl.addComponent(password);
        fl.addComponent(passwordControl);
        fl.setWidthUndefined();

        HorizontalLayout buttons = new HorizontalLayout();
        btnRegistr.setWidth("150px");
        buttons.addComponent(btnRegistr);
        buttons.setWidthUndefined();

        addComponent(header);
        addComponent(new SeparatorL("Предварительные учетные данные пользователя"));
        contentPan.addComponent(fl);
        contentPan.setComponentAlignment(fl, Alignment.TOP_CENTER);
        contentPan.addComponent(ruler2);
        addComponent(contentPan);
        addComponent(new SeparatorL("После заполнения формы нажмите кнопку \"Регистрация\""));
        addComponent(buttons);
        setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        addComponent(new SeparatorL("...."));

/*
        pan = new Panel("Введите ваш логин и пароль");
        pan.setWidth("410px");
        pan.setHeight("320px");

        pan.setContent(contentPan);
        pan.setStyleName("loginpanel");

        addStyleName("login");
        addComponent(pan);
        setComponentAlignment(pan, Alignment.TOP_CENTER);
*/
        setSizeFull();
        if (idGlobal == null){
            Notification.show("Неправильный вход в ресурс. Первоначальный вход должен осуществляться через ресурс экспертной организации");
            btnRegistr.setEnabled(false);
        }
        setImmediate(true);
    }

    private Boolean makeRegister() {
//        fieldGroup.bind(nameOrg, "nameOrg");
//        fieldGroup.bind(eMail,"email");
//        fieldGroup.bind(kontaktTel,"telephone");
//        fieldGroup.bind(login,"login");
//        fieldGroup.bind(password,"password");
//        fieldGroup.bind(passwordControl, "passwordControl");
        Boolean ret = false;
        if (fieldGroup.isValid()) {
            String p = item.getItemProperty("passwordOriginal").getValue().toString();
            String p1 = item.getItemProperty("passwordControl").getValue().toString();
            if (!p.equals(p1)) {
                Notification.show("Пароль введен неверно.");
            } else {
                ret = true;
                Integer retCod = saveToDB();
                switch (retCod){
                    case 0: Notification.show("Ваша учетная запись успешно создана. Для того чтобы ее активизировать, примите почтовое сообщение и перейдите по ссылке указанной с сообщении.");
                            break;
                    case 1: Notification.show("Потребитель с таким почтовым адресом уже зарегистрирован.", Notification.Type.ERROR_MESSAGE);
                        break;
                    case 2: Notification.show("Вам выслан дубликат письма на вашу почту.");
                        break;
                    case 3: Notification.show("Такой логин уже зарегистрирован на ресурсе.",Notification.Type.ERROR_MESSAGE);
                        break;
                }
            }
        } else
            Notification.show("Форма заполнена неверно.");
        return ret;
    }

    private Integer saveToDB() {
        DaoPregerister dao = new DaoPregerister(linkDB);
        Integer ret=0;
        //запись в базу данных
        String login = item.getItemProperty("login").getValue().toString();
        String email = item.getItemProperty("email").getValue().toString();
        BigDecimal ii = (BigDecimal) item.getItemProperty("idGlobal").getValue();
        Integer idGlobal = ii.toBigInteger().intValue();

        if (dao.isRegister(email,idGlobal)){
            //ret="Потребитель с таким почтовым адресом уже зарегистрирован";
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Пользователь с таким почтовым адресом уже зарегистрирован: (" + email + ")");
            ret = 1;
        } else{
            if (dao.inStateRegister(email,idGlobal, timeout)){
                /* пользователь в состоянии регистрации. Ему отправлено мыло но ответа еще нет */
                //  пользователь есть но его время регистрации не истекло. Отправим ему почту повторно и обновим время регистрации
                String url = dao.makeFinishPregister(email, idGlobal);

                try {
                    sendMail(email, url);
                } catch (Exception e) {
//                    e.printStackTrace();
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при отправке почты. ", e);
                    Notification.show("Ошибка при оправке почтового сообщения. " + e.getMessage().toString());
                }
                ret = 2;
            } else {
                 if (dao.isLoginfree(email, login, timeout)){
                    //Регистрируем пользователя
                    String url = getRandomString();
                    WebPreregister ss = (WebPreregister) item.getBean();
                    ss.setDateContact(new Date());
                    try {
                        sendMail(email, url);
                        ss.setUrlTmp(url);
                    } catch (Exception e ){
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Ошибка при отправке почты. ", e);
                        Notification.show("Ошибка при оправке почтового сообщения. " + e.getMessage().toString());
                    }

                    dao.makePregister(ss);
                } else {
                    // логин захвачен
                    ret = 3;
                }
            }
        }
        if (ret.equals(0)){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Учетная запись успешно создана: (" + email+")");
        }
        return ret;
    }

    private void sendMail(String toEmail, String randomStr) throws Exception {
        String bodyMessage = null;
        String themes ="Регистрация на ресурсе СОУТ";
        MailerSSL mailer = new MailerSSL();
        //Mailer mailer = new Mailer();

        String letterFileName = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/letter.txt";
        File fletter = new File(letterFileName);

        try {
//            bodyMessage = new String(Files.readAllBytes(Paths.get(letterFileName)));
            bodyMessage = new Scanner(fletter,"UTF-8").useDelimiter("\\Z").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // сформируем url
        String myURL = (String) VaadinSession.getCurrent().getAttribute("myURL");
//        String url = myURL +"search?id="+randomStr;
        String url = null;
        if (myURL.contains("?")) {
            url = myURL + "&id=" + randomStr;
        } else {
            url = myURL +"?id="+randomStr;

        }
        bodyMessage = bodyMessage.replaceAll("<ref>",url);

        //String pt = fsetup.getCanonicalPath();
//        InputStream inputStream;
//        if(fletter.exists()) {
//            inputStream = new FileInputStream(fsetup);
//        } else {
//            throw new FileNotFoundException("Файл "+ pt + "не найден");
//        }

        try {
            mailer.sendSimpleMail(toEmail,themes,bodyMessage);
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Почтовое сообщение отправлено на адрес: " + url);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private String getRandomString(){
        String symbols = "qwertyuiopasdfghjklzxcvbnm1234567890";
        StringBuilder randString = new StringBuilder();
        int count = (int)(Math.random()*30);
        for(int i=0;i<count;i++)
            randString.append(symbols.charAt((int)(Math.random()*symbols.length())));
        return randString.toString();
    }    
/*
        EntityManager em = Persistence
        .createEntityManagerFactory(linkDB)
                .createEntityManager();
        emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.email = :email and g.dateRegisters is not null").setParameter("email", email).setMaxResults(1).getResultList();
        if (!emps.isEmpty()){
           ret="Потребитель с таким почтовым адресом уже зарегистрирован";
           // ничего делать не нужно
           emps.clear();
        } else {
            GregorianCalendar c = new GregorianCalendar();//календарь на текущую дату
            c.add(GregorianCalendar.HOUR, -5);
            Date waitdate = c.getTime();
            emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.email = :email and g.dateRegisters is null and g.DateContact < :waitdate"  ).setParameter("email", email).setParameter("waitdate",waitdate).setMaxResults(1).getResultList();
            if (!emps.isEmpty()){
                //  пользователь есть но его время регистрации не истекло. Отправим ему почту повторно и обновим время регистрации
                emps.clear();
            } else{
                emps = em.createQuery("SELECT g FROM  WebPreregister g WHERE g.login = :login and g.dateRegisters is not null and g.email<>:email and g.DateContact < :waitdate"  ).setParameter("email", email).setParameter("waitdate",waitdate).setMaxResults(1).getResultList();

            }

        }

        item.getItemProperty("urlTmp").setValue("fghldfhldfgkh");
        WebPreregister ss = (WebPreregister) item.getBean();
        ss.setDateContact(new Date());
        ss.setUrlTmp("hrfhhthdfssb");

        em.getTransaction().begin();
        em.persist(ss);
        em.getTransaction().commit();
        em.close();
        //sendEmail();
//        Transport.send(message);
*/

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
        field.addValidator(new NullValidator("Нет почтового адреса.", false));
        field.addValidator(new EmailValidator("Неверный формат почтового адреса"));
        field.addValueChangeListener(listner);

        field = fieldGroup.getField("login");
        field.addValidator(new StringLengthValidator("Длина строки должна быть от 3 до 30 символов",3,30,false));
        field.addValueChangeListener(listner);

        field = fieldGroup.getField("passwordOriginal");
        field.addValidator(new StringLengthValidator("Длина строки должна быть от 7 до 30 символов",7,30,false));
        field.addValueChangeListener(listner);

        field = fieldGroup.getField("passwordControl");
        field.addValidator(new StringLengthValidator("Длина строки должна быть от 7 до 30 символов",7,30,false));
        field.addValueChangeListener(listner);
    }

    @Override
    public void forEach(Consumer<? super Component> action) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
