package artintech;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

//import artintech.domain.MailPropertyes;
import artintech.dao.DaoPregerister;
import artintech.dao.TestVirtualPage;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Theme("mytheme")
@SuppressWarnings("serial")
//  Протестировать влияние @PreserveOnRefresh
public class MyVaadinUI extends UI
{
    private ApplicationContext applicationContext;
    private Boolean isInit = false;
    private Integer idGlobal = null;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "artintech.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
        // перегружаем
        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(new SessionInitListener() {
                @Override
                public void sessionInit(SessionInitEvent event) throws ServiceException {
                    event.getSession().addBootstrapListener(new BootstrapListener() {
                        @Override
                        public void modifyBootstrapPage(BootstrapPageResponse response) {
                            // With this code, Vaadin servlet will add the line:
                            //
                            // <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" />
                            //
                            // as the first line inside the document's head tag in the generated html document
                            response.getDocument().head().prependElement("script").attr("type", "text/javascript").attr("src", "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js");
                        }

                        @Override
                        public void modifyBootstrapFragment(BootstrapFragmentResponse response) {}
                    });
                }
            });
        }    }

    private static String linkDB = "serviceSOUT";
    private static Integer timeout = 5;
//    private static MailPropertyes mailPropertyes = new MailPropertyes();
    public static final String IMAGE_URL = "myimage.png";
    private String myURL = null;

    // Перегружаем распознаватель запросов
    private final RequestHandler requestHandler = new RequestHandler() {
        @Override
        public boolean handleRequest(VaadinSession session,
                                     VaadinRequest request, VaadinResponse response)
                throws IOException {

//            String ss  = request.getPathInfo();  //  /sout/
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Вход через hadlle");
/* -----------------
            if (isInit) {
                String idVirtualpage = request.getParameter("id");
                if (idVirtualpage != null) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Вход через hadlle на виртуальную страницу");
                    initUserAndredirect(idVirtualpage);
                }

                //Map<String, String[]> m = request.getParameterMap();
            }
------------------*/
            if (("/" + IMAGE_URL).equals(request.getPathInfo())) {
                // Create an image, draw the "text" parameter to it and output
                // it to the browser.
 /*
                String text = request.getParameter("text");
                BufferedImage bi = new BufferedImage(100, 30,
                        BufferedImage.TYPE_3BYTE_BGR);
                bi.getGraphics().drawChars(text.toCharArray(), 0,
                        text.length(), 10, 20);
                response.setContentType("image/png");
                ImageIO.write(bi, "png", response.getOutputStream());
*/
                return true;
            }
            // If the URL did not match our image URL, let the other request
            // handlers handle it
            return false;
        }
    };

    @Override
    protected void init(VaadinRequest request) {
        // установить распознаватель запросов
        isInit = true;
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Вход через init");
        getSession().addRequestHandler(requestHandler); //Добавим новый хендлер
        // получить параметр
        myURL = request.getParameter("v-loc");
        String idVirtualpage = request.getParameter("id");
        Boolean isReqVirtualPage = false;
        if (idVirtualpage !=null) {
            isReqVirtualPage = true;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "На виртуальную страницу");
        }
        String sidGlobal =  request.getParameter("from");
        //------------------ Почтовые реквизиты-------------------
//        mailPropertyes.setHost("smtp.mail.ru");
//        mailPropertyes.setUser("tomat56@mail.ru");
//        mailPropertyes.setPassword("tomatoma56");
//        mailPropertyes.setTheme("Регистрация на ресурсе");
        //--------- извлечем контекст для SPRING------------------
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession)
                session).getHttpSession();

//        Enumeration<String> sss = httpSession.getAttributeNames();
        Enumeration<String> ss = request.getAttributeNames();


        ServletContext servletContext =
                httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.
                getRequiredWebApplicationContext(servletContext);
        //--------------------------------------------------------
        VaadinSession.getCurrent().setAttribute("linkDB", linkDB);
        VaadinSession.getCurrent().setAttribute("timeout", timeout);
        VaadinSession.getCurrent().setAttribute("myURL",myURL);
        if ((idGlobal = idGlobalValid(sidGlobal)) != null) {
            VaadinSession.getCurrent().setAttribute("idglobal", idGlobal);
        }
        //--------------------------------------------------------
        Navigator navigator = new Navigator(this, this);
//        navigator.addView("",(View) this.getApplicationContext().getBean("welkomeView"));
//        navigator.addView("", new WelkomeView());
//        navigator.addView("login",(View) this.getApplicationContext().getBean("loginView"));
        navigator.addView("",new LoginView());
//        navigator.addView("login",new LoginView());
//        navigator.addView("register",(View) this.getApplicationContext().getBean("registerView"));

//        navigator.addView("register",new RegisterView());
        navigator.addView("resource",(View) this.getApplicationContext().getBean("choiceResursView"));
        navigator.addView("track",(View) this.getApplicationContext().getBean("trackView"));



        navigator.addView("request",(View) this.getApplicationContext().getBean("requestView"));
        navigator.addView("kabinet",(View) this.getApplicationContext().getBean("kabinetView"));
//        navigator.addView("export",(View) this.getApplicationContext().getBean("exportView"));
        navigator.addView("loadTools", (View) this.getApplicationContext().getBean("loadTools"));
        navigator.addView("loadProgram", (View) this.getApplicationContext().getBean("loadProgram"));
        navigator.addView("uploadFiles", (View) this.getApplicationContext().getBean("uploadFiles"));
        navigator.addView("help", (View) this.getApplicationContext().getBean("help"));
//        navigator.addView("request",new RequestView());

        if (isReqVirtualPage == true) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "попробуем перенаправит на рабочую область");
            initUserAndredirect(idVirtualpage);
        }

        getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                // Check if a user has logged in

                Integer idLog = (Integer) getSession().getAttribute("iduser");
                //Boolean isUsetExists = false;
                //if (idLog == null) {
                //    isUsetExists = false;
                //} else {
                //    isUsetExists = true;
                //}

                //------  пользователь известен-----------
                Boolean isUsetExists = getSession().getAttribute("iduser") != null;
                //VaadinSession.getCurrent().setAttribute("userverified", true);
                //------------ область логина----------
                //boolean isLoginView = (event.getNewView() instanceof LoginView) || (event.getNewView() instanceof WelkomeView) || (event.getNewView() instanceof RegisterView);
                boolean isLoginView = (event.getNewView() instanceof LoginView) || (event.getNewView() instanceof RegisterView);
                boolean isLogin = (event.getNewView() instanceof LoginView);
                boolean notExixtsGlobal = VaadinSession.getCurrent().getAttribute("idglobal") == null ;

                if (notExixtsGlobal && !isLogin){
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Смена view. экспертная организация не известна"+"("+event.getViewName()+")");
                    getNavigator().navigateTo("");
                    return false;
                }

                if (!isUsetExists && !isLoginView) {
                    // Если пользователь не залогинен но пытается работать в защищенной оласти
                    // перенаправляем на логин
                    // Redirect to login view always if a user has not yet
                    // logged in
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Смена view. пользователь не залогинен но пытается работать в защищенной оласти перенаправляем на логин"+"("+event.getViewName()+")");
                    getNavigator().navigateTo("");
                    return false;

                } else if (isUsetExists && isLoginView) {
                    // Если пользователь уже залогинен но пытается логинится повторно
                    // не пускаем.
                    //getNavigator().navigateTo("");
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Смена view. пользователь уже залогинен но пытается логинится повторно. не пускаем"+"("+event.getViewName()+")");
                    return false;
                }
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Смена view. Разрешаем работу "+"("+event.getViewName()+")");
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });
//        if (isReqVirtualPage == true) {
//            initUserAndredirect(idVirtualpage);
//        }





/*
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);
*/
    }

    @Override
    public void detach() {
        super.detach();

        // Clean up
        getSession().removeRequestHandler(requestHandler);
    }
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public void initUserAndredirect(String idVirtualpage){
        TestVirtualPage tst = new TestVirtualPage(linkDB);
        switch (tst.isVirtualPage(idVirtualpage)) {
            case 0:
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "учетная запись активирована: " + tst.geteMail());
                getUI().getNavigator().navigateTo("request");        // пропускаем в раьоту
                //Notification.show("Ваша учетная запись активирована");
                break;
            case 1:     //Нет такой виртуальной страницы
                //Notification.show("Такая страница отсутствует");
                VaadinSession.getCurrent().setAttribute("preregmess", "Не верная ссылка" );
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Не верная ссылка: " + idVirtualpage);
                break;
            case 2:
                //Notification.show("Ваша учетная запись уже активирована");
                //getUI().getNavigator().navigateTo("");        // пропускаем в раьоту
                VaadinSession.getCurrent().setAttribute("preregmess", "Ваша учетная запись уже активирована");
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "учетная запись уже активирована ранее: " + tst.geteMail());
                break;  // уже в работе
            case 3:
                //Notification.show("Срок активации вашей учетной записи истек.");
                VaadinSession.getCurrent().setAttribute("preregmess", "Срок активации вашей учетной записи истек." );
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Срок активации учетной записи истек: " + tst.geteMail());
                break;  // истек срок ожидания
        }
    };

    public Integer idGlobalValid(String sidGlobal){
        Integer idG = null;
        if (sidGlobal != null) {
            //idGlobal = Integer.valueOf(sidGlobal);
            try {
                idG = Integer.valueOf(sidGlobal);
                DaoPregerister dao = new DaoPregerister(linkDB);
                if (dao.idGlobalIsValid(idG))
                    return  idG;
                else
                    return null;
            }catch (NumberFormatException e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Неверный ID экспертной организации: "+e.getMessage());
                return null;
            }
        } else {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Отсутствует ID экспертной организации.");
            return null;
        }
    }

}
