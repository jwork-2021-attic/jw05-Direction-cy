package com.jw05.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.anish.screen.DefeatScreen;
import com.jw05.app.anish.screen.Screen;
import com.jw05.app.anish.screen.StartScreen;
import com.jw05.app.anish.screen.VictoryScreen;
import com.jw05.app.asciiPanel.AsciiFont;
import com.jw05.app.asciiPanel.AsciiPanel;

public class ServerApp extends JFrame implements KeyListener, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AsciiPanel terminal;
    private Screen screen;
    private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();
    private String result = "playing";

    public ServerApp() {
        super();
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.ROGUELIKE_32x32);
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void response(int keycode, SocketChannel socketChannel)
    {
        screen.setMap(clientsMap);
        screen = screen.response(keycode, socketChannel);
        if (screen.getClass() == VictoryScreen.class)
        {
            result = "win";
        }
        if (screen.getClass() == DefeatScreen.class)
        {
            result = "loss";
        }
        repaint();
    }

    public Screen getScreen(SocketChannel socketChannel) {
        screen.setChannelMessage(socketChannel);
        if (screen.getClass() == VictoryScreen.class)
        {
            result = "win";
        }
        if (screen.getClass() == DefeatScreen.class)
        {
            result = "loss";
        }
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setMap(Map<String, SocketChannel> clientsMap)
    {
        this.clientsMap = clientsMap;
    }

    public String getResult()
    {
        return result;
    }

    @Override
    public void run() {
        while (true) {
            this.repaint();
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
