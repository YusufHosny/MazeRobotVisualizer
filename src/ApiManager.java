import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ApiManager {

    private static ApiManager singleton = null;

    public static ApiManager getInstance() {
        if (singleton == null) {
            singleton = new ApiManager();
        }
        return singleton;
    }

    public ApiManager() {

    }

    public String makeGETRequest(String urlName){
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return "";

    }

    public void sendInstruction(String instr) {
        makeGETRequest("https://studev.groept.be/api/a23ib2a03/sendInstruction/"+instr);
    }

    public DataPacket getDataPacket() {
        String[] result = new String[4];
        String response = makeGETRequest("https://studev.groept.be/api/a23ib2a03/getDataPacket");
        JSONArray array = new JSONArray(response);
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject obj = array.getJSONObject(i);
            result[0] = String.valueOf(obj.getInt("position_x"));
            result[1] = String.valueOf(obj.getInt("position_y"));
            result[2] = obj.getString("mapObject");
            result[3] = String.valueOf(obj.getInt("mapCompletionStatus"));
        }
        return new DataPacket(result);
    }


    public Maze getMaze() {

        // TEST STRING
        // String json = "{\n" +
        //         "        \"dims\" : [4, 4],\n" +
        //         "        \"verticals\" : [1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1],\n" +
        //         "        \"horizontals\" : [1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1]\n" +
        //         "}";

        return new Maze(this.getDataPacket().getMap());
    }
}
