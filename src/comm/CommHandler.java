package comm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * @author Izzy Cecil
 * Dec 14, 2010
 *
 * CommHandler acts as a basic package of a Socket and ObjectIn/OutStream.
 * Meant to be given a socket from a server connection, and will then open
 * all streams.
 */
public class CommHandler {

	/**
	 * Socket which streams will be used
	 */
	protected Socket socket;
	/**
	 * Socket's InputStream
	 */
	protected ObjectInputStream input;
	/**
	 * Socket's OutputStream
	 */
	protected ObjectOutputStream output;

	
	/**
	 * If you attempt to create a CommHandler, an exception will be thrown.
	 */
	public CommHandler() {
		throw new RuntimeException();
	}

	/**
	 * @param socket
	 * @throws IOException
	 * Will construct all socket streams.
	 */
	public CommHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.output = new ObjectOutputStream(socket.getOutputStream());
		this.input = new ObjectInputStream(socket.getInputStream());
	}

	/**
	 * @throws IOException
	 * Properly closes communications.
	 */
	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}

	/**
	 * @return Object read by Socket's Input Stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @see ObjectInputStream.readUnshared()
	 */
	public Object read() throws IOException, ClassNotFoundException {
		Object x = input.readUnshared();
		return x;
	}

	/**
	 * @param out Object to be sent
	 * @throws IOException
	 * @see ObjectOutputStream.writeUnshared(Object)
	 * After sending, due to repeated object issues, the output will be flushed and
	 * reset. This is slow and ugly, but will remain until a new approach arises.
	 */
	public void write(Object out) throws IOException {
		output.writeUnshared(out);
		output.flush();
		output.reset();
	}

}