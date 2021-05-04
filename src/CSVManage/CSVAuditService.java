package CSVManage;

import Classes.Reader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CSVAuditService {

    private static CSVAuditService instance = null;

    private CSVAuditService(){
    }

    public static CSVAuditService getInstance(){
        if(instance == null){
            instance = new CSVAuditService();
        }
        return instance;
    }

    public void writeCSV(String action){
        try (FileWriter writer = new FileWriter("Audit.csv", true);) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            writer.append(action);
            writer.append(",");
            writer.append(String.valueOf(currentTime));
            writer.append("\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
