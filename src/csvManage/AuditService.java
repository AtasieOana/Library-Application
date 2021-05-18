package csvManage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class AuditService {

    private static AuditService instance = null;

    private AuditService(){
    }

    public static AuditService getInstance(){
        if(instance == null){
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String action){
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
