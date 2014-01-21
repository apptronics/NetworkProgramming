import java.io.*;
import java.net.*;
public class CalcServer {
    private int port;
    private InputStream is;
    private BufferedReader br;
    private OutputStream os;
    private BufferedWriter bw;
    private String message;

    public static void main(String[] args){
        new CalcServer(9679);
    }

    public CalcServer(int port){
        this.port = port;
        try{
            ServerSocket sk = new ServerSocket(this.port); // �������� �ϳ��� ������
            while(true){
                System.out.println("Ŭ���̾�Ʈ ��û ���");
                
                Socket s = sk.accept(); //Client �� ����ְ� ���� ������
                
                CalcThread ct = new CalcThread(s); // Thread ������ �Ű������� ����
                
                ct.start(); // Thread ����

            }
        }catch(Exception e){    
            e.printStackTrace();
            System.exit(0);
        }
    }
}

class CalcThread extends Thread{
    private Socket s;
    InputStream is;
    BufferedReader br;
    OutputStream os;
    BufferedWriter bw;
    String message;

    public CalcThread(Socket s){        
        this.s = s; //�Ű������� �޾ƿ� client ���� ������ Thread ���Ͽ� ����
    }

    public void run(){
        try{
            System.out.println("Conn:Client: "+ s.getInetAddress());
            
            is = s.getInputStream(); // Client�� ���� �޽����� ����
            br = new BufferedReader(new InputStreamReader(is));
            message = br.readLine();
            
            System.out.println("Client:MSG: "+ message); // ����� �Դ��� ���
            
            System.out.println("���Ÿ޽��� : "+ Calc(message));
            message += System.getProperty("line.separator");
            
            os = s.getOutputStream(); // ����� ������� �ٽ� �޾Ƽ� Client���� ����
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(message);
            bw.flush();
            
            bw.close(); // �ݾ��ش�.
            br.close();
            s.close();
        }catch(Exception e){
            System.exit(0);
        }
    }

    public int Calc(String msg){
        float res = 0.0;
        String arr[] = msg.split(" ");
        int num1 = Integer.parseInt(arr[0]);
        int num2 = Integer.parseInt(arr[2]);

        if(arr[1].equals("+")){
            res = num1 + num2;
        }else if(arr[1].equals("-")){
            res = num1 - num2;
        }else if(arr[1].equals("*")){
            res = num1 * num2;
        }else if(arr[1].equals("/")){
            res = num1 / num2;
        }else{
            System.out.println("�߸��� ������ ���");
        }
        return res;
    }
}