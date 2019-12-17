import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Database
{

   public void runFile(File fileName)
   {
      
	   
	  String readingFile = fileName.getName();
	  Scanner infile;

      try
      {
    	  //Declares the messages and the message arrays it will be stored in
		  String message;
		  String[] messageArray;
		  List<List<String>> messageLists = new ArrayList<List<String>>();
		  
		  // open dbuser file by creating a file object
		  File dbuser = new File(readingFile);
		  infile = new Scanner(dbuser);
	
		  //  Splits apart the command given and saves it in an array list
		  while (infile.hasNextLine()) {
		    message = infile.nextLine();
		    message = message.substring(0, message.length()-1);
		    System.out.println(message);
			messageArray = message.split("\\.| ");
			messageLists.add(Arrays.asList(messageArray));
		  }
			
			DBCommands dbcommands = new DBCommands();
			
			//Checks to see what command it is
			for(int num = 0; num < messageLists.size(); num++) {
				
				for(int arrayNum = 0; arrayNum < messageLists.get(num).size(); arrayNum++) {
					//Checks if the first word is create
					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("CREATE")) {
						//Checks if the next word is database
						
						if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("DATABASE")) {
							dbcommands.CreateDatabase(messageLists.get(num).get(arrayNum+2));
							
						} else if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("TABLE")) {
							//Checks if the next word is table
							dbcommands.CreateTable(messageLists.get(num).get(arrayNum+2) + "/" + messageLists.get(num).get(arrayNum+3));
						}
					}
					
					//Checks if the first word is drop
					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("DROP")) {
						//Checks if the next word is Database
	                    if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("DATABASE")) {
	                    	dbcommands.deleteDirectory(messageLists.get(num).get(arrayNum+2));
	                    } else if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("TABLE")) {
	                    	//Check if the next word is table
	                    	dbcommands.deleteTable(messageLists.get(num).get(arrayNum+2), messageLists.get(num).get(arrayNum+3));
	                    }
	                }
					
					//Checks if the first word is Insert
					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("INSERT")) {
						boolean firstString = false;
						int k = 0;
	                    String insertMessage = "";
	                    while(k < messageLists.get(num).size()) {
	                    	//Checks if its the beginning of the message
	                        if(messageLists.get(num).get(k).endsWith("\"")) {
	                            firstString = false;
	                            insertMessage += messageLists.get(num).get(k);
	                            break;
	                        }
	                        //Checks if its the middle or the end of the message
	                        if(messageLists.get(num).get(k).startsWith("\"") || firstString) {
	                            firstString = true;
	                            insertMessage += messageLists.get(num).get(k) + " ";
	                        }
	                        k++;
	                    }
	                    dbcommands.appendToTable(messageLists.get(num).get(k + 2), messageLists.get(num).get(k + 3), insertMessage);
	                }
					
					//Checks if first word is Delete
					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("DELETE")) {
						boolean ifWhere = false;
						for(int whereNum = 0; whereNum < messageLists.get(num).size(); whereNum++) {
							if(messageLists.get(num).get(whereNum).equalsIgnoreCase("WHERE")) {
								//Checks to see if Where is in the Select statement
								ifWhere = true;
							}
						}
						if(ifWhere) {
							boolean firstString = false;
		                    int k = 0;
		                    String insertMessage = "";
		                    while(k < messageLists.get(num).size()) {
		                    	//Checks if its the beginning of the message
		                        if(messageLists.get(num).get(k).endsWith("\"")) {
		                        	firstString = false;
		                            insertMessage += messageLists.get(num).get(k);
		                            break;
		                        }
		                        //Checks if its the middle or the end of the message
		                        if(messageLists.get(num).get(k).startsWith("\"") || firstString) {
		                        	firstString = true;
		                            insertMessage += messageLists.get(num).get(k) + " ";
		                        }
		                        k++;
		                        dbcommands.deleteFromTable(messageLists.get(num).get(arrayNum + 2), messageLists.get(num).get(arrayNum + 3), insertMessage);
		                    }
						} else {
							dbcommands.deleteFromTable(messageLists.get(num).get(arrayNum + 2), messageLists.get(num).get(arrayNum + 3), "");
						}
	                    
	                    
	                }
					
					//Checks to see if the first word is Select
					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("SELECT")) {
						boolean ifWhere = false;
						for(int whereNum = 0; whereNum < messageLists.get(num).size(); whereNum++) {
							if(messageLists.get(num).get(whereNum).equalsIgnoreCase("WHERE")) {
								//Checks to see if Where is in the Select statement
								ifWhere = true;
							}
						}
						if(ifWhere) {
							//If Where is located, isolates the column to be looked for
							String insertMessage = "";
	                        boolean firstString = false;
	                        int k = 0;
	                        while(k < messageLists.get(num).size()) {
	                            if(messageLists.get(num).get(k).endsWith("\"")) {
	                            	firstString = false;
	                            	insertMessage += messageLists.get(num).get(k);
	                                break;
	                            }
	                            if(messageLists.get(num).get(k).startsWith("\"") || firstString) {
	                            	firstString = true;
	                            	insertMessage += messageLists.get(num).get(k) + " ";
	                            }
	                            k++;
	                        }
						} else {
							//If where is not found, just run with all of them
							dbcommands.selectRow(messageLists.get(num).get(arrayNum + 3), messageLists.get(num).get(arrayNum + 4), "");
						}
					}
				}
			}
		
	  }
      catch(IOException e)
      {
         System.out.println("Problem with file "+readingFile+",error="+e.getMessage());
      }
	}




	


}