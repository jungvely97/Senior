package Hu;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import Ha.Dajava;
import Ha.Dajava.ClassList;//추가할것 여기부터
import Ha.Dajava.CheckXML;
import Ha.Dajava.CheckObfuscation;
import Ha.Dajava.JarDecode; //여기까지


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
		super("안드로이드 앱 취약점 진단 (DAJAVA) v1.0");

		Dajava test1 = new Dajava();
		JarDecode jardecode = new JarDecode();//여기부터 추가
		ClassList classlist = new ClassList();
		CheckXML checkxml = new CheckXML();
		CheckObfuscation checkob = new CheckObfuscation();// 여기까지
		Panel p = new Panel();
		p.setSize(350, 1500);
		JFrame f1 = new JFrame();
		JLabel lbl1 = new JLabel("다운받을 apk 입력 ( 예 : /data~test.apk) : ");
		JLabel lbl2 = new JLabel("저장할 이름 ( 예 : test.apk) : ");
		JLabel lbl4 = new JLabel("Apk List : ");
		JLabel lbl5 = new JLabel("중부대학교             정보보호학과              AVA4");
		
		JTextArea txt3 = new JTextArea(12,40);
		JTextField txt1 = new JTextField(40);
		JTextField txt2 = new JTextField(40);
		JScrollPane scroll = new JScrollPane(txt3);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
		p.add(lbl4, BorderLayout.NORTH);
		String order = test1.inputCommand("pm list packages -f");
	    String result = test1.resultCommand(order);
	   
		txt3.setText(result);
		p.add(scroll);
		p.add(lbl1);
		p.add(txt1);

		JButton btnEn = new JButton("enter");
		btnEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // 여기서부턴 추가할게 많아서 많이봐야함 
				test1.createfile();
				test1.Move();
				
				
				test1.decompress("dex2jar-2.0.zip","C:\\DaJaVa\\");
				String apklink = txt1.getText();
				String filename = txt2.getText();
				String download = test1.downloadFile(apklink, filename);
				String XmlPath;
				
				XmlPath = test1.XmlFunc(filename);
				test1.ApkToZip(filename);
				test1.decompress(filename, "C:\\DaJaVa\\");
				test1.DexToJar("classes.dex");
				jardecode.JarFunc("classes");
				String[] filelist;
				int cnt;
				filelist = classlist.Start();
		 		for(int i = 0; i < filelist.length; i++) {
		 			if(filelist[i] != null) {
		 				String classpath = filelist[i];
		 				//cnt = CheckObfuscation.CheckFunction(classpath); // 난독화 체크
		 				cnt = checkob.CheckFunction(classpath);
		 				if(cnt == 1) System.out.println("난독화 설정 O");
		 				if(cnt == 0) System.out.println("난독화 설정 X");
		 			}
		 		}
		 		//XML check 
		        checkxml.Manifest_Debug(XmlPath);
		        checkxml.Manifest_Backup(XmlPath);
		        checkxml.Manifest_Location(XmlPath);
		        checkxml.Manifest_Phone(XmlPath);
		        checkxml.Manifest_Activity(XmlPath);
		        checkxml.find();
				
				
			}
		});		
		
		p.add(lbl2);
		p.add(txt2);
		p.add(btnEn);
		p.add(lbl5);
		getContentPane().add(p);
		
		
		//JButton btnNext = new JButton("next");
		
		//p.add(btnNext);
		setSize(500,400);
		setVisible(true);
	}

}

