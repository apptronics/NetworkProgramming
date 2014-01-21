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
            ServerSocket sk = new ServerSocket(this.port); // 서버소켓 하나만 열어줌
            while(true){
                System.out.println("클라이언트 요청 대기");
                
                Socket s = sk.accept(); //Client 손 잡아주고 소켓 정보를
                
                CalcThread ct = new CalcThread(s); // Thread 생성자 매개변수로 전달
                
                ct.start(); // Thread 실행

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
        this.s = s; //매개변수로 받아온 client 소켓 정보를 Thread 소켓에 저장
    }

    public void run(){
        try{
            System.out.println("Conn:Client: "+ s.getInetAddress());
            
            is = s.getInputStream(); // Client가 보낸 메시지를 받음
            br = new BufferedReader(new InputStreamReader(is));
            message = br.readLine();
            
            System.out.println("Client:MSG: "+ message); // 제대로 왔는지 출력
            
            System.out.println("수신메시지 : "+ Calc(message));
            message += System.getProperty("line.separator");
            
            os = s.getOutputStream(); // 계산의 결과값을 다시 받아서 Client에게 전송
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(message);
            bw.flush();
            
            bw.close(); // 닫아준다.
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
            System.out.println("잘못된 연산자 사용");
        }
        return res;
    }
}