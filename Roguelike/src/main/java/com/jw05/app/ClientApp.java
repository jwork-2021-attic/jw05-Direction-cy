package com.jw05.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.jw05.app.anish.calabashbros.World;
import com.jw05.app.anish.screen.Screen;
import com.jw05.app.anish.screen.StartScreen;
import com.jw05.app.asciiPanel.AsciiFont;
import com.jw05.app.asciiPanel.AsciiPanel;

public class ClientApp extends JFrame implements KeyListener, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AsciiPanel terminal;
    private Screen screen;
    private SocketChannel client;

    public ClientApp(SocketChannel client) {
        super();
        this.client = client;
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
        ByteBuffer sBuffer = ByteBuffer.allocate(1024);
        sBuffer.clear();
        System.out.println(e.getKeyCode());
        sBuffer.put(String.valueOf(e.getKeyCode()).getBytes());
        sBuffer.flip();
        try {
            client.write(sBuffer);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sBuffer.clear();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
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
