public class DataPacket {

    private int x;
    private int y;
    private boolean completionStatus;
    private String map;

    public DataPacket(String[] params) {
        x = Integer.parseInt(params[0]);
        y = Integer.parseInt(params[1]);
        map = params[2];
        completionStatus = Integer.parseInt(params[3]) == 1;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public String getMap() {
        return map;
    }
}
