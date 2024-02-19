import org.json.*;

import java.util.ArrayList;
public class Maze {

    private ArrayList<Boolean> horizontals, verticals;

    public ArrayList<Boolean> getHorizontals() {
        return horizontals;
    }

    public ArrayList<Boolean> getVerticals() {
        return verticals;
    }

    public int[] getDims() {
        return dims;
    }

    private int[] dims = {0, 0};

    Maze(String json) {

        // parse json
        JSONObject jsonObj = new JSONObject(json);

        JSONArray jsonDims = jsonObj.getJSONArray("dims");
        JSONArray jsonVert = jsonObj.getJSONArray("verticals");
        JSONArray jsonHor  = jsonObj.getJSONArray("horizontals");

        // populate dimension array
        dims[0] = jsonDims.getInt(0);
        dims[1] = jsonDims.getInt(1);

        // populate wall arrays
        verticals = new ArrayList<>();
        horizontals = new ArrayList<>();

        for(int i = 0; i < jsonVert.length(); i++) {
            Boolean wallPresent = jsonVert.getInt(i) == 1;
            verticals.add(wallPresent);
        }

        for(int i = 0; i < jsonHor.length(); i++) {
            Boolean wallPresent = jsonHor.getInt(i) == 1;
            horizontals.add(wallPresent);
        }

    }

}
