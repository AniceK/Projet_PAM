package khouvramany.wannago;

import android.location.Location;
import android.renderscript.Long2;

/**
 * Created by gautiercouvrat on 07/01/2017.
 */

public class Run {
    private long Duration ;
    private long Distance ;

    public Run() {

    }

    public Long getDuration() {
        return Duration;
    }

    public void setDuration(Long duration) {
        Duration = duration;
    }

    public Long getDistance() {
        return Distance;
    }

    public void setDistance(Long distance) {
        Distance = distance;
    }
}
