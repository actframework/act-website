package act_site;

import act.cli.Command;
import act.cli.Optional;
import act.cli.Required;
import act.db.morphia.MorphiaDao;
import act.db.morphia.MorphiaModel;
import com.alibaba.fastjson.JSON;
import org.osgl.util.S;

/**
 * A Skeleton record has
 * * name
 * * description
 * * download package name
 */
public class Skeleton extends MorphiaModel<Skeleton> {
    public String name;
    public String desc;
    public String fileName;

    public Skeleton(String name, String desc, String fileName) {
        this.name = name;
        this.desc = desc;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Command(name = "skeleton.create", help = "add project skeleton")
    public static Skeleton add(
            @Required("specify name") String name,
            @Required("specify description") String desc,
            @Required("specify file name") String fileName,
            MorphiaDao<Skeleton> dao
    ) {
        return dao.save(new Skeleton(name, desc, fileName));
    }

    @Command(name = "skeleton.updateFilename", help = "update project skeleton")
    public static Skeleton update(
            @Required("specify ID") String id,
            @Optional("specify name") String name,
            @Optional("specify description") String desc,
            @Optional("specify file name") String fileName,
            MorphiaDao<Skeleton> dao
    ) {
        Skeleton skeleton = dao.findById(id);
        if (S.notBlank(name)) {
            skeleton.name = name;
        }
        if (S.notBlank(desc)) {
            skeleton.desc = desc;
        }
        if (S.notBlank(fileName)) {
            skeleton.fileName = fileName;
        }
        return dao.save(skeleton);
    }
}
