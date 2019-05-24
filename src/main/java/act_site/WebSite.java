package act_site;

import act.Act;
import act.db.morphia.MorphiaDao;
import act.util.ActContext;
import org.osgl.mvc.annotation.GetAction;

import java.util.List;
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
    public void master(MorphiaDao<Skeleton> skeletonDao, Video.Dao videoDao, DemoProject.Dao demoProjectDao, ActContext context)
    {
        String lang = "en"; //default language
        Locale locale = context.locale(true);
        boolean isCn = false;
        String imageContext = "/image";
        if ("zh".equals(locale.getLanguage())) {
            lang = "cn" ; //supported language
            isCn = true;
            imageContext = "http://static.jinbaozheng.com/act";
        }
        Iterable<Skeleton> skeletons = skeletonDao.findAll();
        Iterable<Video> videos = videoDao.findAll();
        List<DemoProject> demoProjects = demoProjectDao.list();
        render(demoProjects, videos, lang, isCn, imageContext);
    }

    public static void main(String[] args) throws Exception {
        Act.start("ACT WEB SITE");
    }

}
