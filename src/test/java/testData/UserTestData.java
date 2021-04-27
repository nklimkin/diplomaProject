package testData;

import model.Status;
import model.User;
import model.UserRoles;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class UserTestData {

    public static final int USER1 = 100000;
    public static final int USER2 = 100001;
    public static final int ADMIN1 = 100002;
    public static final int NOT_FOUND = 1;
    public static final int NEW = 100014;

    public static User user1 = new User(100000, "user1", "user1_surName", "user1@email.ru",
            "user1_pass", Status.ACTIVE,  UserRoles.ADMIN, UserRoles.USER);

    public static User user2 = new User(100001, "user2", "user2_surName", "user2@email.ru",
            "user2_pass", Status.ACTIVE, UserRoles.USER);

    public static User admin1 = new User(100002, "admin1", "admin1_surName", "admin1@email.ru",
            "admin1_pass", Status.ACTIVE, UserRoles.ADMIN);

    public static User newUser = new User(100014, "newUser", "newUser_surname", "newuser@email.ru", "newUser_pass", Status.ACTIVE,
            UserRoles.USER);

    public static User updated = new User(100000, "update", "update_surName", "updated@email.ru", "updUser_pass", Status.ACTIVE,
            UserRoles.USER, UserRoles.ADMIN);

    public static List<User> users = List.of(user1, user2, admin1);

    public static User getNew(){
        return new User(null, "newUser", "newUser_surname", "newuser@email.ru", "newUser_pass", Status.ACTIVE, UserRoles.USER);
    }

    public static User getUpdated(){
        User updated = new User(user1);
        updated.setName("update");
        updated.setSurname("update_surName");
        updated.setEmail("updated@email.ru");
        updated.setPassword("updUser_pass");
        return updated;
    }


}
