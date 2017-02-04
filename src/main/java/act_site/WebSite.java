package act_site;

import act.Act;
import act.Version;
import act.app.conf.AppConfigurator;
import act.boot.app.RunApp;
import act.util.ActContext;
import act.view.TemplatePathResolver;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.result.Result;

import java.util.Locale;

import static act.controller.Controller.Util.render;

public class WebSite {

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

    @GetAction
    public void home() {
    }

    public static void main(String[] args) throws Exception {
        Act.start("Act", Version.appVersion(), WebSite.class);
    }

}
