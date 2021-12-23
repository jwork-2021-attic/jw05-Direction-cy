package com.jw05.app.anish.calabashbros;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

class Flame extends Thing implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int moveX;
    private final int moveY;
    private final Creature c;


    public Flame(World world, Creature c, int moveX, int moveY) {
        super(Color.red, (char) 13, world, false, false, false, false);
        tempThing = c;
        this.c = c;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public void run() {
        for (int i = 1; i <= 5; ++i) {
            int x = getX(), y = getY();
            int targetx = x + moveX, targety = y + moveY;
            if (targetx >= 0 && targetx < World.MAZE_WIDTH && targety >= 0 && targety < World.MAZE_HEIGHT) {
                Thing th = world.get(targetx, targety);
                if (world.tryMove(this, x, y, targetx, targety))
                    ;
                else if (th.isEvil() == !c.isEvil()) {
                        Random random = new Random();
                        int damage = random.nextInt(c.attack) + 2 * c.getAttack();
                        th.decreaseHealth(damage);
                        c.levelUp(th.getExp());
                        world.put(tempThing, x, y);
                        return;
                     }
                     else{
                         world.put(tempThing, x, y);
                         world.put(new Floor(world), targetx, targety);
                         return;
                     }
                     
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else 
            {
                world.put(tempThing, x, y);
                return;
            }
        }
        world.put(tempThing, getX(), getY());
    }

}