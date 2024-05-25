package rs.raf.repositories.activity;

import rs.raf.entities.Activity;

import java.util.List;

public interface ActivityRepository {

    public Activity addActivity(Activity activity);
    public Activity findActivity(String name);
    public List<Activity> allActivities();
}
