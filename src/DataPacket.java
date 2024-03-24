public class DataPacket {

    private int x;
    private int y;
    private boolean completionStatus;
    private String map;

    public DataPacket(String[] params) {

        for(int i = 0; i < 4; i++) {
            if(params[i] == null) {
                x = 0;
                y = 0;
                map = null;
                completionStatus = false;
                return;
            }
        }

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
