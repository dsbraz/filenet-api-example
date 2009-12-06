import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;

import com.filenet.wcm.api.*;

public class Delete extends MyCESession{

  public static void main(String[] args) throws Exception
  {
      if(args.length < 1)
      {
        System.out.println("Usage: run MyCESession <userId> <password>");
        System.exit(0);
      }
      try {
      	String nomes[];
      	
		Delete delete = new Delete();
        String folderName = args[0];
		folderName = "/Emissão RIC";
		
		delete.execute(folderName);

      } catch (Exception e)
      {
        e.printStackTrace();
      }
  }
  
  private void execute(String folderName) {
		try {
			
			ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);
			Folder aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,folderName);
			BaseObjects w = aFolder.getContainees();
			Iterator itw = w.iterator();
			while (itw.hasNext())
			{
				BaseObject o = (BaseObject)itw.next();
				if (o instanceof Folder)
				{
					System.out.println(o.getName());
					
					Folder aFolder2 = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,folderName + "/" + o.getName());
					BaseObjects w2 = aFolder2.getContainees();
					Iterator itw2 = w2.iterator();					
					while (itw2.hasNext())
					{
						BaseObject o2 = (BaseObject)itw2.next();
						if (o2 instanceof Folder){
							System.out.println("----aFolder3----->"+folderName +  "/" + o.getName() + "/" + o2.getName());

							Folder aFolder3 = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,folderName +  "/" + o.getName() + "/" + o2.getName());
							BaseObjects w3 = aFolder3.getContainees();
							Iterator itw3 = w3.iterator();
							while (itw3.hasNext()) //deleta os endossos dentros das pastas endossos
							{
								BaseObject o3 = (BaseObject)itw3.next();
//								System.out.println("----deleteDocument3----->"+folderName + "/" + o.getName() + "/" + o2.getName()+ "/" + o3.getName());
//								String arquivo = mid(o3.getName(),1,instr(1,o3.getName(),"/")) + "/" +
//								                 mid(o3.getName(),instr(1,o3.getName(),"/")+1,o3.getName().length()-instr(1,o3.getName(),"/"));
//								System.out.println("----deleteDocument3----->"+folderName + "/" + o.getName() + "/" + o2.getName()+ "/" + arquivo);
								System.out.println( "         " + o3.getName());							
								Document deleteDocument3 = (Document)objectStore.getObject(BaseObject.TYPE_DOCUMENT,folderName + "/" + o.getName() + "/" + o2.getName()+ "/" + o3.getName());				
								deleteDocument3.delete();
							}

							System.out.println( "    " + o2.getName());
							//	deleta o segundo
							Folder deleteFolder2 = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,folderName + "/" + o.getName() + "/" + o2.getName());
							deleteFolder2.delete();
							
						}
						else{
							System.out.println( "    " + o2.getName());							
							Document deleteDocument2 = (Document)objectStore.getObject(BaseObject.TYPE_DOCUMENT,folderName + "/" + o.getName() + "/" + o2.getName());				
							deleteDocument2.delete();
							//deleta o segundo							
						}
						
					}
					
				 Folder deleteFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,folderName + "/" + o.getName());				
				 deleteFolder.delete();
				 //deleta o primeiro

				}		
				else{

					System.out.println(o.getName());
					Document deleteDocument = (Document)objectStore.getObject(BaseObject.TYPE_DOCUMENT,folderName + "/" + o.getName());				
					deleteDocument.delete();
					//deleta o primeiro
															
				}

					
			}	
			System.out.println("Deletado!");

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
