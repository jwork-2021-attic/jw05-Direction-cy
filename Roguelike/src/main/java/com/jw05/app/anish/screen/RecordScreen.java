package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jw05.app.anish.calabashbros.Calabash;
import com.jw05.app.anish.calabashbros.Monster;
import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiPanel;

public class RecordScreen implements Screen {

    private World world;
    private Calabash bro;
    private ExecutorService exec;
    private int recordCnt;

    public RecordScreen() {

        try {
            read();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        recordCnt = 0;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.MAZE_WIDTH; x++) {
            for (int y = 0; y < World.MAZE_HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
        terminal.write("LEVEL: " + bro.getLevel(), World.MAZE_WIDTH + 2, 1);
        terminal.write("HP: " + bro.getHealth() + " / " + bro.getHealthLimit(), World.MAZE_WIDTH + 2, 4);
        terminal.write("MP: " + bro.getMagic() + " / " + bro.getMagicLimit(), World.MAZE_WIDTH + 2, 7);
        terminal.write("ATTACK: " + bro.getAttack(), World.MAZE_WIDTH + 2, 10);
        terminal.write("EXP: " + bro.getExp() + " / " + 100, World.MAZE_WIDTH + 2, 13);
        terminal.write("SKILL:CAST FLAME",World.MAZE_WIDTH + 2, 16);
        terminal.write(2 * bro.getAttack() + " ATTACK / " + "10 MP",World.MAZE_WIDTH + 2, 18);

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
        }
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

    public void read() throws IOException, ClassNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "resources/gameRecords/World.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        world = (World) objectInputStream.readObject();
        objectInputStream.close();
        exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < World.MAZE_WIDTH; ++i)
            for (int j = 0; j < World.MAZE_HEIGHT; ++j)
            {
                if (world.get(i, j).isCreature())
                    if (!world.get(i,j).isEvil())
                        bro = (Calabash) world.get(i,j);
                    else
                        exec.execute((Monster) world.get(i, j));
            }
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
        if (bro.isOpen() && bro.getX() == World.MAZE_WIDTH - 1 && bro.getY() == World.MAZE_HEIGHT - 1) {
            return new VictoryScreen();
        }

        if (bro.getHealth() == 0){
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
        return this;
    }

    @Override
    public void setMap(Map<String, SocketChannel> clientsMap) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setChannelMessage(SocketChannel socketChannel) {
        // TODO Auto-generated method stub

    }

}
