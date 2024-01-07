import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import java.io.*;
import java.net.*;


public class PlayerFrame extends JFrame {

    private int width, height;
    private Container contentPane;
    private HangedMan poorguy;
    private DrawingComponent dc;
    private Socket socket;
    private int playerID;
    private ReadfromServer sRead;
    private WritetoServer sWrite;
    private JLabel noConnect;
    private JLabel waiting;
    private JLabel wordtoGuess;
    private JPanel buttonPanel;
    private JButton a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z;
    private String displayed;
    private String attempt;
    private String guessing;
    private String seen;
    private String recent;
    private volatile boolean turn;
    private volatile char correct;

    //constructor
    public PlayerFrame(int w, int h){
        width = w;
        height = h;
        turn = false;
    }
    //getter for playerID
    public int getID(){
        return playerID;
    }

    //method that establishes the connection between Server and Client
    private void connectToServer(){
        try{
            socket = new Socket("localhost", 3000);
            DataInputStream in = new DataInputStream(socket.getInputStream()); 
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You're player #" + playerID);
            if (playerID == 1) System.out.println("Waiting for other players...");
            if (playerID == 2) System.out.println("Waiting for another player...");
            sRead = new ReadfromServer(in);
            sWrite = new WritetoServer(out);
        }
        catch(IOException ex){
            System.out.println("Error from connectToServer");
        }
    }
    public void setButton(){
        a = new JButton("a");
        b = new JButton("b");
        c = new JButton("c");
        d = new JButton("d");
        e = new JButton("e");
        f = new JButton("f");
        g = new JButton("g");
        h = new JButton("h");
        i = new JButton("i");
        j = new JButton("j");
        k = new JButton("k");
        l = new JButton("l");
        m = new JButton("m");
        n = new JButton("n");
        o = new JButton("o");
        p = new JButton("p");
        q = new JButton("q");
        r = new JButton("r");
        s = new JButton("s");
        t = new JButton("t");
        u = new JButton("u");
        v = new JButton("v");
        w = new JButton("w");
        x = new JButton("x");
        y = new JButton("y");
        z = new JButton("z");
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setAlignmentX(TOP_ALIGNMENT);
        buttonPanel.setAlignmentY(BOTTOM_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(1000,140));
        a.setBackground(Color.GRAY);
        b.setBackground(Color.GRAY);
        c.setBackground(Color.GRAY);
        d.setBackground(Color.GRAY);
        e.setBackground(Color.GRAY);
        f.setBackground(Color.GRAY);
        g.setBackground(Color.GRAY);
        h.setBackground(Color.GRAY);
        i.setBackground(Color.GRAY);
        j.setBackground(Color.GRAY);
        k.setBackground(Color.GRAY);
        l.setBackground(Color.GRAY);
        m.setBackground(Color.GRAY);
        n.setBackground(Color.GRAY);
        o.setBackground(Color.GRAY);
        p.setBackground(Color.GRAY);
        q.setBackground(Color.GRAY);
        r.setBackground(Color.GRAY);
        s.setBackground(Color.GRAY);
        t.setBackground(Color.GRAY);
        u.setBackground(Color.GRAY);
        v.setBackground(Color.GRAY);
        w.setBackground(Color.GRAY);
        x.setBackground(Color.GRAY);
        y.setBackground(Color.GRAY);
        z.setBackground(Color.GRAY);
        buttonPanel.add(a);
        buttonPanel.add(b);
        buttonPanel.add(c);
        buttonPanel.add(d);
        buttonPanel.add(e);
        buttonPanel.add(f);
        buttonPanel.add(g);
        buttonPanel.add(h);
        buttonPanel.add(i);
        buttonPanel.add(j);
        buttonPanel.add(k);
        buttonPanel.add(l);
        buttonPanel.add(m);
        buttonPanel.add(n);
        buttonPanel.add(o);
        buttonPanel.add(p);
        buttonPanel.add(q);
        buttonPanel.add(r);
        buttonPanel.add(s);
        buttonPanel.add(t);
        buttonPanel.add(u);
        buttonPanel.add(v);
        buttonPanel.add(w);
        buttonPanel.add(x);
        buttonPanel.add(y);
        buttonPanel.add(z);
    }
    public void disableButton(){
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
        e.setEnabled(false);
        f.setEnabled(false);
        g.setEnabled(false);
        h.setEnabled(false);
        i.setEnabled(false);
        j.setEnabled(false);
        k.setEnabled(false);
        l.setEnabled(false);
        m.setEnabled(false);
        n.setEnabled(false);
        o.setEnabled(false);
        p.setEnabled(false);
        q.setEnabled(false);
        r.setEnabled(false);
        s.setEnabled(false);
        t.setEnabled(false);
        u.setEnabled(false);
        v.setEnabled(false);
        w.setEnabled(false);
        x.setEnabled(false);
        y.setEnabled(false);
        z.setEnabled(false);
    }
    public void enableButton(){
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);
        e.setEnabled(true);
        f.setEnabled(true);
        g.setEnabled(true);
        h.setEnabled(true);
        i.setEnabled(true);
        j.setEnabled(true);
        k.setEnabled(true);
        l.setEnabled(true);
        m.setEnabled(true);
        n.setEnabled(true);
        o.setEnabled(true);
        p.setEnabled(true);
        q.setEnabled(true);
        r.setEnabled(true);
        s.setEnabled(true);
        t.setEnabled(true);
        u.setEnabled(true);
        v.setEnabled(true);
        w.setEnabled(true);
        x.setEnabled(true);
        y.setEnabled(true);
        z.setEnabled(true);
    }

    public void setUpButtonListeners(){
        ActionListener buttonListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == a){
                    attempt = a.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                else if(ae.getSource() == b){
                    attempt = b.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == c){
                    attempt = c.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == d){
                    attempt = d.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == e){
                    attempt = e.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == f){
                    attempt = f.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == g){
                    attempt = g.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == h){
                    attempt = h.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == i){
                    attempt = i.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == j){
                    attempt = j.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == k){
                    attempt = k.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == l){
                    attempt = l.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == m){
                    attempt = m.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == n){
                    attempt = n.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == o){
                    attempt = o.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == p){
                    attempt = p.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == q){
                    attempt = q.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == r){
                    attempt = r.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == s){
                    attempt = s.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == t){
                    attempt = t.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == u){
                    attempt = u.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == v){
                    attempt = v.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == w){
                    attempt = w.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == x){
                    attempt = x.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == y){
                    attempt = y.getText();
                    sWrite.tellServer();
                    turn = false;
                }
                if(ae.getSource() == z){
                    attempt = z.getText();
                    sWrite.tellServer();
                    turn = false;
                }
            }
        };
        a.addActionListener(buttonListener);
        b.addActionListener(buttonListener);
        c.addActionListener(buttonListener);
        d.addActionListener(buttonListener);
        e.addActionListener(buttonListener);
        f.addActionListener(buttonListener);
        g.addActionListener(buttonListener);
        h.addActionListener(buttonListener);
        i.addActionListener(buttonListener);
        j.addActionListener(buttonListener);
        k.addActionListener(buttonListener);
        l.addActionListener(buttonListener);
        m.addActionListener(buttonListener);
        n.addActionListener(buttonListener);
        o.addActionListener(buttonListener);
        p.addActionListener(buttonListener);
        q.addActionListener(buttonListener);
        r.addActionListener(buttonListener);
        s.addActionListener(buttonListener);
        t.addActionListener(buttonListener);
        u.addActionListener(buttonListener);
        v.addActionListener(buttonListener);
        w.addActionListener(buttonListener);
        x.addActionListener(buttonListener);
        y.addActionListener(buttonListener);
        z.addActionListener(buttonListener);
    }

    
    //method that creates the content for the JFrame
    public void setupGUI(){
        seen = "";
        displayed = "";
        contentPane = this.getContentPane();
        if(playerID == 0){
            this.setTitle("Could Not Connect to Server. Please Exit.");
            contentPane.setPreferredSize(new Dimension(width,height));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setResizable(false);
            noConnect = new JLabel("Sorry Couldn't Connect");
            noConnect.setFont(new Font("Courier",Font.BOLD,75));
            noConnect.setHorizontalAlignment(JLabel.CENTER);
            noConnect.setVerticalAlignment(JLabel.CENTER);
            this.add(noConnect);
            contentPane.setBackground(Color.BLACK);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
        else{
            this.setTitle("Player #" + playerID);
            contentPane.setPreferredSize(new Dimension(width,height));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setResizable(false);
            contentPane.setBackground(Color.BLACK);
            this.setVisible(true);
            if(playerID == 1){
                waiting = new JLabel("Waiting for other players...");
                waiting.setFont(new Font("Courier",Font.BOLD,45));
                waiting.setHorizontalAlignment(JLabel.CENTER);
                waiting.setVerticalAlignment(JLabel.BOTTOM);
                this.add(waiting);
                sRead.waitForPlayers();
                this.remove(waiting);
                this.setButton();
                this.add(buttonPanel,BorderLayout.SOUTH);
                this.setUpButtonListeners();
                createHangedMan();
                dc = new DrawingComponent();
                contentPane.add(dc);
                this.repaint();
                this.revalidate();
                Thread rdThread = new Thread(sRead);
                rdThread.start();
                Thread wtThread = new Thread(sWrite);
                wtThread.start();
                System.out.println(guessing);
                sRead.displayWord();
                    for(int i = 0 ; i < guessing.length() ; i++){
                        displayed = displayed + " _ ";
                    }
                    System.out.println(displayed.length());
                    System.out.println("Attempting to display");
                    wordtoGuess = new JLabel(displayed);
                    wordtoGuess.setFont(new Font("Courier",Font.BOLD,45));
                    wordtoGuess.setHorizontalAlignment(JLabel.CENTER);
                    wordtoGuess.setVerticalAlignment(JLabel.CENTER);
                    this.add(wordtoGuess);
                    this.repaint();
                    this.revalidate();
                    StringBuilder display = new StringBuilder(displayed);
                    while(true){
                        if(turn == false){
                            disableButton();
                        }
                        else enableButton();
                    }
            }
            if(playerID == 2){
                waiting = new JLabel("Waiting for another player...");
                waiting.setFont(new Font("Courier",Font.BOLD,45));
                waiting.setHorizontalAlignment(JLabel.CENTER);
                waiting.setVerticalAlignment(JLabel.BOTTOM);
                this.add(waiting);
                sRead.waitForPlayers();
                this.remove(waiting);
                this.setButton();
                this.add(buttonPanel,BorderLayout.SOUTH);
                this.setUpButtonListeners();
                createHangedMan();
                dc = new DrawingComponent();
                contentPane.add(dc);
                this.repaint();
                this.revalidate();
                Thread rdThread = new Thread(sRead);
                rdThread.start();
                Thread wtThread = new Thread(sWrite);
                wtThread.start();
                System.out.println(guessing);
                sRead.displayWord();
                    for(int i = 0 ; i < guessing.length() ; i++){
                        displayed = displayed + " _ ";
                    }
                    System.out.println(displayed.length());
                    System.out.println("Attempting to display");
                    wordtoGuess = new JLabel(displayed);
                    wordtoGuess.setFont(new Font("Courier",Font.BOLD,45));
                    wordtoGuess.setHorizontalAlignment(JLabel.CENTER);
                    wordtoGuess.setVerticalAlignment(JLabel.CENTER);
                    this.add(wordtoGuess);
                    this.repaint();
                    this.revalidate();
                    StringBuilder display = new StringBuilder(displayed);
                    while(true){
                        if(turn == false){
                            disableButton();
                        }
                        else enableButton();
                    }
            }
            if(playerID == 3){
                this.setButton();
                contentPane.add(buttonPanel,BorderLayout.SOUTH);
                this.setUpButtonListeners();
                createHangedMan();
                dc = new DrawingComponent();
                contentPane.add(dc);
                this.repaint();
                this.revalidate();
                Thread rdThread = new Thread(sRead);
                rdThread.start();
                Thread wtThread = new Thread(sWrite);
                wtThread.start();
                System.out.println(guessing);
                sRead.displayWord();
                    for(int i = 0 ; i < guessing.length() ; i++){
                        displayed = displayed + " _ ";
                    }
                    System.out.println(displayed.length());
                    System.out.println("Attempting to display");
                    wordtoGuess = new JLabel(displayed);
                    wordtoGuess.setFont(new Font("Courier",Font.BOLD,45));
                    wordtoGuess.setHorizontalAlignment(JLabel.CENTER);
                    wordtoGuess.setVerticalAlignment(JLabel.CENTER);
                    this.add(wordtoGuess);
                    this.repaint();
                    this.revalidate();
                    while(true){
                        if(turn == false){
                            disableButton();
                        }
                        else enableButton();
                    }
            }
        }
    }
    //method to create the "Hanged Man"
    private void createHangedMan() {
        poorguy = new HangedMan (620,160,50,Color.RED);
    }
    //Overriding paintComponent in order to allow Graphics2D to be used on the JFrame.
    private class DrawingComponent extends JComponent {

        protected void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            poorguy.drawSprite(g2d);
        }
    }
    /* private class ImageComponent extends JComponent {
        private Image image;
        public ImageComponent(Image image){
            this.image = image;
        }
        
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    } */
    
    //creating a thread to read from the Server
    private class ReadfromServer implements Runnable{
        
        private DataInputStream in;

        public ReadfromServer(DataInputStream input){
            in = input;
            System.out.println("Data from Server is able to be received.");
        }
        public void run(){
            System.out.println("R Thread has started");
            while(true){
                try{
                    turn = in.readBoolean();
                    System.out.println(turn);
                    correct = in.readChar();
                    System.out.println(correct);
                     //System.out.println(guessing);
                      //for(i = 0; i<guessing ;i++) 
                      
                }
                catch(Exception io){
                }
            }
        }

        public void waitForPlayers(){
            try {
                System.out.println("Waiting for all players");
                String confirmation = in.readUTF();
                System.out.println(confirmation);
            } catch (IOException e) {
                System.out.println("Error waiting for players");
            }
        }

        public void displayWord(){
            try{
                guessing = in.readUTF();
                System.out.println("recieving word.");
                while(true){
                    if(guessing !=null){
                    System.out.println("Attempting to display");
                    String wordtoDisplay = guessing;
                    System.out.println(guessing);
                    break;
                    }
                }
            } catch(Exception e){

            }
        }
        /*public void receiveUpdate(){
            try {
                StringBuilder mary = new StringBuilder(seen);
                recent = in.readUTF();
                for(int i = 0; i<recent.length(); i++){
                    if(recent.charAt(i) != ' '){
                        mary.append(' ' + recent.charAt(i) + ' ');
                    }
                    else mary.append(' ' +'_'+' ');
                }
                seen= mary.toString();
                wordtoGuess.setText(seen);
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println(seen);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }*/
        /*public void checkRecent(){
            try {
                recent = in.readUTF();
                if (recent == ""){
                }
                else{
                    StringBuilder string = new StringBuilder(seen);
                    for(int i = 0; i < guessing.length(); i++){
                        string.append(" ");
                        if(recent.charAt(i) != ' '){
                            string.setCharAt(i, recent.charAt(i));
                        }
                    }
                    seen = string.toString();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }*/
    }

    //creating a thread to write to the Server
    private class WritetoServer implements Runnable{
        
        private DataOutputStream out;

        public WritetoServer(DataOutputStream output){
            out = output;
            System.out.println("Data to Server is able to be sent.");
        }
        public void tellServer(){
            try{
            out.writeUTF(attempt);
            out.flush();
            System.out.println(attempt);
            out.writeBoolean(false);
            attempt = null;
            } catch(Exception e){

            }
        }
        public void run(){
            System.out.println("W Thread has started");
            try{
                /*while (true){
                    if(attempt != null){
                        out.writeUTF(attempt);
                        out.flush();
                        System.out.println(attempt);
                        out.writeBoolean(false);
                        attempt = null;
                    }
                }*/
            } catch(Exception e){

            }
        } 
    }

     public static void main(String[] args){
        PlayerFrame pf = new PlayerFrame(1280, 720);
        pf.connectToServer();
        pf.setupGUI();
     }    
}