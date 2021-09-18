/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package userClient;






import TwoInterface.serverInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class userGUI extends JFrame implements ActionListener{

    private JPanel contentPane;
    private paintpanel panel;
    private JList list;
    private JTextArea textArea;
    private String username;
    private serverInterface whiteboard;

    public paintpanel getpanel() {
        return panel;
    }

    public JList getJlist() {
        return list;
    }

    public JTextArea gettextArea() {
        return textArea;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void set_whiteboard(serverInterface whiteboard) {
        this.whiteboard = whiteboard;
    }

    public userGUI() {


        panel = new paintpanel();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    whiteboard.exitRoom(username);
                } catch (RemoteException e1) {

                    e1.printStackTrace();
                }

                e.getWindow().dispose();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 880, 520);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);



        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBounds(0, 0, 210, 35);
        contentPane.add(toolBar);

        JButton btnRectangle = new JButton("Rectangle");
        btnRectangle.setFont(new Font("Apple Chancery", Font.PLAIN, 15));
        toolBar.add(btnRectangle);

        JButton btnLine = new JButton("Line");
        btnLine.setFont(new Font("Apple Chancery", Font.PLAIN, 15));
        toolBar.add(btnLine);


        JButton btnCircle = new JButton("Circle");
        btnCircle.setFont(new Font("Apple Chancery", Font.PLAIN, 15));
        toolBar.add(btnCircle);


        JButton btnText = new JButton("Text");
        btnText.setFont(new Font("Apple Chancery", Font.PLAIN, 15));
        toolBar.add(btnText);


        //button_listener
        btnRectangle.addActionListener(this);
        btnLine.addActionListener(this);
        btnCircle.addActionListener(this);
        btnText.addActionListener(this);



        JLabel lblUsers = new JLabel("User List:");
        lblUsers.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblUsers.setBounds(0, 36, 210, 25);
        contentPane.add(lblUsers);

        list = new JList();
        list.setBounds(0, 61, 210, 410);
        list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        contentPane.add(list);

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroll.setBounds(0, 61, 210, 410);
        contentPane.add(listScroll);

        JLabel boardLabel = new JLabel("White Board:");
        boardLabel.setFont(new Font("Monaco", Font.PLAIN, 14));
        boardLabel.setBounds(211, 0, 400, 35);
        contentPane.add(boardLabel);

        panel.setBounds(211, 36, 400, 435);
        panel.setBackground(Color.white);
        contentPane.add(panel);


        JLabel lblChatbox = new JLabel("Receive Notice:");
        lblChatbox.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblChatbox.setBounds(611, 0, 250, 35);
        contentPane.add(lblChatbox);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(611, 36, 250, 435);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(611, 36, 250, 435);
        contentPane.add(scroll);

        this.setTitle("User");
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String str = e.getActionCommand();
        if(str.contentEquals("Line")) {
            panel.setType("line");
        }
        else if(str.contentEquals("Rectangle")) {
            panel.setType("rect");
        }
        else if(str.contentEquals("Circle")) {
            panel.setType("circle");
        }
        else if(str.contentEquals("Text")) {
            panel.setType("text");
        }
        else if(str.contentEquals("Exit")) {
            System.exit(0);

        }
    }
}

