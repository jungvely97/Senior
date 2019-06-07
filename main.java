import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
		 			  "cmd.exe", "/c", "cd \"C:\\dex2jar-2.0\" && d2j-dex2jar.bat ",dexName); 
		 	 builder.redirectErrorStream(true); 
		 	 Process p = builder.start(); 
		 	 BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream())); 
		 	 while (true) { 
		 		  String line; 
		 		  line = r.readLine(); 
		 		  if (line == null) {break;} 
		 		  System.out.println(line); 
		 	  } 
		 	 } 
		 		catch (IOException e) { 
		 	        e.printStackTrace(); 
		 	} 
		 	return null; 
		 	} 

	
    public static void main(String[] args){
        GetAPK cmd = new GetAPK();
        String apk = new String();
        String ApkName = new String();
        String apk_to_zip = new String();
       
        //Get ApK
        String order = cmd.inputCommand("pm list packages -f");
        String result = cmd.resultCommand(order);

        System.out.println(result);
        System.out.println("Write apk [예).apk 까지 복붙] :");
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
 		System.out.println("dex -> jar 파일변환 성공"); 

        
    }
}


