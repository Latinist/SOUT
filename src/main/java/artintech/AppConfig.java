/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artintech;

/**
 *
 * @author Сергей
 */
//import com.app.domain.UserService_;
//import com.app.domain.UserServiceImpl;

import artintech.beans.CommonView;
import artintech.dao.DaoDogovors;
import artintech.domain.User;
import artintech.spam.FirstDocs;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/*
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.company"})
@ImportResource({"classpath:security.xml"})
public class CompanyWebMvcConfiguration extends WebMvcConfigurerAdapter {
} 
 */
@Configuration
@ComponentScan(basePackages = {"artintech","artintech.beans","artintech.domain"})
public class AppConfig {

/*    
@Bean //(name="availableUser")
    public Users users(){
       UsersImpl res = new UsersImpl();
       return res;
    }
*/

/*
    @Bean(name="welkomeView")
    public View welkomeView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Welkome());
        return myForma;
    }
*/


/*
    @Bean(name="registerView")
    public View registerView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Register());
        return myForma;
    }
*/

/*
    @Bean(name="loginView")
    public View loginView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Login());
        return myForma;
    }
*/

    @Bean(name = "choiceResursView")
    @Scope("prototype")
    public View   choiceResursView(){
        CommonView myForma = new CommonView();
        FrameRender frameRender = new FrameRender();

        final ChoiceDogovor choiceDogovor = new ChoiceDogovor();
        frameRender.setHeaderContainer(choiceDogovor);

        final GridRender gridRender = new GridRender();
        frameRender.addComponentContainer(gridRender); // cодержание основной панели формы
        myForma.setComponentContainer(frameRender);
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);
        choiceDogovor.setDogovorUpdated(new ChoiceDogovor.DogovorUpdated() {
            @Override
            public void doUpdateView() {
                // Оповестить содержимое об изменениях
                gridRender.notifyUpdateDogovor();
            }
        }) ;
        myForma.setMyEnter(new CommonView.MyEnter() {
            @Override
            public void doMyEnter() {
                Boolean refresh = (Boolean) VaadinSession.getCurrent().getAttribute("regreshSlider");
                if ((refresh != null) && (refresh == true)) {
                    choiceDogovor.notifyUpdateDogovor();
                }
                VaadinSession.getCurrent().setAttribute("regreshSlider", false);
            }
        });

        return myForma;
    }

    @Bean(name = "trackView")
    @Scope("prototype")
    public View   trackView(){
        CommonView myForma = new CommonView();
        FrameRender frameRender = new FrameRender();

        final ChoiceDogovor choiceDogovor = new ChoiceDogovor();
        choiceDogovor.setDogovorUpdated(new ChoiceDogovor.DogovorUpdated() {
            @Override
            public void doUpdateView() {
                // Оповестить содержимое формы об изменениях

            }
        }) ;
        frameRender.setHeaderContainer(choiceDogovor);

        final Slider slider =  new Slider();
        frameRender.addComponentContainer(slider); // одержание основной формы
        myForma.setComponentContainer(frameRender);
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);

        choiceDogovor.setDogovorUpdated(new ChoiceDogovor.DogovorUpdated() {
            @Override
            public void doUpdateView() {
                // Оповестить содержимое слайдера об изменениях
                slider.notifyUpdateDogovor();
            }
        }) ;
        myForma.setMyEnter(new CommonView.MyEnter() {
            @Override
            public void doMyEnter() {
                Boolean refresh = (Boolean) VaadinSession.getCurrent().getAttribute("regreshSlider");
                if ((refresh != null) && (refresh == true)) {
                    slider.notifyUpdateDogovor();
                    choiceDogovor.notifyUpdateDogovor();
                }
                VaadinSession.getCurrent().setAttribute("regreshSlider", false);
            }
        });
        return myForma;
        //@Override
        //public void enter(ViewChangeEvent event);
    }



    @Bean(name = "requestView")
    @Scope("prototype")
    public View   requestView(){
        CommonView myForma = new CommonView();
        final Request request = new Request();
        myForma.setComponentContainer(request);
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);

        myForma.setMyEnter(new CommonView.MyEnter() {
            @Override
            public void doMyEnter() {
//                Boolean refresh = (Boolean) VaadinSession.getCurrent().getAttribute("regreshSlider");
//                if ((refresh != null) && (refresh == true)) {
                    request.notifyUpdate();
//                }
//                VaadinSession.getCurrent().setAttribute("regreshSlider", false);
            }
        });

        return myForma;
    }

    @Bean(name = "kabinetView")
    @Scope("prototype")
    public View  kabinetView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Kabinet());
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);
        return myForma;
    }

    @Bean(name = "exportView")
    @Scope("prototype")
    public View   exportView(){
        CommonView myForma = new CommonView();
//        myForma.setComponentContainer(new ExportDoc());
        myForma.setComponentContainer(new FirstDocs());
        MenuBarArea mb = new MenuBarArea();
        mb.setWidthUndefined();
//        HorizontalLayout hlMenuBar = new HorizontalLayout(new MenuBarArea());
        HorizontalLayout hlMenuBar = new HorizontalLayout();
        hlMenuBar.addComponent(mb);
        hlMenuBar.setWidth("100%");
//        MenuBarArea mb = new MenuBarArea();
//        mb.setWidth("100%");
//        HorizontalLayout hlMenuBar = new HorizontalLayout(mb);
//        hlMenuBar.setWidth("100%");
//        hlMenuBar.setSizeFull();
        //hlMenuBar.setStyleName("greylayout");
        myForma.setToolBarContainer(hlMenuBar);
//        myForma.setToolBarContainer(hlMenuBar);
//        myForma.setToolBarContainer(new HorizontalLayout(new MenuBarArea()));
        return myForma;
    }

    @Bean(name = "loadTools")
    @Scope("prototype")
    public  View loadTools(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LoadTools());
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);
        return myForma;
    }


    @Bean(name = "loadProgram")
    @Scope("prototype")
    public  View loadProgram(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LoadProgram());
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        myForma.setToolBarContainer(buttonBarArea);
        return myForma;
    }

    @Bean(name = "uploadFiles")
    @Scope("prototype")
    public  View uploadFiles(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new UploadFiles());
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        //HorizontalLayout hlMenuBar = new HorizontalLayout();
        myForma.setToolBarContainer(buttonBarArea);
        return myForma;
    }

    @Bean(name = "help")
    @Scope("prototype")
    public  View help(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Helper());
        ButtonBarArea buttonBarArea = new ButtonBarArea();
        //HorizontalLayout hlMenuBar = new HorizontalLayout();
        myForma.setToolBarContainer(buttonBarArea);
        return myForma;
    }

/*
@Bean //(name="loadPlat")
    public LoadPlat loadPlat(){
        return new LoadPlatImpl();
    }
    
@Bean(name="loginView")
    public View loginView(){
        CommonView myForma = new CommonView();
        return new LoginView();
    }
@Bean(name="dolgiView")
    public View dolgiView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Dolgi());
        return myForma;
    }
@Bean(name="kabinetView")
    public View kabinetView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Kabinet());
        return myForma;
    }
@Bean(name="helpView")
    public View helpView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Help());
        return myForma;
    }

@Bean(name="licSchetView")
    public View licSchetView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LicSchet());
        return myForma;
    }


@Bean(name="lispTuView")
    public View lispTuView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LispTuWindow());
        return myForma;
    }

@Bean(name="schetsView")
    public View schetsView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Schets());
        return myForma;
    }

@Bean(name="lispRaView")
    public View lispRaView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LispRaWindow());
        return myForma;
    }


@Bean(name="nalogNakladView")
    public View nalogNakladView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Nalog());
        return myForma;
    }
    

@Bean(name="lispAllPuView")
    public View lispAllPuView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new AllPu());
        return myForma;
    }

@Bean(name="historyRaports")
    public CommonView historyRaports(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new HistoryNalNak());
        return myForma;
    }

    @Bean(name="historyTarif")
    public CommonView historyTarifs(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new HistTarif());
        return myForma;
    }
*/
/*
@Bean
@Component(value = "monApplication") @Scope(value = "session") 
@Scope(value = "session")
@Scope(ApplicationScope.NAME)
@Scope(ScopeType.APPLICATION)
    public LinkPoolImpl linkPoolImpl(){
        return new LinkPoolImpl();
    }
@Bean
  public AuthManager authManager() {
    AuthManager res = new AuthManager();
    return res;
  }
  @Bean
  public UserService userService() {
    UserService res = new UserService();
    return res;
  }
  @Bean
  public LoginFormListener loginFormListener() {
    return new LoginFormListener();
  }
*/    
}