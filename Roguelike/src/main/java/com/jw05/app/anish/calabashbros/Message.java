package com.jw05.app.anish.calabashbros;

import com.google.gson.annotations.Expose;

public class Message implements Cloneable {

    @Expose
    private String level;
    @Expose
    private String health;
    @Expose
    private String magic;
    @Expose
    private String attack;
    @Expose
    private String exp;
    @Expose
    private String skill1;
    @Expose
    private String skill2;

    public Message() {
    }

    public Message(Message mes) {

    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public void setSkill1(String skill) {
        this.skill1 = skill;
    }

    public void setSkill2(String skill) {
        this.skill2 = skill;
    }

    public String getLevel() {
        return level;
    }

    public String getHealth() {
        return health;
    }

    public String getMagic() {
        return magic;
    }

    public String getAttack() {
        return attack;
    }

    public String getExp() {
        return exp;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}