/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright Gannon McGibbon 2013
 * 
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * AgilitextFindReplaceManager: Class used to construct a find replace String window
 * 
 * @author Gannon McGibbon 
 * @version 1.1
 * 
 * Date Created: 08/05/13
 * Last Updated: 08/18/13
 */
public class AgilitextFindReplaceManager extends JDialog
{
	//GUI components
	
	//text fields
	protected AgilitextTextField findField;
	protected AgilitextTextField replaceField;
	
	//labels
	protected JLabel findLabel;
	protected JLabel replaceLabel;
	
	//buttons
	protected JButton findButton;
	protected JButton replaceButton;
	protected JButton replaceAllButton;
	protected JButton countButton;
	
	//panels
	protected JPanel fieldPanel;
	protected JPanel buttonPanel;
	protected JPanel mainPanel;
	
	//private objects
	
	//container
	private Container c;
	
	//text area target
	private JTextArea textArea;
	
	//last find and last index
	private String lastFind;
	private int lastIndex;
	
	//initialize serialization long
	private static final long serialVersionUID = 1L;
	
	/**
	 * AgilitextFindReplaceManager: Constructor
	 * @param parent: The parent JFrame used to set position of window
	 * @param textArea: The target text area
	 */
	public AgilitextFindReplaceManager(JFrame parent, JTextArea textArea)
	{
		//call super constructor using parent JFrame with no modal window priority
		super(parent, false);
		//set dialog to be always on top
		setAlwaysOnTop(true);
		//set location relative to parent JFrame
		setLocationRelativeTo(parent);
		
		//set last index and find to default values
		lastIndex=0;
		lastFind="";
		//set textArea to passed textArea
		this.textArea=textArea;
		
		//build panels
		buildFieldPanel();
		buildButtonPanel();
		buildMainPanel();
		
		//assign container
	    c = getContentPane();
	    //add main panel to container
	    c.add(mainPanel);
	    
	    //set size limitations
	    pack();
        setResizable(false);
        setMinimumSize(new Dimension((int)(getWidth()*1.3),getHeight()));
	    
        //add window dressing
	    setTitle("Find/Replace");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    
	    //set initial visibility to false
	    setVisible(false);
	}
	
	/**
	 * buildFontPanel: Initializes and constructs field panel
	 */
	private void buildFieldPanel()
	{
		//initialize field panel and set layout
		fieldPanel=new JPanel();
		fieldPanel.setLayout(new GridLayout(4,1));
		
		//initialize find and replace fields
		findField=new AgilitextTextField();
		replaceField=new AgilitextTextField();
		
		//initialize find and replace labels
		findLabel=new JLabel("Find:", JLabel.LEFT);
		replaceLabel=new JLabel("Replace with:",JLabel.LEFT);
		
		//findField.setMaximumSize(findField.getPreferredSize());
		//replaceField.setMaximumSize(replaceField.getPreferredSize());
		
		//set border for field panel
		fieldPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		
		//add components to panel
		fieldPanel.add(findLabel);
		fieldPanel.add(findField);
		fieldPanel.add(replaceLabel);
		fieldPanel.add(replaceField);
	}
	
	/**
	 * buildButtonPanel: Initializes and constructs button panel
	 */
	private void buildButtonPanel()
	{
		//initialize button panel and set layout
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(1,4));
		
		//set button panel border
		buttonPanel.setBorder(new LineBorder(Color.darkGray));
		
		//initialize panel buttons
		findButton=new JButton("Find Next");
		replaceButton=new JButton("Replace");
		replaceAllButton=new JButton("Replace All");
		countButton=new JButton("Count");
		
		//add buttons to panel
		buttonPanel.add(findButton);
		buttonPanel.add(replaceButton);
		buttonPanel.add(replaceAllButton);
		buttonPanel.add(countButton);
	}
	
	/**
	 * buildMainPanel: Initializes and constructs main panel
	 */
	private void buildMainPanel()
	{
		//initialize main panel and set layout
		mainPanel=new JPanel();
	    mainPanel.setLayout(new BorderLayout());
	    
	    //add field and button panels to main panel
	    mainPanel.add(fieldPanel, BorderLayout.CENTER);
	    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * findText: Used to locate text within the target text area
	 * @return: true if text exists, false if it does not
	 */
	public boolean findText()
	{
		//initialize exists to false
		boolean exists=false;
		//declare index, text, and find
		int index;
		String text;
		String find;
		
		//if findField contains text
		if(!findField.getText().isEmpty())
		{
			//capture lowercase text from target text area 
			//and capture lowercase text from find field
			text=textArea.getText().toLowerCase();
			find=findField.getText().toLowerCase();
			
			try
			{
				//if textArea selected text does not equal find
				//(if text has already been highlighted by the user and is irrelevant)
				if(!textArea.getSelectedText().equals(find))
				{
					//set lastIndex to 0
					lastIndex=0;
				}
			}
			catch(NullPointerException e)
			{
				//if an error occurs
				//(if no text is selected), set lastIndex to 0 anyway
				lastIndex=0;
			}
			
			//if lastFind does not equal find
			if((!lastFind.equals(find)))
			{
				//set lastIndex to 0
				lastIndex=0;
			}
			
			//set index to be the index of find from lastIndex in text
			index=text.indexOf(find, lastIndex);
			
			//if index is -1
			//(if index of find from lastIndex does not exist in text)
			if(index==-1)
			{
				//set index to index of find in all of text
				index=text.indexOf(find);
			}
			
			//if index is not -1
			//(if find exists within text)
			if(index!=-1)
			{
				//set exists to true
				exists=true;
				//select index to the end of find's length
				textArea.select(index,index+find.length());
				//set lastFind to find
				lastFind=find;
				//set lastFind to find + find's length
				lastIndex=index+find.length();
			}
		}
		
		return exists;
	}
	
	/**
	 * replaceText: Used to replace highlighted text 
	 * @return: true if replace is successful
	 */
	public boolean replaceText()
	{
		//Initialize success to false and selected to null
		boolean success=false;
		String selected=null;
		
		try
		{
			//attempt to assign selected the selected lowercase text of textArea
			selected=textArea.getSelectedText().toLowerCase();
			//set success to true temporarily
			success=true;
		}
		catch(NullPointerException e)
		{
			//if an error occurs
			//(if there was no selected text), call findText
			findText();
		}
		
		//if obtaining selected text was successful
		if(success)
		{
			//if selected text equals lastFind
			//(if the selected text is acceptable to replace)
			if(selected.equals(lastFind))
			{
				//replace selection with replaceField text
				textArea.replaceSelection(replaceField.getText());
				//call findText again to move to another occurrence
				findText();
			}
			else
			{
				//set success to false
				success=false;
				//call findText
				findText();
			}
		}
		
		return success;
	}
	
	/**
	 * replaceAll: Used to replace all occurrences of find text
	 */
	public void replaceAll()
	{
		do
		{
			//call replaceText
			replaceText();
		}
		//as long as lastIndex is not 0
		while(lastIndex!=0);
	}
	
	/**
	 * count: Used to count all occurrences of find text
	 */
	public void count()
	{
		//declare i and lastIndex
		int i;
		int lastIndex;
		//initialize text to target textArea's text
		String text=textArea.getText();
		//initialize find to findField's text
		String find=findField.getText();
		
		//if text and find are not empty
		if((!(text.isEmpty())&&(!(find.isEmpty()))))
		{
			//set i and lastIndex to 0
			i=0;
			lastIndex=0;
			//set text and find to their lowercase equivalents
			text=text.toLowerCase();
			find=find.toLowerCase();
			
			//while lastIndex is not -1
			while(lastIndex!=-1)
			{
				//set lastIndex to index of find from itself
			    lastIndex=text.indexOf(find,lastIndex);
			    
			    //if lastIndex does not equal -1
			    if(lastIndex!=-1)
			    {
			    	//increment i
			        i++;
			        //set lastIndex to itself plus find's length
			        lastIndex+=find.length();
			    }
			}
			
			//display a JOptionPane with count result
			JOptionPane.showMessageDialog(null, "\""+find+"\" occurs in document "+i+" time"+((i!=1)? "s":""), "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
