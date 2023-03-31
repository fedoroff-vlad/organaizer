package com.example.organaizer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlUserRepository implements UserRepository {
    private static final String FILE_NAME = "users.xml";
    private final File file = new File(FILE_NAME);

    @Override
    public List<User> findAll() {
        try {
            XmlMapper mapper = new XmlMapper();
            return mapper.readValue(file, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public User findById(int id) {
        return findAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        List<User> users = findAll();
        user.setId(users.size() + 1);
        users.add(user);
        saveAll(users);
    }

    @Override
    public void update(User user) {
        List<User> users = findAll();
        int index = users.indexOf(findById(user.getId()));
        if (index != -1) {
            users.set(index, user);
            saveAll(users);
        }
    }

    @Override
    public void delete(int id) {
        List<User> users = findAll();
        users.removeIf(user -> user.getId() == id);
        saveAll(users);
    }

    private void saveAll(List<User> users) {
        try {
            XmlMapper mapper = new XmlMapper();
            mapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
