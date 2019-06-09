import java.io.IOException;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import test.ClassList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Dajava {
	public static class GetAPK{
	    public static StringBuffer buff, readbuff;
	    public static Process p;
	    public static BufferedReader buffreader;

	    public String inputCommand(String cmd) {
	        buff = new StringBuffer();

	        buff.append("cmd.exe ");
	        buff.append("/c ");
	        buff.append("adb shell ");
	        buff.append(cmd);

	        return buff.toString();
	    }
	    public String resultCommand(String cmd){
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
	    public String downloadFile(String apk, String fileName){
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
	
	public static class ZipDecode{
	public static void decompress(String zipFileName, String directory) throws Throwable { 
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
		     } catch (Throwable e) { 
		       throw e; 
		     } finally { 
		       if (zis != null) 
		         zis.close(); 
		       if (fis != null) 
		         fis.close(); 
		     } 
		   } 
		   private static void createFile(File file, ZipInputStream zis) throws Throwable { 
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
		     } catch (Throwable e) { 
		       throw e; 
		     } 
		   }
	}
	
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
        GetAPK cmd = new GetAPK();
        String apk = new String();
        String ApkName = new String();
        String apk_to_zip = new String();
        String[] filelist;
        int cnt;
 		
        //Get ApK
        String order = cmd.inputCommand("pm list packages -f");
        String result = cmd.resultCommand(order);

        System.out.println(result);
        System.out.println("Write apk [예)/ 부터 .apk 까지 복붙] :");
        Scanner apkScan = new Scanner(System.in);
        apk = apkScan.next();
        System.out.println("Write file name [예) test.apk] :");
        Scanner fileNameScan = new Scanner(System.in);
        ApkName = fileNameScan.next();

        String download = cmd.downloadFile(apk, ApkName);
        System.out.println(download);
        //apk to zip
        apk_to_zip = ApkToZip(ApkName);
        //zip decode 
        try{ 
		       ZipDecode.decompress(apk_to_zip, "C:\\DaJaVa\\dex2jar-2.0"); 
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
       
    }   	
}


