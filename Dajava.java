package Ha;
import java.io.*;

import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Dajava {
	public static void createfile(){
		String path = "C:\\DaJaVa";
		File folder = new File(path);
		
		if(!folder.exists()) {
			try {
				folder.mkdir();
				System.out.println("DaJaVa 폴더 생성 되었습니다.");
			}
			catch(Exception e) {
				e.getStackTrace();
			}
		}
		else {
			System.out.println("폴더가 있습니다.");
		}
	}
	public static void Move(){

		try {
		 	 ProcessBuilder builder = new ProcessBuilder( "cmd.exe", "/c", "move C:\\dex2jar-2.0.zip C:\\DaJaVa\\dex2jar-2.0.zip"); 
		 	 builder.redirectErrorStream(true); 
		 	 Process p = builder.start();
			 BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			Scanner s = new Scanner(p.getInputStream()); 
            while(true) {
		 		  String line = r.readLine(); 
		 		  if (line == null) {break;} 
		 		  System.out.println(line);            	
            }
            System.out.println("파일 이동 성공");
            } catch (IOException e) { 
            	e.printStackTrace();
            	System.out.println("파일 이동 실패");
            } 
		}	
	//public class GetAPK{
	    public static StringBuffer buff, readbuff;
	    public static Process p;
	    public static BufferedReader buffreader;

	    public static String inputCommand(String cmd) {
	    	
	        buff = new StringBuffer();

	        buff.append("cmd.exe ");
	        buff.append("/c ");
	        buff.append("adb shell ");
	        buff.append(cmd);

	        return buff.toString();
	    }
	    public static String resultCommand(String cmd){
	        try{
	        	p = Runtime.getRuntime().exec(cmd);
	            buffreader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line = null;
	            readbuff = new StringBuffer();

	            while((line = buffreader.readLine()) != null){
	                if(line.indexOf("/data/app") > -1) {
	                    readbuff.append(line);
	                    readbuff.append("\n");
	                }
	            }
	            return readbuff.toString();
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	        return  null;
	    }
	    public static String downloadFile(String apk, String fileName){
	        buff = new StringBuffer();
	        buff.append("cmd.exe ");
	        buff.append("/c ");
	        buff.append("adb pull ");
	        buff.append(apk);
	        buff.append(" C:\\DaJaVa\\dex2jar-2.0\\");
	        buff.append(fileName);

	        try {
	            p = Runtime.getRuntime().exec(buff.toString());
	            buffreader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String arr = null;
	            readbuff = new StringBuffer();

	            while ((arr = buffreader.readLine()) != null) {
	                readbuff.append(arr);
	                readbuff.append("\n");
	            }
	            return readbuff.toString();
	        }
	        catch (Exception e){
	            e.printStackTrace();
	            System.exit(1);
	        }
	        return null;
	    }

	//}
    public static String XmlFunc(String args) {
        String[] cmd = { "cmd", "/c",  "cd c:\\DaJaVa\\dex2jar-2.0\\ && apktool d -f", args};
        Process process = null;
        try {
            process = new ProcessBuilder(cmd).start();

            Scanner s = new Scanner(process.getInputStream(), "EUC-KR");
            while (s.hasNextLine() == true) {
                System.out.println(s.nextLine());
            }
         int pos = args.lastIndexOf(".");
   	 	 args = args.substring(0, pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "c:\\DaJaVa\\dex2jar-2.0\\" +args+"\\AndroidManifest.xml"; //xml경로를 String으로 return
    }
	public static class CheckXML{
	    public void Manifest_Debug(String path) {
	        // 디버그 모드 확인 (AndroidManifest.xml 파일에서 확인 가능)
	        // 디버그 모드면 외부에서 임의의 코드를 주입 가능, 개발시에만 사용해야함
	        try {
	            String check = "android:debuggable=\"true\"";

	            File f = new File(path);
	            FileReader fr = new FileReader(String.valueOf(f));
	            BufferedReader bufr = new BufferedReader(fr);
	            String line = "";
	            while ((line = bufr.readLine()) != null) {
	                if (line.contains(check))
	                    // 라인 중 android:debuggable="true" 포함한 라인 찾음
	                    System.out.println("디버그 모드 작동 중 -> 취약");
	            }
	            bufr.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("파일을 찾을 수 없음");
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	    }

	    public void Manifest_Backup(String path) {
	        // 백업 허용 확인 (AndroidManifest.xml 파일에서 확인 가능)
	        // 악의적 사용자가 adb를 사용하여 앱의 개인 데이터를 자신의 PC에 가져올 수 있음.
	        try {
	            String check = "android:allowBackup=\"true\"";

	            File f = new File(path);
	            FileReader fr = new FileReader(String.valueOf(f));
	            BufferedReader bufr = new BufferedReader(fr);
	            String line = "";
	            while ((line = bufr.readLine()) != null) {
	                if (line.contains(check))
	                    // 라인 중 android:allowBackup="true" 포함한 라인 찾음
	                    System.out.println("adb를 통해 백업 허용 중 -> 취약");
	            }
	            bufr.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("파일을 찾을 수 없음");
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	    }

	    public void Manifest_Location(String path) {
	        // 사용자의 위치 정보 접근 확인 (AndroidManifest.xml 파일에서 확인 가능)
	        // Wi-Fi와 같은 네트워크 위치 소스를 통해 위치 얻어짐. 기본 권한으로 설정되어 있으면 위험함.
	        try {
	            String check = "android.permission.ACCESS_COARSE_LOCATION";

	            File f = new File(path);
	            FileReader fr = new FileReader(String.valueOf(f));
	            BufferedReader bufr = new BufferedReader(fr);
	            String line = "";
	            while ((line = bufr.readLine()) != null) {
	                if (line.contains(check))
	                    // 라인 중 android.permission.ACCESS_COARSE_LOCATION 포함한 라인 찾음
	                    System.out.println("위치 정보 접근 허용 -> 취약");
	            }
	            bufr.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("파일을 찾을 수 없음");
	        } catch (IOException e) {
	            System.out.println(e);
	        }	    
	}
	    public void Manifest_Phone(String path) {
	        // Phone 권한 확인 (AndroidManifest.xml 파일에서 확인 가능)
	        // 장치의 전화번호, 네트워크정보, 진행중인 통화의 상태 읽어오기, 사용자의 통화 기록 읽기 가능
	        try {
	            String check = "android.permission.READ_CALL_LOG";
	            String check2 = "android.permission.READ_PHONE_STATE";

	            File f = new File(path);
	            FileReader fr = new FileReader(String.valueOf(f));
	            BufferedReader bufr = new BufferedReader(fr);
	            String line = "";
	            while ((line = bufr.readLine()) != null) {
	                if (line.contains(check))
	                    // 라인 중 android.permission.READ_CALL_LOG 포함한 라인 찾음
	                    System.out.println("사용자의 통화 기록 읽기 허용 -> 취약");
	                if (line.contains(check2))
	                	// 라인 중 android.permission.READ_PHONE_STATE 포함된 라인 찾음
	                	System.out.println("장치의 전화 번호, 네트워크 정보, 진행중인 통화의 상태 읽어오기 허용 -> 취약");
	            }
	            bufr.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("파일을 찾을 수 없음");
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	    }
	    public void Manifest_Activity(String path) {
	        // 액티비티 컴포넌트 확인 (AndroidManifest.xml 파일에서 확인 가능)
	        // 다른 에플리케이션의 Activity를 실행 할 수 있음.
	    	try {
	    		int i = 0;
	    		int count = 0;
	            String check = "activity android:exported=\"true\"";

	            File f = new File(path);
	            FileReader fr = new FileReader(String.valueOf(f));
	            BufferedReader bufr = new BufferedReader(fr);
	            String line = "";
	            while ((line = bufr.readLine()) != null) {
	                
					if (line.contains(check))
	                    // 라인 중 android:exported="true" 포함한 라인 찾음
	                    //System.out.println("다른 애플리케이션의 Activity 실행가능 -> 취약");
	                	i++;
						count = i;
	            }
	            if (i > 0)
	            {
	            System.out.println("노출된 Activity 갯수 : "+count+" -> 취약");
	            }
	            bufr.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("파일을 찾을 수 없음");
	        } catch (IOException e) {
	            System.out.println(e);
	        }
}
	    public int find(){
	        //xml파일에서 provider 검색 함수
	        String filename = "AndroidManifest.xml";
	        String word = "(?i).*provider.*";
	        int num=0;
	        try {
	            BufferedReader reader = new BufferedReader(new FileReader(filename));
	            String s;

	            int line =1;

	            while ((s = reader.readLine()) != null) {
	                if (s.matches(word)){
	                    System.out.println(line + " : " + s);
	                    num++;
	                }
	                line++; }
	        } catch (FileNotFoundException e) { e.printStackTrace(); }
	        catch (IOException e) { e.printStackTrace(); }
	        if(num>0)
	            return 1; //content provide 취약점이 있으면 1 return
	        else
	            return 0;
	    }
}

	
	public static String ApkToZip(String FileName){ 
		 File file = new File("C:\\DaJaVa\\dex2jar-2.0",FileName); 
		 int pos = FileName.lastIndexOf(".");
	 	 String filename = FileName.substring(0, pos);
	 	 StringBuilder _filename = new StringBuilder(filename);
	 	 _filename.append(".zip");
	 	 filename = _filename.toString();
		 file.renameTo(new File("C:\\DaJaVa\\dex2jar-2.0\\",filename));
		 System.out.println("zip 변환 성공");
	 		 
		 return filename;
		} 
	
	//public static class ZipDecode{ //여기 주석 및 디컴프레스 함수랑 크리에이트 파일 예외처리 고치기 함수를 비교하면서 고쳐야함
	public static void decompress(String zipFileName, String directory){ 
			 File zipFile = new File("C:\\DaJaVa\\dex2jar-2.0\\",zipFileName); 
		     FileInputStream fis = null; 
		     ZipInputStream zis = null; 
		     ZipEntry zipentry = null; 
		     try { 
		        
		       fis = new FileInputStream(zipFile); 
		       zis = new ZipInputStream(fis); 
		       while ((zipentry = zis.getNextEntry()) != null) { 
		         String filename = zipentry.getName(); 
		         File file = new File(directory, filename); 
		         if (zipentry.isDirectory()) { 
		           file.mkdirs(); 
		         } else { 
		           //파일이면 파일 만들기 
		           createFile(file, zis); 
		         } 
		       } 
		     } catch (IOException e) { 

		     } finally { 
		    	 try {
		    		 zis.close();
		    		 fis.close();
		    	 }catch(IOException e) {
		    		 
		    	 }		    	 
		     } 
		   } 
		   private static void createFile(File file, ZipInputStream zis) {
		     //디렉토리 확인 
		     File parentDir = new File(file.getParent()); 
		     //디렉토리가 없으면 생성 
		     if (!parentDir.exists()) { 
		       parentDir.mkdirs(); 
		     } 
		     //파일 스트림 선언 
		     try (FileOutputStream fos = new FileOutputStream(file)) { 
		       byte[] buffer = new byte[256]; 
		       int size = 0; 
		      
		       while ((size = zis.read(buffer)) > 0) { 
		         //byte로 파일 만들기 
		         fos.write(buffer, 0, size); 
		       } 
		     } catch (IOException e) { 

		     } 
		   }
	//} //여기 주석
	
	public static String DexToJar(String dexName){ 
		 	try { 
		 	 ProcessBuilder builder = new ProcessBuilder( 
		 			  "cmd.exe", "/c", "cd \"C:\\DaJaVa\\dex2jar-2.0\" && d2j-dex2jar.bat ",dexName); 
		 	 builder.redirectErrorStream(true); 
		 	 Process p = builder.start(); 
		 	 BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		 	 while (true) { 
		 		  String line; 
		 		  line = r.readLine(); 
		 		  if (line == null) {break;} 
		 		  System.out.println(line); 
		 	  } System.out.println("dex -> jar 파일변환 성공");
		 	 }  
		 		catch (IOException e) { 
		 	        e.printStackTrace(); 
		 	} 
		 	return null; 
		 	} 
	
	public static class JarDecode { 
		     public static void JarFunc(String args) { 
		    	 StringBuilder jarname = new StringBuilder(args);
		    	 jarname.append("-dex2jar.jar");
		    	 args = jarname.toString();
		         String[] cmd = { "cmd", "/c","cd C:\\DaJaVa\\dex2jar-2.0 && jar xvf ",args};
		         Process process = null; 
		         try { 
		             process = new ProcessBuilder(cmd).start(); 
		             Scanner s = new Scanner(process.getInputStream(), "EUC-KR"); 
		             while (s.hasNextLine() == true) { System.out.println(s.nextLine()); } 
		             System.out.println("Jar파일 압축해제 성공");
		         } catch (IOException e) { e.printStackTrace(); } 
		     } 
		 } 
	public static class ClassList { 
	     public static String[] filelist = new String[9999]; 
	     public static String[] Start() { 
	         try { 
	             String path="C:\\DaJaVa\\dex2jar-2.0"; // --------------- 읽어들일 DIR 경로  
	             System.out.println("Directory Name:" + path); 
	             // main process. 
	             (new ClassList()).showFileList(path); 
	         } catch (Exception ex) { ex.printStackTrace(); } 
	         return filelist; 
	     } 
	     int j=0; 
	     public void showFileList(String path) throws Exception { 
	         File dir = new File(path); 
	         File[] files = dir.listFiles(); 
	 
	 
	         for (int i = 0; i < files.length; i++) { 
	        	 File file = files[i];
	             if (file.isFile()) {            	 
	                 if(file.getCanonicalPath().toString().contains(".class")) {
	                	 filelist[j] = file.getCanonicalPath().toString();
	                	 System.out.println(j+filelist[j]);j++; 
	                 }
	             //파일 목록 출력 없애려면 바로 윗줄의 System.out.println(j+filelist[j]); 부분을 지우시면 됩니다 
	             } else if (file.isDirectory()) { try { showFileList(file.getCanonicalPath().toString()); } catch (Exception e) { } } 
	         } 
	     } 
	 } 

		//난독화 체크
	 public static class CheckObfuscation { 		 
	     public static int CheckFunction(String classPath) { 
	    	 int cnt = 0;
	          try { 
		            File f = new File(classPath); 
		            FileReader fr = new FileReader(String.valueOf(f)); 
		            BufferedReader bufr = new BufferedReader(fr); 
		            String input = "void";  
		            String line = ""; 
		            while ((line = bufr.readLine()) != null) { 
		                 if (line.contains(input)) { 
		                      int a = line.indexOf(input); 
		                      int b = line.indexOf("("); 
		                      String word = line.substring(a, b); 
		 		 
		                      if (word.length() <= 2) { 
	                             //System.out.println("난독화 설정 O"); 
		                    	   cnt  = 1;		                    	  
		                      }
		                         else cnt = 0; //System.out.println("난독화 설정 X"); 
		                     } } 
		                 bufr.close(); 		 
		            	 }
		              catch (FileNotFoundException e) { 
		                 e.printStackTrace(); 
		             } catch (IOException e) { 
		                 e.printStackTrace(); 
		             } return cnt;
	          } 
	 }
	
    public static void main(String[] args){
        //GetAPK cmd = new GetAPK();
        CheckXML checkxml = new CheckXML();
        String apk = new String();
        String ApkName = new String();
        String apk_to_zip = new String();
        String[] filelist;
        int cnt;
 		//Create DaJaVa File
        createfile();
        //move dex2 tool zip file
        Move();
        //dex2.zip decode
        try{ 
        		String zipfile = "dex2jar-2.0.zip";
		        decompress(zipfile, "C:\\DaJaVa\\");  //여기
		       System.out.println("zip파일 압축해제 성공"); 
		     }catch(Throwable e){ 
		       e.printStackTrace(); 
		     }
        //Get ApK
        //String order = new String();
        //String result = new String();
        //gui 에 있으니까 주석함 
       // String order = inputCommand("pm list packages -f");
        //String result = resultCommand(order);

        //System.out.println(result);
        //gui에 있어서 
       /* System.out.println("Write apk [예)/ 부터 .apk 까지 복붙] :");
        Scanner apkScan = new Scanner(System.in);
        apk = apkScan.next();
        System.out.println("Write file name [예) test.apk] :");
        Scanner fileNameScan = new Scanner(System.in);
        ApkName = fileNameScan.next();

        String download = downloadFile(apk, ApkName);
        System.out.println(download);*/
        //XML decode 
        String XmlPath;
        XmlPath = XmlFunc(ApkName);
        System.out.println(XmlPath);
        //apk to zip
        apk_to_zip = ApkToZip(ApkName);
        //application zip decode 
        try{ 
		       decompress(apk_to_zip, "C:\\DaJaVa\\dex2jar-2.0"); //여기 수정
		       System.out.println("zip파일 압축해제 성공"); 
		     }catch(Throwable e){ 
		       e.printStackTrace(); 
		     }
        //dex to jar 
        String DexName = "classes.dex";
		String dex_to_jar; 
 		dex_to_jar = DexToJar(DexName);  
 		
 		JarDecode.JarFunc("classes"); // jar 파일 압축 풀기 함수선언
 		
 		filelist = ClassList.Start(); // .class 경로 넣기 함수 선언 
 		for(int i = 0; i < filelist.length; i++) {
 			if(filelist[i] != null) {
 				String classpath = filelist[i];
 				cnt = CheckObfuscation.CheckFunction(classpath); // 난독화 체크
 				if(cnt == 1) System.out.println("난독화 설정 O");
 				if(cnt == 0) System.out.println("난독화 설정 X");
 			}
 		}
        //XML Check
        //String path = "C:\\DaJaVa\\dex2jar-2.0\\파일이름\\AndroidManifest.xml";
        checkxml.Manifest_Debug(XmlPath);
        checkxml.Manifest_Backup(XmlPath);
        checkxml.Manifest_Location(XmlPath);
        checkxml.Manifest_Phone(XmlPath);
        checkxml.Manifest_Activity(XmlPath);
        checkxml.find();
       
    }   	
}

