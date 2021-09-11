import javax.swing.*;
import java.awt.*;

public class Window
{
	public Window()
	{
		JFrame frame = new JFrame("Window Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 480);

		CardLayout cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);
		JLabel label = new JLabel("Enter Link");

		//Textfield accepts upto 50 characters, inputLink length
		JTextField linkTextField = new JTextField(50);
		linkTextField.setBounds(270, 400, 50, 10);

		//Download after clicking downloadButton
		JButton downloadButton = new JButton("Download");
		downloadButton.setBounds(50, 50, 100, 10);

		//clear the Textfield
		JButton clearButton = new JButton("Clear");

		JPanel titelRow = new JPanel();
		titelRow.add(label);

		JPanel textFieldRow = new JPanel();
		textFieldRow.add(linkTextField);
		textFieldRow.add(clearButton);

		//add everything to JPanel
		panel.add(label);
		panel.add(textFieldRow);
		panel.add(downloadButton);

		//add Components to JFrame
		frame.getContentPane().add(BorderLayout.NORTH, panel);
		//frame.getContentPane().add(BorderLayout.SOUTH, downloadButton);

		frame.setVisible(true);
	}


}
