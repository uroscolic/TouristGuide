package rs.raf.services;

import rs.raf.entities.Activity;
import rs.raf.repositories.activity.ActivityRepository;

import javax.inject.Inject;
import java.util.List;

public class ActivityService {

    @Inject
    private ActivityRepository activityRepository;

    public ActivityService() {
        System.out.println("Creating ActivityService");
    }

    public Activity addActivity(Activity activity) {
        return this.activityRepository.addActivity(activity);
    }
    public Activity findActivity(String name) {
        return this.activityRepository.findActivity(name);
    }
    public List<Activity> allActivities() {
        System.out.println("All activities in ActivityService");
        return this.activityRepository.allActivities();
    }

}
