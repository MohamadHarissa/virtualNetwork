package Client_Package;
import ISP.IISP;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Server_Package.ServerIF;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientGUI extends JFrame implements ActionListener {
    static ServerIF theRouter;
    static Thread t;
    static IISP isp;
    static String sip = "";
    private static final JLabel message = new JLabel("Joined at: ");
    private static final JLabel welcome = new JLabel();
    static final JTextField field = new JTextField(20);
    private static final JButton post = new JButton("Post Message");
   // private static final JButton sendto = new JButton("sendto");
    static final JTextField sipfield = new JTextField(10);
    static final JButton change = new JButton("Change Router");
    private static final JButton disconnect = new JButton("Disconnect");
    static final JTextArea area = new JTextArea(5, 5);
    static Boolean posted = false;
    static Boolean disconnected = false;
    static Boolean isconnected=false;
    static Client client;
    private ClientGUI() {

        super("Chat Client");
        setUpPanel(this);
        setAddEventListeners();
    }

    private void setUpPanel(ClientGUI clientGUI) {
        JPanel panel_north = new JPanel();
        JPanel panel_middle = new JPanel(new GridLayout(1, 2));
        panel_north.add(welcome);
        panel_north.add(message);
        panel_north.add(change);
        JPanel panel_south = new JPanel();
        panel_south.add(field);
        panel_south.add(post);
        panel_south.add(sipfield);
       // panel_south.add(sendto);
        

        clientGUI.setResizable(false);
        clientGUI.setVisible(false);
        clientGUI.showTime();

        area.setEditable(false);
        addScrollPanelToPanel(panel_middle);

        clientGUI.setUserTextColor();
        setComponentColors(panel_south,panel_north);

        clientGUI.setBounds(400, 400, 750, 500);
        addEverythingToMainPanel(clientGUI,panel_middle,panel_north,panel_south);

        area.setBackground(Color.white);
        clientGUI.setVisible(true);
    }

    private void addScrollPanelToPanel(JPanel panel_middle){
        JScrollPane scrollPane = new JScrollPane(area);
        panel_middle.add(scrollPane);
    }

    private void setComponentColors(JPanel panel_south, JPanel panel_north){
        post.setBackground(Color.BLACK);
        post.setForeground(Color.WHITE);
       // sendto.setBackground(Color.BLACK);
      //  sendto.setForeground(Color.WHITE);
        change.setBackground(Color.BLACK);
        change.setForeground(Color.WHITE);
        disconnect.setBackground(Color.BLACK);
        disconnect.setForeground(Color.WHITE);
        panel_north.setBackground(Color.WHITE);
        panel_south.setBackground(Color.WHITE);
    }

    private void addEverythingToMainPanel(ClientGUI clientGUI, JPanel panel_middle, JPanel panel_north, JPanel panel_south){
        JPanel c = new JPanel();
        c.setBackground(Color.WHITE);
        c.setBorder(new EmptyBorder(5, 5, 5, 5));
        c.setLayout(new BorderLayout(0, 0));
        clientGUI.setContentPane(c);
        c.add(panel_north, BorderLayout.NORTH);
        c.add(panel_middle, BorderLayout.CENTER);
        c.add(panel_south, BorderLayout.SOUTH);
        panel_north.add(disconnect);
    }

    private void setAddEventListeners(){
        field.addActionListener(this);
        post.addActionListener(this);
        disconnect.addActionListener(this);
        change.addActionListener(this);
      //  sendto.addActionListener(this);
    }

    private int generateRandomRGBValue(){
        Random rand = new Random();
        return rand.nextInt(255);
    }

    private void setUserTextColor(){
        area.setForeground(new Color(generateRandomRGBValue(),generateRandomRGBValue(),generateRandomRGBValue()));
    }

    private void showTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        message.setText("Joined: " + dateFormat.format(cal.getTime()));
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == post) {
            System.out.println(field.getText());
            String message=field.getText();
            String to=sipfield.getText();
                    
            try {
                client.send(message,to);
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            posted = true;
         }

        else if (source == disconnect) {

            try {
                client.Disconnect();
            } catch (Exception ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        else
        if (source == change) {

            isconnected=false;        
            try {
                client.Disconnect();
                 ServerIF Router=pickRouter(isp);
                 client.ChangeRouter(Router);
              //  System.out.println("hi");
            } catch (Exception ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    
    }
    
    public static void senditto(String msg , String sip) throws RemoteException
    {
        theRouter.sendmessage(msg, sip);    
    }
    

    public static ServerIF pickRouter(IISP chatServer) throws RemoteException{
        ArrayList<ServerIF>routers= chatServer.getAvailableRouterss();
        ArrayList<String> router=new ArrayList();
        if(routers.size()>0){
        for(int i=0;i<routers.size();i++){
            router.add(routers.get(i).getAvsip());
        }
        }
        String input = (String) JOptionPane.showInputDialog(null, "Choose The Router...",
                 "Choose router to connect", JOptionPane.QUESTION_MESSAGE, null,                                                                     
    router.toArray(), // Array of choices
    router.toArray()[0]); // Initial choice
        sip=input;
       int index=router.indexOf(input);
       ServerIF selected=routers.get(index);
       // System.out.println(input);
       for(int i=0;i<routers.size();i++){ 
               routers.get(i).FreeSip(router.get(i));
               System.out.println("Free "+router.get(i));
       }
        return selected;
       
   
    }
    public static void main(String[] args) throws MalformedURLException,RemoteException, NotBoundException, IOException {
        new ClientGUI();
       
        String chatServerURL = "ISP";
          Registry r=LocateRegistry.getRegistry("localhost",123);
          isp = (IISP) r.lookup(chatServerURL);
          startClient();
    }
private static void startClient() throws RemoteException, IOException{
    System.out.println("starting client");
       ServerIF Router=pickRouter(isp);
       theRouter = Router;
       
        try {
            client=new Client(sip, Router);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
      System.out.println("Client Created");
      isconnected=true;
     new Thread(client).start();
        welcomeUser(sip);
}
    private static String getUserName(){
        String name = JOptionPane.showInputDialog("Enter Your Name");
        welcome.setText("Welcome " + name);
        return name;
    }

    private static void welcomeUser(String name){
        System.out.println("Welcome " + name);
        System.out.println("Please Enter A Message");
    }
}
