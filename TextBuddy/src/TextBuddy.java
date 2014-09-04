/*
 *This class is used to store user input data into the text file and read,write,delete specific line,clear all the content
 *The file name is assumed to end with ".txt"
 *The user is assumed to read the file that is already existed
 *The user delete the line from the range available in the display
 *@author Paing Zin Oo
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextBuddy {

	//all the string and integer constant that are necessary for the program
	private static final String ADD = "add";
	private static final String DISPLAY = "display";
	private static final String DELETE = "delete";
	private static final String EXIT = "exit";
	private static final String CLEAR = "clear";
	private static final String WELCOME_MSG ="Welcome to TextBuddy. %1$s is ready for use";
	private static final String ADDED_MSG = "added to %1$s : \"%2$s\"";
	private static final String COMMAND = "command: ";
	private static final String CANNOT_OPEN_FILE_MSG= "The file cannot be opened";
	private static final String DELETE_LINE_MSG = "deleted from %1$s: \"%2$s\"";
	private static final String EMPTY_MSG ="%1s is empty";
	private static final String TEMP_FILE_NAME ="temp";
	private static final String DISPLAY_TEXT = "%1$d. %2$s";
	private static final String ALL_CONTENT_DELETED = "all content deleted from %1$s";

	private static final int TEXT_DISPLAY_START_NUM = 1;

	public static Scanner sc = new Scanner(System.in);

	public static void main (String[] args) {
		String fileName =args[0] ;

		System.out.println(String.format(WELCOME_MSG,fileName));
		System.out.print(COMMAND);
		String command = sc.nextLine();

		//this while loop will end while the user command is equal to EXIT
		while (true){
			if(command.split(" ")[0].equalsIgnoreCase(ADD)){
				doWriteFile(fileName, command);
			}
			else if(command.equalsIgnoreCase(DISPLAY)){
				readFile(fileName);
			}
			else if(command.split(" ")[0].equalsIgnoreCase(DELETE)){	
				deleteLineFromFile(fileName,Integer.parseInt(command.substring(DELETE.length()).trim()));
			}
			else if(command.equalsIgnoreCase(CLEAR)){
				clearFile(fileName);
			}
			else if(command.equalsIgnoreCase(EXIT)){
				System.exit(0);
			}

			System.out.print(COMMAND);
			command = sc.nextLine();
		}

	}
 private static void testing(){
	 System.out.println();
	 
 }
	//This method extract out the text that needs to be added into the text file and 
	// parse it to another method to write into specific file
	private static void doWriteFile(String fileName, String command) {
		String addText = command.substring(ADD.length()).trim();
		writeTextFile(fileName,addText);
		System.out.println(String.format(ADDED_MSG, fileName,addText));
	}

	//This method is used to clear all the texts from the text file
	private static void clearFile(String fileName) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println(String.format(ALL_CONTENT_DELETED, fileName));

	}

	//This method is used to delete specific line from the text file
	private static void deleteLineFromFile(String fileName, int delLineNo) {
		File inputFile = new File(fileName);
		File tempFile = new File(fileName+TEMP_FILE_NAME);
		BufferedReader reader;
		BufferedWriter writer;
		String currentLine;
		int currentLineNo =TEXT_DISPLAY_START_NUM;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			writer = new BufferedWriter(new FileWriter(fileName+TEMP_FILE_NAME));
			if(inputFile.length() ==0){
				System.out.println(String.format(EMPTY_MSG,fileName));
			}
			else{
				while((currentLine = reader.readLine()) != null){
					if(currentLineNo != delLineNo){
						writer.write(currentLine);
						writer.newLine();
					}
					else{
						System.out.println(String.format(DELETE_LINE_MSG,fileName,currentLine));
					}
					currentLineNo++;
				}

			}
			writer.close();
			reader.close();
		}
		catch(Exception e){
			System.out.println(CANNOT_OPEN_FILE_MSG);
		}

		inputFile.delete();
		tempFile.renameTo(inputFile);	
	}

	//This method is used to read the file accordin to the parameter fileName from the user input
	private static void readFile(String fileName) {
		File file = new File(fileName);
		BufferedReader br = null;
		int textNo = TEXT_DISPLAY_START_NUM;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(fileName));
			if(file.length()==0){
				System.out.println(String.format(EMPTY_MSG, fileName));
			}

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(String.format(DISPLAY_TEXT,textNo,sCurrentLine));
				textNo++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	//This method is used to write text into the text using parameter fileName and string from user input
	private static void writeTextFile(String fileName, String text) {
		FileWriter output = null;
		try {
			output = new FileWriter(fileName,true);
			BufferedWriter writer = new BufferedWriter(output);
			writer.append(text);
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {

				}
			}
		}

	}
}

