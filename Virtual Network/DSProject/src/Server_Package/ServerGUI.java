package Server_Package;
import ISP.IISP;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

class ServerGUI extends JFrame implements ActionListener {
private final IISP isp;
private static vRouter v;
private static ArrayList<ServerIF> connectedRouters;
    private final JLabel welcome = new JLabel("Chat Server Initialized");
    private final JPanel panel = new JPanel();
    private final JButton close = new JButton("Terminate Session");
    private final JButton add1 = new JButton("Connect with interf01");
    private final JButton add2 = new JButton("Connect with interf02");
    private final JButton add3 = new JButton("Connect with interf03");
    private final JButton add4 = new JButton("Connect with interf04");
    private final JLabel time = new JLabel();
    private final JLabel clientList = new JLabel("Clients Connected :" + "0");
    private final JButton clients_total = new JButton("Refresh Client List");
    private static JList<String> routersList = new JList<String>(new DefaultListModel<String>());

    public ServerGUI(IISP isp,vRouter v) throws RemoteException {
        super("Router");
        this.add1.setText((String) v.getSIP().keySet().toArray()[0]);
        this.add2.setText((String) v.getSIP().keySet().toArray()[1]);
        this.add3.setText((String) v.getSIP().keySet().toArray()[2]);
        this.add4.setText((String) v.getSIP().keySet().toArray()[3]);
        setUpWindow();
        addEventListeners();
        int myLocalVar = vRouter.getClients();
        clientList.setText("" + myLocalVar);
        this.isp=isp;
        connectedRouters=new ArrayList<>();
        this.v=v;
    }

    private void addEventListeners() {
        close.addActionListener(this);
        clients_total.addActionListener(this);
        add1.addActionListener(this);
        add2.addActionListener(this);
        add3.addActionListener(this);
        add4.addActionListener(this);
    }

    private void setUpWindow() {
        Container c = getContentPane();
        c.add(panel);
        c.setSize(300, 400);
        panel.add(welcome);
        panel.add(time);
        panel.add(clientList);
        panel.add(clients_total);
        panel.add(close);
       // add.setSize(190, 100);
        panel.add(add1);
        panel.add(add2);
        panel.add(add3);
        panel.add(add4);
        
        panel.add(new JLabel("\n"));
        panel.add(routersList);
        setSize(1000, 400);
        setResizable(true);
        setVisible(true);
        showTime();
    }

    private void showTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));
        time.setText(dateFormat.format(cal.getTime()));
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == close) {

            System.exit(0);
        }
        if (source == clients_total) {
            int myLocalVar = vRouter.getClients();
            clientList.setText("Total Connected Today: " + myLocalVar);
        }
         if (source == add1) {
            try {
                pickRouter(isp , add1);
            } catch (Exception ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (source == add2) {
            try {
                pickRouter(isp , add2);
               
            } catch (Exception ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (source == add3) {
            try {
                pickRouter(isp , add3);
               
            } catch (Exception ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (source == add4) {
            try {
                pickRouter(isp , add4);
               
            } catch (Exception ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        public static void pickRouter(IISP chatServer , JButton jb) throws RemoteException, IOException, InterruptedException{
        
            ArrayList<ServerIF> routers= chatServer.getAvailableRouterss();
            //HashMap<ServerIF , HashMap<String, Integer>> hrouters= chatServer.getAvailableRouters();
        ArrayList<String> temp=new ArrayList();
        ArrayList<ServerIF> router=new ArrayList();
        if(routers.size()>0){
        for(int i=0;i<routers.size();i++){
            
            if(!connectedRouters.contains(routers.get(i))&&!routers.get(i).getusername().equals(v.getusername())){
                //System.out.println("own sip = "+sip+" router "+routers.get(i).getSIP());
                router.add(routers.get(i));
                temp.add(routers.get(i).getusername());
            }
        }
            
        }
        if(temp.size()>0){
        String input = (String) JOptionPane.showInputDialog(null, "Choose The Router...", "Choose supplier of the product !!", JOptionPane.QUESTION_MESSAGE, null,temp.toArray(),temp.toArray()[0]);
        //sip=input;
       
        int index=temp.indexOf(input);
            System.out.println("index : " + index + " username : " + input);
       ServerIF selected= router.get(index);
       String sip = selected.getAvsip();
       int avport = selected.getport();
            if(!sip.equals(null) && avport != -1)
            {
                v.getroutercomun().connect( sip , InetAddress.getLoopbackAddress().getHostAddress(), avport);
                v.connectedRouters.put(sip,selected);
                connectedRouters.add(selected);
                ((DefaultListModel)routersList.getModel()).addElement(selected.getSIP());
                jb.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "no ports available");
       }
        else JOptionPane.showMessageDialog(null, "No Routers available");
    }
}
