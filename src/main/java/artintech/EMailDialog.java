package artintech;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.*;

import javax.mail.search.RecipientStringTerm;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 24.01.15
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class EMailDialog extends Window {
//    Window parent;
//    Window dialog;
    Recipient r;
//    public EMailDialog(Window parentWnd, final Recipient recipient){
    public EMailDialog(final Recipient recipient){
//        parent = parentWnd;
//        dialog = this;
        this.r = recipient;
        this.addStyleName("greylayout");
        setCaption("Ввод почтового адреса");
        setModal(true);
        VerticalLayout layout = new VerticalLayout(); //(VerticalLayout) getContent();
        layout.setSizeUndefined();
        layout.setMargin(true);
        layout.setSpacing(true);
        final TextField inputText = new TextField("Введите ваш eMail.");
        inputText.setWidth("240px");
        inputText.addValidator(new EmailValidator("Неверный формат почтового адреса"));
        HorizontalLayout hl = new HorizontalLayout();
        Button close = new Button("Готово", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (inputText.isValid()){
                    r.gotInput(inputText.getValue());
                    close();
                } else {
                    Notification.show("Неверный формат почтового адреса");
                }
            }
        });
        Button reset = new Button("Отказ");
        reset.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
                close();
//                getUI().removeWindow(dialog);
            }
        });
        reset.setWidth("120px");
        close.setWidth("120px");
        hl.addComponent(reset);
        hl.setComponentAlignment(reset, Alignment.TOP_LEFT);
        hl.addComponent(close);
        hl.setComponentAlignment(close, Alignment.TOP_RIGHT);
        layout.addComponent(inputText);
        //layout.addComponent(close);
        layout.addComponent(hl);
        this.setContent(layout);
    }
    public interface Recipient {
        public void gotInput(String in);
    }

}
