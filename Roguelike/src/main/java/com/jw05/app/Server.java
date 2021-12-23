package com.jw05.app;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jw05.app.anish.screen.WorldScreen;

/**
 * 
 * This is a simple NIO based server.
 *
 */
public class Server {
	private Selector selector;
	private InetSocketAddress listenAddress;
	private final static int PORT = 9093;
	private ByteBuffer sBuffer = ByteBuffer.allocate(1024000);
	private ByteBuffer rBuffer = ByteBuffer.allocate(1024000);
	private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();
	int i = 0;
	public ServerApp app;
	private int startType = 0;

	public Server(String address, int port) throws IOException {
		listenAddress = new InetSocketAddress(address, PORT);
		this.app = new ServerApp();
		this.app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.app.setVisible(false);
		new Thread(app).start();
	}

	/**
	 * Start the server
	 * 
	 * @throws IOException
	 */
	private void startServer() throws IOException {
		this.selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);

		// bind server socket channel to port
		serverSocketChannel.socket().bind(listenAddress);
		serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server started on port >> " + PORT);
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (!clientsMap.isEmpty()) {
						for (Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()) {
							SocketChannel temp = entry.getValue();
							String name = entry.getKey();
							sBuffer.clear();
							Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
							if (startType == KeyEvent.VK_1){
								if (app.getResult() == "win")
								{
									sBuffer.put("win".getBytes());
								}else if (app.getResult() == "loss")
								{
									sBuffer.put("loss".getBytes());
								}else{
									sBuffer.put(gson.toJson((WorldScreen) app.getScreen(temp)).getBytes());
								}
							}
							sBuffer.flip();
							try {
								temp.write(sBuffer);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							sBuffer.clear();
						}
					}
					try {
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
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

	private void handle(SelectionKey selectionKey) throws IOException {

		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText = null;
		int count = 0;
		if (selectionKey.isAcceptable()) {
	
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			client.configureBlocking(false);
			clientsMap.put(client.getLocalAddress().toString().substring(1) + i++, client);
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
	
			client = (SocketChannel) selectionKey.channel();
			rBuffer.clear();
			count = client.read(rBuffer);
			if (count > 0) {
				rBuffer.flip();
				byte[] data = new byte[count];
				System.arraycopy(rBuffer.array(), 0, data, 0, count);
				String getstr = new String(data);
				System.out.println(client.getLocalAddress().toString().substring(1)+":"+ getstr);
				int input = Integer.parseInt(getstr);
				app.setMap(clientsMap);
				app.response(input, client);
				if (startType == 0 && (input == KeyEvent.VK_1 || input == KeyEvent.VK_2 || input == KeyEvent.VK_3)){
					startType = input;
				}
				sBuffer.clear();
				sBuffer.flip();
				if(!clientsMap.isEmpty()){
					for(Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()){
						SocketChannel temp = entry.getValue();
						sBuffer.clear();
						Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
						if (startType == KeyEvent.VK_1){
							if (app.getResult().equals("win"))
								{
									sBuffer.put("win".getBytes());
								}else if (app.getResult().equals("loss"))
								{
									sBuffer.put("loss".getBytes());
								}else{
									sBuffer.put(gson.toJson((WorldScreen) app.getScreen(temp)).getBytes());
								}
						}
						sBuffer.flip();		
						temp.write(sBuffer);
						sBuffer.clear();
					}
				}
	
				client = (SocketChannel) selectionKey.channel();
				client.register(selector, SelectionKey.OP_READ);
			}  
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			new Server("localhost", 9093).startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}  