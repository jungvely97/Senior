package Hu;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Ha.Dajava;
import java.awt.BorderLayout;

public class dajavagui extends JFrame {
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new dajavagui();
	}

	/**
	 * Create the frame.
	 */
	public dajavagui() {
		super("DaJaVa");
		//setBounds(300,300,600,600);	
		//setVisible(true);
		Dajava test1 = new Dajava();
		//GetAPK test = new GetAPK();
		Panel p = new Panel();
		JLabel lbl1 = new JLabel("다운받을 apk 입력 ( 예 : /data~test.apk) : ");
		JLabel lbl2 = new JLabel("저장할 이름 ( 예 : test.apk) : ");
		
		JTextArea txt3 = new JTextArea(13,50);
		JTextField txt1 = new JTextField(25);
		JTextField txt2 = new JTextField(25);
		JScrollPane scroll = new JScrollPane(txt3);
		
		//p.add(txt3);
		String order = test1.inputCommand("pm list packages -f");
	    String result = test1.resultCommand(order);
		//String result = test1.resultCommand(cmd)
		txt3.setText(result);
		getContentPane().add(scroll, BorderLayout.NORTH);
		p.add(lbl1);
		p.add(txt1);
		//String apklink = new String();
		JButton btnEn = new JButton("enter");
		btnEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String apklink = txt1.getText();
				String filename = txt2.getText();
				String download = test1.downloadFile(apklink, filename);
			}
		});		
		
		p.add(lbl2);
		p.add(txt2);
		p.add(btnEn);
		getContentPane().add(p);
		
		
		//JButton btnNext = new JButton("next");
		
		//p.add(btnNext);
		setSize(600,450);
		setVisible(true);
	}

}

