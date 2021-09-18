/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package managerClient;


import TwoInterface.serverInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;

public class managerGUI extends JFrame implements ActionListener{

    private JPanel contentPane;
    private paintpanel panel;
    private JFileChooser fileChooser;
    private File file;
    private String filepath;
    private JList list;
    private JTextArea textArea;

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

    public void set_whiteboard(serverInterface whiteboard) {
        this.whiteboard = whiteboard;
    }

    public managerGUI() {

        panel = new paintpanel();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(null,"Do you want to save this file?","Information", JOptionPane.YES_NO_OPTION);
                if(flag == 0) {
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        fileChooser.setCurrentDirectory(new File("."));
                        String str;
                        try {
                            restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                            str = filter.getEnds();
                        }
                        catch(Exception e2) {
                            str = ".png";
                        }
                        file = fileChooser.getSelectedFile();
                        File newFile = null;
                        try {
                            if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                                newFile = file;
                                filepath = file.getAbsolutePath();
                            } else {
                                newFile = new File(file.getAbsolutePath() + str);
                                filepath = file.getAbsolutePath() + str;
                            }
                            str = str.substring(1);//remove the point
                            ImageIO.write(panel.save(),str, newFile);
                            JOptionPane.showMessageDialog(null, "save success", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                try {
                    whiteboard.endApplication();
                } catch (RemoteException e1) {

                    System.out.println("exit");
                    //e1.printStackTrace();
                }

                e.getWindow().dispose();
            }
        });

        fileChooser = new JFileChooser();
        restrictFileType jpgFilter = new restrictFileType(".jpg", "jpg file (*.jpg)");
        restrictFileType pngFilter = new restrictFileType(".png", "png file (*.png)");
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(100, 100, 880, 520);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        mnFile.setFont(new Font("Monaco", Font.PLAIN, 14));
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mnFile.add(mntmNew);
        mntmNew.setFont(new Font("Monaco", Font.PLAIN, 14));
        mntmNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int flag = JOptionPane.showConfirmDialog(null,"Save this file?","INFO", JOptionPane.YES_NO_OPTION);
                if(flag == 0) {
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        fileChooser.setCurrentDirectory(new File("."));
                        String str;
                        try {
                            restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                            str = filter.getEnds();
                        }
                        catch(Exception e2) {
                            str = ".png";
                        }
                        file = fileChooser.getSelectedFile();
                        File newFile = null;
                        try {
                            if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                                newFile = file;
                                filepath = file.getAbsolutePath();
                            } else {
                                newFile = new File(file.getAbsolutePath() + str);
                                filepath = file.getAbsolutePath() + str;
                            }
                            str = str.substring(1);//remove the point
                            ImageIO.write(panel.save(),str, newFile);
                            JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {

                            e1.printStackTrace();
                        }
                    }
                }
                filepath = null;
                panel.clear();
                panel.repaint();
                panel.shareImage();
            }
        });

        JMenuItem mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);
        mntmOpen.setFont(new Font("Monaco", Font.PLAIN, 14));
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int flag = JOptionPane.showConfirmDialog(null,"Save this file?","INFO", JOptionPane.YES_NO_OPTION);
                if(flag == 0) {
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        fileChooser.setCurrentDirectory(new File("."));
                        String str;
                        try {
                            restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                            str = filter.getEnds();
                        }
                        catch(Exception e2) {
                            str = ".png";
                        }
                        file = fileChooser.getSelectedFile();
                        File newFile = null;
                        try {
                            if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                                newFile = file;
                                filepath = file.getAbsolutePath();
                            } else {
                                newFile = new File(file.getAbsolutePath() + str);
                                filepath = file.getAbsolutePath() + str;
                            }
                            str = str.substring(1);//remove the point
                            ImageIO.write(panel.save(),str, newFile);
                            JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fileChooser.setCurrentDirectory(new File("."));
                    filepath = fileChooser.getSelectedFile().getAbsolutePath();
                    if (filepath == null) {
                        return;
                    }
                    else {
                        file=new File(filepath);
                    }
                    try {
                        BufferedImage bufferedImage = ImageIO.read(file);
                        panel.load(bufferedImage);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    panel.shareImage();
                }
            }
        });


        JMenuItem mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);
        mntmSave.setFont(new Font("Monaco", Font.PLAIN, 14));
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Locale.setDefault(Locale.ENGLISH);

                if (filepath == null) {
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        fileChooser.setCurrentDirectory(new File("."));
                        String str;
                        try {
                            restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                            str = filter.getEnds();
                        }
                        catch(Exception e2) {
                            str = ".png";
                        }
                        file = fileChooser.getSelectedFile();
                        File newFile = null;
                        try {
                            if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                                newFile = file;
                                filepath = file.getAbsolutePath();
                            } else {
                                newFile = new File(file.getAbsolutePath() + str);
                                filepath = file.getAbsolutePath() + str;
                            }
                            str = str.substring(1);//remove the point
                            ImageIO.write(panel.save(),str, newFile);
                            JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {

                            e1.printStackTrace();
                        }
                    }
                    return;
                }
                else {
                    file = new File(filepath);
                }
                try {
                    String[] format = filepath.split("\\.");
                    ImageIO.write(panel.save(), format[format.length - 1],file);
                    JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }
        });

        JMenuItem mntmSaveAs = new JMenuItem("Save As");
        mnFile.add(mntmSaveAs);
        mntmSaveAs.setFont(new Font("Monaco", Font.PLAIN, 14));
        mntmSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fileChooser.setCurrentDirectory(new File("."));
                    String str;
                    try {
                        restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                        str = filter.getEnds();
                    }
                    catch(Exception e2) {
                        str = ".png";
                    }
                    file = fileChooser.getSelectedFile();
                    File newFile = null;
                    try {
                        if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                            newFile = file;
                            filepath = file.getAbsolutePath();
                        } else {
                            newFile = new File(file.getAbsolutePath() + str);
                            filepath = file.getAbsolutePath() + str;
                        }
                        str = str.substring(1);//remove the point
                        ImageIO.write(panel.save(),str, newFile);
                        JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e1) {

                        e1.printStackTrace();
                    }
                }
            }
        });


        JMenuItem mntmClose = new JMenuItem("Close");
        mntmClose.setFont(new Font("Monaco", Font.PLAIN, 14));
        mnFile.add(mntmClose);
        mntmClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int flag = JOptionPane.showConfirmDialog(null,"Save this file?","INFO", JOptionPane.YES_NO_OPTION);
                if(flag == 0) {
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        fileChooser.setCurrentDirectory(new File("."));
                        String str;
                        try {
                            restrictFileType filter = (restrictFileType) fileChooser.getFileFilter();
                            str = filter.getEnds();
                        }
                        catch(Exception e2) {
                            str = ".png";
                        }
                        file = fileChooser.getSelectedFile();
                        File newFile = null;
                        try {
                            if (file.getAbsolutePath().toUpperCase().endsWith(str.toUpperCase())) {
                                newFile = file;
                                filepath = file.getAbsolutePath();
                            } else {
                                newFile = new File(file.getAbsolutePath() + str);
                                filepath = file.getAbsolutePath() + str;
                            }
                            str = str.substring(1);//remove the point
                            ImageIO.write(panel.save(),str, newFile);
                            JOptionPane.showMessageDialog(null, "Save Successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {

                            e1.printStackTrace();
                        }
                    }
                }
                try {
                    whiteboard.endApplication();
                } catch (RemoteException e1) {

                    System.out.println("exit");
                    //e1.printStackTrace();
                }
                System.exit(0);
            }
        });




        JMenu mnClientControl = new JMenu("Manage");
        mnClientControl.setFont(new Font("Monaco", Font.PLAIN, 14));
        menuBar.add(mnClientControl);

        JMenuItem mntmKickOut = new JMenuItem("Kick Out");
        mnClientControl.add(mntmKickOut);
        mntmKickOut.setFont(new Font("Monaco", Font.PLAIN, 14));
        mntmKickOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog( "Input the Username" );
                try {
                    whiteboard.removeUser(input);
                } catch (RemoteException e1) {

                    System.out.println("Error in GUI");
                    //e1.printStackTrace();
                }
            }
        });



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


        btnRectangle.addActionListener(this);
        btnLine.addActionListener(this);
        btnCircle.addActionListener(this);
        btnText.addActionListener(this);

        JLabel lblUsers = new JLabel("User List:");
        lblUsers.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblUsers.setBounds(0, 36, 210, 25);
        contentPane.add(lblUsers);

        list = new JList();
        list.setBounds(0, 61, 210, 390);
        list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        contentPane.add(list);

        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScroll.setBounds(0, 61, 210, 390);
        contentPane.add(listScroll);

        JLabel boardLabel = new JLabel("White Board:");
        boardLabel.setFont(new Font("Monaco", Font.PLAIN, 14));
        boardLabel.setBounds(211, 0, 400, 35);
        contentPane.add(boardLabel);


        panel.setBounds(211, 36, 400, 415);
        panel.setBackground(Color.white);
        contentPane.add(panel);


        JLabel lblChatbox = new JLabel("Receive Notice:");
        lblChatbox.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblChatbox.setBounds(611, 0, 250, 35);
        contentPane.add(lblChatbox);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(611, 36, 250, 415);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        scroll.setBounds(611, 36, 250, 415);
        contentPane.add(scroll);


        this.setTitle("Manager");
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

