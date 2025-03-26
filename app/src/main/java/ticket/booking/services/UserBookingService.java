package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user; // globally declaring an user

    private List<User> userList;

    private ObjectMapper  objectMapper = new ObjectMapper();

    private static  final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<User>>() {});
    }
    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }
    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(usersFile, userList);
    }
    public void fetchBooking(){
        user.printTickets();
    }
    public Boolean cancelBooking(String ticketId){
        return Boolean.FALSE;
    }





}