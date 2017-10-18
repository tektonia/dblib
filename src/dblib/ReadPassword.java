package dblib;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Classe com métodos para ler uma password
 * 
 * @author 2014 - Vitor Vaz da Silva
 */
public class ReadPassword {
  String texto="";
  String titulo="";
  
  /**
   * <h4>LerPassword</h4>
     * <pre>public LerPassword(String textoPassword)</pre>
     * <p>Cria um objecto para poder vir a ler uma password.</p>
   * 
   * @param textoPassword - Text shown on the window that asks for the password
  */
  public ReadPassword(String textoPassword) {
		texto=textoPassword;
  }

  /**
   * <h4>read</h4>
   * <pre>public String read()</pre>
   * <p>
   * Opens a window to read a String and returns it after clicking OK button
   * Shows ****** while typing.
   * </p>
   * @return null - if nothing was written<br>the written text.
   */
  public String read() {
	JPanel pergunta = new JPanel();
	JLabel label = new JLabel(texto);
	JPasswordField senha = new JPasswordField(20);
	pergunta.add(label);
	pergunta.add(senha);
	String[] opcao = new String[]{"OK", "Cancel"};
	int idx = JOptionPane.showOptionDialog(null, pergunta, titulo,
	                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
	                         null, opcao, opcao[0]);
    return idx==0 ? String.valueOf(senha.getPassword()) : null;
  }  
}
