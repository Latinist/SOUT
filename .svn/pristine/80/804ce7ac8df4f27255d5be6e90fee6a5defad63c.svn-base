package artintech;

import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Анатолий on 10.07.2015.
 */
public class FactoryTrains {
    public static Layout makeTrain(String[] headers, int i){
        HorizontalLayout mainHl = new HorizontalLayout();
        mainHl.setWidth("97%");
        mainHl.setMargin(true);
        HorizontalLayout hl =  new HorizontalLayout();

        final ThemeResource LOGOART = new ThemeResource("img/logo_artsout.png");
        Image imageLogo = new Image(null,LOGOART);
        imageLogo.setWidth("183px");
        imageLogo.setHeight("50px");
        hl.addComponent(imageLogo);

        HorizontalLayout prokladka = new HorizontalLayout(); prokladka.setWidth("50px");  hl.addComponent(prokladka);



        int count = 1;
        for (String header : headers){
            HorizontalLayout vh = new HorizontalLayout();
            vh.setWidth("150px");
            vh.setHeight("50px");
            vh.addStyleName("greylayout");
            vh.addStyleName("shadow");
            if (count == i){
                vh.addStyleName("borderred");
            }

            Vagen v = new Vagen(header);
            vh.addComponent(v);
            vh.setComponentAlignment(v,Alignment.MIDDLE_CENTER);
            hl.addComponent(vh);

            if (count < headers.length) {
                HorizontalLayout v1 = new HorizontalLayout();
                v1.addStyleName("greylayout");
                v1.setHeight("2px");
                v1.setWidth("20px");
                hl.addComponent(v1);
                hl.setComponentAlignment(v1, Alignment.MIDDLE_CENTER);
            }
            count++;
        }

        mainHl.addComponent(hl);
        mainHl.setComponentAlignment(hl,Alignment.TOP_LEFT);


        final ThemeResource LOGOART1 = new ThemeResource("img/Logo_artinttech.png");
        Image imageLogo1 = new Image(null,LOGOART1);
        imageLogo1.setWidth("87px");
        imageLogo1.setHeight("50px");
        mainHl.addComponent(imageLogo1);
        mainHl.setComponentAlignment(imageLogo1,Alignment.TOP_RIGHT);

        return mainHl;
    }
    static class Vagen extends Label{
        public Vagen(String capt){
            super("<style type=\"text/css\">p {margin: 0.2em;}</style><p align=\"center\"><font face=\"Uni Sans Regular\" size=\"3\">"+capt+"</font></p>", ContentMode.HTML);
            setWidth("100%");
        }
    }
}
