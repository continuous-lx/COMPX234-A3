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
            totalReads += 1;
            updateAverages();
            return tuples.get(key);
        } else {
            totalErrors += 1;
            updateAverages();
            return "";
        }
    }
  
    public synchronized String get(String key) {
        String value;
        if(tuples.containsKey(key)) {
            value = tuples.get(key);
            tuples.remove(key);
            size -= 1;
            totalGets += 1;
            updateAverages();
            return value;
        } else {
            totalErrors += 1;
            updateAverages();
            return "";
        }
    }

    public synchronized int put(String key, String value) {
        if(tuples.containsKey(key) == false) {
            tuples.put(key, value);
            size += 1;
            totalPuts += 1;
            updateAverages();
            return 1;
        } else {
            totalErrors += 1;
            updateAverages();
            return 0;
        }
    }

    private void updateAverages() {
        try {
            int entries = 0;
            int totalKeySize = 0;
            int totalValueSize = 0;

            for(String key: tuples.keySet()) {
                entries += 1;

                String value = tuples.get(key);
                totalKeySize += key.length();
                totalValueSize += value.length();
            }

            if (entries != 0){
                aveKeysize = (double)totalKeySize / (double)entries;
                aveValueSize = (double)totalValueSize / (double)entries;
                aveTuplesize = ((double)totalKeySize + (double)totalValueSize) / (double)entries;
            }
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}