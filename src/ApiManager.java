public class ApiManager {

    public ApiManager() {

    }
    public Maze getMaze() {
        // TODO ADD API
        String json = "{\n" +
                "        \"dims\" : [4, 4],\n" +
                "        \"verticals\" : [1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1],\n" +
                "        \"horizontals\" : [1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1]\n" +
                "}";
        return new Maze(json);
    }
}
