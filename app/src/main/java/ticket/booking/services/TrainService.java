package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {

    private Train train;

    private List<Train> trainList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static  final String TRAIN_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";


    public TrainService() throws IOException {
        loadTrainListFromFile();
    }


    private void loadTrainListFromFile() throws IOException {
        trainList = objectMapper.readValue(new File(TRAIN_FILE_PATH), new TypeReference<List<Train>>() {});
    }

    public Boolean addTrain(Train train){
        trainList.add(train);
        saveTrainListToFile();
        return Boolean.TRUE;
    }

    private void saveTrainListToFile()  {
        try{
        objectMapper.writeValue(new File(TRAIN_FILE_PATH), trainList);
        }
        catch(IOException e){
            e.printStackTrace();// handles
        }
    }

    public Train getTrain(String id) {
        if (trainList == null) return null;
        return trainList.stream()
                .filter(train -> id.trim().equalsIgnoreCase(train.getTrainId().trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }
    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }








}
