package CSVManage;
import java.io.*;
import java.util.Optional;

public class CSVReadWrite {

    public static void readCSV(String FilePath) {
        try (BufferedReader buffer = new BufferedReader(new
                FileReader(FilePath))) {
            String line = buffer.readLine();
            while (line != null) {
                System.out.println(line);
                line = buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeUsingDataOutputStream(String FilePath, String text) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new
                FileOutputStream(FilePath))) {
            dataOutputStream.writeChars(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
