package rs.raf.repositories.activity;

import rs.raf.entities.Activity;

import java.util.List;

public interface ActivityRepository {

    public Activity addActivity(Activity activity);
    public Activity findActivity(Integer id);
    public List<Activity> allActivities();
}
