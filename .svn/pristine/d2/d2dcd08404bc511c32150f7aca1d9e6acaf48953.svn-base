/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
//import com.vaadin.server.VaadinSession;

/**
 *
 * @author Сергей
 */
//@org.springframework.stereotype.Component
//@Scope("prototype")
//@Scope (value = "session")
//@com.vaadin.server.VaadinSession.Scope("prototype")
//@Scope("session")
//@Scope(ScopeType.APPLICATION)
public class ToolBarImpl extends HorizontalLayout implements Button.ClickListener{
    private Button buttonReboot;
    private Button buttonLogin;
    private Button buttonRegister;
//    private StatePlat statePlat;
//    private Config config;
//private static final ThemeResource ICON =  new ThemeResource("img/arr_left.gif");
    private static final ThemeResource ICON =  new ThemeResource("img/refresh.png");

    public ToolBarImpl(){
        init();
    };
    
//    @Autowired
    public void init(){
//        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
//        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        setStyleName("toolbararea");
        buttonReboot = new Button();
        buttonReboot.setIcon(ICON);
        buttonReboot.setDescription("Перегрузить программу в исходное состояние");
        buttonReboot.addClickListener(this);
        
        buttonLogin = new Button("Войти в портал");
        buttonLogin.setDescription("Войти в портал через свою учетную запись");
//        if ( (statePlat.getPause().intValue() == 1) ) {buttonPokaz.setEnabled(false);}
//        if (statePlat.getGoest() & config.getTested().equals("1")) {buttonPokaz.setEnabled(true);}
        buttonRegister = new Button("Зарегистрироваться");

        addComponent(buttonReboot);
        addComponent(buttonLogin);
        addComponent(buttonRegister);

        buttonReboot.addClickListener(this);
        buttonLogin.addClickListener(this);
        buttonRegister.addClickListener(this);

    };
    
    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(buttonReboot)){
            //  перенаправим на другую страницу. После закрытия сессии ничего уже не сделать
            String ss3  = getUI().getPage().getLocation().getPath();
            getUI().getPage().setLocation(ss3);
            // закрываем сессию
            getSession().close();
        }
        if (event.getButton().equals(buttonLogin)){
            if (getUI().getNavigator().getState().equals("login")) return;
            getUI().getNavigator().navigateTo("login");
//           statePlat.refreshState();
//           if (statePlat.getGoest() & config.getTested().equals("1")) {
//              if (statePlat.getRaportEnabled().equals(0))
//              Notification.show("Доступ к вводу показаний закрыт (" + statePlat.getMess() + "). Но из за тестового режима доступ откроем");
//              statePlat.setRaportEnabled(1); //raportenabled = 1;
//           }
//           if ( statePlat.getRaportEnabled().equals(1) ){
//               getUI().getNavigator().navigateTo("lispRa");
//           } else {
//                  Notification.show(statePlat.getMess());
//           }
        }

        if (event.getButton().equals(buttonRegister)){
           if (getUI().getNavigator().getState().equals("register"))
               return;
           getUI().getNavigator().navigateTo("register");
        } 
/*
        if (event.getButton().equals(buttonSchets)){
           if (getUI().getNavigator().getState().equals("schets"))
               return;
           getUI().getNavigator().navigateTo("schets");
           
        } 

        if (event.getButton().equals(buttonNn)){
           if (getUI().getNavigator().getState().equals("nalogNaklad"))
               return;
           getUI().getNavigator().navigateTo("nalogNaklad");
        } 
*/
           
           
                
    }
}
