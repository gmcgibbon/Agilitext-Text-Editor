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

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * AgilitextTextField: Modified JTextFiled with a different look
 * 
 * @author Gannon McGibbon 
 * @version 1.1
 * 
 * Date Created: 08/05/13
 * Last Updated: 08/18/13
 */
public class AgilitextTextField extends JTextField
{
	//initialize serialization long
	private static final long serialVersionUID = 1L;
	
	/**
	 * AgilitextTextField: Constructor
	 */
	public AgilitextTextField()
	{
		//call superclass zero argument constructor
		super();
		
		//set empty border using insets
		setBorder(new EmptyBorder(new Insets(2,4,2,4)));
		
		//set component styles
		setBackground(Color.darkGray);
		setCaretColor(Color.WHITE);
		setForeground(Color.WHITE);
		//set component to translucent
		setOpaque(false);
	}
	
	/**
	 * AgilitextTextField: Constructor
	 * @param fieldPlaceholder: String value to display as initial text
	 */
	public AgilitextTextField(String initialText)
	{
		//run object zero argument constructor
		this();
		
		//if initialText is not null
		if(initialText!=null)
		{
			//set text of component to initialText
			this.setText(initialText);
		}
	}
	
	/**
	 * paintComponent: Paints component using a color gradient
	 * @param graphics: Graphics object
	 */
	protected void paintComponent(Graphics graphics) 
	{
		//cast Graphics object to a Graphics2D object
        Graphics2D g2d = (Graphics2D) graphics;
        //set rendering settings for new Graphics2D object
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        //create new GradientPaint object with gradient options
        GradientPaint gp = new GradientPaint(0, 0,
                getBackground().brighter(), 0, getHeight(),
                getBackground().darker().darker());
        //set paint object to GradientPAint object and paint
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        //call superclass paintComponment to repaint component
        super.paintComponent(graphics);
    }
	
	/**
	 * getNumber: Used to obtain a valid number from field
	 * @param min: The minimum value that a valid number must be equal to or greater than
	 * @param max: The maximum value that a valid number must be equal to or less than
	 * @return: A valid number, or -1 if a valid number could not be parsed
	 */
	protected double getNumber(double min, double max)
	{
		//initialize parseNumber to -1
		double parsedNumber=-1;
		//initialize filedText to current field text
		String fieldText=getText();
		
		try
		{
			//attempt to assign parsedNumber the result of parsing fieldText to double
			parsedNumber=Double.parseDouble(fieldText);
			
			//if parsedNumber is not within specified min max range
			if(parsedNumber<min||parsedNumber>max)
			{
				//set parsedNumber back to -1 and throw a NumberFormatException
				parsedNumber=-1;
				throw new NumberFormatException();
			}
		}
		catch (NumberFormatException e)
		{
			//if an error occurs, request focus on field and select all text within it
			requestFocusInWindow();
			selectAll();
		}
		
		return parsedNumber;
	}
}
