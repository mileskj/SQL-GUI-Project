import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class DBCommands extends Project2 {
	
    public boolean deleteDirectory(String dbName) throws IOException {
    	//Declares the file as the database
        File database = new File(dbName);
        if(database.isDirectory()) {
        	//Checks to see if the databse has files, and deletes them
            File[] components = database.listFiles();
            for(File innerFile : components) {
                if(!innerFile.delete()) {
                    return false;
                }
            }
        } else {
        	System.out.println(dbName + " is not a directory.");
        }

        return database.delete();
    }

    public boolean deleteTable(String dbName, String tableName) {
    	
        System.out.println(dbName + " " + tableName);
        //Declares the table file being deleted
        File table = new File(dbName + "/" + tableName + ".txt");

        if(table.exists())
        	//Checks to see if the table exists and then deletes
            if(!table.isDirectory()) {
                return table.delete();
            }
        else
            System.out.println(dbName + "\"" + tableName + " does not exist");

        return false;
    }

    public boolean deleteFromTable(String dbName, String table, String whatToDelete) throws IOException{

        String lineRead;
        try {
        	//Declares the file to be read and deleted from
            FileWriter fw = new FileWriter((dbName + "/" + table + ".txt"), true);
            PrintWriter pw = new PrintWriter(fw);

            if(whatToDelete.equals("")) {
            	//If theres is no what to delete argument, it will just clear the table file
                fw = new FileWriter((dbName + "/" + table + ".txt"), false);
                pw = new PrintWriter(fw);
                pw.println("");
            }

            BufferedReader reader = new BufferedReader(new FileReader(dbName + "/" + table + ".txt"));

            while((lineRead = reader.readLine()) != null) {
            	//Checks to see if the line being read is what to delete
                String trim = lineRead.trim();
                System.out.println("trim is: " + trim + "what to delete is: " + whatToDelete);
                if(trim.equals(whatToDelete))
                    continue;
                else
                    pw.println(lineRead);
            }
            File myTable = new File(dbName + "/" + table + ".txt");
            if(myTable.exists()) {
                myTable.delete();
                myTable.createNewFile();
            }
            pw.close();
            reader.close();

            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public void appendToTable(String dbName, String tableFile, String whatToAppend) {
    	//Declares the file and sets up the file needing to be appended
        File table = new File(dbName + "/" + tableFile + ".txt");
        whatToAppend = whatToAppend.substring(1);
        whatToAppend = whatToAppend.substring(0, (whatToAppend.length()-1));

        try {
        	if(table.exists()) {
        		//Checks to see if the table it is writing to exists
	            FileWriter fw = new FileWriter(table.getAbsolutePath(), true);
	            PrintWriter pw = new PrintWriter(fw);
	            //Prints what needs to be written
	            pw.println(whatToAppend);
	            pw.flush();
	
        	}
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean CreateTable(String file) {

        try {
        	//Declares and creates the new text file
            File table = new File(file+".txt");
            return table.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean CreateDatabase(String dbName) {
    	//Declares and creates the next text file
        File db = new File(dbName);
        return db.mkdir();

    }

    
    public void selectRow(String dbName, String tableFile, String whatToSelect) throws IOException {
    	//Declares the new file being read
    	String lineRead;
    	String file = dbName + "/" + tableFile + ".txt";
		
		BufferedReader reader = new BufferedReader(new FileReader(dbName + "/" + tableFile + ".txt"));
    	
		while((lineRead = reader.readLine()) != null) {
			//Reads each individual line and runs it through the add list method
			 String trim = lineRead.trim();
			 //addList(trim);
    	}
    	
    }
}
