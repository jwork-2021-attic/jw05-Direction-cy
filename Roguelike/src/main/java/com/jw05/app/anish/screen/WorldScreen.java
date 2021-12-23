package com.jw05.app.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.annotations.Expose;
import com.jw05.app.anish.calabashbros.Calabash;
import com.jw05.app.anish.calabashbros.CommonMonster;
import com.jw05.app.anish.calabashbros.Door;
import com.jw05.app.anish.calabashbros.EliteMonster;
import com.jw05.app.anish.calabashbros.ExpPacket;
import com.jw05.app.anish.calabashbros.Floor;
import com.jw05.app.anish.calabashbros.MazeGenerator;
import com.jw05.app.anish.calabashbros.Message;
import com.jw05.app.anish.calabashbros.Monster;
import com.jw05.app.anish.calabashbros.HealthMedication;
import com.jw05.app.anish.calabashbros.Key;
import com.jw05.app.anish.calabashbros.MagicMedication;
import com.jw05.app.anish.calabashbros.MagicMonster;
import com.jw05.app.anish.calabashbros.Wall;
import com.jw05.app.anish.calabashbros.Weapon;
import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {
    @Expose
    private World world;
    private MazeGenerator mazegenerator;
    private ExecutorService exec;
    private int[][] maze;
    private int recordCnt;
    private Map<SocketChannel, Message> messagesMap;
    private Map<SocketChannel, Calabash> brosMap;
    @Expose
    private Message mes = null;

    public WorldScreen(Map<String, SocketChannel> clientsMap) {
        init();
        brosMap = new HashMap<SocketChannel, Calabash>();
        messagesMap = new HashMap<SocketChannel, Message>();
        int i = 0;
        if(!clientsMap.isEmpty()){
            for(Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()){
                Calabash bro = new Calabash(Color.white, world);
                Message message = new Message();
                brosMap.put(entry.getValue(), bro);
                messagesMap.put(entry.getValue(), message);
                setMessage(entry.getValue());
                world.put(bro, i++, 0);
            }
        }
       
        world.put(new Door(world), World.MAZE_WIDTH - 1, World.MAZE_HEIGHT - 1);
        exec.shutdown();
        recordCnt = 0;

    }

    public void init(){
        Random random = new Random();
        world = new World();
        mazegenerator = new MazeGenerator(World.MAZE_WIDTH, World.MAZE_HEIGHT);
        mazegenerator.generateMaze();
        maze = mazegenerator.getArraysMaze();
        for (int i = 0; i < World.MAZE_WIDTH; ++i)
            for (int j = 0; j < World.MAZE_HEIGHT; ++j)
                if (maze[i][j] == 0) {
                    if (random.nextInt(3) < 2)
                        world.put(new Wall(world), i, j);
                }
        exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            Monster mons = new CommonMonster(world, Color.yellow);
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }
        for (int i = 0; i < 3; i++) {
            Monster mons = new MagicMonster(world, Color.pink);
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }

        for (int i = 0; i < 2; i++) {
            Monster mons = new EliteMonster(world, Color.red);
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(mons, x, y);
            exec.execute(mons);
        }

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new HealthMedication(world), x, y);
        }

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new MagicMedication(world), x, y);
        }

        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new ExpPacket(world), x, y);
        }

        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new Weapon(world), x, y);
        }

        for (int i = 0; i < 1; i++) {
            int x = random.nextInt(World.MAZE_WIDTH), y = random.nextInt(World.MAZE_HEIGHT);
            while (maze[x][y] == 0) {
                x = random.nextInt(World.MAZE_WIDTH);
                y = random.nextInt(World.MAZE_HEIGHT);
            }
            maze[x][y] = 0;
            world.put(new Key(world), x, y);
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int x = 0; x < World.MAZE_WIDTH; x++) {
            for (int y = 0; y < World.MAZE_HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y);
            }
        }
        if(messagesMap != null && !messagesMap.isEmpty()){
            for(Map.Entry<SocketChannel,Message> entry : messagesMap.entrySet()){
                setMessage(entry.getKey());
            }
        }
        if (mes != null)
        {
        terminal.write(mes.getLevel(), World.MAZE_WIDTH + 2, 1);
        terminal.write(mes.getHealth(), World.MAZE_WIDTH + 2, 4);
        terminal.write(mes.getMagic(), World.MAZE_WIDTH + 2, 7);
        terminal.write(mes.getAttack(), World.MAZE_WIDTH + 2, 10);
        terminal.write(mes.getExp(), World.MAZE_WIDTH + 2, 13);
        terminal.write(mes.getSkill1(),World.MAZE_WIDTH + 2, 16);
        terminal.write(mes.getSkill2(),World.MAZE_WIDTH + 2, 18);
        }
        

        try {
            record();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public Screen respondToUserInput(KeyEvent key) {
        /*
        if (bro.isOpen() && bro.getX() == World.MAZE_WIDTH - 1 && bro.getY() == World.MAZE_HEIGHT - 1) {
            return new VictoryScreen();
        }
        if (bro.getHealth() == 0){
            return new DefeatScreen();
        }

        int keycode = key.getKeyCode();
        switch (keycode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bro.tryWalk(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                bro.tryWalk(0, 1);
                break;
            case KeyEvent.VK_S:
                if (key.isControlDown())
                    try {
                        write();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                else
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
            case KeyEvent.VK_Q:
                if (key.isControlDown())
                    return new StartScreen();
            default:
                break;
        }*/
        return this;
        
    }

    public void write() throws IOException, ClassNotFoundException
    {
        FileOutputStream fileOutputStream = new FileOutputStream(this.getClass().getResource("/").getPath() + "resources/gameRecords/World.txt");
        ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(world);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void record() throws IOException, ClassNotFoundException
    {
        recordCnt++;
        FileOutputStream fileOutputStream = new FileOutputStream(this.getClass().getResource("/").getPath() + "resources/gameRecords/Record" + recordCnt + ".txt");
        ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(world);
        objectOutputStream.flush();
        objectOutputStream.close();
        PrintWriter outputStream = null;
        outputStream = new PrintWriter(new FileWriter(this.getClass().getResource("/").getPath() + "resources/gameRecords/RecordCnt.txt"));
        outputStream.println(recordCnt);
        outputStream.close();
    }

    @Override
    public Screen response(int keycode, SocketChannel socketChannel) {
        Calabash bro = brosMap.get(socketChannel);
        if (bro.isOpen() && bro.getX() == World.MAZE_WIDTH - 1 && bro.getY() == World.MAZE_HEIGHT - 1) {
            return new VictoryScreen();
        }
        if (bro.getHealth() == 0){
            world.put(new Floor(world), bro.getX(), bro.getY());
            return new DefeatScreen();
        }

        switch (keycode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                bro.tryWalk(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                bro.tryWalk(0, 1);
                break;
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
            case KeyEvent.VK_Q:
                return new StartScreen();
            default:
                break;
        }
        setMessage(socketChannel);
        return this;
    }

    void setMessage(SocketChannel socketChannel)
    {
        Message message = messagesMap.get(socketChannel);
        Calabash bro = brosMap.get(socketChannel);
        message.setLevel("LEVEL: " + bro.getLevel());
        message.setHealth("HP: " + bro.getHealth() + " / " + bro.getHealthLimit());
        message.setMagic("MP: " + bro.getMagic() + " / " + bro.getMagicLimit());
        message.setAttack("ATTACK: " + bro.getAttack());
        message.setExp("EXP: " + bro.getExp() + " / " + 100);
        message.setSkill1("SKILL:CAST FLAME");
        message.setSkill2(2 * bro.getAttack() + " ATTACK / " + "10 MP");
    }

    @Override
    public void setMap(Map<String, SocketChannel> clientsMap) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setChannelMessage(SocketChannel socketChannel) {
        // TODO Auto-generated method stub
        mes =messagesMap.get(socketChannel);
    }
}
