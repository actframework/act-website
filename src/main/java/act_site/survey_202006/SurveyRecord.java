package act_site.survey_202006;

import act.controller.annotation.UrlContext;
import act.db.morphia.MorphiaAdaptiveRecord;
import act.db.morphia.MorphiaDao;
import org.mongodb.morphia.annotations.Entity;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.util.C;
import org.osgl.util.S;

import java.util.List;

import static act.controller.Controller.Util.*;

@Entity("sr")
public class SurveyRecord extends MorphiaAdaptiveRecord<SurveyRecord> {
    public String participant;
    public List<String> moduleExt;

    @UrlContext("/survey/202006")
    public static class Dao extends MorphiaDao<SurveyRecord> {
        @GetAction
        public void form() {
            renderTemplate("/survey.html");
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
    }
}
