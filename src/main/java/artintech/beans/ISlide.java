package artintech.beans;

/**
 * Created by Анатолий on 12.11.2015.
 */
public interface ISlide {
    public void setNotify(NotifySlider notify);
    public interface NotifySlider {
        void doNotify(Object object);
    }

}
