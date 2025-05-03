import java.util.HashMap;

public class TupleSpace {

    private final HashMap<String, String> tuples = new HashMap<>();

    public synchronized String read(String key){
        if(tuples.containsKey(key)) {
            return "OK ("+key+","+tuples.get(key)+") read";
        } else {
            return "ERR"+key+"does not exist.";
        }
    }
  
    public synchronized String get(String key) {
        String value;
        if(tuples.containsKey(key)) {
            value = tuples.get(key);
            tuples.remove(key);
            return "OK ("+key+","+value+") removed";
        } else {
            return "ERR"+key+"does not exist.";
        }
    }
}