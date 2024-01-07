import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
public class GameServer {
    
    private ServerSocket ss;
    private Socket p1;
    private Socket p2;
    private Socket p3;
    private ReadfromClient p1Read;
    private ReadfromClient p2Read;
    private ReadfromClient p3Read;
    private WritetoClient p1Write;
    private WritetoClient p2Write;
    private WritetoClient p3Write;
    private int numPlayers;
    private int maxPlayers;
    private int curPlayer;
    private int wrongGuesses;
    private AtomicBoolean turn;
    private AtomicBoolean newLet;
    private String word;
    private volatile String att;
    private String reveal;
    private String save;
    private volatile char rightguess;


    public GameServer() {
        System.out.println("Server is running...");
        numPlayers = 0;
        maxPlayers = 3;
        turn = new AtomicBoolean(false);
        newLet = new AtomicBoolean(false);
        rightguess = ' ';


        try{
            ss = new ServerSocket(3000);
        }
        catch(IOException ex){
            System.out.println("Error from new ServerSocket");
        }
    }

    public void acceptConnections(){
        try{
            System.out.println("Waiting for Connections...");
            while(numPlayers < maxPlayers){
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream((s.getInputStream()));
                DataOutputStream out = new DataOutputStream((s.getOutputStream()));
                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");
                ReadfromClient rfc = new ReadfromClient(numPlayers, in);
                WritetoClient wtc = new WritetoClient(numPlayers, out);
                
                if(numPlayers == 1){
                    p1 = s;
                    p1Read = rfc;
                    p1Write = wtc;
                }
                if(numPlayers == 2){
                    p2 = s;
                    p2Read = rfc;
                    p2Write = wtc;
                }
                if(numPlayers == 3){;
                    p3 = s;
                    p3Read = rfc;
                    p3Write = wtc;
                    p1Write.sendStartMessage();
                    p2Write.sendStartMessage();
                    curPlayer = 1;
                    Thread rdThread1 = new Thread(p1Read);
                    Thread rdThread2 = new Thread(p2Read);
                    Thread rdThread3 = new Thread(p3Read);
                    rdThread1.start();
                    rdThread2.start();
                    rdThread3.start();
                    Thread wtThread1 = new Thread(p1Write);
                    Thread wtThread2 = new Thread(p2Write);
                    Thread wtThread3 = new Thread(p3Write);
                    wtThread1.start();
                    wtThread2.start();
                    wtThread3.start();
                }
            }
                    System.out.println("No longer accepting connections");
        }
        catch(IOException ex){
            System.out.println("Error from acceptConnections");
        }
    }
    private class ReadfromClient implements Runnable {
        private int playerID;
        private DataInputStream input;

        public ReadfromClient(int pid,DataInputStream in){
            playerID =  pid;
            input = in;
            System.out.println("Data from Client #" + playerID + " is able to be received.");
        }
        public void run(){

                try {
                    while(true){
                            
                            att = input.readUTF();
                            System.out.println(att);
                            turn.set(input.readBoolean());
                            System.out.println("from player: " + turn.get());
                            StringBuilder build = new StringBuilder("");
                            if(word.contains(att)){
                                System.out.println("Correct Guess");
                                rightguess = att.charAt(0);
                                att = null;
                                }
                                //reveal = build.toString();
                               // newLet.set(true);
                    else {
                        wrongGuesses++;
                        System.out.println("Incorrect Guess");
                    }
                }
                } catch (Exception e) {
                    // TODO: handle exception
                }

            
        }
    }
    private class WritetoClient implements Runnable {
        private int playerID;
        private DataOutputStream output;

        public WritetoClient(int pid,DataOutputStream out){
            playerID =  pid;
            output = out;
            System.out.println("Data to Client #" + playerID + " is able to be sent.");
        }

        public void sendStartMessage(){
                try{
                    output.writeUTF("Start");
                    output.flush();
                    System.out.println("True");
                    }
                 catch(IOException i){

                }
        }
        public void updateKnown(){
            try{
                if(att != null){
                StringBuilder bob = new StringBuilder(reveal);
            for(int i = 0; i<word.length();i++){
                if(word.charAt(i) == att.charAt(0)) bob.append(word.charAt(i));
                else bob.append(' ');
            }
            reveal = bob.toString();
            output.writeUTF(reveal);}
            } catch(IOException ie){

            }
        }
        public void run(){
                try{
                    output.writeUTF(word);
                    System.out.println(word);
                }
                catch(IOException i){
                }
                try {
                    while(true){
                        if (curPlayer >3 ) curPlayer = 1;
                        if(turn.get() == false && playerID == curPlayer){
                            turn.set(true);
                            curPlayer++;
                            System.out.println("to player:" + turn.get());
                            output.writeBoolean(turn.get());
                        }
                        //output.writeChar(rightguess);
                        //System.out.println(rightguess);
                    } 
                }catch (IOException e) {
                    }
        }
    }
        public void assignWord(){
            String[] randWords = new String[]{"peanut","apple","bread","mold","water","ration"};
            int rand = (int)(Math.random()*(randWords.length));
            word = randWords[rand];
            StringBuilder buil = new StringBuilder("");
            for(int i = 0;i<word.length();i++){
                buil.append(" ");
            }
            save = buil.toString();
        }
        public void shareLetter(){
            try {
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    public static void main(String[] args){
        GameServer gs = new GameServer();
        gs.assignWord();
        gs.acceptConnections();
    }
}
