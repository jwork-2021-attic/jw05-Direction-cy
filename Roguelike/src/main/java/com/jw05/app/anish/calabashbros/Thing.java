package com.jw05.app.anish.calabashbros;

import java.awt.Color;
import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Thing implements Serializable{

    private static final long serialVersionUID = 1L;
    protected World world;
    protected Thing tempThing;
    private final boolean standable;
    private final boolean available;
    private final boolean living;
    private final boolean evil;
    protected int health;
    protected int attack;
    protected int healthLimit;
    protected int magicLimit;
    protected int magic;
    protected int exp;


    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public boolean isStandable(){
        return standable;
    }

    public boolean isAvailable(){
        return available;
    }

    public boolean isCreature(){
        return living;
    }

    public boolean isEvil(){
        return evil;
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    Thing(Color color, char glyph, World world, boolean standable, boolean available, boolean living, boolean evil) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
        this.standable = standable;
        this.available = available;
        this.living = living;
        this.evil = evil;
        this.health = 0;
        this.attack = 0;
        this.healthLimit = 0;
        this.exp = 0;
    }

    private Color color;

    public void setWorld(World world)
    {
        this.world = world;
    }
    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Thing getTempThing(){
        return tempThing;
    }

    public void setTempThing(Thing t)
    {
        tempThing = t;
    }

    @Expose
    private final char glyph;

    public char getGlyph() {
        return this.glyph;
    }

    public int getHealth()
    {
        return health;
    }

    public int getHealthLimit()
    {
        return healthLimit;
    }

    public int getMagic()
    {
        return magic;
    }

    public int getMagicLimit()
    {
        return magicLimit;
    }

    public int getAttack()
    {
        return attack;
    }

    public void increaseHealth(int x)
    {
        health = health + x <= healthLimit ? health + x : healthLimit;
    }

    public synchronized void decreaseHealth(int x)
    {
        health = health - x >= 0 ? health - x : 0;
    }

    public void increaseMagic(int x)
    {
        magic = magic + x <= magicLimit ? magic + x : magicLimit;
    }

    public synchronized boolean decreaseMagic(int x)
    {
        magic = magic - x >= 0 ? magic - x : magic;
        return magic - x >= 0;
    }

    public void increaseAttack(int x)
    {
        attack += x;
    }

    public void work(Creature c){}

    public int getExp(){
        return exp;
    }

}
