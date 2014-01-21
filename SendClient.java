import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
 
public class SendClient {
    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        Socket socket = null;
 
        try {
            // ���� ����
            socket = new Socket(serverIp, 7777);
            System.out.println("������ ����Ǿ����ϴ�.");
 
            FileSender fs = new FileSender(socket);
            fs.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 
class FileSender extends Thread {
    Socket socket;
    DataOutputStream dos;
    FileInputStream fis;
    BufferedInputStream bis;
 
    public FileSender(Socket socket) {
        this.socket = socket;
        try {
            // ������ ���ۿ� ��Ʈ�� ����
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void run() {
        try {
            System.out.println("���� ���� �۾��� �����մϴ�.");
 
            // ���� �̸� ����
            // String fName = "�۾���a.txt";
            // String fName = "��Ƽa.ppt";
            // String fName = "�۾���a.jpg";
            String fName = "�۾���a.mp4";
            dos.writeUTF(fName);
            System.out.printf("���� �̸�(%s)�� �����Ͽ����ϴ�.\n", fName);
 
            // ���� ������ �����鼭 ����
            File f = new File(fName);
            fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
 
            int len;
            int size = 4096;
            byte[] data = new byte[size];
            while ((len = bis.read(data)) != -1) {
                dos.write(data, 0, len);
            }
 
            dos.flush();
            dos.close();
            bis.close();
            fis.close();
            System.out.println("���� ���� �۾��� �Ϸ��Ͽ����ϴ�.");
            System.out.println("���� ������ ������ : " + f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}