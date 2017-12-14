package artintech;

import artintech.beans.IComponentContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 18.01.15
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class ToolBarImplMin  extends HorizontalLayout implements Button.ClickListener{
    private Button buttonReboot;
    private MenuBarArea menuBarArea;
//    private static final ThemeResource ICON =  new ThemeResource("img/arr_left.gif");
    private static final ThemeResource ICON =  new ThemeResource("img/refresh.png");

    public ToolBarImplMin(){
        init();
    };

    //    @Autowired
    public void init(){
        buttonReboot = new Button();
        buttonReboot.setIcon(ICON);
        buttonReboot.setDescription("Перегрузить программу в исходное состояние");
        buttonReboot.addClickListener(this);
        //buttonReboot.setWidth("20px");
        menuBarArea = new MenuBarArea();
        addComponent(buttonReboot);
        addComponent(menuBarArea);
    };

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().equals(buttonReboot)){
            //  перенаправим на другую страницу. После закрытия сессии ничего уже не сделать
            String ss3  = getUI().getPage().getLocation().getPath();
            getUI().getPage().setLocation(ss3);
            // закрываем сессию
            getSession().close();
        }
    }
}
