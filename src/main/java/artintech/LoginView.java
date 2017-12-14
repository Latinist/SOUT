package artintech;

import artintech.beans.CommonView;

/**
 * Created by Анатолий on 09.01.2015.
 */
public class LoginView  extends CommonView {
    public  LoginView(){
        //setToolBarContainer(new ToolBarImpl());
        setComponentContainer(new Login());
    }

}
