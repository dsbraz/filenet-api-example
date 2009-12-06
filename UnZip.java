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

public class UnZip {

	  public static final void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) >= 0)
		  out.write(buffer, 0, len);
		in.close();
		out.close();
	  }

	  public static final void main(String[] args) {
		Enumeration entries;
		ZipFile zipFile;

		if(args.length != 1) {
		  System.err.println("Sintaxe dos argumentos do programa: Unzip arquivo");
		  return;
		}
		try {
		  String destino = "C:/WebDocRic/";	
		  zipFile = new ZipFile(args[0]);
		  entries = zipFile.entries();
		  while(entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)entries.nextElement();
			if(entry.isDirectory()) {
			  System.err.println("Descompactando diretório: " + entry.getName());
			  (new File(entry.getName())).mkdir();
			  continue;
			}
			System.out.println("Descompactando arquivo:" + entry.getName());
			copyInputStream(zipFile.getInputStream(entry),
			   new BufferedOutputStream(new FileOutputStream(destino + entry.getName())));
		  }
		  zipFile.close();
		} catch (IOException ioe) {
		  System.err.println("Erro ao descompactar:" + ioe.getMessage());
		  return;
		}
	  }

}
