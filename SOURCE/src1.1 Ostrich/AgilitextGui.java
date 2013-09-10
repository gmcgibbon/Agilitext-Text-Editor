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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * AgilitextGui: Class used to construct the main Agilitext GUI
 * 
 * @author Gannon McGibbon 
 * @version 1.1
 * 
 * Date Created: 06/06/13
 * Last Updated: 08/18/13
 */
public class AgilitextGui extends JPanel
{
	//GUI components
	
	//panels
	protected JPanel mainPanel;
	protected JPanel subPanel;
	
	//scroll panes
	protected JScrollPane mainScroll;
	
	//text areas
	public AgilitextTextArea textArea;
	
	//buttons
	protected JButton saveButton;
	protected JButton saveAsButton;
	protected JButton openButton;
	
	//menu components
	
	//bar
	protected JMenuBar menuBar;
	
	//menus
	protected JMenu fileMenu;
	protected JMenu editMenu;
	protected JMenu toolsMenu;
	protected JMenu aboutMenu;
	
	//menu items
	protected JMenuItem newItem;
	protected JMenuItem openItem;
	protected JMenuItem saveItem;
	protected JMenuItem saveAsItem;
	
	protected JMenuItem cutItem;
	protected JMenuItem copyItem;
	protected JMenuItem pasteItem;
	protected JMenuItem deleteItem;
	protected JMenuItem selectAllItem;
	protected JMenuItem revertItem;
	
	protected JMenuItem statsItem;
	protected JMenuItem undoItem;
	protected JMenuItem redoItem;
	protected JMenuItem findReplaceItem;
	protected JMenuItem fontItem;
	protected JMenuItem dateItem;
	protected JMenuItem timeItem;
	
	protected JMenuItem welcomeItem;
	protected JMenuItem aboutItem;
	protected JMenuItem siteItem;
	
	//dialogs
	protected  AgilitextFindReplaceManager findMan;
	protected AgilitextFontManager fontMan;
	
	//private objects
	
	//fonts
	private Font editorFont;
	private Font componentFont;
	private Font menuFont;
	
	//images
	private Image image;
	
	//handler
	private AgilitextHandler handler;
	
	//initialize serialization long
	private static final long serialVersionUID = 1L;
	
	/**
	 * AgilitextGui: Constructor
	 */
	public AgilitextGui()
	{
		//initialize handler using this JPanel
		handler=new AgilitextHandler(this);
		
		try
		{
			//attempt to set look and feel to default cross platform laf
		    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch(Exception e)
		{
			//if an error occurs, print error and stack trace
			System.err.println("Cross platform Look & Feel failed to load");
		    e.printStackTrace();
		}
		
		//call getJarResources to initialize jar font and icon objects
		getJarResources();
		
		//set layout of GUI panel
		setLayout(new BorderLayout());
		
		//build panels
		buildMainPanel();
		buildButtonPanel();
		//build menu
		buildMenu();
		//set mnemonics
		setAltshortcutKeys();
		setCtrlshortcutKeys();
		//add panels to main panel
		add(mainPanel, BorderLayout.CENTER);
		add(subPanel, BorderLayout.SOUTH);
		
		//initialize findMan using parent JFrame and target text area
		findMan=new AgilitextFindReplaceManager((JFrame)SwingUtilities.getWindowAncestor(this), textArea);
		//initialize fontMan using parent JFrame and target text area
		fontMan=new AgilitextFontManager((JFrame)SwingUtilities.getWindowAncestor(this), textArea);
		
		//set fontMan panel borders
		setComponentTitledBorder(fontMan.fontScroll, new LineBorder(Color.darkGray), "Font");
		setComponentTitledBorder(fontMan.samplePanel, null, "Sample");
		setComponentTitledBorder(fontMan.sizePanel, null, "Size");
		
		//add listeners and style main panel components
		configComponents(this.getComponents());
		//add listeners and style menu components
		configComponents(menuBar.getComponents());
		//add listeners and style find/replace frame
		configComponents(findMan.mainPanel.getComponents());
		//add listeners and style font frame
		configComponents(fontMan.mainPanel.getComponents());
	}
	
	/**
	 * buildMenu: Initializes and constructs menu bar
	 */
	private void buildMenu()
	{
		//initialize bar
		menuBar=new JMenuBar();
		
		//initialize menus
		fileMenu=new JMenu("File");
		editMenu=new JMenu("Edit");
		toolsMenu=new JMenu("Tools");
		aboutMenu=new JMenu("?");
		
		//initialize and add file menu items
		newItem=new JMenuItem("New");
		openItem=new JMenuItem("Open");
		saveItem=new JMenuItem("Save");
		saveAsItem=new JMenuItem("Save As");
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		
		//initialize and add edit menu items
		cutItem=new JMenuItem("Cut");
		copyItem=new JMenuItem("Copy");
		pasteItem=new JMenuItem("Paste");
		deleteItem=new JMenuItem("Delete");
		selectAllItem=new JMenuItem("Select All");
		revertItem=new JMenuItem("Revert");
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
		editMenu.add(selectAllItem);
		editMenu.add(revertItem);
		
		//initialize and add tools menu items
		statsItem=new JMenuItem("Stats");
		redoItem=new JMenuItem("Redo");
		undoItem=new JMenuItem("Undo");
		findReplaceItem=new JMenuItem("Find/Replace");
		fontItem=new JMenuItem("Change Font");
		dateItem=new JMenuItem("Insert Date");
		timeItem=new JMenuItem("Insert Time");
		toolsMenu.add(statsItem);
		toolsMenu.add(undoItem);
		toolsMenu.add(redoItem);
		toolsMenu.add(findReplaceItem);
		toolsMenu.add(fontItem);
		toolsMenu.add(dateItem);
		toolsMenu.add(timeItem);
		
		//initialize and add about menu item
		welcomeItem=new JMenuItem("Welcome");
		aboutItem=new JMenuItem("About");
		siteItem=new JMenuItem("Visit My Website");
		aboutMenu.add(welcomeItem);
		aboutMenu.add(aboutItem);
		aboutMenu.add(siteItem);
		
		//add menus to bar
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(aboutMenu);
	}
	
	/**
	 * buildMain: Initializes and constructs main panel
	 */
	private void buildMainPanel()
	{
		//initialize main panel and set layout
		mainPanel=new JPanel();
		mainPanel.setLayout(new GridLayout(1,1));
		
		//initialize text area
		textArea=new AgilitextTextArea();
		//set font for text area implicitly before panel add
		textArea.setFont(editorFont);
		
		//initialize and add text area to main scroll pane
		mainScroll=new JScrollPane(textArea);
		mainPanel.add(mainScroll);
		
		//add empty border to scroll pane
		mainScroll.setBorder(new EmptyBorder(0,0,0,0));
		
	}
	
	/**
	 * buildSub: Initializes and constructs sub panel
	 */
	private void buildButtonPanel()
	{
		//initialize and set layout for sub panel
		subPanel=new JPanel();
		subPanel.setLayout(new GridLayout(1,3));
		
		//initialize buttons and add them to sub panel
		saveButton=new JButton("Save");
		saveAsButton=new JButton("Save As");
		openButton=new JButton("Open");
		subPanel.add(saveButton);
		subPanel.add(saveAsButton);
		subPanel.add(openButton);
		
		//set dark gray line border for sub panel
		subPanel.setBorder(new LineBorder(Color.darkGray));
	}
	
	/**
	 * setAltshortcutKeys: Sets alt key mnemonics for GUI objects
	 */
	private void setAltshortcutKeys()
	{
		//set menu mnemonics
		fileMenu.setMnemonic('F');
		editMenu.setMnemonic('E');
		toolsMenu.setMnemonic('T');
		aboutMenu.setMnemonic('H');
		
		//set file menu item mnemonics
		newItem.setMnemonic('N');
		openItem.setMnemonic('O');
		saveItem.setMnemonic('S');
		saveAsItem.setMnemonic('A');
		
		//set edit menu item mnemonics
		cutItem.setMnemonic('T');
		copyItem.setMnemonic('C');
		pasteItem.setMnemonic('P');
		deleteItem.setMnemonic('D');
		selectAllItem.setMnemonic('A');
		revertItem.setMnemonic('R');
		
		//set tool menu item mnemonics
		statsItem.setMnemonic('S');
		undoItem.setMnemonic('U');
		redoItem.setMnemonic('R');
		findReplaceItem.setMnemonic('F');
		fontItem.setMnemonic('C');
		dateItem.setMnemonic('D');
		timeItem.setMnemonic('T');
		
		//set about menu item mnemonics
		welcomeItem.setMnemonic('W');
		aboutItem.setMnemonic('A');
		siteItem.setMnemonic('V');
		
		//set button mnemonics
		saveButton.setMnemonic('S');
		saveAsButton.setMnemonic('A');
		openButton.setMnemonic('O');
	}
	
	/**
	 * setCtrlshortcutKeys: Sets ctrl key mnemonic for GUI objects
	 */
	private void setCtrlshortcutKeys()
	{
		//declare shortcutKeys String
		String shortcutKeys;
		
		//set shortcutKeys to undo key combination
		shortcutKeys="Control Z";
		//register shortcut in input map and action maps, passing a new AbstractAction to fire on event
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK), shortcutKeys);
	    getActionMap().put(shortcutKeys, new AbstractAction() 
	    {
	    	//initialize serialization long
	    	private static final long serialVersionUID = 1L;
	        
	    	@Override
	    	/**
	         * actionPerformed: Called to handle GUI action events
	         * @param e: GUI action event 
	         */
	        public void actionPerformed(ActionEvent e) 
	        {
	    		//call undo action
	            textArea.doUndo();
	        }
	    });
		
	    //set shortcutKeys to redo key combination
	    shortcutKeys="Control Y";
	    //register shortcut in input map and action maps, passing a new AbstractAction to fire on event
	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK), shortcutKeys);
	    getActionMap().put(shortcutKeys, new AbstractAction() 
	    {
	    	//initialize serialization long
	    	private static final long serialVersionUID = 1L;
	        
	        @Override
	        /**
	         * actionPerformed: Called to handle GUI action events
	         * @param e: GUI action event 
	         */
	        public void actionPerformed(ActionEvent e) 
	        {
	        	//call redo action
	        	textArea.doRedo();
	        }
	    });
	    
	    //set shortcutKeys to find/replace key combination
	    shortcutKeys="Control F";
	    //register shortcut in input map and action maps, passing a new AbstractAction to fire on event
	    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK), shortcutKeys);
	    getActionMap().put(shortcutKeys, new AbstractAction() 
	    {
	    	//initialize serialization long
	    	private static final long serialVersionUID = 1L;
	        
	        @Override
	        /**
	         * actionPerformed: Called to handle GUI action events
	         * @param e: GUI action event 
	         */
	        public void actionPerformed(ActionEvent e) 
	        {
	        	//display findMan dialog
	        	findMan.setVisible(true);
	        }
	    });
	}
	
	/**
	 * configComponenets: Applies styles and listeners to GUI objects on the main GUI panel
	 * @param items: An array of Component objects to apply Fonts to
	 */
	private void configComponents(Component[] items)
	{
		//iterate though components
		for(int i=0;i<items.length;i++)
		{
			
			//if current component is an instance of JPanel or JScrollPane
			if (items[i] instanceof JPanel || items[i] instanceof JScrollPane)
			{
				//rerun method passing current JPanel components
				configComponents(((Container)items[i]).getComponents());
	        }
			//if current component is an instance of JViewPort
			else if (items[i] instanceof JViewport)
			{
				//create temporary component array of component inside view
				Component[] tmp={((JViewport)items[i]).getView()};
				//rerun method passing temporary array
				configComponents(tmp);
	        }
			//if current component is an instance of JMenu
			else if(items[i] instanceof JMenu)
			{
				//set font for menu
				items[i].setFont(menuFont);
				//rerun method passing all menu item components
				configComponents(((JMenu)items[i]).getMenuComponents());
			}
			//if current component is an instance of JMenuItem
			else if(items[i] instanceof JMenuItem)
			{
				//set font for menu item
				items[i].setFont(menuFont);
				//add listener to menu item
				((JMenuItem)items[i]).addActionListener(handler);
			}
			//if current component is an instance of JButton
			else if(items[i] instanceof JButton)
			{
				//set font for button
				items[i].setFont(componentFont);
				//add listener to button
				((JButton)items[i]).addActionListener(handler);
			}
			//if current component is an instance of JTextArea
			else if(items[i] instanceof JTextComponent)
			{
				//set font for text area
				items[i].setFont(editorFont);
				//add focus listener
				((JTextComponent)items[i]).addFocusListener(handler);
			}
			//if current component is an instance of JComponent
			else if(items[i] instanceof JList<?>)
			{
				//add selection listener to component
				((JList<?>)items[i]).addListSelectionListener(handler);
				//set font for list
				items[i].setFont(componentFont);
			}
			//if current component is an unspecified JComponent
			else if(items[i] instanceof JComponent)
			{
				//set font for component
				((JComponent)items[i]).setFont(componentFont);
			}
			
			//if current item is not a JMenuItem, JMenu (child or JMenuItem), JTextComponent, or JScrollBar
			if(!((items[i] instanceof JMenuItem)||(items[i] instanceof JTextComponent)||(items[i] instanceof JScrollBar)))
			{
				//set background and foreground colors
				items[i].setForeground(Color.WHITE);
				items[i].setBackground(Color.BLACK);
			}
		}
	}
	
	/**
	 * getJarResources: Used to read in and instantiate file resources from jar
	 */
	private void getJarResources()
	{
		//initialize jarFont to retrieved Font object from jar
		Font jarFont=Appsistant.getJarFont("UbuntuMono-B.ttf",1f);
		//initialize image to retrieved Image object from jar
		image=Appsistant.getJarImage("icon.png");
		
		//if running from archive
		if(Appsistant.isInArchive())
		{
			//register jarFont in local graphics environment
			Appsistant.registerFont(jarFont);
			//set GUI Font objects to different sized instances of jarFont
			editorFont=jarFont.deriveFont(16f);
			menuFont=jarFont.deriveFont(14f);
			componentFont=jarFont.deriveFont(15f);
		}
		else
		{
			//print message to standard output stating that Agilitext is not being run from jar
			System.out.println("Agilitext not running from jar, using default resources.");
			//initialize editor and menu Font objects to their defaults
			editorFont=new JTextArea().getFont();
			menuFont=new JMenuBar().getFont();
			
		}
	}
	
	/**
	 * setComponentBorder: Used to set border on a passed JComponent
	 * @param component: The target JComponent to set border to
	 * @param border: The border to use
	 * @param title: The title String to use
	 */
	protected void setComponentTitledBorder(JComponent component, Border border, String title)
	{
		//if border is null
		if(border==null)
		{
			//set empty component border using specified title String
			component.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(new Insets(6,0,6,0)), title, TitledBorder.LEFT, TitledBorder.TOP, componentFont, Color.WHITE));
		}
		else
		{
			//set component border using passed border and specified title String
			component.setBorder(BorderFactory.createTitledBorder(border, title, TitledBorder.LEFT, TitledBorder.TOP, componentFont, Color.WHITE));
		}
	}
	
	/**
	 * getMenu: Used to get main menu bar object
	 * @return: Main menu bar object
	 */
	protected JMenuBar getMenu()
	{
		return menuBar;
	}
	
	/**
	 * getHandler: Used to get GUI component handler
	 * @return: GUI component handler
	 */
	protected AgilitextHandler getHandler()
	{
		return handler;
	}
	
	protected Image getImage()
	{
		return image;
	}
	
	protected Font getEditorFont()
	{
		return editorFont;
	}
	
}