package com.jw05.app.anish.screen;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;
import java.util.Map;

import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.asciiPanel.AsciiPanel;

public class ReplayScreen implements Screen {
    private World world;
    private int recordCnt;
    private int recordLimit;

    public ReplayScreen() {
        recordCnt = 0;
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(
                    new FileReader(this.getClass().getResource("/").getPath() + "resources/gameRecords/RecordCnt.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            recordLimit = Integer.parseInt(inputStream.readLine());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        // TODO Auto-generated method stub
        try {
            read();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int x = 0; x < World.MAZE_WIDTH; x++) {
            for (int y = 0; y < World.MAZE_HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
        if (recordCnt > recordLimit)
            terminal.writeCenter("Press Ctrl + q exit", World.MAZE_HEIGHT + 1);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        // TODO Auto-generated method stub
        int keycode = key.getKeyCode();
        switch (keycode){
            case KeyEvent.VK_Q:
                if (key.isControlDown())
                    return new StartScreen();
            default:
                break;
        }
        return this;
    }

    public void read() throws IOException, ClassNotFoundException
    {
        recordCnt++;
        if (recordCnt > recordLimit)
            return;
        FileInputStream fileInputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "resources/gameRecords/Record" + recordCnt + ".txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        world = (World) objectInputStream.readObject();
        objectInputStream.close();
    }

    @Override
    public Screen response(int keycpde, SocketChannel socketChannel) {
        // TODO Auto-generated method stub
        return null;
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