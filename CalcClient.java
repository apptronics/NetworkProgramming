import java.io.*;
import java.net.*;
public class CalcClient {
    private String ip;
    private int port;
    private String str;
    BufferedReader file;

    //������
    public CalcClient(String ip, int port) throws IOException{
        this.ip = ip;
        this.port = port;
        
        Socket tcpSocket = getSocket();
        
        BufferedWriter bufferW
          = new BufferedWriter(new OutputStreamWriter(tcpSocket.getOutputStream()));
        BufferedReader bufferR
          = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        System.out.print(" �Է� : ");

        file = new BufferedReader(new InputStreamReader(System.in));
        str = file.readLine();
        str += System.getProperty("line.separator");
        bufferW.write(str);
        bufferW.flush();
        str = bufferR.readLine();
        System.out.println("Ŭ���̾�Ʈ ��� : "+str);

        file.close();
        bufferW.close();
        bufferR.close();
        getSocket().close();

    }
    public Socket getSocket(){   //ȣ��Ʈ�� �ּҿ� ��Ʈ�� ���,
                                           // ������ ����� �����ϴ� ����� �޼���
        Socket tcpSocket = null;

        try{
            tcpSocket = new Socket(ip, port);
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.exit(0);
        }
        return tcpSocket;
    }

    public static void main(String[] args)throws IOException{
        new CalcClient("localhost", 9679);
    }
}
