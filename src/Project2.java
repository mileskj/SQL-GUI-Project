/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree.*;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Project2 extends JPanel {
	
	//Declaring all of the GUI objects
    private JPanel main, sideMain, database, editor, rowList, statError;
    private JButton allCode, oneLineCode, sweepCode;
    private JMenuBar menu;
    private JTextArea textEditor;
    private JMenu file, edit, help;
    private JMenuItem open, run, save, copy, paste, preferences, about;
    private JTree tree;
    private static JList<String> selectList;
    private static JTable statusErrorTable;
    private JLabel separator, sqleditor, databasestruc, select, status, error;
    private static JScrollPane scroll, listScrollPane, statusScroll;
    private JScrollBar scroller;
    private File openedFile;
    //private Database data = new Database();
    private JOptionPane preference, aboutFrame;
    private static DefaultListModel<String> model;
    private static DefaultTableModel tableModel;
    private static boolean isNew = true;
    
    public Project2() {
        //Declaring all of the necessary panels
        main = new JPanel();
        database = new JPanel();
        editor = new JPanel();
        rowList = new JPanel();
        statError = new JPanel();
        sideMain = new JPanel();
        
        //Giving the panels their sizes
        database.setPreferredSize(new Dimension(300, 300));
        editor.setPreferredSize(new Dimension(700, 300));
        rowList.setPreferredSize(new Dimension(700, 245));
        statError.setPreferredSize(new Dimension(700, 265));
        sideMain.setPreferredSize(new Dimension(700, 850));
        
        //Setting layout of the frame
        setLayout(new BorderLayout());
        
        //Adding the labels for the parts of the GUI
        sqleditor = new JLabel("SQL EDITOR");
        select = new JLabel("SELECTED TABLE ROWS");
        databasestruc = new JLabel("DATABASE STRUCTURE");
        status = new JLabel("STATUS & ERROR LOG");
        //error = new JLabel("ERROR LOG");
        
        //Calling the methods and adding the GUI elements
        menuBar();
        sqlEditor();
        selectList();
        statusError();
        
        //Adding elements to panels
        sideMain.add(sqleditor);
        sideMain.add(editor);
        editor.add(scroll);
        rowList.add(select);
        rowList.add(listScrollPane);
        sideMain.add(rowList);
        statError.add(status);
        statError.add(statusScroll);
        sideMain.add(statError);
        database.add(databasestruc);
        
        //Adding panels to Frame
        add(sideMain, BorderLayout.EAST);
        add(database, BorderLayout.WEST);
        add(main);
    }
    
    public void menuBar() {
        //Creating the new Menu Bar
        menu = new JMenuBar();
        separator = new JLabel("   |   ");
        
        //Creating the new Menu's in the Menu Bar
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        
        //The three buttons to interact with, their images and sizes
        ImageIcon all = new ImageIcon("src/AllCommands.png");
        ImageIcon one = new ImageIcon("src/1command.png");
        ImageIcon sweep = new ImageIcon("src/sweep.png");
        
        //Declaring the three buttons in menu bar and adding button listeners
        allCode = new JButton(all);
        allCode.addActionListener(new buttonListener());
        oneLineCode = new JButton(one);
        oneLineCode.addActionListener(new buttonListener());
        sweepCode = new JButton(sweep);
        sweepCode.addActionListener(new buttonListener());
        
        //Setting the size of the buttons
        allCode.setPreferredSize(new Dimension(40, 40));
        oneLineCode.setPreferredSize(new Dimension(40, 40));
        sweepCode.setPreferredSize(new Dimension(40, 40));
        
        //Creating the items in the Menus
        open = new JMenuItem("Open");
        run = new JMenuItem("Run");
        save = new JMenuItem("Save");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        preferences = new JMenuItem("Preferences");
        about = new JMenuItem("About");
        
        //Adding the items to each other
        menu.add(file);
        file.add(open);
        file.add(run);
        file.add(save);
        menu.add(edit);
        edit.add(copy);
        edit.add(paste);
        edit.add(preferences);
        menu.add(help);
        help.add(about);
        menu.add(separator);
        menu.add(allCode);
        menu.add(oneLineCode);
        menu.add(sweepCode);
        
        //Adding action listeners to the menu items
        open.addActionListener(new buttonListener());
        run.addActionListener(new buttonListener());
        save.addActionListener(new buttonListener());
        copy.addActionListener(new buttonListener());
        paste.addActionListener(new buttonListener());
        preferences.addActionListener(new buttonListener());
        about.addActionListener(new buttonListener());
        
        //Adding the menu to the frame
        add(menu, BorderLayout.NORTH);
    }
    
    public void sqlEditor() {
    	//Declaring the text editor in the panel
    	scroller = new JScrollBar();
        textEditor = new JTextArea(17, 60);
        textEditor.setEditable(true);
        textEditor.setLineWrap(true);
        textEditor.setWrapStyleWord(true);
        
        //Setting the scroll settings for the text editor
        scroll = new JScrollPane(textEditor); 
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
    }
    
    public void selectList() {
    	if(isNew) {
	    	//Creating the model for the list
	    	this.model = new DefaultListModel();
	    	System.out.println(model);
	    	
	    	//Creating the list and the scroll for the list
	    	selectList = new JList(this.model);
	    	listScrollPane = new JScrollPane(selectList);
	    	isNew = false;
	    	listScrollPane.setPreferredSize(new Dimension(700, 250));
	    	
    	}
    }
    
    public void statusError() {
    	//Declaring the two icons for status and error
    	ImageIcon status = new ImageIcon("src//StatusIcon.png");
    	ImageIcon error = new ImageIcon("src//ErrorIcon.png");
    	
    	String[] columnNames = {"Icon", "Message"};
    	Object[][] data = {
    			{status, "Succesfully Opened Program."}
    	};
    	tableModel = new DefaultTableModel(data, columnNames);
    	statusErrorTable = new JTable(tableModel)
    	{
    		public Class getColumnClass(int column) {
    			return getValueAt(0, column).getClass();
    		}
    	};
    	statusScroll = new JScrollPane(statusErrorTable);
    	statusScroll.setPreferredSize(new Dimension(700, 240));
    }
    
    public void writeStatusError(boolean ifStatus, String update) {
    	ImageIcon status = new ImageIcon("src//StatusIcon.png");
    	ImageIcon error = new ImageIcon("src//ErrorIcon.png");
    	
    	if(ifStatus == true) {
    		tableModel.addRow(new Object[]{status, update});
    	} else if(ifStatus == false) {
    		tableModel.addRow(new Object[]{error, update});
    	}
    }
    
    
    private class buttonListener implements ActionListener {
    	//Calling these methods for each button and menu item pressed
    	public void actionPerformed(ActionEvent event) {
    		if(event.getSource() == open) {
    			openFile();
    		} else if(event.getSource() == run) {
    			runEditorFile();
    		} else if(event.getSource() == save) {
    			saveFile();
    		} else if(event.getSource() == copy) {
    			textEditor.copy();
    		} else if(event.getSource() == paste) {
    			textEditor.paste();
    		} else if(event.getSource() == preferences) {
    			
    		} else if(event.getSource() == about) {
    			about();
    		} else if(event.getSource() == allCode) {
    			runEditorFile();
    		} else if(event.getSource() == oneLineCode) {
    			highlighted();
    		} else if(event.getSource() == sweepCode) {
    			sweep();
    		}
    	}
    }
    
    public void openFile() {
    	//Replacing all current text
    	textEditor.selectAll();
    	textEditor.replaceSelection("");
    	//Looks specifically for SQL Files and saves the one selected
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "SQL Files", "sql", "txt");
        chooser.setFileFilter(filter);
        //chooser.setCurrentDirectory(new File(System.getProperty("src")));
        chooser.showOpenDialog(open);
        openedFile = chooser.getSelectedFile();
        
        //Reads the SQL File to the Editor
        try {
			BufferedReader inFile = new BufferedReader(new FileReader(openedFile));
			String line = inFile.readLine();
			while(line != null) {
				textEditor.append(line + "\n");
				line = inFile.readLine();
			}
			inFile.close();
			String updateMessage = "File " + openedFile + " has been opened.";
			writeStatusError(true, updateMessage);
		} catch (FileNotFoundException e) {
			String updateMessage = "File " + openedFile + " is not avaliable.";
			writeStatusError(false, updateMessage);
			e.printStackTrace();
		} catch (IOException e) {
			String updateMessage = "File " + openedFile + " is not avaliable.";
			writeStatusError(false, updateMessage);
			e.printStackTrace();
		}
        
    }
    
    public void runEditorFile() {
    	//Creating a temporary text file to place the SQL code on
		BufferedWriter outfile = null;
		File runningSQL = new File("temp.txt");
        
		 try {
			 runningSQL.createNewFile();
	         outfile = new BufferedWriter(new FileWriter(runningSQL));
	         System.out.println("printed and made");
	         textEditor.write(outfile);
	     } catch(Exception e) {
	    	 String updateMessage = "File " + openedFile + " could not be run.";
	    	 writeStatusError(false, updateMessage);
	         e.printStackTrace();
	     } finally {
	    	 if (outfile != null) {
	    		 try {
					outfile.close();
				} catch (IOException e) {
					String updateMessage = "File " + openedFile + " could not be run.";
			    	writeStatusError(false, updateMessage);
					e.printStackTrace();
				}
	    	 }
	     }
		//Running the temp file through the original database file, then deleting the temp file
		runFile(runningSQL);
		runningSQL.delete();
    }
    
    public void saveFile() {
    	//Opening the file chooser
    	JFileChooser saving = new JFileChooser();
		saving.setApproveButtonText("Save");
		int action = saving.showOpenDialog(save);
		if (action != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		//Saving the file to the system
		File savedFile = new File(saving.getSelectedFile() + ".sql");
		BufferedWriter outfile = null;
		try {
		         outfile = new BufferedWriter(new FileWriter(savedFile));
		         System.out.println("printed and made");
		         textEditor.write(outfile);
		     } catch(Exception e) {
		    	String updateMessage = "File " + savedFile + " could not be saved.";
		     	writeStatusError(false, updateMessage);
		        e.printStackTrace();
		     } finally {
		    	 if (outfile != null) {
		    		 try {
						outfile.close();
					} catch (IOException e) {
						String updateMessage = "File " + savedFile + " could not be saved.";
				     	writeStatusError(false, updateMessage);
						e.printStackTrace();
					}
		    	 }
		     }
		String updateMessage = "File " + savedFile + " has been saved.";
     	writeStatusError(true, updateMessage);
    }
   
    public void about() {
    	//Displaying the about frame
    	String info = "This SQL Database GUI Program was coded by Miles Jordan.\nEach database and table are "
    			+ "displayed to the left.\nSQL code can be typed in to the text editor.\nYour selected rows"
    			+ "and the status and error logs are displayed as well.";
    	aboutFrame = new JOptionPane("ABOUT");
    	JFrame helpFrame = new JFrame("ABOUT");
    	aboutFrame.showMessageDialog(helpFrame, info);
    	String updateMessage = "Opened the about tab.";
     	writeStatusError(true, updateMessage);
    }
    
    public void highlighted() {
    	//Selecting the highlighted text, saving it to a text file and running it
		File runningSQL = new File("temp.txt");
		String text = textEditor.getSelectedText();
		try {
			 runningSQL.createNewFile();
	         PrintWriter outfile = new PrintWriter("temp.txt");
	         outfile.println(text);
	         outfile.close();
	     } catch(Exception e) {
	    	 String updateMessage = "Highlighted text cannot be run.";
		     writeStatusError(false, updateMessage);
	         e.printStackTrace();
	     }
		String updateMessage = "Highlighted text has been run.";
     	writeStatusError(true, updateMessage);
		runFile(runningSQL);
		runningSQL.delete();
    }

    public void sweep() {
    	//Remvoes all text in the editor
    	textEditor.selectAll();
    	textEditor.replaceSelection("");
    	writeStatusError(true, "All Code has been sweeped.");
    }
    
    public static void main(String[] args) {
    	//Declares the main frame and adds the content to it
        JFrame frame = new JFrame("SQL Database GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Project2 panel = new Project2();
        
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void runFile(File fileName)
    {
       
 	   
 	  String readingFile = fileName.getName();
 	  Scanner infile;

       try
       {
    	  model.removeAllElements();
    	  //tableModel.setRowCount(0);
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
 			
 			//Checks to see what command it is
 			for(int num = 0; num < messageLists.size(); num++) {
 				
 				for(int arrayNum = 0; arrayNum < messageLists.get(num).size(); arrayNum++) {
 					//Checks if the first word is create
 					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("CREATE")) {
 						//Checks if the next word is database
 						
 						if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("DATABASE")) {
 							CreateDatabase(messageLists.get(num).get(arrayNum+2));
 							
 						} else if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("TABLE")) {
 							//Checks if the next word is table
 							CreateTable(messageLists.get(num).get(arrayNum+2) + "/" + messageLists.get(num).get(arrayNum+3));
 						} else {
 							//If line doesn't have correct syntax will show this
 							String updateMessage = "Could not interpret line: ";
 							for(String i : messageLists.get(num)) {
 								System.out.println(i);
 								updateMessage += (i + " ");
 							}
 							writeStatusError(false, updateMessage);
 						}
 					}
 					
 					//Checks if the first word is drop
 					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("DROP")) {
 						//Checks if the next word is Database
 	                    if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("DATABASE")) {
 	                    	deleteDirectory(messageLists.get(num).get(arrayNum+2));
 	                    } else if(messageLists.get(num).get(arrayNum+1).equalsIgnoreCase("TABLE")) {
 	                    	//Check if the next word is table
 	                    	deleteTable(messageLists.get(num).get(arrayNum+2), messageLists.get(num).get(arrayNum+3));
 	                    } else {
 	                    	//If Line doesnt have correct syntax will display this
 	                    	String updateMessage = "Could not interpret line: ";
 							for(String i : messageLists.get(num)) {
 								System.out.println(i);
 								updateMessage += (i + " ");
 							}
 							writeStatusError(false, updateMessage);
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
 	                    appendToTable(messageLists.get(num).get(k + 2), messageLists.get(num).get(k + 3), insertMessage);
 	                }
 					
 					//Checks if first word is Delete
 					if(messageLists.get(num).get(arrayNum).equalsIgnoreCase("DELETE")) {
 						boolean ifWhere = false;
 						for(int whereNum = 0; whereNum < messageLists.get(num).size(); whereNum++) {
 							if(messageLists.get(num).get(whereNum).equalsIgnoreCase("WHERE")) {
 								//Checks to see if Where is in the Select statement
 								ifWhere = true;
 								break;
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
 		                    }
 		                    insertMessage = insertMessage.substring(1);
		                    insertMessage = insertMessage.substring(0, insertMessage.length()-1);
 		                    deleteFromTable(messageLists.get(num).get(arrayNum + 2), messageLists.get(num).get(arrayNum + 3), insertMessage);
 						} else {
 							deleteFromTable(messageLists.get(num).get(arrayNum + 2), messageLists.get(num).get(arrayNum + 3), "");
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
 	                        //Removing the quotes before and after and importing the new message
 	                       insertMessage = insertMessage.substring(1);
		                   insertMessage = insertMessage.substring(0, insertMessage.length()-1);
 	                       selectRow(messageLists.get(num).get(arrayNum + 3), messageLists.get(num).get(arrayNum + 4), insertMessage);
 						} else {
 							//If where is not found, just run with all of them
 							selectRow(messageLists.get(num).get(arrayNum + 3), messageLists.get(num).get(arrayNum + 4), "");
 						}
 					}
 				}
 			}
 		
 	  }
       catch(IOException e)
       {
          String updateMessage = "Problem with file "+ readingFile;
          writeStatusError(false, updateMessage);
       } catch (ArrayIndexOutOfBoundsException e) {
    	   String updateMessage = "Could not interpret line";
    	   writeStatusError(false, updateMessage);
       }
 	}

    public boolean deleteDirectory(String dbName) throws IOException {
    	//Declares the file as the database
        File database = new File(dbName);
        if(database.isDirectory()) {
        	//Checks to see if the databse has files, and deletes them
            File[] components = database.listFiles();
            for(File innerFile : components) {
                if(!innerFile.delete()) {
                	String updatedMessage = "Could not delete files from" + dbName;
                	writeStatusError(false, updatedMessage);
                    return false;
                }
            }
        } else {
        	String updatedMessage = dbName + " is not a directory.";
        	writeStatusError(false, updatedMessage);
        }
        
        String updatedMessage = "Succesfully deleted database " + dbName;
        writeStatusError(true, updatedMessage);
        return database.delete();
    }

    public boolean deleteTable(String dbName, String tableName) {
    	
        System.out.println(dbName + " " + tableName);
        //Declares the table file being deleted
        File table = new File(dbName + "/" + tableName + ".txt");

        if(table.exists())
        	//Checks to see if the table exists and then deletes
            if(!table.isDirectory() && table.delete()) {
            	String updatedMessage = "Succesfully deleted " + tableName + " from " + dbName;
            	writeStatusError(true, updatedMessage);
                return true;
            } else {
            	String updatedMessage = "Could not delete " + tableName + " from " + dbName;
            }
        else {
        	String updatedMessage = dbName + "\"" + tableName + " does not exist";
        	writeStatusError(false, updatedMessage);
        }
        return false;
    }

    public boolean deleteFromTable(String dbName, String table, String whatToDelete) throws IOException{

        String lineRead;
        File tableFile = new File(dbName + "/" + table + ".txt");
        try {
        	if(tableFile.exists()) {
	        	//Declares the file to be read and deleted from
	            FileWriter fw = new FileWriter((dbName + "/" + table + ".txt"), true);
	            PrintWriter pw = new PrintWriter(fw);
	            
	
	            if(whatToDelete.equals("")) {
	            	//If theres is no what to delete argument, it will just clear the table file
	                fw = new FileWriter((dbName + "/" + table + ".txt"), false);
	                pw = new PrintWriter(fw);
	                pw.println("");
	                String updatedMessage = table + " file cleared";
	                writeStatusError(true, updatedMessage);
	                fw.close();
	            } else {
	            	File temp = new File(dbName + "/" + "temp.txt");
	            	temp.createNewFile();
	            	
	            	BufferedReader reader = new BufferedReader(new FileReader(dbName + "/" + table + ".txt"));
	            	FileWriter fw2 = new FileWriter((temp), true);
	                PrintWriter pw2 = new PrintWriter(fw2);
	                
	                while((lineRead = reader.readLine()) != null) {
	                	//Checks to see if the line being read is what to delete
	                    String trim = lineRead.trim();
	                    System.out.println("trim is: " + trim + "what to delete is: " + whatToDelete);
	                    if(trim.equals(whatToDelete))
	                        continue;
	                    else
	                        pw2.println(lineRead);
	                }
	                pw2.close();
	                pw.close();
	                reader.close();
	                fw2.close();
	                File myTable = new File(dbName + "/" + table + ".txt");
	                if(myTable.exists()) {
	                    if(myTable.delete()) {
	                    	System.out.println("Deleted");
	                    }
	                    if(temp.renameTo(myTable)) {
	                    	System.out.println("Renamed");
	                    }
	                }
	                
	                String updatedMessage = whatToDelete + " deleted from " + table;
	                writeStatusError(true, updatedMessage);
	            }
	            
	            return true;
        	} else {
        		String updatedMessage = "File " + table + " does not exist.";
                writeStatusError(false, updatedMessage);
                return false;
        	}
        } catch (Exception e) {
        	String updatedMessage = "Could not read file " + table;
        	writeStatusError(false, updatedMessage);
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
	            fw.close();
	            String updatedMessage = whatToAppend + " added to table " + table;
                writeStatusError(true, updatedMessage);
        	} else {
        		String updatedMessage = whatToAppend + " unable to be added to " + table;
                writeStatusError(false, updatedMessage);
        	}
        } catch (Exception e){
        	String updatedMessage = whatToAppend + " unable to be added to " + table;
            writeStatusError(false, updatedMessage);
            e.printStackTrace();
        }
    }

    public boolean CreateTable(String file) {

        try {
        	//Declares and creates the new text file
            File table = new File(file+".txt");
            if(table.createNewFile()) {
            	 String updatedMessage = "New table " + file + " created.";
                 writeStatusError(true, updatedMessage);
                 return true;
            } else {
            	String updatedMessage = file + " unable to be created.";
                writeStatusError(false, updatedMessage);
                return false;
            }
            
        } catch(Exception e) {
        	String updatedMessage = file + " unable to be created.";
            writeStatusError(false, updatedMessage);
            e.printStackTrace();
            return false;
        }

    }
    public boolean CreateDatabase(String dbName) {
    	//Declares and creates the next text file
        File db = new File(dbName);
        if(db.mkdir()) {
        	String updatedMessage = "New database " + dbName + " created.";
            writeStatusError(true, updatedMessage);
        	return true;
        } else {
        	String updatedMessage = "Database " + dbName + " could not be created.";
        	writeStatusError(false, updatedMessage);
        	return false;
        }
    }

    
    public void selectRow(String dbName, String tableFile, String whatToSelect) throws IOException {
    	try{
	    	model.removeAllElements();
	    	//Declares the new file being read
	    	String lineRead;
	    	String file = dbName + "/" + tableFile + ".txt";
			
			BufferedReader reader = new BufferedReader(new FileReader(dbName + "/" + tableFile + ".txt"));
			
			if(whatToSelect.equals("")) {
				while((lineRead = reader.readLine()) != null) {
					//Reads each individual line and runs it through the add list method
					 String trim = lineRead.trim();
					 model.addElement(trim);
		    	}
				String updatedMessage = "All lines selected from " + tableFile;
	    		writeStatusError(true, updatedMessage);
	    		reader.close();
			} else {
				while((lineRead = reader.readLine()) != null) {
					if(lineRead.equals(whatToSelect)) {
						//Checks if the selected line equals the whatToSelect line
						 String trim = lineRead.trim();
						 model.addElement(trim);
					}
		    	}
				String updatedMessage = whatToSelect + " selected from " + tableFile;
	    		writeStatusError(true, updatedMessage);
	    		reader.close();
			}
    	} catch(IOException e) {
    		String updatedMessage = whatToSelect + " unable to be selected from " + tableFile;
    		writeStatusError(false, updatedMessage);
    	}
    	
    }
}

