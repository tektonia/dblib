package dblib;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

/**
 * Class to aid message visualisation of a program.
 * <br/>A window is created with a non volatile text area.
 * <br/>It contains several methods to write Strings, and clean the whole text area.
 * <br/>All methods are static
 * 
 * @author Vitor - 2014
 */
public class BDebug extends JFrame {
	private static final long serialVersionUID = 1L;
	static int ORIGEM_X=100;
	static int ORIGEM_Y=120;
	static int ALTURA=300;
	static int COMPRIMENTO=450;
	static int DIM_MARGEM=5;
	final static String TITULO="Debug";
	private static JPanel painel;
	private static JTextArea jDebug;
	private static JFrame debugFrame; 
	private static boolean ignora=false;

    /**
     * <h4>BDebug</h4>
     * <pre>public BDebug(int x, int y, int c, int a)</pre>
     * <p>Creates a window at (x,y) with size lxh for writing text.</p>
     * 
     * @param x - Origin X
     * @param y - Origin Y
     * @param l - Length
     * @param h - Height
     */
	public BDebug(int x, int y, int l, int h){
		ORIGEM_X=x; ORIGEM_Y=y; COMPRIMENTO=l; ALTURA=h;
		createWindow();
	}
	
	/**
	 * <h4>BDebug</h4><pre>public BDebug()</pre>
     * <p>Creates a window for writing text.</p> 
	 */
	public BDebug(){createWindow();}
	
	/**
	 * <H4>novo</H4>
     * <pre>public static void novo()</pre>
     * <p>Creates a window for text writing</p>
	 */
    public static void createWindow() {
    	// Create the frame
    	debugFrame= new JFrame();     		 
    	debugFrame.setAlwaysOnTop(false);
    	debugFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		debugFrame.setBounds(ORIGEM_X, ORIGEM_Y, COMPRIMENTO, ALTURA);
		// Create a panel
		painel = new JPanel();				 
		painel.setBorder(new EmptyBorder(DIM_MARGEM, DIM_MARGEM, DIM_MARGEM, DIM_MARGEM));
		painel.setLayout(new BorderLayout());
		// Set the panel on the frame
		debugFrame.setContentPane(painel);	 
		// Create Scroll and add it to the panel
		JScrollPane scroll = new JScrollPane();   
		painel.add(scroll, BorderLayout.CENTER);
		// Create text area
		jDebug = new JTextArea();
		// Set text area in Scroll
		scroll.setViewportView(jDebug);
		// Set a title on the Scroll
		JLabel lblJanelaDeDebug = new JLabel(TITULO);
		scroll.setColumnHeaderView(lblJanelaDeDebug);
	}
	
    /**
     * minimize Minimize the window
     */
    public static void minimize(){ debugFrame.setState(JFrame.ICONIFIED);}

    /**
     * ignore Ignores the writing according to parameter b
     * 
     * @param b true=ignore
     */
    public static void ignore(boolean b){ ignora=b;}
    
    
	/**
	 * <h4>showAt</h4>
     * <pre>public static void showAt(int x, int y)</pre>
     * <p> Position window at ( x , y )</p>
	 * <p>
	 * @param x	position x in pixels
	 * @param y	position y in pixels
	 * </p>
	 */
	public static void showAt(int x, int y){ 
	  if(jDebug !=null) jDebug.setLocation(x, y);
	}
	
	/**
	 * <h4>changeLine</h4>
	 * changeLine Changes line on text area
	 */
	public static void changeLine(){ jDebug.append("\n"); }
	
	/** <h4>write(String str)</h4>
	 * <p>write a String str
	 * @param str shows the string on the text area
	 */
	public static void write(String str){
		if(ignora) return;
		if(jDebug!=null) jDebug.append("«"+str+"»");
	  }
	
	/**
	 * <h4>String write(String str, String val)</h4>
	 * <p> Writes a string concatenated with <code>val</code>
	 * @param str	message to write before {@code val}
	 * @param valor - Text
	 * @return <code>val</code>
	 */
	public static String write(String str, String val){
		if(ignora) return val;
		if(str!=null && !str.equals("")) str=str+": ";
		if(jDebug!=null) jDebug.append("\n"+str+"<"+val+">\n");
	      return(val);
	  }
	
	/**
	 * <h4>write(String s, byte []val)</h4>
     * <pre>public static byte [] write(String s, byte []val)</pre>
	 * <p> Writes a string concatenated with<code>val</code></p>
	 * @param s - message to write before {@code val}
	 * @param valor - byte array
	 * @return <code>val</code>
	 */
	public static byte [] write(String s, byte []val){
		if(ignora) return val;
	    if(jDebug!=null){
	      if(s!=null && !s.equals("")) s=s+": ";
		  String str=s+"<";   
	      for(byte b : val) str+=(char)b;
	      jDebug.append("\n"+str+">\n");
	    }
	    return(val);
	  }

	/**
	 * <h4>write</h4>
     * <pre>public static int write(String str, int i) </pre>
     * <p>Writes an integer in the text area and returns its value unchanged.</p>
     * @param str - message to write before {@code i}
	 * @param i - integer.
	 * @return the integer i.
	 */
	public static int write(String str, int i) {
		if(ignora) return i;
		if(!str.equals("")) str=str+": ";
		write(str+"<"+i+">");
		return i;
	}	

	/**
	 * <h4>hex</h4>
     * <pre>public static byte[] hexa(String str, byte[] frame)</pre>
     *  <p> Writes a string concatenated with<code>frame</code></p>
	 *  The frame is presented as bytes separated by a space and coded as two hexadecimal characters
	 * </p>
	 * @param str - message to write before {@code frame}
	 * @param frame - value to be written
	 * @return frame
	 */
	public static byte[] hexa(String str, byte[] frame){
		if(ignora) return frame;
		String hexa="";
		if(!str.equals("")) str=str+": ";    
		for(int i=0; i< frame.length; i++){
			if((frame[i]& 0x00FF)<16) hexa+='0';
	          hexa+=Integer.toHexString(frame[i] & 0x00FF)+' ';
	    }
	    jDebug.append("\n"+str+"<"+hexa.toUpperCase()+"> ");
	    return(frame);
	}
	   
	/**
	 * <h4>visible</h4>
     * <pre>public static void visible(boolean b)</pre>
	 * <p> Sets the window visibility with <code>b</code></p>
	 * @param b	makes the window visible if {@value true}
	 */
	public static void visible(boolean b){
		debugFrame.setVisible(b);
		debugFrame.toFront();
	}
	   
	/**
	 * <h4>clean</h4>
     * <pre>public static void clean()</pre>
     * <p>Clears all content of the text area</p> 
	 */
	public static void clean() {if(ignora) return; jDebug.setText("");}

}
