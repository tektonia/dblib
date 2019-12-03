package dblib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

class ExtensaoFich extends FileFilter{
	String extensao;
	
	ExtensaoFich (String ext){
		extensao=ext;
	}
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String s = f.getName();
		return s.toLowerCase().endsWith(extensao);
	}

	@Override
	public String getDescription() {
		return "*"+extensao;
	}
}

/**
 * Esta classe tem métodos para aceder a ficheiros com extensão .bd e .txt
 * 
 * @author 2014 - Vitor Vaz da Silva
 *
 */
public class DbFile {
	FileOutputStream saida;
	InputStream entrada;
	Scanner scnEntrada;
	public File file=null;
	
	static final String EXTENSAO_TXT = ".txt";
	static final String EXTENSAO_BD = ".db";
	
	private static JFileChooser procuraFicheiro = new JFileChooser(EXTENSAO_TXT);
	private static ExtensaoFich filtroDB = new ExtensaoFich(EXTENSAO_BD);
	private static ExtensaoFich filtroTxt = new ExtensaoFich(EXTENSAO_TXT);
	private FileLock fileLock;
	private FileChannel canalIO;
	
	/**
	 * <h3>openForReading</h3>
     * <pre>public static boolean openForReading(String name)</pre>
     * <p>Opens file <code>name</code> for reading</p>
	 * 
	 * @param nome - File name
	 * @return - false - file does not exist or error while opening<br>
	 *           true - file is open
	 */
	public boolean openForReading(String nome){
		try {
			entrada = new FileInputStream(nome);
			scnEntrada = new Scanner(entrada);
			return true;
		} catch (FileNotFoundException e) {
			BDebug.write("DbFile.openForReading: Error opening file <"+nome+"> for reading.\n");
		}			
		return false;
	}
	
	/**
	 * <h3>openForWriting</h3>
     * <pre>public static boolean openForWriting(String name)</pre>
     * <p>Opens file <code>name</code> for writing</p>
	 * 
	 * @param nome - File name
	 * @return - false - file does not exist or error while opening<br>
	 *           true - file is open
	 */
	public boolean openForWritting(String nome){
		try {
			saida = new FileOutputStream(nome);
			canalIO = saida.getChannel();
			//fileLock = canalIO.lock();
			fileLock = canalIO.tryLock();
			return true;
		} catch (FileNotFoundException e) {
			BDebug.write("DbFile.openForWritting: Error while opening file <"+nome+"> for writing.\n");
		} catch (IOException e) {
			BDebug.write("DbFile.openForWritting: File <"+nome+"> is beeing used by another program.\n");
		}
		return false;
	}	
	
	/**
	 * <h3>close</h3>
     * <pre>public static void close()</pre>
     * 
	 * <p>Closes the file. No further reading or writing on that file after this call.</p>
	 */
	public void close(){
		try {
			if(saida!=null){
				fileLock.release();
				saida.close();
			}
			if(entrada!=null) entrada.close();
		} catch (IOException e) {
			BDebug.write("DbFile.close: Error while closing file.\n");
		}
	}
	
	/**
	 * <h3>write</h3>
     * <pre>public static boolean write(byte [] data)</pre>
     * <p>Writes an array of bytes in the file.</p>
	 * 
	 * @param data - Data to be written
	 * @return - false if not all values were written.<br>
	 *           true all values were written.
	 */
	public boolean write(byte [] data){
		try {
			saida.write(data);
			return true;
		} catch (IOException e) { 
			BDebug.write("DbFile.write: Error while writing <"+data+"> in file\n");
		} catch (NullPointerException n){
			BDebug.write("DbFile.write: input data is null!\n");
		}
		return false;
	}
	
	/**
	 * <h3>read</h3>
     * <pre>public static String read()</pre>
     * <p>Reads a string terminated by "\\n" from the file or null if no more data to read</p>
	 * 
	 * @return null if error or string read.
	 */
	public String read(){
		if(scnEntrada.hasNextLine()) return scnEntrada.nextLine();
		else return null;
	}

	/**
	 * <h3>choseFile</h3>
     * <pre>public static File choseFile()</pre>
     * <p>Opens a window to browse and choose a file</p>
	 * 
	 * @return Returns a File object with the chosen file.
	 */
	public File choseFile() {
		procuraFicheiro.addChoosableFileFilter(filtroDB);
		procuraFicheiro.addChoosableFileFilter(filtroTxt);		
		procuraFicheiro.setFileFilter(filtroDB);		
		procuraFicheiro.showOpenDialog(null);
		return file=procuraFicheiro.getSelectedFile();
	}
	
	/**
	 * <h3>choseFile</h3>
     * <pre>public static File choseFile(String ext, File name)</pre>
     * <p>Opens a window to browse and choose a file with extension <code>ext</code><br>
     * and expected name like <code>name</code></p>
	 * 
	 * @param ext  extension
	 * @param name	 name
	 * @return Returns a File object with the chosen file.
	 */
	public File choseFile(String ext, File name) {
		ExtensaoFich filtro = new ExtensaoFich(ext);
		procuraFicheiro.addChoosableFileFilter(filtroTxt);	
		procuraFicheiro.addChoosableFileFilter(filtro);	
		procuraFicheiro.setFileFilter(filtro);	
		procuraFicheiro.setSelectedFile(name);
		if(procuraFicheiro.showOpenDialog(null)!=JFileChooser.APPROVE_OPTION) return null;
		return file=procuraFicheiro.getSelectedFile();
	}
}
