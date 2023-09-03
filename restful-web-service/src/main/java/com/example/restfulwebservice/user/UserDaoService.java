package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    private static List<Users> users = new ArrayList<>();
    private static int usersCnt = 3;

    static {
        users.add(new Users(1, "Kenneth", new Date(), "pass1", "701010-1111111"));
        users.add(new Users(2, "Alice", new Date(), "pass2", "801010-2222222"));
        users.add(new Users(3, "Elena", new Date(), "pass3", "901010-3333333"));
    }

    public List<Users> findAll() {
        return users;
    }

    public Users save(Users user) {
        if (user.getId() == null) {
            user.setId(++usersCnt);
        }

        users.add(user);
        return user;
    }

    public Users findOne(int id) {
        for (Users user : users) {
            if(user.getId() == id) return user;
        }

        return null;
    }

    public Users deleteById(int id) {
        Iterator<Users> iterator = users.iterator();

        while (iterator.hasNext()){
            Users user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
