import java.util.HashMap;

public class TupleSpace {

    private final HashMap<String, String> tuples = new HashMap<>();
    private int size = 0;
    private int totalReads = 0;
    private int totalGets = 0;
    private int totalPuts = 0;
    private int totalErrors = 0;
    private double aveTuplesize = 0.0;
    private double aveKeysize = 0.0;
    private double aveValueSize = 0.0;

    public synchronized int getSize() {
        return size;
    }

    public synchronized int getTotalReads(){
        return totalReads;
    }

    public synchronized int getTotalGets(){
        return totalGets;
    }

    public synchronized int getTotalPut(){
        return totalPuts;
    }

    public synchronized int getTotalErrors(){
        return totalErrors;
    }

    public synchronized double getAveTupleSize(){
        return aveTuplesize;
    }

    public synchronized double getAveKeySize(){
        return aveKeysize;
    }

    public synchronized double getAveValueSize(){
        return aveValueSize;
    }

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

    public synchronized String put(String key, String value) {
        if(tuples.containsKey(key) == false) {
            tuples.put(key, value);
            return "OK ("+key+","+value+") added";
        } else {
            return "ERR"+key+"already exists.";
        }
    }

    private void updateAverages() {
        
    }
}