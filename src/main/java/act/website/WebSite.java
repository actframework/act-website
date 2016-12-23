package act.website;

import act.boot.app.RunApp;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.result.Result;

import static act.controller.Controller.Util.render;

public class WebSite {

    @GetAction("/")
    public Result home() {
        return render();
    }

    public static void main(String[] args) throws Exception {
        RunApp.start(WebSite.class);
    }

}
