import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private BufferedReader reader;
    private Socket sock;
    private PrintWriter writer;
    private Counter number;


    public  PrintWriter getWriter(){
        return writer;
    }

    public ClientHandler(Socket clientSocket, Counter number) {
        try {
            sock = clientSocket;
            this.number = number;
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream());
            start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        String message;
        try{
            writer.write("number = " + number.number + "\n");
            writer.flush();

            //while(!(message=in.readLine()).equals("stop"))
            while ((message = reader.readLine()) != null){

                if(message.equals("stop"))
                {
                    System.out.println("client passed out");
                    break;
                }

                switch (message) {
                    case ("tell"):
                        writer.write("number = " + number.number + "\n");
                        writer.flush();
                        break;

                    default:
                        try {
                            synchronized (number) {
                                changeNumber(message);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (!sock.isClosed()) {
                    writer.close();
                    sock.close();
                    reader.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void changeNumber(String message){
        try{
            number.number += Integer.parseInt(message);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
