import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

class Cmd {
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

public class Start {
    public static void main(String[] args){
        Cmd cmd = new Cmd();
        String apk = new String();
        String fileName = new String();

        String order = cmd.inputCommand("pm list packages -f");
        String result = cmd.resultCommand(order);

        System.out.println(result);
        System.out.println("Write apk");
        Scanner apkScan = new Scanner(System.in);
        apk = apkScan.next();
        System.out.println("Write file name");
        Scanner fileNameScan = new Scanner(System.in);
        fileName = fileNameScan.next();

        String download = cmd.downloadFile(apk, fileName);
        System.out.println(download);

    }
}

