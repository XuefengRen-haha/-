/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package userClient;




import TwoInterface.serverInterface;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class paintpanel extends JPanel{

    private String type = "line";

    private int x,y;

    private Color selectColor = Color.BLACK;
    private Stroke selectStroke = new BasicStroke(1.0f);
    private serverInterface whiteboard;
    private BufferedImage image;



    private static ArrayList<shapeMemory> shapelist = new ArrayList<shapeMemory>();


    public void setwhiteboard(serverInterface whiteboard) {
        this.whiteboard = whiteboard;
    }
    public void setType(String type) {
        this.type = type;
    }


    public void clear() {
        shapelist = new ArrayList<shapeMemory>();
        image = null;
    }


    public BufferedImage save() {
        Dimension imagesize = this.getSize();
        BufferedImage image = new BufferedImage(imagesize.width,imagesize.height,BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = image.createGraphics();
        this.paint(graphics);
        graphics.dispose();
        return image;
    }


    public void load(BufferedImage image) {
        clear();
        repaint();
        this.image = image;
    }


    public void paint(Graphics g){
        super.paint(g);
        if(image != null) {
            g.drawImage(image, 0, 0, this);
        }
        for (int i = 0; i < shapelist.size(); i++) {
            if (shapelist.get(i) == null) {break;}
            shapelist.get(i).drawShapes(g);
        }
    }

    public void draw(int x,int y,int x1,int y1,String type) {
        Graphics2D g = (Graphics2D)getGraphics();
        g.setColor(selectColor);
        g.setStroke(selectStroke);
        if(type.equals("line")) {
            shapelist.add(new shapeMemory(g,x,y,x1,y1,type,selectColor,selectStroke));
            g.drawLine(x,y, x1, y1);
        }
        else {
            int height = Math.abs(y1 - y);
            int width = Math.abs(x1 - x);
            if(type.equals("rect")) {
                shapelist.add(new shapeMemory(g,x,y,x1,y1,type,selectColor,selectStroke));
                g.drawRect(Math.min(x, x1),Math.min(y, y1), width, height);
            }
            if(type.equals("circle")) {
                shapelist.add(new shapeMemory(g,x,y,x1,y1,type,selectColor,selectStroke));
                int round = Math.max(width, height);
                g.drawOval(Math.min(x, x1),Math.min(y, y1), round,round);
            }

        }
    }


    public void shareImage() {
        try {

            BufferedImage image = save();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image,"png", out);
            byte[] b = out.toByteArray();
            whiteboard.getImage(b);
        } catch (RemoteException e) {

            JOptionPane.showMessageDialog(null, "The application is closed.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public paintpanel() {
        addMouseListener(new MouseListener() {

                             @Override
                             public void mousePressed(MouseEvent e) {
                                 x = e.getX();
                                 y = e.getY();
                                 if(whiteboard!=null&&type.equals("text")){
                                     Graphics2D g = (Graphics2D)getGraphics();
                                     String input;
                                     input = JOptionPane.showInputDialog("Input the Text!");
                                     if(input != null) {
                                         g.setColor(selectColor);
                                         g.drawString(input,x,y);
                                         shapelist.add(new shapeMemory(g,x,y,input,type,selectColor));
                                         shareImage();
                                     }
                                 }
                             }

                             @Override
                             public void mouseReleased(MouseEvent e) {
                                 if(whiteboard != null) {
                                     int x1 = e.getX();
                                     int y1 = e.getY();
                                     draw(x, y, x1, y1, type);
                                     shareImage();
                                 }
                             }

                             @Override
                             public void mouseClicked(MouseEvent e) {
                             }

                             @Override
                             public void mouseEntered(MouseEvent e) {
                             }

                             @Override
                             public void mouseExited(MouseEvent e) {
                             }

                         }
        );
        addMouseMotionListener(new MouseMotionListener() {
                                   @Override
                                   public void mouseDragged(MouseEvent e) {
                                       if(whiteboard != null) {
                                           int x2 = e.getX();
                                           int y2 = e.getY();

                                           Graphics2D g = (Graphics2D)getGraphics();
                                           g.setColor(selectColor);
                                           g.setStroke(selectStroke);

                                           if (type.equals("line")) {
                                               g.drawLine(x, y, x2, y2);
                                           } else {
                                               int height = Math.abs(y2 - y);
                                               int width = Math.abs(x2 - x);
                                               if (type.equals("rect")) {
                                                   g.drawRect(Math.min(x, x2), Math.min(y, y2), width, height);
                                               }
                                               if (type.equals("circle")) {
                                                   int round = Math.max(width, height);
                                                   g.drawOval(Math.min(x, x2),Math.min(y, y2), round,round);
                                               }
                                           }
                                           repaint();
                                       }
                                   }
                                   @Override
                                   public void mouseMoved(MouseEvent e) {

                                   }

                               }
        );
    }
}

