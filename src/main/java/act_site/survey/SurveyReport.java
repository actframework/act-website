package act_site.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SurveyReport {
    public int responders;
    public int maxCoursePrice;
    public int minCoursePrice;
    public int averageCoursePrice;
    public SortedMap<String, Integer> modules = new TreeMap<>();
    public List<String> requestForConsulting = new ArrayList<>();
    public List<String> requestForPersonalCourse = new ArrayList<>();
}
