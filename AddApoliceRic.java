import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.filenet.wcm.api.*;

public class AddApoliceRic {

  public AddApoliceRic() throws Exception {
  }
 
  public AddApoliceRic(Arquivo arq,Session myCESession) {
    try {

	  System.out.println(" ********* Início AddApoliceRic *********** ");
	  
      AddApoliceRic myInstance = new AddApoliceRic();
	  String docTitle = "";
	  if (RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/") != 0){
		docTitle = RoboECMRic.mid(arq.getDESC_TIPO_DOCUMENTO(),1,RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/")-1) +
				"-" +	 RoboECMRic.mid(arq.getDESC_TIPO_DOCUMENTO(),RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/")+1,arq.getDESC_TIPO_DOCUMENTO().length()-RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/"));	  
	  }
	  else{
		docTitle = arq.getDESC_TIPO_DOCUMENTO();
	  }
	  
	  System.out.println("docTitle " + docTitle);
	  
	  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
      String folderName = arq.getCIA() + " " + arq.getSUCURSAL() + " " + 
                          arq.getPRODUTO() + " " + arq.getAPOLICE(); 
//                         + " EM " + sdf.format(arq.getDATA_EMISSAO()) + 
//                           " VIG " + sdf.format(arq.getDATA_INICIO_VIGENCIA()) + " " + sdf.format(arq.getDATA_FIM_VIGENCIA()) +
//                           " - " + arq.getNOME_SEGURADO();
                           
	  System.out.println("folderName " + folderName);
      String fileName = "C:/WebDocRic/" + RoboECMRic.mid(arq.getNOME_ARQ(),1, RoboECMRic.instr(1,arq.getNOME_ARQ(),".TXT")-1) + "." + arq.getEXTENSAO_ARQUIVO();
	  System.out.println("fileName " + fileName);
	  String nomeDocFileNet = "/Emissão RIC/" + folderName + "/" + RoboECMRic.mid(arq.getNOME_ARQ(),1, RoboECMRic.instr(1,arq.getNOME_ARQ(),".TXT")-1) + "." + arq.getEXTENSAO_ARQUIVO();
	  System.out.println("nomeDocFileNet " + nomeDocFileNet);	  
	  
	  ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);

	  System.out.println("Testando se folder de apolice existe... " + folderName);
	  Folder aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emissão RIC/" + folderName);
	  if (aFolder.getClassId() == null){  //não existe
		aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emissão RIC");
		//System.out.println(aFolder.getClassId());
		Folder parentFolder = aFolder.addSubFolder(folderName, null, null);		
		//Folder  childFolder = parentFolder.addSubFolder(newFolderName, null, null);
		  
		  //Não está tendo association properties
		//AdicionaArqPropriedade(arq,objectStore,folderName);
		
			
	  }
	  else{
		System.out.println("Folder Existente... Folder não criado....");
	  }
	  
//	  try {	
//		System.out.println("Testando se  folder existe... " + folderName);
//		Folder aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emissão RIC");
//		Folder parentFolder = aFolder.addSubFolder(folderName, null, null);		
//		//Folder  childFolder = parentFolder.addSubFolder(newFolderName, null, null);
//		
//		AdicionaArqPropriedade(arq,objectStore,folderName);
//	  }	
//	   catch (com.filenet.wcm.api.UniquenessConstraintException e) {
//		System.out.println("Folder Existente... Folder não criado....");
//	    //e.printStackTrace();
//		//System.out.println("hashcode " + e.hashCode());
//		//System.out.println("message "  + e.getMessage()); 
//	   }
//    // }
      
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
     
	  property =  ObjectFactory.getProperty("CIA");
	  property.setValue(arq.getCIA());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("SUCURSAL");
	  property.setValue(arq.getSUCURSAL());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("CARTEIRA");
	  property.setValue(arq.getPRODUTO());
	  properties.add(property);	        
	  property =  ObjectFactory.getProperty("APOLICE");
	  property.setValue(arq.getAPOLICE());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("NOME_SEGURADO");
	  property.setValue(arq.getNOME_SEGURADO());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("CPF_CNPJ");
	  property.setValue(arq.getCPF_CNPJ());
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("DATA_EMISSAO");
	  property.setValue(arq.getDATA_EMISSAO());
	  properties.add(property);

	  property =  ObjectFactory.getProperty("DAT_INIC_VIGENCIA");
	  property.setValue(arq.getDATA_INICIO_VIGENCIA());
	  properties.add(property);	  

	  property =  ObjectFactory.getProperty("DAT_FIM_VIGENCIA");
	  property.setValue(arq.getDATA_FIM_VIGENCIA());
	  properties.add(property);
	  
	  sdf = new SimpleDateFormat("dd/MM/yyyy");
	  String data = sdf.format(new Date());
	  sdf.setLenient(false);	  
	  java.util.Date date = sdf.parse(data);
	  property =  ObjectFactory.getProperty("DAT_INCLUSAO_ECM");
	  property.setValue(date);
	  properties.add(property);

	  property =  ObjectFactory.getProperty("NOME_FUNCIONARIO");
	  property.setValue(arq.getNOME_FUNCIONARIO());
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("DESCRICAO");
	  property.setValue(arq.getDESC_TPO_APO_END());
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("DESC_TIPO_DOCUMENTO");
	  property.setValue(arq.getDESC_TIPO_DOCUMENTO());
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("SIGLA_SISTEMA");
	  property.setValue(arq.getSIGLA_SISTEMA());
	  properties.add(property);
	  	  	  	  
	//  property =  ObjectFactory.getProperty("DESC_TIPO_DOCUMENTO");
	//  property.setValue("Frontispício");
	//  properties.add(property);
	  
	  try {
	  //Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
	    Document document = (Document) objectStore.createObject("APOLICE_RIC", properties, null);
        Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, "/Emissão RIC/" + folderName);
        document.file(folder, false);
      
        TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
        document.setContent(content, false, false);
        document.checkin(false);
	    System.out.println("Documento Apolice Adicionado com sucesso. Doc -> "+nomeDocFileNet);
	  }
	  catch (com.filenet.wcm.api.UniquenessConstraintException e) {
		System.out.println("Documento já existente... filename ----> " + fileName);
		System.out.println("nomeDocFileNet ------------------------> " + nomeDocFileNet);
		
		Document document = (Document) objectStore.createObject("APOLICE_RIC", properties, null);
		Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, "/Emissão RIC/" + folderName);
		document.file(folder, true);
      
		TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
		document.setContent(content, false, false);
		document.checkin(false);
		System.out.println("Documento Apolice Adicionado com mesmo nome com sucesso. Doc -> "+nomeDocFileNet);

	  }

	  System.out.println(" *********  Fim AddApoliceRic  *********** ");
	  System.out.println("  ");
	  
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void AdicionaArqPropriedade(Arquivo arq,ObjectStore objectStore,String folderName) {
  	
	com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
	Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
	property.setValue("Propriedades");
	properties.add(property);
     
	property =  ObjectFactory.getProperty("CIA");
	property.setValue(arq.getCIA());
	properties.add(property);
	property =  ObjectFactory.getProperty("SUCURSAL");
	property.setValue(arq.getSUCURSAL());
	properties.add(property);
	property =  ObjectFactory.getProperty("PRODUTO");
	property.setValue(arq.getPRODUTO());
	properties.add(property);	        
	property =  ObjectFactory.getProperty("APOLICE");
	property.setValue(arq.getAPOLICE());
	properties.add(property);
	property =  ObjectFactory.getProperty("NOME_SEGURADO");
	property.setValue(arq.getNOME_SEGURADO());
	properties.add(property);
	  
	property =  ObjectFactory.getProperty("DATA_EMISSAO");
	property.setValue(arq.getDATA_EMISSAO());
	properties.add(property);

	property =  ObjectFactory.getProperty("DAT_INIC_VIGENCIA");
	property.setValue(arq.getDATA_INICIO_VIGENCIA());
	properties.add(property);	  

	property =  ObjectFactory.getProperty("DAT_FIM_VIGENCIA");
	property.setValue(arq.getDATA_FIM_VIGENCIA());
	properties.add(property);
	  
	try {
	//Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
	 Document document = (Document) objectStore.createObject("APOLICE_RIC2", properties, null);
	 Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, "/Emissão RIC/" + folderName);
	 document.file(folder, false);

	 System.out.println(" **** Documento de Propriedades da Apolice Adicionado com sucesso.");
	}
	catch (com.filenet.wcm.api.UniquenessConstraintException e) {
	  System.out.println(" **** Documento de Propriedades já existente... filename ----> " + folderName);

	}
  	
  }

}
