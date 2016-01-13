package act.website;

import act.app.ActionContext;
import act.boot.app.RunApp;
import act.view.ActForbidden;
import org.osgl._;
import org.osgl.http.H;
import org.osgl.mvc.annotation.Before;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.result.Result;
import org.osgl.util.C;
import org.osgl.util.S;

import static act.controller.Controller.Util.*;

public class WebSite {

    @GetAction("/")
    public Result home() {
        return render();
    }

    public static void main(String[] args) throws Exception {
        RunApp.start(WebSite.class);
    }

}
