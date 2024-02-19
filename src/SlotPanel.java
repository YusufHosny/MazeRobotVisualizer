import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SlotPanel extends JPanel {

    private JPanel topWall, botWall, leftWall, rightWall;
    boolean hasRobot;
    JButton center;
    ImageIcon robot;

    public SlotPanel(boolean top, boolean bot, boolean left, boolean right) {
        super();

        hasRobot = false;

        // load robot icon
        robot = new ImageIcon("src/rbt_ico.png");

        // color initialization
        Color offWhite = new Color(0xFAF9F6);
        Color black = new Color(0);

        // create center and wall panels
        center = new JButton();
        center.setBorderPainted(false);
        center.setBackground(offWhite);
        center.setOpaque(true);

        topWall = new JPanel();
        topWall.setBackground(top ? black : offWhite);
        botWall = new JPanel();
        botWall.setBackground(bot ? black : offWhite);
        leftWall = new JPanel();
        leftWall.setBackground(left ? black : offWhite);
        rightWall = new JPanel();
        rightWall.setBackground(right ? black : offWhite);

        // size setup
        setPreferredSize(new Dimension(150, 150));
        center.setPreferredSize(new Dimension(130, 130));
        topWall.setPreferredSize(new Dimension(130, 20));
        botWall.setPreferredSize(new Dimension(130, 20));
        leftWall.setPreferredSize(new Dimension(20, 130));
        rightWall.setPreferredSize(new Dimension(20, 130));
        setMinimumSize(new Dimension(150, 150));
        center.setMinimumSize(new Dimension(130, 130));
        topWall.setMinimumSize(new Dimension(130, 20));
        botWall.setMinimumSize(new Dimension(130, 20));
        leftWall.setMinimumSize(new Dimension(20, 130));
        rightWall.setMinimumSize(new Dimension(20, 130));

        // setup layout and add components
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 1;
        add(topWall, c);
        c.gridx = 0;
        c.gridy = 4;
        add(botWall, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        add(center, c);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 5;
        add(leftWall, c);
        c.gridx = 4;
        c.gridy = 0;
        add(rightWall, c);

        revalidate();
    }

    public void setHasRobot(boolean hasRobot) {
        if (hasRobot) {
            center.setIcon(robot);
            center.setHorizontalAlignment(JLabel.CENTER);
            center.setVerticalAlignment(JLabel.CENTER);
        } else {
            center.setIcon(null);
        }
        this.hasRobot = hasRobot;
    }

    public void onClick(ActionListener listener) {
        center.addActionListener(listener);
    }
}
