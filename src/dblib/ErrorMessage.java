package dblib;

import java.nio.channels.ClosedByInterruptException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe para escrever mensagens para o utilizador ler
 * @author 2014 - Vitor Vaz da Silva
 */
public class ErrorMessage {
	
  /**
   * <h3>simOuNao </h3>
   * <pre>public static boolean simOuNao(String msg, String nomeIcon)</pre>
   * 
   * <p>
   * Abre uma janela onde é escrita uma pergunta sobre a qual o utilizador terá de dar uma resposta.
   * </p>
   * @param msg - Pergunta a ser apresentada
   * @param nomeIcon - Imagem associada à pergunta
   * @return false - Não<br>true - Sim
   */
  public static boolean simOuNao(String msg, String nomeIcon){
    JFrame saiOuNao = new JFrame("Adeus");
    ImageIcon icon = nomeIcon!=null && !nomeIcon.equals("") ? new ImageIcon(nomeIcon) : null;
    return JOptionPane.showConfirmDialog(saiOuNao, msg, "SaiOuNao", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon)
			      ==JOptionPane.OK_OPTION;
  }
		  
  /**
   * <h3>aviso</h3>
   * <pre>public static void aviso(String msg)</pre>
   * 
   * <p>
   * Apresenta uma janela com uma mensagem o utilizador que terá de clicar em OK para indicar que foi lida. 
   * </p>
   * @param msg - Mensagem a apresentar.
   */
  public static void aviso(String msg){					//:)
    JFrame saiOuNao = new JFrame();
	 JOptionPane.showMessageDialog(saiOuNao, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
  }  
  
  public static void aviso(String msg, final int tempo){					//:)
	  if(tempo<=0) aviso(msg);
	  else{
	    final JFrame saiOuNao = new JFrame();
	    Thread t=new Thread(){
	    	public void run(){
	    			try {
						sleep(tempo);
					} catch (InterruptedException e) {}
	    			saiOuNao.dispose();
	    	}
	    };
	    t.start();
	    JOptionPane.showMessageDialog(saiOuNao, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
	    t.interrupt();
	  }  
  }
  
  /**
   * <h3>aviso</h3>
   * <pre>public static void informar(String msg)</pre>
   * 
   * <p>
   * Apresenta uma janela com uma mensagem o utilizador que terá de clicar em OK para indicar que foi lida. 
   * </p>
   * @param msg - Mensagem a apresentar.
   */
  public static void informar(String msg){					//:)
    JFrame saiOuNao = new JFrame();
	 JOptionPane.showMessageDialog(saiOuNao, msg, "Informar", JOptionPane.INFORMATION_MESSAGE);
  }   
}
