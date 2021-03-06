package artintech.spam;

import artintech.Ruler;
import artintech.SeparatorL;
import artintech.beans.IComponentContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.BaseTheme;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Анатолий on 05.01.2015.
 */

public class Welkome extends VerticalLayout implements IComponentContainer {
   // private VerticalLayout contentPan = new VerticalLayout();

    @Override
    public void init() {

   //     contentPan.setMargin(true);
        String ss = "<h1 align=\"center\">Система оценки условий труда на предприятии</h1> <font size=\"4\" FACE=\"Times New Roman\"> <p>Настоящий ресурс предлагает комплекс услуг в проведении работ по специальной оценке условий труда на предприятии."
                +" Ресурс создан с связи с введением федерального закона № 426-Ф3 от 28.12.2013,который регулирует обязанности работодателя по обеспечению безопасности"
                +" работников в процессе их трудовой деятельности и прав работников рабочие места,соответствующие государственным нормативным"
                +" требованиям охраны труда."
                +"</p></font>";

        Label headComment = new Label(ss, ContentMode.HTML);
        //Ruler ruler1 = new Ruler();
        Ruler ruler2 = new Ruler();

        addComponent(new SeparatorL("Краткое описание ресурса"));
        addComponent(headComment);
        addComponent(ruler2);
        addComponent(new SeparatorL("......"));


        //------------------------------------------
//        HorizontalLayout ll1 = new HorizontalLayout();
//
//        MenuBarArea mb = new MenuBarArea();
//       // mb.setWidth("100%");
//        mb.setWidthUndefined();
//        ll1.addComponent(mb);
//        ll1.setWidth("100%");
//        addComponent(ll1);
        //-------------------------------------------------
//        HorizontalLayout ll = new HorizontalLayout();
//        Button b1 = new Button("ewerewr");
//        b1.setWidth("100%");
//        //b1.setStyleName("transparentColor");
//        b1.addStyleName(BaseTheme.BUTTON_LINK);
//        Button b2 = new Button("ewerewr");
//        b2.setWidth("100%");
//        //b2.setStyleName("transparentColor");
//        b2.addStyleName("transparentColor");
//        Label b3 = new Label("fffghjj");
//        b3.setWidth("10px");
//        //b3.setStyleName("transparentColor");
//        ll.addComponent(b1);
//        ll.addComponent(b2);
//        ll.addComponent(b3);
//        //ll.setStyleName("toolbararea");
//        ll.setStyleName("backColor");
//        ll.setWidth("100%");
//        addComponent(ll);
        //--------------------------------------

        setSizeFull();

        if (VaadinSession.getCurrent().getAttribute("idglobal") == null){
            Notification.show("При входе не указан (или не верен) код экспертной организации");
        }
        setImmediate(true);


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
