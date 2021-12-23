package com.jw05.app.anish.calabashbros;

import java.awt.Color;
import java.util.Random;

public class Calabash extends Creature {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int moveX, moveY;
    private int level;

    public Calabash(Color color, World world) {
        super(color, (char) 2, world, false);
        health = 100;
        healthLimit = 100;
        magic = 50;
        magicLimit = 50;
        attack = 10;
        moveX = 1;
        moveY = 0;
        exp = 0;
        level = 1;
    }

    public void tryWalk(int directionx, int directiony) {
        int x = getX(), y = getY();
        moveX = directionx;
        moveY = directiony;
        int targetx = x + directionx, targety = y + directiony;
        if (targetx >= 0 && targetx < World.MAZE_WIDTH && targety >= 0 && targety < World.MAZE_HEIGHT) {
            world.tryMove(this, x, y, targetx, targety);
        }
    }

    public synchronized void tryAttack() {
        int x = getX(), y = getY();
        int targetx = x + moveX, targety = y + moveY;
        if (targetx >= 0 && targetx < World.MAZE_WIDTH && targety >= 0 && targety < World.MAZE_HEIGHT
                && world.get(targetx, targety).isEvil()) {
            Thing th = world.get(targetx, targety);
            Random random = new Random();
            int damage = random.nextInt(attack) + attack / 2;
            th.decreaseHealth(damage);
            levelUp(th.getExp());
        }
    }

    public void tryBomb() {
        int x = getX(), y = getY();
        int targetx = x + moveX, targety = y + moveY;
        if (targetx >= 0 && targetx < World.MAZE_WIDTH && targety >= 0 && targety < World.MAZE_HEIGHT
                && decreaseMagic(10)) {
            Flame flame = new Flame(world, this, moveX, moveY);
            Thread thread = new Thread(flame);
            world.put(flame, x, y);
            world.put(this, x, y);
            thread.start();
        }
    }

    public void tryGet(){
        if (tempThing.isAvailable())
        {
            tempThing.work(this);
        }
    }

    @Override
    public void levelUp(int x)
    {
        exp += x;
        if (exp >= 100)
        {
            healthLimit += 100;
            magicLimit += 50;
            health = healthLimit;
            magic = magicLimit;
            attack += 10;
            level++;
            exp -= 100;
        }
    }

    public int getLevel()
    {
        return level;
    }


}

