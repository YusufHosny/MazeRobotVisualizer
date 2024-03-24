import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class Core extends JFrame {

    private JPanel centralArea;
    private final ApiManager api;

    public Core() {
        super("Robo-Manager Core");

        // initialize the Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 700);


        // initialize api manager
        api = ApiManager.getInstance();

        // central area
        centralArea = new JPanel();


        JButton mapButton = new JButton("Begin Mapping");
        centralArea.add(mapButton);
        mapButton.addActionListener(e -> {
            api.sendInstruction("MAP");
            mapButton.setEnabled(false);

            System.out.println("mapping...");
            ScheduledExecutorService s = Executors.newScheduledThreadPool(1);

            s.scheduleWithFixedDelay(() -> {
                if(api.getDataPacket().isCompletionStatus()) {
                    System.out.println("done");
                    centralArea.removeAll();
                    centralArea.add(new MazePanel(api.getMaze()));
                    refreshContainer(this);
                    s.shutdown();
                }
            }, 10, 750, TimeUnit.MILLISECONDS);
        });



        //Adding Components to the frame.
        this.getContentPane().add(BorderLayout.WEST, centralArea);

        this.setVisible(true);

    }

    public void refreshContainer(Container container) {
        container.setVisible(false);
        container.setVisible(true);
    }

}
