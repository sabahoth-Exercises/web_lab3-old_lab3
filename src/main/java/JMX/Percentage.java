package JMX;

import lombok.Data;
import Data.Dot;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
@Data
@SessionScoped
@ManagedBean(name = "percentage")
public class Percentage extends NotificationBroadcasterSupport implements PercentageMBean {
    private int all_nums = 0;
    private int hit = 0;
    public double percentage = 0;
    private Dot dot = new Dot();

    private int sequenceNumber = 0;

    private static final Percentage PB = new Percentage();

    public static Percentage getInstance(){
        return PB;
    }


    @Override
    public void percentageGet() {
        update();
        if(all_nums!=0){
            setPercentage((double) Math.round(100-(hit*100/all_nums)));
        }

        Percentage.PB.setPercentage((double) Math.round(100-(hit*100/all_nums)));
        PB.sendMessage();
    }
    public void sendMessage(){
        sendNotification(new Notification("Percentage", this, sequenceNumber++,
                System.currentTimeMillis(), String.format("Percentage of NOT hit dot is %f",this.percentage)));
    }
    public void update(){
        dot = new Dot(dot.getX(),dot.getY(),dot.getR());
        if(dot.isHit()){
            hit = hit+1;
        }
        all_nums = all_nums + 1;

    }
}
