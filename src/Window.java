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

	private enum OS {WINDOWS, LINUX, MAC};

	private OS os = null;
	private String pwd = null;

	public Window()
	{
		/*
		 * os is contains one of the following
		 * "win" 			-> Windows OS
		 * "nix" / "nux" / "aix"	-> Linux OS
		 * "mac" 			-> Mac OS
		 */
		String currentOperatingSystem = System.getProperty("os.name");
		if (currentOperatingSystem.contains("win")) {
			os = OS.WINDOWS;
		}
		else if (currentOperatingSystem.contains("nix") || currentOperatingSystem.contains("nux")
			|| currentOperatingSystem.contains("aix")) {
			os = OS.LINUX;
		}
		//TODO: implement everything for macintosh
		else if (currentOperatingSystem.contains("mac")) {
			os = OS.MAC;
		}

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
					ProcessBuilder pb;
					if (os == OS.LINUX)
					{
						pb = new ProcessBuilder("pwd");
					}
					else if (os == OS.WINDOWS)
					{
						pb = new ProcessBuilder("echo %cd%");
					}

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

						if (os == OS.LINUX)
						{
							pwd += "/src/download.sh";
						}
						else if (os == OS.WINDOWS)
						{
							pwd += "\src\download.sh";
						}
					}
					catch (IOException ev)
					{
						ev.printStackTrace();
					}
				}

				//run download script
				ProcessBuilder pb = new ProcessBuilder("sudo", pwd, link);

				try {
					pb.start();
				}
				catch (IOException ev) {
					ev.printStackTrace();
				}

				System.out.println("--");
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
