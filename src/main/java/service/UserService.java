package service;

import model.User;
import util.FileDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String FILE_NAME = "user.txt";

    public boolean createUser(User user) throws Exception {
        user.setId(FileDatabase.getNextId(FILE_NAME));
        String[] record = {
                String.valueOf(user.getId()),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getAddress() != null ? user.getAddress() : "",
                String.valueOf(user.isActive())
        };
        return FileDatabase.addRecord(FILE_NAME, record);
    }

    public User authenticate(String email, String password) {
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            if (record.length > 3 && record[2].equals(email) && record[3].equals(password)) {
                return mapToUser(record);
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            if (record.length > 0 && FileDatabase.safeParseInt(record[0]) == id) {
                return mapToUser(record);
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String[] record : FileDatabase.readAll(FILE_NAME)) {
            User u = mapToUser(record);
            if (u != null) users.add(u);
        }
        return users;
    }

    public boolean updateUser(User user) throws Exception {
        String[] record = {
                String.valueOf(user.getId()),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone() != null ? user.getPhone() : "",
                user.getAddress() != null ? user.getAddress() : "",
                String.valueOf(user.isActive())
        };
        return FileDatabase.updateRecord(FILE_NAME, String.valueOf(user.getId()), record);
    }

    public boolean deleteUser(int id) throws Exception {
        return FileDatabase.deleteRecord(FILE_NAME, String.valueOf(id));
    }

    public int getUserCount() {
        return FileDatabase.readAll(FILE_NAME).size();
    }

    private User mapToUser(String[] record) {
        if (record.length < 7) return null;
        try {
            int id = FileDatabase.safeParseInt(record[0]);
            if (id <= 0) return null;
            User user = new User();
            user.setId(id);
            user.setName(record[1]);
            user.setEmail(record[2]);
            user.setPassword(record[3]);
            user.setPhone(record[4]);
            user.setAddress(record[5]);
            user.setActive(Boolean.parseBoolean(record[6].trim()));
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
