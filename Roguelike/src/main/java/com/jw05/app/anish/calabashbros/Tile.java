package com.jw05.app.anish.calabashbros;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Tile<T extends Thing> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Expose
    private T thing;
    @Expose
    private int xPos;
    @Expose
    private int yPos;

    public T getThing() {
        return thing;
    }

    public void setThing(T thing) {
        this.thing = thing;
        this.thing.setTile(this);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Tile() {
        this.xPos = -1;
        this.yPos = -1;
    }

    public Tile(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

}
