import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleChatClient {
    private PrintWriter writer;
    private BufferedReader reader;
    private BufferedReader in;
    private Socket sock;

    private void setUpNetworking() {
        try {
            sock = new Socket("127.0.0.1", 5000);
            in = new BufferedReader(new InputStreamReader(System.in));
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());

            System.out.println("Networking established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void go() {
        setUpNetworking();

        try {
            System.out.println("to exit write stop");
            String message = reader.readLine();
            System.out.println(message);
            System.out.println("Please, write number");

            //while(!(message=in.readLine()).equals("stop"))
            while ((message = in.readLine()) != null) {

                if (message.equals("stop")) {
                    writer.write(message + "\n");
                    writer.flush();
                    break;
                }

                switch (message) {
                    case ("tell"):
                        writer.write(message + "\n");
                        writer.flush();
                        String answer =  reader.readLine();
                        System.out.println(answer);
                        break;

                    default:
                        try {
                            Integer.parseInt(message);

                            writer.write(message + "\n");
                            writer.flush();

                        }
                        catch (NumberFormatException e) {
                            System.out.println(message + " not integer number");

                        }
                        catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;

                }
                System.out.println("Please, write number");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!sock.isClosed()) {
                    writer.close();
                    reader.close();
                    in.close();
                    sock.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
        SimpleChatClient client = new SimpleChatClient();
        client.go();
    }
}
