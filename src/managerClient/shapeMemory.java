/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package managerClient;



import java.awt.*;

public class shapeMemory {
    private int x, x1, y, y1;
    private String type;
    private Color color;

    private Stroke stroke;
    private String input;

    public shapeMemory(Graphics g, int x, int y, int x1, int y1, String type, Color color,Stroke stroke)
    {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.type = type;
        this.color = color;
        this.stroke = stroke;

    }




    public shapeMemory(Graphics g, int x, int y, String in, String type, Color color)
    {
        this.x =x;
        this.y = y;
        this.input = in;
        this.type = type;
        this.color =color;
    }

    public void drawShapes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(type.equals("text")){
            g.setColor(color);
            g.drawString(input,x,y);
        }
        else {
            g2.setColor(color);
            g2.setStroke(stroke);
            if (type.equals("line")) {
                g.drawLine(x, y, x1, y1);
            } else if (type.equals("rect")) {
                g.drawRect(Math.min(x, x1), Math.min(y, y1), Math.abs(x - x1), Math.abs(y - y1));
            }  else if (type.equals("circle")) {
                g.drawOval(Math.min(x, x1), Math.min(y, y1), Math.max(Math.abs(x - x1),Math.abs(y - y1)),Math.max(Math.abs(x - x1),Math.abs(y - y1)));
            }
        }
    }

}

