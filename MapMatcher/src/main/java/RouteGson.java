import java.util.List;

public class RouteGson {
    public RouteGson(int id, List<String> locations) {
        this.id = id;
        this.locations = locations;
    }
    private int id;
    private List<String> locations;
}