package com.jw05.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.ParseException;
import java.util.Set;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jw05.app.anish.screen.DefeatScreen;
import com.jw05.app.anish.screen.VictoryScreen;
import com.jw05.app.anish.screen.WorldScreen;

public class Client {
    private Selector selector;
	private InetSocketAddress SERVER;
	private final static int PORT = 9093;
    private SocketChannel client;
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024000);
    public ClientApp app = null;
	
	
	private int count=0;

    public Client(String address, int port) throws IOException {
		this.SERVER = new InetSocketAddress(address, PORT);
	}

    /**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	private void startClient() throws IOException {

        
		this.selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
	    socketChannel.connect(SERVER);
        
		while (true) {
			try {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for (SelectionKey key : selectionKeys) {
					handle(key);
				}
				selectionKeys.clear();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}

    /**
	 * @param selectionKey
	 * @throws IOException
	 * @throws ParseException
	 */
	private void handle(SelectionKey selectionKey) throws IOException, ParseException{
 
	    if (selectionKey.isConnectable()) { 

	        client = (SocketChannel) selectionKey.channel();
	        if (client.isConnectionPending()) {
	            client.finishConnect();
	            System.out.println("connect success !");
				if (app == null){
					app = new ClientApp(client);
                    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    app.setVisible(true);
	            	new Thread(app).start();
				}
	        }
	         
	        client.register(selector, SelectionKey.OP_READ);
	    } else if (selectionKey.isReadable()) {
			rBuffer.clear();
	        client = (SocketChannel) selectionKey.channel();
	        count=client.read(rBuffer);
	        if(count>0){
	            String receiveText = new String(rBuffer.array(), 0, count);
				if (receiveText.equals("win")){
					app.setScreen(new VictoryScreen());
				}else if (receiveText.equals("loss"))
				{
					app.setScreen(new DefeatScreen());
				}else{
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					app.setScreen(gson.fromJson(receiveText, WorldScreen.class));
				}
				app.repaint();
	            client = (SocketChannel) selectionKey.channel();
	            client.register(selector, SelectionKey.OP_READ);
	            rBuffer.clear();
	        }
	    }
	}
    
    public static void main(String[] args) throws Exception {
		try {
			new Client("localhost", 9093).startClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}