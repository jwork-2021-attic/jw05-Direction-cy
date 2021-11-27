package com.jw05.app.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jw05.app.anish.calabashbros.Calabash;
import com.jw05.app.anish.calabashbros.CommonMonster;
import com.jw05.app.anish.calabashbros.EliteMonster;
import com.jw05.app.anish.calabashbros.ExpPacket;
import com.jw05.app.anish.calabashbros.MazeGenerator;
import com.jw05.app.anish.calabashbros.Monster;
import com.jw05.app.anish.calabashbros.Floor;
import com.jw05.app.anish.calabashbros.HealthMedication;
import com.jw05.app.anish.calabashbros.Key;
import com.jw05.app.anish.calabashbros.MagicMedication;
import com.jw05.app.anish.calabashbros.MagicMonster;
import com.jw05.app.anish.calabashbros.Wall;
import com.jw05.app.anish.calabashbros.Weapon;
import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Calabash bro;
    private MazeGenerator mazegenerator;
    private ExecutorService exec;
    private int[][] maze;
    private boolean victory;
    String[] sortSteps;

    public WorldScreen() {
        Random random = new Random();

        world = new World();
        bro = new Calabash(Color.white, world);
        world.put(bro, 0, 0);

        mazegenerator = new MazeGenerator(world.MAZE_WIDTH, world.MAZE_HEIGHT);
        mazegenerator.generateMaze();
        maze = mazegenerator.getArraysMaze();
        for (int i = 0; i < world.MAZE_WIDTH; ++i)
            for (int j = 0; j < world.MAZE_HEIGHT; ++j)
                if (maze[i][j] == 0) {
                    if (random.nextBoolean())
                        world.put(new Wall(world), i, j);
                }
        exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            Monster mons = new CommonMonster(world, Color.yellow);
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }
        for (int i = 0; i < 3; i++) {
            Monster mons = new MagicMonster(world, Color.pink);
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }

        for (int i = 0; i < 2; i++) {
            Monster mons = new EliteMonster(world, Color.red);
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new HealthMedication(world), x, y);
        }

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new MagicMedication(world), x, y);
        }

        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new ExpPacket(world), x, y);
        }

        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new Weapon(world), x, y);
        }

        for (int i = 0; i < 1; i++) {
            int x = random.nextInt(world.MAZE_WIDTH), y = random.nextInt(world.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(world.MAZE_WIDTH);
                y = random.nextInt(world.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new Key(world), x, y);
        }

        exec.shutdown();

        victory = false;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.MAZE_WIDTH; x++) {
            for (int y = 0; y < World.MAZE_HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
                if (y == 1)
                    terminal.write("  LEVEL: " + bro.getLevel());
                if (y == 4)
                    terminal.write("  HP: " + bro.getHealth() + " / " + bro.getHealthLimit());
                if (y == 7)
                    terminal.write("  MP: " + bro.getMagic() + " / " + bro.getMagicLimit());
                if (y == 10)
                    terminal.write("  ATTACK: " + bro.getAttack());
                if (y == 13)
                    terminal.write("  EXP: " + bro.getExp() + " / " + 100);
            }
        }

        if (bro.isOpen() && bro.getX() == world.MAZE_WIDTH - 1 && bro.getY() == world.MAZE_HEIGHT - 1) {
            terminal.write("Victory!!!", 0, world.MAZE_HEIGHT + 1);
            victory = true;
        }
        
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int keycode = key.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bro.tryWalk(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                bro.tryWalk(0, 1);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                bro.tryWalk(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                bro.tryWalk(1, 0);
                break;
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_J:
                bro.tryAttack();
                break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_K:
                bro.tryBomb();
                break;
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_SPACE:
                bro.tryGet();
                break;
            default:
                break;
        } 
        return this;
    }

    @Override
    public boolean isVictory()
    {
        return victory;
    }
}
