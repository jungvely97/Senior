import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Cmd {
    private StringBuffer buff, readbuff;
    private Process p;
    private BufferedReader buffreader;

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
        buff.append(" ");
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

public class Conversion2{
    public static String Builder(String FileName){
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"C:\\dex2jar-2.0\" && d2j-dex2jar.bat ",FileName); ///try to chatch로
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (true) {
            String line;
            line = r.readLine();
            if (line == null) {break;}
            System.out.println(line);
        }
        return null;
    }
}

public class Jar {
    public static String JarFunc(String args) {
        String[] cmd = {"cmd", "/c", "cd C:\\test && jar xvf ", args + "-dex2jar.jar"};
        Process process = null;
        try {
            process = new ProcessBuilder(cmd).start();

            Scanner s = new Scanner(process.getInputStream(), "EUC-KR");
            while (s.hasNextLine() == true) {
                System.out.println(s.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Start {
    public static void main(String[] args){
        Cmd cmd = new Cmd();
        String apk = new String();
        String FileName = new String();
        String Conversion2Class = new String();

        String order = cmd.inputCommand("pm list packages -f");
        String result = cmd.resultCommand(order);

        System.out.println(result);
        System.out.println("Write apk : 예).apk 까지 복붙");
        Scanner apkScan = new Scanner(System.in);
        apk = apkScan.next();
        System.out.println("Write file name : 예) test.apk");
        Scanner fileNameScan = new Scanner(System.in);
        FileName = fileNameScan.next();

        String download = cmd.downloadFile(apk, FileName);
        System.out.println(download);

        Conversion2Class = Conversion2.Builder(FileName);
        String JarClass = Jar.JarFunc(FileName);
    }
}

