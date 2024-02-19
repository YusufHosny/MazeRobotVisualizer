import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Core extends JFrame {

    private JPanel centralArea;
    private final ApiManager api;

    public Core() {
        super("Robo-Manager Core");

        // initialize the Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 700);

        // TODO SETTINGS IF NEEDED
        // JMenuBar mb = new JMenuBar();
        // JMenu settings = new JMenu("Settings");
        // mb.add(settings);
        // JMenuItem settingA = new JMenuItem("settingA");
        // JMenuItem settingB = new JMenuItem("settingB");
        // JMenuItem settingC = new JMenuItem("settingC");
        // settings.add(settingA);
        // settings.add(settingB);
        // settings.add(settingC);

        // initialize api manager
        api = new ApiManager();

        // central area
        centralArea = new JPanel();


        JButton mapButton = new JButton("Begin Mapping");
        centralArea.add(mapButton); // TODO action listener integrated with api
        mapButton.addActionListener(e -> {
            centralArea.removeAll();
            centralArea.add(new MazePanel(api.getMaze()));
            refreshContainer(this);
        });



        //Adding Components to the frame.
        // this.getContentPane().add(BorderLayout.NORTH, mb);
        this.getContentPane().add(BorderLayout.WEST, centralArea);

        this.setVisible(true);

    }

    public void refreshContainer(Container container) {
        container.setVisible(false);
        container.setVisible(true);
    }

}
