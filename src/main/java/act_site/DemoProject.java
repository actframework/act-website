package act_site;

import act.db.morphia.MorphiaAdaptiveRecord;
import act.db.morphia.MorphiaDao;
import act.job.Every;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.mongodb.morphia.annotations.Entity;
import org.osgl.$;
import org.osgl.util.C;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity("demo")
public class DemoProject extends MorphiaAdaptiveRecord<DemoProject> implements Comparable<DemoProject> {

    public String name;
    public String description;

    @Override
    public int compareTo(DemoProject o) {
        return name.compareTo(o.name);
    }

    public String getDownloadUrl() {
        return "https://github.com/act-gallery/" + name + "/archive/master.zip";
    }

    public static class Dao extends MorphiaDao<DemoProject> {

        @Every("1h")
        public void sync() {
            try {
                List<DemoProject> demoProjects = doSyncGithub();
                this.drop();
                this.save(demoProjects);
            } catch (Exception e) {
                warn(e, "Error sync demo projects from act-gallery github organisation");
            }
        }

        public List<DemoProject> list() {
            return C.newList(findAll()).sorted();
        }

        private List<DemoProject> doSyncGithub() throws Exception {
            List<DemoProject> list = new ArrayList<>();
            OkHttpClient http = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS).build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url("https://api.github.com/orgs/act-gallery/repos").get().build();
            Response response = http.newCall(request).execute();
            JSONArray array = JSON.parseArray(response.body().string());
            for (int i = 0; i < array.size(); ++i) {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");
                if ("crud".equalsIgnoreCase(name)
                        || name.contains("obsolete")
                        || name.contains("ebean2")
                        || name.contains("feature-test")
                        || name.contains("jax-rs")
                        || name.contains("single-jar")
                        || name.contains("java7")
                ) {
                    continue; // skip crud as it is not a normal showcase project
                }
                DemoProject proj = $.copy(array.getJSONObject(i)).filter("-id").to(DemoProject.class);
                proj.name = proj.name.replace("transaction", "tx");
                list.add(proj);
            }
            Collections.sort(list);
            return list;
        }

    }

}
