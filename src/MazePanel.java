import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MazePanel extends JPanel {

    private ArrayList<SlotPanel> slotList;
    private Mode mode;
    private Box sideBar;
    int[] dims;
    private int[] currentpos = {0, 0},  endpos = {0, 0};
    private JLabel currentEnd, currentStart;

    private final ApiManager api = ApiManager.getInstance();

    public MazePanel(Maze maze) {
        super();

        // get dimensions
        dims = maze.getDims();

        // create panel that will hold the maze and create a grid layout
        JPanel mazePanel = new JPanel();
        add(mazePanel);
        mazePanel.setLayout(new GridLayout(dims[1], dims[0]));

        // get horizontal and vertical wall lists
        ArrayList<Boolean> horizontals = maze.getHorizontals();
        ArrayList<Boolean> verticals = maze.getVerticals();

        // set default mode
        mode = Mode.IDLE;

        // take wall arrays and create visual panels (create maze visually)
        slotList = new ArrayList<>();
        for(int y = 0; y < dims[1]; y++) {
            for(int x = 0; x < dims[0]; x++) {
                boolean wallTop, wallBot, wallLeft, wallRight;
                wallTop = horizontals.get(x + (y*dims[0]));
                wallBot = horizontals.get(x + ((y+1)*dims[0]));
                wallLeft = verticals.get(x + (y*(dims[0]+1)));
                wallRight = verticals.get(x + (y*(dims[1]+1))+1);

                SlotPanel gridSlot = new SlotPanel(wallTop, wallBot, wallLeft, wallRight);
                slotList.add(gridSlot);
                mazePanel.add(gridSlot);

                int currentX = x;
                int currentY = y;
                // when the maze tile is clicked update end if correct mode
                gridSlot.onClick(e -> {
                    if(mode == Mode.SELECT_END) {
                        this.updateEnd(currentX, currentY);
                        mode = Mode.IDLE;
                    }
                });
            }
        }



        // sidebar button initialization
        sideBar = Box.createVerticalBox();
        currentStart = new JLabel("Current Position:  (" + currentpos[0]+ ", " + currentpos[1] + ")");
        JButton endBtn = new JButton("Select End Position");
        currentEnd = new JLabel("Selected Target Position:  (" + endpos[0]+ ", " + endpos[1] + ")");
        sideBar.add(currentStart);
        sideBar.add(Box.createRigidArea(new Dimension(0,20)));
        sideBar.add(endBtn);
        sideBar.add(Box.createRigidArea(new Dimension(0,20)));
        sideBar.add(currentEnd);
        sideBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        endBtn.addActionListener(e -> {
            if(mode == Mode.IDLE) {
                mode = Mode.SELECT_END;
            }
        });
        JButton moveBtn = new JButton("Move");
        sideBar.add(Box.createRigidArea(new Dimension(0,20)));
        sideBar.add(moveBtn);


        // movement button event listener
        moveBtn.addActionListener(e -> {
            if(currentpos[0] == endpos[0] && currentpos[1] == endpos[1]) return;

            endBtn.setEnabled(false);
            moveBtn.setEnabled(false);
            String inst = "MOV,";
            inst += endpos[0] + "," + endpos[1];
            api.sendInstruction(inst);

            ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
            s.schedule(() -> {
                while(currentpos[0] != endpos[0] || currentpos[1] != endpos[1]) {
                    DataPacket packet = api.getDataPacket();
                    int x = packet.getX();
                    int y = packet.getY();

                    this.setRobotAt(x, y);
                }
                endBtn.setEnabled(true);
                moveBtn.setEnabled(true);
            }, 10, TimeUnit.MILLISECONDS);

        });


        add(sideBar);

        DataPacket packet = api.getDataPacket();
        this.setRobotAt(packet.getX(), packet.getY());

        setVisible(true);
    }

    // get position of a slot in x and y coords
    public int[] getPosition(SlotPanel p) {
        int ix = slotList.indexOf(p);
        return new int[]{(ix % dims[0]), (ix / dims[0])};
    }

    // get slot using x and y coordinates
    public SlotPanel getSlot(int x, int y) {
        return slotList.get(y * dims[0] + x);
    }

    public void setRobotAt(int x, int y) {
        if (currentpos[0] == x && currentpos[1] == y) return;

        this.getSlot(currentpos[0], currentpos[1]).setHasRobot(false);
        this.getSlot(x, y).setHasRobot(true);
        updateStart(x, y);
    }

    // update start/end label functions

    private void updateEnd(int x, int y) {
        endpos[0] = x;
        endpos[1] = y;
        currentEnd.setText("Selected Target Position:  (" + endpos[0]+ ", " + endpos[1] + ")");
    }
    private void updateStart(int x, int y) {
        currentpos[0] = x;
        currentpos[1] = y;
        currentStart.setText("Current Position:  (" + currentpos[0]+ ", " + currentpos[1] + ")");
    }
}
