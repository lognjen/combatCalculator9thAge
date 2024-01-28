package armies;

public class unit {
    public String name, type;
    public Integer adv, mar, dis, hp, def, res, arm, att, off, str, ap, agi, models, columns;

    public Boolean champ, bnb, mus;

    public unit(String name, String type, Integer adv, Integer mar, Integer dis, Integer hp, Integer def, Integer res, Integer arm, Integer att, Integer off, Integer str, Integer ap, Integer agi, Integer models, Integer columns, Boolean champ, Boolean bnb, Boolean mus) {
        this.name = name;
        this.type = type;
        this.adv = adv;
        this.mar = mar;
        this.dis = dis;
        this.hp = hp;
        this.def = def;
        this.res = res;
        this.arm = arm;
        this.att = att;
        this.off = off;
        this.str = str;
        this.ap = ap;
        this.agi = agi;
        this.models = models;
        this.columns = columns;
        this.champ = champ;
        this.bnb = bnb;
        this.mus = mus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAdv(Integer adv) {
        this.adv = adv;
    }

    public void setMar(Integer mar) {
        this.mar = mar;
    }

    public void setDis(Integer dis) {
        this.dis = dis;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public void setArm(Integer arm) {
        this.arm = arm;
    }

    public void setAtt(Integer att) {
        this.att = att;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    public void setStr(Integer str) {
        this.str = str;
    }

    public void setAp(Integer ap) {
        this.ap = ap;
    }

    public void setAgi(Integer agi) {
        this.agi = agi;
    }

    public void setModels(Integer models) {
        this.models = models;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public void setChamp(Boolean champ) {
        this.champ = champ;
    }

    public void setBnb(Boolean bnb) {
        this.bnb = bnb;
    }

    public void setMus(Boolean mus) {
        this.mus = mus;
    }
}
