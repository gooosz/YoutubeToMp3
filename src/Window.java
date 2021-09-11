import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//Exception handling
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Window
{
	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JTextField linkTextField;
	private JButton downloadButton;
	private JButton clearButton;

	private String pwd = null;

	public Window()
	{
		frame = new JFrame("Window Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 200);

		panel = new JPanel(new BorderLayout());
		label = new JLabel("Enter Link");

		//Textfield accepts upto 50 characters, inputLink length
		linkTextField = new JTextField(50);
		linkTextField.setBounds(270, 400, 50, 10);

		//Download after clicking downloadButton
		downloadButton = new JButton("Download");
		downloadButton.setBounds(50, 50, 100, 20);

		//clear the Textfield
		clearButton = new JButton("Clear");

		//add actionListener for important buttons
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String link = linkTextField.getText();

				if (pwd == null)
				{
					//get pwd
					ProcessBuilder pb = new ProcessBuilder("pwd");
					try
					{
						Process process = pb.start();
						BufferedReader br = new BufferedReader(
										new InputStreamReader(
											process.getInputStream()
										));
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = br.readLine()) != null)
						{
							builder.append(line);
						}

						pwd = builder.toString();
						pwd += "/src/download.sh";
					}
					catch (IOException ev)
					{
						ev.printStackTrace();
					}
				}

				//run download script
				ProcessBuilder pb = new ProcessBuilder(pwd, link);

				try {
					pb.start();
				}
				catch (IOException ev) {
					ev.printStackTrace();
				}
			}
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				linkTextField.setText("");
			}
		});

		//Layout
		JPanel titelRow = new JPanel();
		titelRow.add(label);

		JPanel textFieldRow = new JPanel();
		textFieldRow.add(linkTextField, BorderLayout.EAST);
		textFieldRow.add(clearButton, BorderLayout.WEST);

		//add everything to JPanel
		panel.add(titelRow, BorderLayout.NORTH);
		panel.add(textFieldRow);
		panel.add(downloadButton, BorderLayout.SOUTH);


		panel.setPreferredSize(new Dimension(480, 360));
		panel.setMaximumSize(panel.getPreferredSize());
		panel.setMinimumSize(panel.getPreferredSize());


		//add Components to JFrame
		frame.getContentPane().add(panel);
		//frame.getContentPane().add(BorderLayout.SOUTH, downloadButton);

		frame.setVisible(true);
	}


}
