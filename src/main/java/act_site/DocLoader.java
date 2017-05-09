package act_site;

import act.app.ActionContext;
import act.app.App;
import act.controller.Controller;
import act.controller.ParamNames;
import act.handler.builtin.controller.FastRequestHandler;
import act.view.RenderTemplate;
import org.osgl.http.H;
import org.osgl.util.S;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;

/**
 * Responsible for loading the Markdown documents
 */
@Singleton
public class DocLoader extends FastRequestHandler {

    @Inject
    public DocLoader(App app) {
        app.router().addMapping(H.Method.GET, "/doc", this);
    }

    @Override
    public void handle(ActionContext context) {
        String path = context.paramVal(ParamNames.PATH);
        RenderTemplate result = _handle(path, context);
        result.apply(context);
    }

    private RenderTemplate _handle(String path, ActionContext context) {

        Locale locale = context.locale(true);
        String newPath;
        String sLocale = locale.getLanguage();
        boolean isCn = ("zh".equals(sLocale));
        if (path.toLowerCase().startsWith("/release_notes")) {
            newPath = "https://raw.githubusercontent.com/actframework/act-doc/master/RELEASE_NOTES.md";
        } else if ("/".equals(path) || "".equals(path)) {
            throw Controller.Util.redirect("/doc/index.md");
        } else {
            String lang =  isCn ? "cn" : "en";
            String server = ("zh".equals(sLocale)) ? "http://git.oschina.net/actframework/act-doc/raw/master/" : "https://raw.githubusercontent.com/actframework/act-doc/master/";
            server = "https://raw.githubusercontent.com/actframework/act-doc/master/"; // until OSC fixed the CORS issue
            S.Buffer sb = S.newBuffer(server).append(lang);
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
            newPath = sb.toString();
        }
        String docId = S.afterLast(newPath, "/");
        if (docId.contains(".")) {
            docId = S.before(docId, ".");
        }
        context.renderArg("doc", docId.toLowerCase());
        context.renderArg("docPath", newPath);
        context.renderArg("isCn", isCn);
        context.renderArg("imageContext", isCn ? "http://static.jinbaozheng.com/act" : "/image");
        context.templatePath("/act_site/WebSite/doc.html");
        return RenderTemplate.get();
    }

    @Override
    public boolean supportPartialPath() {
        return true;
    }
}
