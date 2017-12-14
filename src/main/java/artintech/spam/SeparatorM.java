package artintech.spam;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

/**
 * Created by Анатолий on 20.01.2015.
 */
public class SeparatorM extends Label{
    public SeparatorM(String caption){
        super ("<b><p align=\"center\">"+caption+"</p><b>", ContentMode.HTML);
        setHeight("40px");
    }
}
