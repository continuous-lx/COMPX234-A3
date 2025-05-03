import java.util.HashMap;

public class TupleSpace {

    private final HashMap<String, String> tuples = new HashMap<>();

    public synchronized String read(String key){
        if(tuples.containsKey(key)) {
            return tuples.get(key);
        } else {
            return "key does not exist.";
        }
    }
  
    public synchronized String get(String key) {
        
    }
}