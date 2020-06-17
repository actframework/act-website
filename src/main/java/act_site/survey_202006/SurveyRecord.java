package act_site.survey_202006;

import act.controller.annotation.UrlContext;
import act.db.morphia.MorphiaAdaptiveRecord;
import act.db.morphia.MorphiaDao;
import org.mongodb.morphia.annotations.Entity;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.C;
import org.osgl.util.S;

import java.util.*;

import static act.controller.Controller.Util.*;

@Entity("sr")
public class SurveyRecord extends MorphiaAdaptiveRecord<SurveyRecord> {

    public static class Report {
        int responders;
        int maxCoursePrice;
        int minCoursePrice;
        int averageCoursePrice;
        SortedMap<String, Integer> modules = new TreeMap<>();
        List<String> requestForConsulting = new ArrayList<>();
        List<String> requestForPersonalCourse = new ArrayList<>();
    }

    public String participant;
    public List<String> moduleExt;

    @UrlContext("/survey/202006")
    public static class Dao extends MorphiaDao<SurveyRecord> {
        @GetAction
        public void form() {
            renderTemplate("/survey.html");
        }

        @GetAction("report")
        public void report(String pass) {
            notFoundIf(S.neq(pass, System.getenv("survey202006pass")));
            Report report = report();
            renderTemplate("/survey_result.html", report);
        }

        @PostAction
        public void submit(SurveyRecord record) {
            String participant = record.participant;
            if (S.notBlank(participant)) {
                deleteBy("participant", participant);
                if (null != record.moduleExt && record.moduleExt.size() == 1) {
                    String lines = record.moduleExt.get(0);
                    record.moduleExt.clear();
                    record.moduleExt.addAll(C.listOf(lines.split("[\\n\\r]+")));
                }
                save(record);
            }
            redirect("/survey/202006/thank_you");
        }

        @GetAction("thank_you")
        public void thankYou() {
            renderHtml("<html><head><meta http-equiv=\"refresh\" content=\"1; URL='http://actframework.org'\" /></head><body><h1>谢谢</h1></body></html>");
        }

        private Report report() {
            Report report = new Report();
            reportTotalResponders(report);
            reportCoursePrices(report);
            reportModules(report);
            reportRequestForConsulting(report);
            reportRequestForPersonalCourse(report);
            return report;
        }

        private void reportTotalResponders(Report report) {
            report.responders = (int) count();
        }

        private void reportCoursePrices(Report report) {
            report.maxCoursePrice = max("coursePrice").intValue();
            report.minCoursePrice = min("coursePrice").intValue();
            report.averageCoursePrice = average("coursePrice").intValue();
        }

        private void reportModules(Report report) {
            for (SurveyRecord sr : findAll()) {
                aggregateModules(sr, report.modules);
            }
        }

        private void reportRequestForConsulting(Report report) {
            for (SurveyRecord sr: findBy("service:consulting", true)) {
                report.requestForConsulting.add(sr.participant);
            }
        }

        private void reportRequestForPersonalCourse(Report report) {
            for (SurveyRecord sr: findBy("service:personalCourse", true)) {
                report.requestForPersonalCourse.add(sr.participant);
            }
        }

        private void aggregateModules(SurveyRecord sr, Map<String, Integer> modules) {
            Map<String, Object> map = sr.asMap();
            for (Map.Entry<String, Object> entry: map.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("module:")) {
                    String moduleId = S.cut(key).afterLast(":");
                    modules.compute(moduleId, (mapKey, count) -> null == count ? 1 : count++);
                }
            }
        }
    }
}
