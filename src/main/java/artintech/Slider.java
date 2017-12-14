package artintech;

import artintech.beans.IComponentContainer;
import artintech.beans.ISlide;
import artintech.dao.DaoDogovors;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import org.apache.wicket.markup.html.form.ImageButton;

import java.beans.Visibility;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Анатолий on 30.10.2015.
 */
public class Slider extends VerticalLayout implements IComponentContainer {
    private String linkDB;
    private BigDecimal idDogovor = null;
    private DaoDogovors.StateTrack stateTrack = null;

    private AnimatorSlide  animator =  new AnimatorSlide();
    private ThemeResource LEFTARROW = new ThemeResource("img/arrowleftblack48.png");
    private ArrayList<Component> lispSliders = new ArrayList<Component>();
    private final ThemeResource RIGHTARROW = new ThemeResource("img/arrowrightblack48.png");
    private Label lCapt = new Label("<p align=\"center\">Вам предлагается выполнить несколько последовательных операций для корректной подготовки исходных данных СОУТ</p>", ContentMode.HTML);
    private GridLayout gridLayoutCaption = new GridLayout(3,1);
    private GridLayout gridLayout = new GridLayout(3,1);
    private Image imageLeft = new Image(null,LEFTARROW);
    private Image imageRight = new Image(null,RIGHTARROW);
    private int currentSlide=0;
    private ISlide.NotifySlider notifySlider;
    private DaoDogovors daoD = null;
    private boolean isInit = false;
    private VerticalLayout slide = new VerticalLayout();


    @Override
    public void init() {
       // lCapt.setId("anim");

        linkDB = (String) VaadinSession.getCurrent().getAttribute("linkDB");
        daoD = new DaoDogovors(linkDB);
        slide.setWidth("100%");
        gridLayout.setWidth("100%");
        gridLayoutCaption.setWidth("100%");
        if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
            idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
        }
        if (VaadinSession.getCurrent().getAttribute("stateTrack")!= null){
           stateTrack = (DaoDogovors.StateTrack) VaadinSession.getCurrent().getAttribute("stateTrack");
           currentSlide = stateTrack.getCurrentstate()-1;
        }

//        final VerticalLayout slide = new VerticalLayout();
//        slide.setWidth("100%");


        notifySlider = new ISlide.NotifySlider() {
            @Override
            public void doNotify(Object object) {
                if ( (stateTrack.getActiveLength()).intValue() < lispSliders.size()){
                    int oldLen = stateTrack.getActiveLength().intValue();
                    if (oldLen == currentSlide+1){
                        stateTrack.setActiveLength(oldLen+1);
                    }
                    //stateTrack.setActiveLength(stateTrack.getActiveLength()+1);
                    try {
                        daoD.setStateTrack(idDogovor,stateTrack);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Notification.show("Ошибка: "+e.getMessage());
                    }
                    refreshArrow();
                }

            }
        };

        createLispSlisers(stateTrack);

        imageLeft.setWidth("48px");
        imageLeft.setHeight("48px");
        imageLeft.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                if (currentSlide > 0) {
                    currentSlide--;
                }
                refreshArrow();
                stateTrack.setCurrentstate(currentSlide+1);
                try {
                    daoD.setStateTrack(idDogovor,stateTrack);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Notification.show("Ошибка: "+e.getMessage());
                }
                refreshNumberPage();
                slide.removeAllComponents();
                slide.addComponent(lispSliders.get(currentSlide));
                slide.setComponentAlignment(lispSliders.get(currentSlide), Alignment.TOP_CENTER);
                lispSliders.get(currentSlide).setStyleName("animateLeft");


//                CustomLayout divLayout = new CustomLayout("div-object");
//                //divLayout.setStyleName("animateLeft");
//                lispSliders.get(currentSlide).setStyleName("animateLeft");
//                divLayout.addComponent((Component)lispSliders.get(currentSlide),"div-object");
//                divLayout.setSizeFull();
//                slide.addComponent( divLayout);
//                slide.setComponentAlignment(divLayout, Alignment.TOP_CENTER);

                  JavaScript.getCurrent().execute("$('.animateLeft').animate({margin-left: -1000px,transition-property: margin-left}, 3000);");


                //String script = "setTimeout(\"document.getElementById('btnPrepare').click()\","+ MilliSec + ")";
                //JavaScript.getCurrent().execute("document.getElementById('anim').animate({opacity: 0.0}, 3000);");
                //Page.getCurrent().getJavaScript().execute(script);
//                  JavaScript.getCurrent().execute("$('.animateLeft').animate({margin-left: -1000px,transition-property: margin-left}, 3000);");

//                margin-left: -1000px;
//                transition-property: margin-left;
//                transition-duration: 3s;
//                transition-delay: 0.5s;

                //  ------- работает  одинаково но передвигаются не все элементы -------
                //  animator.animate(1);
                //  JavaScript.getCurrent().execute("$('.v-label').animate({opacity: 0.0,left: '1000px'}, 3000);");
                //  JavaScript.getCurrent().execute("jQuery('.v-label').animate( { opacity: 0.0,left: '1000px' }, 3000 );");
                //----------------------------
                //Notification.show("В лево");

            }
        });

        imageRight.setWidth("48px");
        imageRight.setHeight("48px");
        imageRight.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {

                if (currentSlide < lispSliders.size()-1){
                    currentSlide ++;
                }
                refreshArrow();
                stateTrack.setCurrentstate(currentSlide + 1);
                try {
                    daoD.setStateTrack(idDogovor,stateTrack);
                } catch (SQLException e) {
                    e.printStackTrace();
                    Notification.show("Ошибка: "+e.getMessage());
                }
                refreshNumberPage();
                slide.removeAllComponents();
                slide.addComponent(lispSliders.get(currentSlide));
                slide.setComponentAlignment(lispSliders.get(currentSlide), Alignment.TOP_CENTER);

                //animator.animate(-1);
            }
        });
        refreshArrow();
        lCapt.setWidth("100%");
        gridLayoutCaption.setWidth("100%");
        gridLayoutCaption.setHeightUndefined();
        gridLayoutCaption.setSpacing(false);
        gridLayoutCaption.setMargin(false);
        Label ll = new Label(""); ll.setWidth("200px");

        String textSlideNumber = "Шаг "+(currentSlide+1)+" из "+lispSliders.size()+"  ";
        String textR = "<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">"+textSlideNumber+"</font></p><p class=\"dline\" align=\"justify\"></p>";

        Label lr = new Label(textR, ContentMode.HTML);


        lr.setWidth("200px");

        gridLayoutCaption.addComponent(ll);
        gridLayoutCaption.addComponent(lCapt);
        gridLayoutCaption.addComponent(lr);
        gridLayoutCaption.setComponentAlignment(lCapt, Alignment.TOP_CENTER);
        gridLayoutCaption.setColumnExpandRatio(1,1);

        //gridLayout.setWidth("100%");
        //gridLayout.setHeightUndefined();
        gridLayout.setSpacing(true);
        gridLayout.setMargin(false);
        gridLayout.setHeight("550px");

        gridLayout.addComponent(imageLeft);
        gridLayout.setComponentAlignment(imageLeft, Alignment.MIDDLE_CENTER);

        slide.addComponent(lispSliders.get(currentSlide));
        slide.setComponentAlignment(lispSliders.get(currentSlide), Alignment.TOP_CENTER);

        gridLayout.addComponent(slide);
        gridLayout.addComponent(imageRight);
        gridLayout.setComponentAlignment(imageRight, Alignment.MIDDLE_CENTER);
        gridLayout.setColumnExpandRatio(1,1);

//        addComponent(lCapt);
        addComponent(gridLayoutCaption);
        addComponent(gridLayout);
        setComponentAlignment(gridLayout,Alignment.TOP_CENTER);
        isInit =  true;

    }

    private void refreshNumberPage() {
        Label label = (Label) gridLayoutCaption.getComponent(2, 0);
        String textSlideNumber = "Шаг "+(currentSlide+1)+" из "+lispSliders.size()+"  ";
        String textR = "<style type=\"text/css\">p {margin: 0.2em;} p.dline {line-height: 1;} </style><p align=\"center\"><font color=\"#FF0000\" face=\"Uni Sans Regular\" size=\"3\">"+textSlideNumber+"</font></p><p class=\"dline\" align=\"justify\"></p>";
        label.setValue(textR);
        label.setImmediate(true);

    }

    private void refreshArrow(){
        if (currentSlide == 0){
            imageLeft.setVisible(false);
        } else {
            imageLeft.setVisible(true);
        }

        if ((currentSlide == lispSliders.size()-1) || (currentSlide >= stateTrack.getActiveLength()-1)){
            imageRight.setVisible(false);
        } else{
            imageRight.setVisible(true);
        }
    }

    private void createLispSlisers(DaoDogovors.StateTrack stateTrack){
        lispSliders.clear();
        SlideLoad slideLoad = null;
        SlideUpload slideUpload = null;

        slideLoad = SlideLoad.createSlide(SlideLoad.SLIDELOAD_DOCS);
        slideLoad.init();
        slideLoad.setNotify(notifySlider);
        lispSliders.add(slideLoad);
        // выгрузить требуемые документы
        slideUpload = SlideUpload.createSlide(SlideUpload.UPLOAD_DOCS);
        slideUpload.init();
        slideUpload.setNotify(notifySlider);
        lispSliders.add(slideUpload);

        switch (stateTrack.getIdTrack()){
            case 1 :{
                // загрузить программу 1С
                slideLoad = SlideLoad.createSlide(SlideLoad.SLIDELOAD_1C);
                slideLoad.init();
                slideLoad.setNotify(notifySlider);
                lispSliders.add(slideLoad);
                // выгрузить программу 1С
                slideUpload = SlideUpload.createSlide(SlideUpload.UPLOAD_1C);
                slideUpload.init();
                slideUpload.setNotify(notifySlider);
                lispSliders.add(slideUpload);
                // загрузить расширенную форму
                slideLoad = SlideLoad.createSlide(SlideLoad.SLIDELOAD_SVED);
                slideLoad.init();
                slideLoad.setNotify(notifySlider);
                lispSliders.add(slideLoad);
                // выгрузить расширенную форму
                slideUpload = SlideUpload.createSlide(SlideUpload.UPLOAD_SVED);
                slideUpload.init();
                slideUpload.setNotify(notifySlider);
                lispSliders.add(slideUpload);
                break;
            }
            case 2 :{
//                // загрузить шаблон структуры предприятия
//                slideLoad = SlideLoad.createSlide(SlideLoad.SLIDELOAD_STRUCTURE);
//                slideLoad.init();
//                slideLoad.setNotify(notifySlider);
//                lispSliders.add(slideLoad);
//                // выгрузить структуру предприятия
//                slideUpload = SlideUpload.createSlide(SlideUpload.UPLOAD_STRUCTURE);
//                slideUpload.init();
//                slideUpload.setNotify(notifySlider);
//                lispSliders.add(slideUpload);
                // загрузить расширенную форму
                slideLoad = SlideLoad.createSlide(SlideLoad.SLIDELOAD_SVED);
                slideLoad.init();
                slideLoad.setNotify(notifySlider);
                lispSliders.add(slideLoad);
                // выгрузить расширенную форму
                slideUpload = SlideUpload.createSlide(SlideUpload.UPLOAD_SVED);
                slideUpload.init();
                slideUpload.setNotify(notifySlider);
                lispSliders.add(slideUpload);
                break;
            }
            case 3 :{
                break;
            }
        }

    }

    public void notifyUpdateDogovor(){
        if (!isInit) return;
        if (VaadinSession.getCurrent().getAttribute("idDogovor") != null) {
            idDogovor = (BigDecimal) VaadinSession.getCurrent().getAttribute("idDogovor");
        }

        //переустановить договор на новое значение если оно отличается от текущего (смена со стороны)
        if (VaadinSession.getCurrent().getAttribute("stateTrack")!= null){
            stateTrack = (DaoDogovors.StateTrack) VaadinSession.getCurrent().getAttribute("stateTrack");
            currentSlide = stateTrack.getCurrentstate()-1;
        }
        createLispSlisers(stateTrack);
        slide.removeAllComponents();
        slide.addComponent(lispSliders.get(currentSlide));
        slide.setComponentAlignment(lispSliders.get(currentSlide), Alignment.TOP_CENTER);

        refreshArrow();
        refreshNumberPage();
        setImmediate(true);
    }

//    @Override
//    public void animationEnd(Animator.AnimationEndEvent animationEndEvent) {
//        Notification.show(" конец операции");
//
//    }


    class AnimatorSlide {
        ArrayList<Component> arrayList =  new ArrayList<Component>();
        public void addSlide(Component slide){
            arrayList.add(slide);
        }
        public void animate(int vidAnamation){
            int maxSdvig = 2000;
            int sdvig = 0;

//            Animator a = new Animator(new Label());
//            AnimatorProxy proxy = new AnimatorProxy();proxy.animate(new Label(""), AnimType.ROLL_RIGHT_OPEN)
            switch (vidAnamation){
                case 1:sdvig = maxSdvig;
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide), new Css().opacity(1));
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide), new Css().translateX(sdvig+"px")).delay(100).duration(2000);
//                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide-1), new Css().opacity(1));
//                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide-1), new Css().translateX(sdvig+"px")).delay(100).duration(2000);
                               break;
                case -1:sdvig = -maxSdvig;
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide), new Css().opacity(1));
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide), new Css().translateX(sdvig+"px")).delay(100).duration(2000);
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide+1), new Css().opacity(1));
                    Animator.animate((AbstractComponent) lispSliders.get(currentSlide+1), new Css().translateX(sdvig+"px")).delay(100).duration(2000);
                                break;
            }

//            Animator.animate((AbstractComponent) arrayList.get(0), new Css().opacity(1));
//            Animator.animate((AbstractComponent) arrayList.get(0), new Css().translateX(sdvig+"px")).delay(1000).duration(2000);

//            Animator.animate((AbstractComponent) arrayList.get(1), new Css().opacity(0.5));
//            Animator.animate((AbstractComponent) arrayList.get(1), new Css().translateX("1000px")).delay(1000).duration(0);

//            Animator.animate((AbstractComponent) arrayList.get(1), new Css().setProperty("animation-direction", "reverse")).sendEndEvent();
//            Animator an = new Animator(getUI()).addListener(new Animator.AnimationListener() {
//                @Override
//                public void animationEnd(Animator.AnimationEndEvent animationEndEvent) {
//
//                }
//            });
            }




//            Animator.AttachListener(new Animator().)
//            Animator.animate((AbstractComponent) arrayList.get(1), new Css().translateX("-1000px")).delay(1000).duration(2000);
//            Animator animator = new Animator(this.UI);
//            animator.addListener();

//            Animator anim = new Animator(getUI());
//            anim. rollDown(300, 200);
//            anim.fadeIn(300, 400);
//            addComponent(anim);

        }

//    class myAnimator extends Animator{
//        @Override
//
//
//    }
}
