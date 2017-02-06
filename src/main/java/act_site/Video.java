package act_site;

import act.cli.Command;
import act.cli.JsonView;
import act.cli.Required;
import act.db.morphia.MorphiaDao;
import act.util.SimpleBean;

import java.util.Map;

import static act.controller.Controller.Util.notFoundIfNull;

public class Video extends MorphiaDao<Video> {

    public enum Platform {
        YOUTUBE() {
            @Override
            public String siteUrl(String id) {
                return "https://www.youtube.com/watch?v=" + id;
            }

            @Override
            public String embeddedUrl(String id) {
                return "https://www.youtube.com/embed/" + id;
            }
        }, TUDOU() {
            @Override
            public String siteUrl(String id) {
                return "http://www.tudou.com/programs/view/" + id;
            }

            @Override
            public String embeddedUrl(String id) {
                return "http://www.tudou.com/programs/view/html5embed.action?autoPlay=false&playType=AUTO&code=" + id;
            }
        }, YOUKU() {
            @Override
            public String siteUrl(String id) {
                return "http://v.youku.com/v_show/id_" + id;
            }

            @Override
            public String embeddedUrl(String id) {
                return "http://player.youku.com/embed/" + id;
            }
        };

        public abstract String siteUrl(String id);

        public abstract String embeddedUrl(String id);
    }

    public static class Source implements SimpleBean {
        public String id;
        public Platform platform;

        public String siteUrl() {
            return platform.siteUrl(id);
        }

        public String embeddedUrl() {
            return platform.embeddedUrl(id);
        }
    }

    public String title;
    public String desc;
    // Map source to a language
    public Map<String, Source> sources;

    public boolean hasSource(String lang) {
        return sources.containsKey(lang);
    }

    public Source source(String lang) {
        return sources.get(lang);
    }

    @Command(name = "video.create", help = "create video record")
    @JsonView
    public static Video create(
            @Required("specify video title") String title,
            @Required("specify video description") String desc,
            Dao dao
    ) {
        return dao.create(title, desc);
    }

    @Command(name = "video.source", help = "set video source")
    public static Video setSource(
            @Required("specify video id") String id,
            @Required("specify language") String lang,
            @Required("specify video platform ID") String videoId,
            @Required("specify platform") Platform platform,
            Dao dao
    ) {
        Video video = dao.findById(id);
        notFoundIfNull(video);
        return dao.updateSource(video, lang, videoId, platform);
    }

    public static class Dao extends MorphiaDao<Video> {

        public Video create(String title, String desc) {
            Video video = new Video();
            video.title = title;
            video.desc = desc;
            save(video);
            return video;
        }

        public Video updateSource(Video video, String lang, String videoId, Platform platform) {
            Source source = new Source();
            source.id = videoId;
            source.platform = platform;
            video.sources.put(lang, source);
            save(video);
            return video;
        }

    }


}
