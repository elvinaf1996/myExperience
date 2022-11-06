import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Main{
    private static String dataFile = "src/main/resources/debitCard.json";

    public static void main(String[] args) {

        HashMap<String, String> newHash = getHashMapFirstDepth("appStatus");
        System.out.println(newHash.get("Стадия"));
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    private static HashMap<String, String> getHashMapFirstDepth(String nameHashMap){
        String stringBuilder = getJsonFile();
        int indexOfName = stringBuilder.indexOf(nameHashMap);
        String newStringWithoutName = stringBuilder.substring(indexOfName + nameHashMap.length());
        int indexOfStartHash = newStringWithoutName.indexOf("{");
        int indexOfFinishHash = newStringWithoutName.indexOf("}");
        String ourHashInString = newStringWithoutName.substring(indexOfStartHash + 1, indexOfFinishHash);

        return splitStringOnHash(ourHashInString);
    }

    private static HashMap<String, String> splitStringOnHash(String ourHashInString){
        HashMap<String, String> ourHash = new HashMap<>();
        String[] pairs = ourHashInString.split(",");
        for(int i = 0; i < pairs.length; i++){
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            ourHash.put(keyValue[0].trim().replaceAll("\"", ""), keyValue[1].trim().replaceAll("\"", ""));
        }
        return ourHash;
    }
}