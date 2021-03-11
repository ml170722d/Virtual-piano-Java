package myTools;

import java.awt.Color;

@SuppressWarnings("serial")
public class WhiteButton extends MyButton{

	public WhiteButton(char c) {
		super(c);
		super.setBackground(Color.WHITE);
		super.setForeground(Color.BLACK);
		
	}
}
