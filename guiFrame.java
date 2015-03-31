/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanization_netbean;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author HanJo
 */
public class guiFrame extends JFrame
{
    
    private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 450;
	
	private JLabel input;
	private JLabel output;
	private JLabel fileLabel;
	private JFileChooser inputfilechoose;
	private JFileChooser outputfilechoose;
	private JTextField inputField;
	private JTextField outputField;
	private JTextField fileField;
	
	private JButton search;
	private JButton search2;

	private JButton romanization;
	private JButton cancel;
	
	private String inputDirectory;
	private String outputDirectory;
	private String fileName;
	private String finalOutputDirectory;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//////////romanization variables/////////////////////////////////////////////////	
	
	
	public guiFrame()
	{

		//labels and text field
		createInputField();
		createOutputField();
		createFileField();
		
		//buttons
		createSearchButton();
		createSearchButton2();

		cancelationButton();
		romanizationButton();
		
		createPanel();
		
		setSize(FRAME_WIDTH, FRAME_WIDTH);
	}
	
////////set input file////////////////////////////////////////////////////////////////
	private void createInputField()
	{
		input = new JLabel("input file name:  ");
		inputField = new JTextField("please click the search button                       ");
	}
	

	class searchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			inputfilechoose = new JFileChooser();
			inputfilechoose.setCurrentDirectory(new java.io.File("."));
			inputfilechoose.setDialogTitle("file");
			int returnValue = inputfilechoose.showOpenDialog(null);
			File selectedFile = inputfilechoose.getSelectedFile();
			inputDirectory = selectedFile.getPath();
			 
			inputField.setText(inputDirectory);

		}
	}
	
	private void createSearchButton()
	{
		search= new JButton("Search");
		ActionListener listener = new searchListener();
		search.addActionListener(listener);

	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////set output path////////////////////////////////////////////////////////////////////////////////	
	
	private void createOutputField()
	{
		output = new JLabel("output path : ");
		outputField = new JTextField("please click search button                                   ");
	}
	
	private void createSearchButton2()
	{
		search2=new JButton("Search");
		ActionListener listener = new searchListener2();
		search2.addActionListener(listener);
		
	}
	
	class searchListener2 implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			outputfilechoose = new JFileChooser();
			outputfilechoose.setMultiSelectionEnabled(false);
			outputfilechoose.setCurrentDirectory(new java.io.File("."));
			outputfilechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			outputfilechoose.setApproveButtonText("select");
            int returnVal = outputfilechoose.showOpenDialog(null);
            File selectedFile = outputfilechoose.getSelectedFile();
            outputDirectory =  selectedFile.getPath();
            outputField.setText(outputDirectory);
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
/////set output file name///////////////////////////////////////////////////////////////////////////////////////
	private void createFileField()
	{
		fileLabel = new JLabel("output file name : ");
		fileField = new JTextField("File name            ");
		fileField.setEditable(true);
	}
	


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
///////create romanizatoin//////////////////////////////////////////////////////////////////////////////////////
	private void romanizationButton()
	{
		romanization = new JButton("Rominiaztion");
		ActionListener listener = new romanizationListener();
		romanization.addActionListener(listener);
	}
	class romanizationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{

				fileName = fileField.getText();
				finalOutputDirectory = outputDirectory + "/" + fileName;
				System.out.println("romanization works!!");
				try 
				{
					//do romanization
					
					doRomanization();
					JOptionPane.showMessageDialog(null, "Done", "it is done", JOptionPane.PLAIN_MESSAGE);
					
					File outputFile = new File(finalOutputDirectory);
					//opening the output file
					if (outputFile.exists()) {
						 
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(outputFile);
						} else {
							System.out.println("Awt Desktop is not supported!");
						}
			 
					} else {
						System.out.println("File is not exists!");
					}
			 
					
				} 
				
				//if input file is not found
				catch(NullPointerException e)
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "input file is not selected", "ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
				//if output path is not found
				catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "please select output path", "ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} 
				//if outputfile is not found
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "It is not done. your ounput file name should ends with .txt ", "ERROR", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//////create cancelation////////////////////////////////////////////////////////////////////////////////////////
	private void cancelationButton()
	{
		cancel = new JButton("cancel");
		ActionListener listener = new cancelListener();
		cancel.addActionListener(listener);
		
		
	}
	
	class cancelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//add all item into panel, the panel is added into jframe///////////////////////////////////////////////////////
	private void createPanel()
	{
		JPanel panel = new JPanel();
		panel.add(input);
		panel.add(inputField);
		panel.add(search);
		panel.add(output);
		panel.add(outputField);
		panel.add(search2);
		panel.add(fileLabel);
		panel.add(fileField);

		panel.add(romanization);
		panel.add(cancel);

		add(panel);
		setVisible(true);
	}
	private void doRomanization() throws FileNotFoundException
	{
		 
		 

		// romanized output and origianl text;

			String original = new String();
		 
		 //line seperator
		 String newline = System.getProperty("line.separator");
		 
		//set the scanner and print writer the file name
		File inputFile = new File(inputDirectory);
		
		Scanner in = new Scanner(inputFile);
	    PrintWriter out = new PrintWriter(finalOutputDirectory);
	    
	    
//this loop does the romanization*********************************************************************************
	    //check lines
	    
	    while(in.hasNextLine())
	    	{
	    		//getting whole line
	    		original = in.nextLine();
	    		
	    		System.out.println(original);
	    		
	    		
	    		original = " " +original;
	    		String product = romanizator(original);
	    		original= original.substring(1, original.length());
	    		
	    		//printing original and romanized output
	    		out.print(original + newline);
	    		out.print("<font color=\"red\">"+product + "</font>"+newline);
	    		out.print("<font color=\"blue\">" + "</font>"+newline);
	    		//initialize counter to zero
	    		
	    		
	    		//original and normalized sentences go back to blank sentence
	    		original = "";

	    	}
	    in.close();
		out.close();
}
//****************************************************************************************************************
	 
	 //romanizator
	 public static String romanizator(String input)
	 {
		 
		 //arrays for first consonant, vowel and last consonant
		 String[] firstConArray = {"g","gg","n","d","dd","l","m","b","bb","s","ss","","j","jj","ch","k","t","p","h"};
		 String[] vowelArray = {"a","ae","ya","ye","u","e","yu","ye","o","wa","wae","oi","yo","oo","war","ue","we","you","eu","ui","i"};
		 String[] lastConArray = {"","g","gg","gs","n","nj","nh","d","l","g","lm","lb","ls","lt","lp","lh","m","b","bs","s","t","ng","j","ch","k","t","p","h"};
		 
		 String finalProduct = new String();
		 
		 for(int i = 1; i<input.length(); i++)
		{		
			
			//change Korean character to unicode number
			String singleString =Character.toString(input.charAt(i));
			
			
			//change korean character into hashcode
			int hangeulNum = singleString.hashCode();
				
			//check whether or not current character is space
			if(input.charAt(i) == 32)
			{
				//removing "-"
				if(finalProduct.charAt(finalProduct.length()-1) == '-')
				{
				
				finalProduct = changePartOfString(finalProduct,finalProduct.length()-1,' ');
				finalProduct += " ";
				hangeulNum = 0;
				}
				else
				finalProduct += " ";
				hangeulNum = 0;
				
			}
			//check whether or not the character is Korean
			else if(hangeulNum < 44032)
			{
				finalProduct+= singleString;
				hangeulNum = 0;
				if(i<input.length()-1 && Character.toString(input.charAt(i+1)).hashCode()>=44032)
				{
					finalProduct+="-";
				}
			}
			//otherwise do romanization
			else
			{	
			
				
				hangeulNum -= 44032;
				//splitting first consonant, vowel and last consonant
				int firstCon = (hangeulNum / 588);
				int vowl = (hangeulNum % 588) / 28;
				int lastCon = (hangeulNum % 588) % 28;
				finalProduct += firstConArray[firstCon] +vowelArray[vowl]+lastConArray[lastCon]+"-";
				
//exception cases
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
				//case when previous character has last consonent and 占쏙옙 is the current consonant	
				//if the last consonant is 占쏙옙 , then we do not have to do this
				if(i>0 && firstCon == 11 && ((SingleStringToInt(input, i-1))%588)%28>0 && singleString != " " && (SingleStringToInt(input, i-1))%588%28!= 21)
				{
					int pos_con = finalProduct.length()-countingFullKoreanWord(finalProduct)-3;
					int pos_hy = finalProduct.length()-countingFullKoreanWord(finalProduct)-2;
					
					//first is the position of last consonant. second is the position of -
					char first = finalProduct.charAt(pos_con);
					char second = finalProduct.charAt(pos_hy);
					
					//switching the last consonant is the -
					finalProduct = changePartOfString(finalProduct,pos_con,second);
					finalProduct = changePartOfString(finalProduct,pos_hy,first);	
				}
				
				//case when previous character has 占쏙옙 or 占쏙옙 as last consonent and  current has 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 as first consonant
				if(i>0 &&((SingleStringToInt(input, i-1))%588)%28==19 || ((SingleStringToInt(input, i-1))%588%28)==25)
				{
					if(firstCon==0 || firstCon==3 || firstCon==7 || firstCon==9 || firstCon==12)
					{
						//splitting finalproduct into two pieces and delete the last consonant from previous character
						int positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
						String subString1 = finalProduct.substring(0,positionOfcurrentCon-3);	
						String subString2 = finalProduct.substring(positionOfcurrentCon-2,finalProduct.length());					
						finalProduct = subString1+subString2;
						
						// Recalculate consonant position.  Splits the final product againand add a consonant between them.
						positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
						String subString3 = finalProduct.substring(0,positionOfcurrentCon);
						String subString4 = finalProduct.substring(positionOfcurrentCon,finalProduct.length());
						
						char con = finalProduct.charAt(positionOfcurrentCon-1);
						//add a con between the substrings
						finalProduct = subString3 + con+ subString4;
						
					}
				}
				//case when pervious character has 占쏙옙 as last consonant and current character has 占쏙옙 as first consonant
				if(i>0 && (SingleStringToInt(input, i-1)%588)%28 == 1 && firstCon == 18)
				{
					int positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
					String subString1 = finalProduct.substring(0,positionOfcurrentCon-3);	
					String subString2 = finalProduct.substring(positionOfcurrentCon-2,finalProduct.length());					
					finalProduct = subString1+subString2;
					
					positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct)-1;
					finalProduct = changePartOfString(finalProduct, positionOfcurrentCon, 'k');
					
					System.out.println(finalProduct);
					
				}
				//case when previous character's last consonant and current character's first consonant are identical
				if(i>0 && ((SingleStringToInt(input, i-1)%588)%28 == 1 && firstCon== 0) || ((SingleStringToInt(input, i-1)%588)%28 == 7 && firstCon== 3)||((SingleStringToInt(input, i-1)%588)%28 == 17 && firstCon== 7)||((SingleStringToInt(input, i-1)%588)%28 == 19 && firstCon== 9)||((SingleStringToInt(input, i-1)%588)%28 == 22 && firstCon== 12))
				{
					//splitting finalproduct into two pieces and delete the last consonant from previous character
					int positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
					String subString1 = finalProduct.substring(0,positionOfcurrentCon-3);	
					String subString2 = finalProduct.substring(positionOfcurrentCon-2,finalProduct.length());					
					finalProduct = subString1+subString2;
					
					// Recalculate consonant position.  Splits the final product againand add a consonant between them.
					positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
					String subString3 = finalProduct.substring(0,positionOfcurrentCon);
					String subString4 = finalProduct.substring(positionOfcurrentCon,finalProduct.length());
					
					char con = finalProduct.charAt(positionOfcurrentCon-1);
					//add a con between the substrings
					finalProduct = subString3 + con+ subString4;
				}
				
				
			//case when last consonant of previous character is 占쏙옙 and first consonant of current character is 占쏙옙
				
			if((i>0 && (SingleStringToInt(input, i-1)%588)%28 == 20) && firstCon == 11)
			{
				int positionOfcurrentCon = finalProduct.length()-countingFullKoreanWord(finalProduct);
				String subString1 = finalProduct.substring(0, positionOfcurrentCon-1);
				String subString2 = finalProduct.substring(positionOfcurrentCon, finalProduct.length());
				
				finalProduct =subString1+"ss"+subString2; 
				
			}
				//case when last consonant of previous character is 占쏙옙 and current character's first consonant is  占쏙옙占쏙옙占쏙옙 占쏙옙 占쏙옙 ,
				
				hangeulNum = 0;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
			}
		
		}
			//removing the last -
   		if(finalProduct.charAt(finalProduct.length()-1) == '-')
			{
   		StringBuilder tmp = new StringBuilder(finalProduct);
			tmp.setCharAt(finalProduct.length()-1, ' ');
			finalProduct = tmp.toString();
			finalProduct += " ";
			}
		 return finalProduct;
	 }

	 //bringing a single string into a hash code
	 public static int SingleStringToInt(String input, int index)
	 {
		 String singleString = Character.toString(input.charAt(index));
		 return singleString.hashCode()-44032;
	 }
	 //counting the romanized korean until it reaches -
	 public static int countingFullKoreanWord(String sentence)
	 {
		 int counter=0;
		 int i = sentence.length()-2;
		 while(sentence.charAt(i) != '-')
		 {
			 counter++;
			 i--;
		 }
		 return counter;
	 }
	
	//changing part of string by index number
	 public static String changePartOfString(String input, int index, char item)
	 {
		 String output = new String();
		 StringBuilder tmp = new StringBuilder(input);
		 tmp.setCharAt(index, item);
		 output = tmp.toString();
		 
		 return output;
	 }
	
}
