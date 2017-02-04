package act_site;

import act.cli.Command;
import act.cli.Required;
import act.db.morphia.MorphiaDao;
import act.db.morphia.MorphiaModel;

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

    @Command(name = "skeleton.create", help = "add project skeleton")
    public static void add(
            @Required("specify name") String name,
            @Required("specify description") String desc,
            @Required("specify file name") String fileName,
            MorphiaDao<Skeleton> dao
    ) {
        dao.save(new Skeleton(name, desc, fileName));
    }
}
