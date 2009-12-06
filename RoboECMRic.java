import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import com.filenet.wcm.api.*;

public class RoboECMRic extends MyCESession {

  public static void main(String[] args) {
	String caminho = args[0];
	RoboECMRic ecmric = new RoboECMRic();
	ecmric.execute(caminho);
  }
  
  private void execute(String caminho) {
		try {
			int contApol = 0;
			int contEndo = 0;
			int contFatu = 0;			
			File dir = new File(caminho);    
			String[] files = dir.list();  //Lista os arquivos do diretorio

			int filtereds = 0;			
			if (files != null) 
			{
				for (int i=0; i< files.length; i++) // Lê cada um destes arquivos
				{
					String filename = files[i];
					if (instr(1,filename,".TXT") != 0 || instr(1,filename,".txt") != 0)
					{
					//	System.out.println(filename);					 
						String line = null;
						int cont = 1;
						Arquivo arq = new Arquivo();  // Vai setar o objeto do tipo arquivo com seus atributos
						BufferedReader input = new BufferedReader( new FileReader(caminho+filename) );
							while (( line = input.readLine()) != null)  // Lê o arquivo
							{
							//	System.out.println(cont); 
								 switch (cont) {
									case 1 :   // Linha 1 --> CIA
										arq.setCIA(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									  //  System.out.println(arq.getCIA());
										break;								    
									case 2 :   // Linha 2 --> SUCURSAL
										arq.setSUCURSAL(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									  //  System.out.println(arq.getSUCURSAL());
										break;								    
									case 3 :   // Linha 3 --> PRODUTO
										arq.setPRODUTO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									  //  System.out.println(arq.getPRODUTO());
										break;
									case 4 :   // Linha 4 --> APOLICE
										arq.setAPOLICE(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									  //  System.out.println(arq.getAPOLICE());
										break;
									case 5 :   // Linha 5 --> ENDOSSO
										arq.setENDOSSO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));									
										break;
									case 6 :   // Linha 6 --> FATURA
										arq.setFATURA(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
										break;
									case 7 :   // Linha 7 --> SINISTRO
										arq.setSINISTRO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
										break;
									case 8 :   // Linha 8 --> SINISTRO
										arq.setTIPO_DOCUMENTO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
										break;
									case 9 :   // Linha 9 --> NOME_SEGURADO
										arq.setNOME_SEGURADO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									  //  System.out.println(arq.getNOME_SEGURADO());									
										break;
									case 10 :  // Linha 10 --> DATA_EMISSAO
										//java.text.SimpleDateFormat hsdf = new java.text.SimpleDateFormat("hh:mm:ss");
										//String hora = hsdf.format(new java.util.Date());
										String hora = "00:00:00";
									
										String data;
										data = mid(line,instr(1,line,"=")+1,2) + "/" + mid(line,instr(1,line,"=")+3,2) + "/" + mid(line,instr(1,line,"=")+5,4);
										java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
										sdf.setLenient(false);
										java.util.Date date = sdf.parse(data + " " + hora);
										arq.setDATA_EMISSAO(date);
									//	System.out.println(sdf.format(arq.getDATA_EMISSAO()));									
										break;
									case 11 :   // Linha 11 --> CPF_CNPJ
										arq.setCPF_CNPJ(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getCPF_CNPJ());									
										break;			
									case 12 :   // Linha 12 --> SIGLA_SISTEMA
										arq.setSIGLA_SISTEMA(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getSIGLA_SISTEMA());									
										break;																		
									case 13 :   // Linha 12 --> EXTENSAO_ARQUIVO
										arq.setEXTENSAO_ARQUIVO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getEXTENSAO_ARQUIVO());									
										break;			
									case 14 :   // Linha 14 --> NOME_FUNCIONARIO
										arq.setNOME_FUNCIONARIO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getNOME_FUNCIONARIO());									
										break;			
									case 15 :   // Linha 15 --> DESC_TIPO_DOCUMENTO
										arq.setDESC_TIPO_DOCUMENTO(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getDESC_TIPO_DOCUMENTO());									
										break;			
									case 16 :  // Linha 16 --> DATA_CRIACAO
										data = mid(line,instr(1,line,"=")+1,2) + "/" + mid(line,instr(1,line,"=")+3,2) + "/" + mid(line,instr(1,line,"=")+5,4);
										sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
									    sdf.setLenient(false);
										date = sdf.parse(data);
										arq.setDATA_CRIACAO(date);
									//	System.out.println(sdf.format(arq.getDATA_CRIACAO()));									
										break;
									case 17 :  // Linha 17 --> DATA_INIC_VIGENCIA
										data = mid(line,instr(1,line,"=")+1,2) + "/" + mid(line,instr(1,line,"=")+3,2) + "/" + mid(line,instr(1,line,"=")+5,4);
										sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
									    sdf.setLenient(false);
										date = sdf.parse(data);
										arq.setDATA_INICIO_VIGENCIA(date);
									//	System.out.println(sdf.format(arq.getDATA_INICIO_VIGENCIA()));									
										break;					
									case 18 :  // Linha 18 --> DATA_FIM_VIGENCIA
										data = mid(line,instr(1,line,"=")+1,2) + "/" + mid(line,instr(1,line,"=")+3,2) + "/" + mid(line,instr(1,line,"=")+5,4);
										sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
									    sdf.setLenient(false);
										date = sdf.parse(data);
										arq.setDATA_FIM_VIGENCIA(date);
									//	System.out.println(sdf.format(arq.getDATA_FIM_VIGENCIA()));									
										break;		
									case 19 :   // Linha 19 --> DESC_TPO_APO_END
										arq.setDESC_TPO_APO_END(mid(line,instr(1,line,"=")+1, line.length()-instr(1,line,"=")));
									//	System.out.println(arq.getDESC_TPO_APO_END());										
																									
									default :
										break;
								}
							
								cont++;  					      	  
							  //System.out.println("     -----  " + line);
							}
						input.close();
  										
						arq.setNOME_ARQ(filename);
						int numEndosso = Integer.parseInt( arq.getENDOSSO() );
						int numFatura  = Integer.parseInt( arq.getFATURA() );
						int numApolice = Integer.parseInt( arq.getAPOLICE() );						 
						if (numEndosso == 0) {  //Documento é uma apólice ou fatura
							if (numFatura == 0) { //Documento é uma apólice
								if (numApolice != 0) { //Documento é uma apólice
							   		System.out.println("Vai chamar o AddApoliceRic....");
							   		new AddApoliceRic(arq,myCESession);
							   		contApol++;
								}
								else{
									System.out.println("*****");									
									System.out.println("***** Arquivo não foi carregado - Apólice zerada.. " + filename);
									System.out.println("*****");									
								}
							}
							else{   
								System.out.println("Vai chamar o AddFaturaRic....");
								new AddFaturaRic(arq,myCESession);
								contFatu++;								
							}
						}	 	
						else{
							System.out.println("Vai chamar o AddEndossoRic....");
							new AddEndossoRic(arq,myCESession);
							contEndo++;							
						}

  					
					}
				
				}
			}
	
		  System.out.println("**** Documentos referentes a apolices inseridos no FileNet ----> "+contApol);	
		  System.out.println("**** Documentos referentes a endossos inseridos no FileNet ----> "+contEndo);
		  System.out.println("**** Documentos referentes a faturas  inseridos no FileNet ----> "+contFatu);
		  		  		  
		  System.out.println("Término do Robô.");

		} catch (Exception e) {
		  e.printStackTrace();
		}
		
	}


  public static int instr(int start, String string1, String string2) 
  {
	  if (start == 1)
	  {
		  String string0 = string1;
		  return string0.indexOf(string2)+1;
	  }
	  else
	  {
		  String string0 = mid(string1, 2, string1.length()-1);
		  return string0.indexOf(string2)+start;
	  }		
  }
  
  public static String mid(String text, int start, int length) 
  {
	  return text.substring(start-1,start-1+length);
  }
  
}
