package act_site;

import act.Act;
import act.app.conf.AppConfigurator;
import act.db.morphia.MorphiaDao;
import act.util.ActContext;
import act.view.TemplatePathResolver;
import org.osgl.mvc.annotation.GetAction;

import java.util.Locale;

import static act.controller.Controller.Util.render;

public class WebSite {

    /*
    public static class MyConfiguration extends AppConfigurator {
        @Override
        public void configure() {
            templatePathResolver(new TemplatePathResolver() {
                @Override
                protected String resolveTemplatePath(String path, ActContext context) {
                    String resolved = super.resolveTemplatePath(path, context);
                    Locale locale = context.locale(true);
                    if ("zh".equals(locale.getLanguage())) {
                        resolved = "/cn" + (resolved.startsWith("/") ? "" : "/") + resolved;
                    }
                    return resolved;
                }
            });
        }
    }
    */

    /*
    @GetAction
    public void home(MorphiaDao<Skeleton> skeletonDao, Video.Dao videoDao) {
        Iterable<Skeleton> skeletons = skeletonDao.findAll();
        Iterable<Video> videos = videoDao.findAll();
        render(skeletons, videos);
    }
    */

    @GetAction
    public void master()
    {
        render();
    }

    public static void main(String[] args) throws Exception {
        Act.start("ACT WEB SITE");
    }

}
