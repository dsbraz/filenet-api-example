import java.io.File;
import java.io.FileInputStream;

import com.filenet.wcm.api.*;

public class AddEndossoRicBKP extends MyCESession {

  public AddEndossoRicBKP() throws Exception {
  }
 
  public static void main(String[] args) {
    try {
      if(args.length < 3)
      {
        System.out.println("Usage: run MyAddDocument <DocTitle> <FolderPath> <FileName>");
        System.exit(-1);
      }
      AddEndossoRicBKP myInstance = new AddEndossoRicBKP();
      String docTitle = "Teste classe endosso_RIC via API2";
      String folderName = "/Emissão RIC/7110 930 230 1234567- Palermo/Endosso 00001 - Palermo";
      String fileName = "C:/LogoAss.bmp";
	  String documentNameApo = "/Emissão RIC/7110 930 230 1234567- Palermo/Apolice/Teste classe apolice_RIC via API";
	   
      if(!new File(fileName).exists())
      {
        System.out.println("File "+fileName+" does not exist.");
        System.exit(-1);
      }
      com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);
     
	  property =  ObjectFactory.getProperty("ENDOSSO");
	  property.setValue(00001);
	  properties.add(property);
	  property =  ObjectFactory.getProperty("NOME_SEGURADO");
	  property.setValue("TESTE");
	  properties.add(property);
	  property =  ObjectFactory.getProperty("DESC_TIPO_END");
	  property.setValue("Substituição");
	  properties.add(property);	  
	  
	  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	  java.util.Date date = sdf.parse("03/05/2005");
	  property =  ObjectFactory.getProperty("DAT_INIC_VIGENCIA");
	  property.setValue(date);
	  properties.add(property);
	  sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	  date = sdf.parse("03/05/2006");
	  property =  ObjectFactory.getProperty("DAT_FIM_VIGENCIA");
	  property.setValue(date);
	  properties.add(property);
	  
	  ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);
	  
	  Document documentApo = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT, documentNameApo);

	  property =  ObjectFactory.getProperty("ENDOSSOAPOLICE");
	  property.setValue(documentApo);
	  properties.add(property);	  	  
	  	  	  	  
      
      //Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
	  Document document = (Document) objectStore.createObject("ENDOSSO_RIC", properties, null);
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
