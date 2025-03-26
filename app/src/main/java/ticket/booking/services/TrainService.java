package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrainService {

    private Train train;

    private List<Train> trainList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static  final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";





    private void loadTrainListFromFile() throws IOException {
        trainList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<Train>>() {});
    }

    private Boolean addTrain(Train train){
        try{
            trainList.add(train);
            saveTrainListToFile();
            return Boolean.TRUE;
        }
        catch(IOException e){
            return Boolean.FALSE;
        }
    }

    private void saveTrainListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(usersFile, trainList);
    }

    private Train getTrain(int id){
        return trainList.get(id);
    }


}
