package com.jw05.app.anish.calabashbros;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class World implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Expose
    public static final int WIDTH = 50;
    @Expose
    public static final int HEIGHT = 28;
    @Expose
    public static final int MAZE_WIDTH = 30;
    @Expose
    public static final int MAZE_HEIGHT = 25;
    @Expose
    private Tile<Thing>[][] tiles;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < MAZE_WIDTH; i++) {
            for (int j = 0; j < MAZE_HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public synchronized boolean tryMove(Thing t, int x, int y, int targetx, int targety)
    {
        Thing th = get(targetx, targety);
        if (th.isStandable())
        {
            put(t.getTempThing(), x, y);
            t.setTempThing(th);
            put(t, targetx, targety);
            return true;
        }
        return false;
    }

}
