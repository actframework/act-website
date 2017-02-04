package act_site;

import act.app.ActionContext;
import act.app.App;
import act.controller.Controller;
import act.controller.ParamNames;
import act.handler.RequestHandler;
import act.handler.RequestHandlerResolverBase;
import act.handler.builtin.StaticResourceGetter;
import act.handler.builtin.controller.FastRequestHandler;
import act.view.RenderTemplate;
import org.osgl.http.H;
import org.osgl.mvc.result.NotFound;
import org.osgl.mvc.result.Redirect;
import org.osgl.mvc.result.RenderBinary;
import org.osgl.mvc.result.Result;
import org.osgl.util.S;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Responsible for loading the Markdown documents
 */
@Singleton
public class DocLoader extends FastRequestHandler {

    public static class DocResourceGetter extends StaticResourceGetter {
        public DocResourceGetter() {
            super("/doc");
        }

        @Override
        public void handle(ActionContext context) {
            try {
                super.handle(context);
            } catch (NotFound notFound) {
                // the document doesn't have the version for the current language
                String path = context.paramVal(ParamNames.PATH);
                if (path.startsWith("/cn/")) {
                    path = path.replace("/cn/", "/en/");
                    super.handle(path, context);
                } else {
                    throw notFound;
                }
            }
        }
    }

    public static final String PATH_IMG = "/img";

    @Inject
    public DocLoader(App app) {
        app.router().addMapping(H.Method.GET, "/doc", this);
        app.router().addMapping(H.Method.GET, "/~doc/", new DocResourceGetter());
    }

    @Override
    public void handle(ActionContext context) {
        String path = context.paramVal(ParamNames.PATH);
        RenderTemplate result = _handle(path, context);
        result.apply(context);
    }

    private RenderTemplate _handle(String path, ActionContext context) {
        Locale locale = context.locale(true);
        String sLocale = locale.getLanguage();
        String lang = ("zh".equals(sLocale)) ? "cn" : "en";
        StringBuilder sb = S.builder("https://raw.githubusercontent.com/actframework/act-doc/master/").append(lang);
        if (path.contains("#")) {
            String[] pa = path.split("#");
            String pa1 = pa[0];
            sb.append(pa1);
            if (!pa1.endsWith(".md")) {
                sb.append(".md");
            }
            sb.append("#").append(pa[1]);
        } else {
            sb.append(path);
            if (!path.endsWith(".md")) {
                sb.append(".md");
            }
        }
        String newPath = sb.toString();
        context.renderArg("docPath", newPath);
        context.templatePath("/doc.html");
        return RenderTemplate.get();
    }

    @Override
    public boolean supportPartialPath() {
        return true;
    }
}
