package com.jw05.app.anish.calabashbros;

import java.awt.Color;

public class Creature extends Thing {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected boolean key;

    Creature(Color color, char glyph, World world, boolean evil) {
        super(color, glyph, world, false, false, true, evil);
        tempThing = new Floor(world);
        key = false;
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    public void levelUp(int x){}

    public void getKey()
    {
        key = true;
    }

    public boolean isOpen()
    {
        return key;
    }

}
