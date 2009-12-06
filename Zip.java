/*
 * Created on 17/05/2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author LUCA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Zip {

	//static final int TAMANHO_BUFFER = 2048; // 2kb
	static final int TAMANHO_BUFFER = 512; // 2kb
	  public static final void main(String[] args) {

		if(args.length != 1) {
		  System.err.println("Sintaxe dos argumentos do programa: Zip arquivo");
		  return;
		}
		String nomeZip = args[0];
		String arqSaida = "C:/WebDocRic/" + nomeZip;
		int i, cont; 
		byte[] dados = new byte[TAMANHO_BUFFER]; 
		String arquivos[]; 
		File[] arqs;
		File f = null; 
		BufferedInputStream origem = null; 
		FileInputStream streamDeEntrada = null; 
		FileOutputStream destino = null; 
		ZipOutputStream saida = null; 
		ZipEntry entry = null;
      
		try { 
		   destino = new FileOutputStream(arqSaida); 
		   saida = new ZipOutputStream(new BufferedOutputStream(destino));
		   f = new File("C:/WebDocRic/."); // Todos os arquivos da pasta onde a classe está
		   arqs = f.listFiles(); 
		   //arquivos = f.list();
         
		   for (i = 0; i<arqs.length; i++) { 
			 // File arquivo = new File(arquivos[i]); 
			 File arquivo = arqs[i];

			  if (arquivo.isFile() && !(arquivo.getName()).equals(nomeZip)) { 
				 System.out.println("Compactando: " + arqs[i]); 

				 streamDeEntrada = new FileInputStream(arquivo); 
				 origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER); 
				 entry = new ZipEntry(arqs[i].toString()); 
				 saida.putNextEntry(entry);
               
				 while((cont = origem.read(dados)) != -1) { 
					saida.write(dados, 0, cont); 
				 } 
				saida.closeEntry();
				 origem.close(); 
			  } 
		   } 

		   saida.close();
          
		} catch(Exception e) { 
		   e.printStackTrace(); 
		} 
	  
	  }

}
