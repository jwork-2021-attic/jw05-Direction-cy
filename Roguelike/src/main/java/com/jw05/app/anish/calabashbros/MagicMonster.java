package com.jw05.app.anish.calabashbros;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class MagicMonster extends Monster{

    public MagicMonster(World world, Color color)
    {
        super(world, color, 30, 30, 5, 30);
    }

    @Override
    protected void autoAttack() {
        while (health > 0) {
            if (inSight(5) == -1)
                return;
            tryCast(inSight(5));
            try {
                TimeUnit.MILLISECONDS.sleep(800);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tryAttack(inSight(5));
        }
    }
    
    protected void tryCast(int choose)
    {
        int x = getX(), y = getY();
        int targetx = x + directions[choose][0], targety = y + directions[choose][1];
        if (targetx >= 0 && targetx < world.MAZE_WIDTH && targety >= 0 && targety < world.MAZE_HEIGHT) {
            Flame flame = new Flame(world, this, directions[choose][0], directions[choose][1]);
            Thread thread = new Thread(flame);
            world.put(flame, x, y);
            world.put(this, x, y);
            thread.start();
        }
    }
    
}