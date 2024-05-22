package rs.raf.repositories.activity;

import rs.raf.entities.Activity;
import rs.raf.repositories.MySqlAbstractRepository;

import java.util.List;

public class MySqlActivityRespository extends MySqlAbstractRepository implements ActivityRepository{
    @Override
    public Activity addActivity(Activity activity) {
        return null;
    }

    @Override
    public Activity findActivity(Integer id) {
        return null;
    }

    @Override
    public List<Activity> allActivities() {
        return null;
    }
}
