package com.jw05.app.anish.calabashbros;

public class World {

    public static final int WIDTH = 65;
    public static final int HEIGHT = 40;
    public static final int MAZE_WIDTH = 45;
    public static final int MAZE_HEIGHT = 30;

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
