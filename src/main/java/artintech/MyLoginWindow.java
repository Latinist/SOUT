/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

/**
 *
 * @author Сергей
 */
@SuppressWarnings("serial")
public class MyLoginWindow extends Window implements Button.ClickListener{
   private Field usernameFld;  //= new PasswordField("Пароль");;
   private PasswordField passwordFld;  //= new PasswordField("Пароль");;
   private PasswordField confirmPasswordFld;
   private Button saveButton;
   private Button cancelButton;
   private  String login;
   private  String password;
   private  LoginRecipient recipient;
   
   MyLoginWindow(String login, String password, LoginRecipient recipient){
       setModal(true);
       setWidth("250px");
       this.setHeight("300px");
       //setWidthUndefined();
       //setHeightUndefined();
       this.login = login;
       this.password = password;
       this.recipient = recipient;
       this.addStyleName("greylayout");
       Main();
   };    
   public void Main(){
//        VerticalLayout layout = (VerticalLayout) getContent();
        VerticalLayout layout = new VerticalLayout();
        //layout.setWidth();
        //layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        //----------- прикрепим к окну форму ввода данных ---------------
        VerticalLayout body = new VerticalLayout();
        //body.setHeight("350px");
        //body.setWidth("300px");
       body.setWidth("100%");
       body.setHeightUndefined();
        //---------------- добавим поля -------------------------------
        // Username
        usernameFld = new TextField("Логин");
        usernameFld.setValue(login);
        usernameFld.setWidth("100%");
        body.addComponent(usernameFld);
        // Password
        passwordFld = new PasswordField("Пароль");
        passwordFld.setValue(password);
        passwordFld.setWidth("100%");
        body.addComponent(passwordFld);
        confirmPasswordFld = new PasswordField("Подтвердите пароль");
        confirmPasswordFld.setValue(password);
        confirmPasswordFld.setWidth("100%");
        body.addComponent(confirmPasswordFld);
               
        HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("25px");
       //---------------- сделаем кропки выхода из окна ----------------
       saveButton = new Button("Выполнить", this);
       cancelButton = new Button("Отменить  ", this);
       //--------------- поместим кнопки выхода в подвал ---------------
       footer.addComponent(cancelButton);
       footer.setComponentAlignment(cancelButton, Alignment.TOP_LEFT);
       footer.addComponent(saveButton);
       footer.setComponentAlignment(saveButton, Alignment.TOP_RIGHT);


       layout.addComponent(body);
       layout.setComponentAlignment(body, Alignment.TOP_CENTER);
       layout.addComponent(new Ruler());
       layout.addComponent(footer);
       layout.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);

      // layout.setExpandRatio(body, 1);
       this.setContent(layout);
      //-------------------- активизируем окно ------------------------
//        getWindow().addWindow(subwindow);
   };

    @Override
    public void buttonClick(ClickEvent event) {
       if (event.getButton() == saveButton) {
          if (usernameFld.getValue().toString().length()<3) {
              Notification.show("Длина логина менее 3 символов.");
          } if (usernameFld.getValue().toString().length()>40){
              Notification.show("Длина логина более 40 символов.");
          } if (passwordFld.getValue().toString().length()< 7){
              Notification.show("Длина пароля менее 7 символов.");
          } if (passwordFld.getValue().toString().length()>40){
              Notification.show("Длина пароля более 40 символов.");
          } else {
             if (passwordFld.getValue().equals(confirmPasswordFld.getValue())) {
                 if ((usernameFld.getValue().toString().equals(login)) | (recipient.testLogin(usernameFld.getValue().toString()))){
                     // showNotification("Логин верный.");
                     login = usernameFld.getValue().toString();
                     password = passwordFld.getValue().toString();
                     recipient.gotInput(login,password);  
                     //getParent().removeWindow(this);
                     getUI().removeWindow(this);
                 } else{
                      usernameFld.setValue(login);
                      Notification.show("Такой логин уже есть.");
                 }
             } else{
               Notification.show("Вы не правильно ввели пароль.");
             }
          }
          
       }
       if (event.getButton() == cancelButton) {
          //getParent().removeWindow(this);
          getUI().removeWindow(this);
       }
    }

    
    public interface LoginRecipient {
        public Boolean testLogin(String input);
        void gotInput(String login, String password);
    } 
    
}
