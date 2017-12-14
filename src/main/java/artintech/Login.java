package artintech;

import artintech.beans.IComponentContainer;
import artintech.dao.DaoDogovors;
import artintech.dao.DaoPregerister;
import artintech.domain.User;
import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 29.12.14
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */

public class Login extends VerticalLayout implements IComponentContainer {
    private Panel pan = new Panel();
    private VerticalLayout contentPan = new VerticalLayout();
    private Button btnLogin = new Button("Вход");
    private TextField login = new TextField ( "Логин");
    private PasswordField password = new PasswordField ( "Пароль");
    private String linkDB;
    private Integer idGlobal = null;

    @Override
    public void init() {
        //setWidth("100%");
        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        idGlobal = (Integer) VaadinSession.getCurrent().getAttribute("idglobal");
        String phone = "";
        DaoPregerister dao = new DaoPregerister(linkDB);
        try {
            phone = dao.getPhoneExpOrg(idGlobal);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnLogin.addStyleName("shadow");
        btnLogin.setWidth("100px");
        btnLogin.addClickListener(new Button.ClickListener(){
                                      @Override
                                      public void buttonClick(Button.ClickEvent clickEvent) {
                                          if (login.isValid() & password.isValid()){
                                              User user = getIduser();
                                              if (user != null){
                                                  Integer idUser = user.getId();
                                                  String sidDogovor = user.getWebState();
                                                  //if (idUser != null) {
                                                  VaadinSession.getCurrent().setAttribute("iduser", idUser);
                                                  VaadinSession.getCurrent().setAttribute("user", user);
                                                  BigDecimal idDogovor = ((sidDogovor != null) && (!sidDogovor.equals("")))? BigDecimal.valueOf(Long.parseLong(sidDogovor)) : null;
                                                  if (idDogovor == null){
                                                      DaoDogovors daoD = new DaoDogovors(linkDB);
                                                      idDogovor = daoD.getFirstDogovor(idUser);
                                                  }
                                                   DaoDogovors.StateTrack stateTrack = null;
                                                   BigDecimal idRequest = getIdRequests(idUser);
                                                   if (idDogovor != null) {
                                                       // прописываем новое состояние пользователя (сменил договор)
                                                       String webState = idDogovor.toString();
                                                       user.setWebState(webState);
                                                       DaoPregerister daoP = new DaoPregerister(linkDB);
                                                       if (!daoP.setWebState(webState,idUser)){
                                                           Notification.show("Ошибка записи.", Notification.Type.ERROR_MESSAGE);
                                                       }
                                                       // меняем сосояние трека на новое в соответствии с новым договором
                                                       stateTrack = getNewStateDogovor(idDogovor);
                                                       // выложим StateTrack  в доступ
                                                       VaadinSession vs = VaadinSession.getCurrent();
                                                       if (vs != null) {
                                                           vs.getCurrent().setAttribute("idRequest", idRequest);
                                                           vs.getCurrent().setAttribute("idDogovor", idDogovor);
                                                           vs.getCurrent().setAttribute("stateTrack", stateTrack);
                                                       }

                                                   } else{

                                                   }
                                                      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Пользователь " + idUser.toString() + " вошел в ресурс (" + user.geteMail() + ")");
                                                      if ((stateTrack == null) || (stateTrack.getIdTrack() == null) ){
                                                          getUI().getNavigator().navigateTo("resource");    //("request");
                                                      } else {
                                                          getUI().getNavigator().navigateTo("track");    //("request");
                                                  //    }
                                                  //} else {
                                                  //    Notification.show("Пароль или логин ведены неверно.", Notification.Type.ERROR_MESSAGE);
                                                  }
                                              } else
                                                  Notification.show("Пароль или логин ведены неверно.", Notification.Type.ERROR_MESSAGE);

                                          }
                                      }
                                  }
        );

        contentPan.setMargin(false);
        contentPan.setWidth("100%");
        //contentPan.addComponent(btnLogin);
        Property.ValueChangeListener listner = new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                // пустой обработчик, но без него не идут проверки на валидность
            }
        };


//        Ruler ruler1 = new Ruler();
//        Ruler ruler2 = new Ruler();

        FormLayout fl = new FormLayout();
        login.focus();
        //    login.addStyleName("greylayou");
        //   password.addStyleName("greylayou");
        //-----------------------------------------------------------------
        HorizontalLayout buttons = new HorizontalLayout();
        //btnLogin.setWidth("210px");
        //btnLogin.addStyleName(ValoTheme.BUTTON_HUGE);
        //-----------------------------------------------------------------
//       Label lvelkome = new Label("<h3 align=\"center\">Если вы хотите получить информацию по СОУТ или коммерческое предложение - звоните по телефону: +7(495) 888-88-88." +
         Label lvelkome = new Label("<h3 align=\"center\">Если Вы хотите получить информацию по СОУТ или коммерческое предложение, звоните по телефону "+ phone +
                ". Если Вы уже заключили договор на проведение работ по СОУТ, введите свой логин и пароль.</h1>", ContentMode.HTML);
        lvelkome.setWidth("70%");
        //lvelkome.setStyleName("h3");
        addComponent(lvelkome);
        setComponentAlignment(lvelkome, Alignment.TOP_CENTER);
        //        addComponent(new SeparatorL("Введите логин и пароль."));
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        //Label lvelkome1 = new Label("Если вы уже заключили договор на проведение работ по СОУТ, пожалуйста введите свой логин и пароль.");
        //lvelkome1.setWidthUndefined();
        //lvelkome1.addStyleName("h2");
        //addComponent(lvelkome1);
        //setComponentAlignment(lvelkome1, Alignment.TOP_CENTER);
        //------------------------------------------------------------------------
        HorizontalLayout layoutImage = new HorizontalLayout();
        layoutImage.setSizeFull();
//        final ThemeResource HANDS = new ThemeResource("img/hands.png");
//        final ThemeResource CENTERIMAGE = new ThemeResource("img/ekspert.jpg");
//        final ThemeResource CENTERIMAGE = new ThemeResource("img/bookglass.jpg");
//        final ThemeResource CENTERIMAGE = new ThemeResource("img/leaves.jpg");
//        final ThemeResource CENTERIMAGE = new ThemeResource("img/microscope.jpg");
        final ThemeResource CENTERIMAGE = new ThemeResource("img/expert.jpg");

        //600 378
        Image imageHand = new Image(null,CENTERIMAGE);
        imageHand.setHeight("378px");
        layoutImage.addComponent(imageHand);
        layoutImage.setComponentAlignment(imageHand, Alignment.MIDDLE_CENTER);
        layoutImage.setMargin(true);
        addComponent(layoutImage);

        Button b = new Button("Забыл пароль");
        b.setStyleName("link");
        b.addStyleName("colorlink");
//        contentPan.addComponent(ruler1);
//        contentPan.addComponent(ruler1);
//------------------------------------------------------------------

        b.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                // ввести почтовый адрес
//                EMailDialog emd = new EMailDialog(Window parentWnd, final EMailDialog.Recipient recipient)
                EMailDialog emd = new EMailDialog(new EMailDialog.Recipient() {
                    @Override
                    public void gotInput(String eMail) {
                        //To change body of implemented methods use File | Settings | File Templates.
                        DaoPregerister dao = new DaoPregerister(linkDB);
                        if (dao.isRegister(eMail,idGlobal)){
                            // сгенерировать новые логин и пароль
                            String loginS = getFixRandomString(5);
                            String passwordS = getFixRandomString(7);
                            // отправить их по почте
                            try {
                                sendLogToMail(eMail, loginS, passwordS);
                            } catch (Exception e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            };
                            // записать их в базу данных
                            if (dao.updateLoginPassword(eMail,loginS, passwordS,idGlobal)){
                                Notification.show("Новые значения логина и пароля высланы вам на електронный почтовый адрес.",Notification.Type.WARNING_MESSAGE);
                            } else {
                                Notification.show("Пользователь с таким почтовым адресом не зарегистрирован.",Notification.Type.ERROR_MESSAGE);
                            }
                        } else{
                            Notification.show("Пользователь с таким почтовым адресом не зарегистрирован.",Notification.Type.ERROR_MESSAGE);
                        }


                    }

                    private void sendLogToMail(String eMail, String loginS, String passwordS) throws Exception {
                        MailerSSL mailer = new MailerSSL();
                        String themes ="Ваши новые учетные данные на сервере СОУТ";
                        String bodyMessage = null;

                        String letterFileName = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/letterforget.txt";
                        File fletter = new File(letterFileName);
                        try {
                            bodyMessage = new Scanner(fletter,"UTF-8").useDelimiter("\\Z").next();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        bodyMessage = bodyMessage.replaceAll("<log>",loginS);
                        bodyMessage = bodyMessage.replaceAll("<pass>",passwordS);
                        try {
                            mailer.sendSimpleMail(eMail,themes,bodyMessage);
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Почтовое сообщение отправлено на адрес: " + eMail);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }

                    }
                });
                getUI().addWindow(emd);
            }
        });
//--------------------------------------------------------------------



        buttons.addComponent(btnLogin);
        buttons.setWidthUndefined();
        fl.addComponent(login);
        fl.addComponent(password);
        VerticalLayout bl = new VerticalLayout();
        login.setWidth("170px");
        login.addStyleName("gray");
        login.addStyleName("shadow");
        password.setWidth("170px");
        password.addStyleName("gray");
        password.addStyleName("shadow");
        bl.setWidth("170px");

        //bl.setCaption("ertretre");
        //bl.setWidth("100%");
        //bl.setStyleName("whiteLabel");
        bl.addComponent(buttons);
        bl.setComponentAlignment(buttons, Alignment.TOP_CENTER);
        bl.addComponent(b);
        bl.setComponentAlignment(b, Alignment.TOP_CENTER);


        fl.addComponent(bl);
        fl.setWidthUndefined();

        contentPan.addComponent(fl);
        //contentPan.addComponent(ruler2);
        contentPan.setComponentAlignment(fl, Alignment.TOP_CENTER);
//        contentPan.addComponent(b);
//        contentPan.addComponent(buttons);
//        contentPan.setComponentAlignment(buttons,Alignment.MIDDLE_CENTER);

        HorizontalLayout parentPan = new HorizontalLayout();
        parentPan.setWidthUndefined();

        pan.setContent(contentPan);
        //pan.setStyleName("panelFields");
        //pan.addStyleName(Runo.PANEL_LIGHT);
        //pan.setStyleName("reindeer");
        pan.addStyleName(ValoTheme.PANEL_BORDERLESS);
        //pan.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        pan.addStyleName("transparent");
        pan.setWidthUndefined();
        pan.setHeightUndefined();

        parentPan.addComponent(pan);

        addComponent(parentPan);
        setComponentAlignment(parentPan, Alignment.TOP_CENTER);
        //-----------------------------------------
        FactoryTrains factoryTrains =  new FactoryTrains();
        HorizontalLayout podballogo = (HorizontalLayout) factoryTrains.makeTrain(new String[]{},0);
        addComponent(podballogo);
//        addComponent(factoryTrains.makeTrain(new String[]{"Договоры", "Список документов","Сервис","Загрузка"},1));
        setComponentAlignment(podballogo,Alignment.TOP_CENTER);
        //-----------------------------------------
        setSizeFull();
        setImmediate(true);
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


//    private Layout makeProba(){
//        HorizontalLayout layout50 = new HorizontalLayout();
//        layout50.setHeightUndefined();
//        layout50.setWidth("100%");
//        layout50.setStyleName("menubararea");
//        layout50.setMargin(false);
//
//        String hMenu = "40px";
//        Button bSercons =  new Button();
//        final ThemeResource sercons = new ThemeResource("img/logo_sercons.png");
//        bSercons.setIcon(sercons);
//        bSercons.addStyleName("transparentColor");
//        bSercons.setHeight(hMenu);
//        layout50.addComponent(bSercons);
//
//        ArrayList<Button> buttons =  new ArrayList<Button>();
//        Button buttonDogovor = new Button("Договоры и приложения");
//        Button buttonRequiredDoc = new Button("Требуемые документы");
//        Button buttonServis = new Button("Сервис");
//        Button buttonLoad = new Button("Загрузка документов");
//        Button buttonRecover =  new Button("Перезагрузить");
//        buttons.add( buttonDogovor);
//        buttons.add(buttonRequiredDoc);
//        buttons.add(buttonServis);
//        buttons.add(buttonLoad);
//        buttons.add(buttonRecover);
//
//        for (Button value: buttons) {
//            value.setWidth("100%");
//            value.setHeight(hMenu);
//            value.addStyleName("transparentColor");
//            layout50.addComponent(value);
//            layout50.setExpandRatio(value, 1.0f);
//        };
//
//        return layout50;
//
//    }
//    Прозрачная кнопка
//    btnLogin.addStyleName("transparentColor");
//    btnLogin.addStyleName(ValoTheme.BUTTON_BORDERLESS);
//    btnLogin.addStyleName("mybutton");
//    .mybutton:hover {
//        background-color: #ddd;
//    }


    private User getIduser() {
        User user = null;
        DaoPregerister dao = new DaoPregerister(linkDB);
//        user = (dao.getUser(login.getValue(),   password.getValue()));
        try {
            user = (dao.getUser(login.getValue(), Digester.getDigest(password.getValue()),idGlobal));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    private BigDecimal getIdRequests(int idUser) {
        DaoPregerister dao = new DaoPregerister(linkDB);
        return dao.getIdRequests(idUser);
    }


    private String getFixRandomString(int len){
        String symbols = "QWERTYUIPASDFGHJKLZXCVBNM123456789";
        StringBuilder randString = new StringBuilder();
//        int count = (int)(Math.random()*30);
        for(int i=0;i<len;i++)
            randString.append(symbols.charAt((int)(Math.random()*symbols.length())));
        return randString.toString();
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
