package com.jw05.app.anish.calabashbros;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Monster extends Creature implements Runnable {


    private static final long serialVersionUID = 1L;
    protected int bothX;
    protected int bothY;
    protected int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

    public Monster(World world, char glyph, Color color, int healthLimit, int attack, int exp) {
        super(color, glyph, world, true);
        this.healthLimit = healthLimit;
        this.health = healthLimit;
        this.attack = attack;
        this.exp = exp;
    }

    @Override
    public void run() {
        bothX = getX();
        bothY = getY();
        while (health > 0) {
            autoWalk();
            autoAttack();
        }
        world.put(tempThing, getX(), getY());
    }

    protected void autoWalk() {
        Random random = new Random();
        while (health > 0) {
            if (inSight(2) != -1)
                return;
            int choose = random.nextInt(4);
            while (tryWalk(choose)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (inSight(2) != -1)
                    return;
            }
        }
    }

    protected void autoAttack() {
        while (health > 0) {
            if (inSight(5) == -1)
                return;
            tryWalk(inSight(5));
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tryAttack(inSight(5));
        }
    }

    protected boolean tryWalk(int choose)
    {
        
        if (choose == -1) return false;
        int x = getX(), y = getY();
        int targetx = x + directions[choose][0], targety = y + directions[choose][1];
        if (inBoundary(targetx, targety))
        {
            return world.tryMove(this, x, y, targetx, targety);
        }
        return false;
    }

    protected void tryAttack(int choose)
    {
        if (choose == -1) return;
        int x = getX(), y = getY();
        int targetx = x + directions[choose][0], targety = y + directions[choose][1];
        if (!inBoundary(targetx, targety)) return;
        Thing th = world.get(targetx, targety);
        if (th.isCreature() && !th.isEvil())
        {
            Random random = new Random();
            int damage = random.nextInt(attack) + attack / 2;
            th.decreaseHealth(damage);           
        }
    }

    protected boolean inBoundary(int x, int y)
    {
        return x >= 0 && x < World.MAZE_WIDTH && y >= 0 && y < World.MAZE_HEIGHT && x >= bothX - 5 && x <= bothX + 5
                && y >= bothY - 5 && y <= bothY + 5;
    }

    protected int inSight(int d)
    {
        int x = getX(), y = getY();
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 1; j <= d; ++j)
            {
                int targetx = x + directions[i][0] * j, targety = y + directions[i][1] * j;
                if (!inBoundary(targetx, targety)) continue;
                Thing th = world.get(targetx, targety);
                if (th.isCreature() && !th.isEvil()) return i;
                else if (!th.isStandable()) break;
            }
        }
        return -1;
    }

    @Override
    public int getExp()
    {
        if (health == 0)
        {
            int temp = exp;
            exp = 0;
            return temp;
        }
        return 0;
    }

}