import java.io.File;
import java.io.FileInputStream;

import com.filenet.wcm.api.*;

public class AddApoliceRicBKP extends MyCESession {

  public AddApoliceRicBKP() throws Exception {
  }
 
  public static void main(String[] args) {
    try {
      if(args.length < 3)
      {
        System.out.println("Usage: run MyAddDocument <DocTitle> <FolderPath> <FileName>");
        System.exit(-1);
      }
      AddApoliceRicBKP myInstance = new AddApoliceRicBKP();
      String docTitle = "Teste classe apolice_RIC via API sem doc";
      String folderName = "/Emissão RIC/7110 930 230 1234567- Palermo/Apolice";
      String fileName = "C:/LogoAss.bmp";
      if(!new File(fileName).exists())
      {
        System.out.println("File "+fileName+" does not exist.");
        System.exit(-1);
      }
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
     
	  property =  ObjectFactory.getProperty("CIA");
	  property.setValue(7110);
	  properties.add(property);
	  property =  ObjectFactory.getProperty("SUCURSAL");
	  property.setValue(930);
	  properties.add(property);
	  property =  ObjectFactory.getProperty("PRODUTO");
	  property.setValue(213);
	  properties.add(property);	        
	  property =  ObjectFactory.getProperty("APOLICE");
	  property.setValue(212121);
	  properties.add(property);
	  property =  ObjectFactory.getProperty("NOME_SEGURADO");
	  property.setValue("TESTE");
	  properties.add(property);
	  
	  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	  java.util.Date date = sdf.parse("01/05/2006");
	  property =  ObjectFactory.getProperty("DATA_EMISSAO");
	  property.setValue(date);
	  properties.add(property);
	  sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	  date = sdf.parse("02/05/2005");
	  property =  ObjectFactory.getProperty("DAT_INIC_VIGENCIA");
	  property.setValue(date);
	  properties.add(property);	  
	  sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	  date = sdf.parse("02/05/2006");
	  property =  ObjectFactory.getProperty("DAT_FIM_VIGENCIA");
	  property.setValue(date);
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("DESC_TIPO_DOCUMENTO");
	  property.setValue("Frontispício");
	  properties.add(property);
	  
	  property =  ObjectFactory.getProperty("DESC_TIPO_APO");
	  property.setValue("");
	  properties.add(property);
	  	  
	  //property =  ObjectFactory.getProperty("CPF_CNPJ");
	  //property.setValue(9999999999);
	  //properties.add(property);
	  	  
      
      ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);
      //Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
	  Document document = (Document) objectStore.createObject("APOLICE_RIC", properties, null);
      Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, folderName);
      document.file(folder, false);
      
      TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
      document.setContent(content, false, false);
      document.checkin(false);
      
      System.out.println("Document successfully added. DocID = "+document.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
