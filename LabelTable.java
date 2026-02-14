
import java.util.HashMap;

public class LabelTable {
    private HashMap<String, Integer> labels = new HashMap<>();

    public void addLabel(String l, int add){
        labels.put(l, add);
    }

    public int getAddress(String l){
        return labels.getOrDefault(l, 0);
    }

    public int getAddress(int location){
        return location;
    }
}
