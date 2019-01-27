package com.od.notesmaker.service;

import com.od.notesmaker.entity.User;
import com.od.notesmaker.exception.UserNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static User jan123;

    private static User userUpdate;

    @BeforeClass
    public static void beforeclass() throws ParseException {
        String birthdayDate = "2012-12-11";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(birthdayDate);
        java.sql.Date sqlBirthdayDate = new java.sql.Date(date.getTime());
        jan123 = new User("Jan", "Kowalski", "jan123",
                "$2a$10$FabGp5zLvInm/DAcHtmRc.ws1.tQK7eXamK/mCj/BPCSlB5yDyNH2",
                "jan123@test.com", sqlBirthdayDate);
        jan123.setId((long) 1);

        String birthdayDateUpdate = "2015-02-01";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date2 = sdf2.parse(birthdayDateUpdate);
        java.sql.Date sqlBirthdayDateUpdate = new java.sql.Date(date2.getTime());
        userUpdate = new User("Adam", "Nowak", "adam123",
                "$2a$10$FabGp5zLvInm/DAcHtmRc.ws1.tQK7eXamK/mCj/BPCSlB5yDyNH2",
                "adam123@test.com", sqlBirthdayDateUpdate);
        userUpdate.setId((long) 1);
    }

    @Test
    public void getUserByLogin() {
        User user = userService.getUserByLogin("jan123");
        assertEquals(jan123, user);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserByNotexistingLogin() {
        userService.getUserByLogin("qweasdsdadadf");
    }

    @Test
    public void updateUser() {
        userService.updateUserDetails((long) 1, userUpdate);
        assertEquals(jan123.getId(), userService.getUserByLogin("jan123").getId());
        assertEquals(jan123.getLogin(), userService.getUserByLogin("jan123").getLogin());
        assertEquals(jan123.getPassword(), userService.getUserByLogin("jan123").getPassword());

        assertEquals(userUpdate.getName(), userService.getUserByLogin("jan123").getName());
        assertEquals(userUpdate.getSurname(), userService.getUserByLogin("jan123").getSurname());
        assertEquals(userUpdate.getEmail(), userService.getUserByLogin("jan123").getEmail());
        assertEquals(userUpdate.getBirthDate(), userService.getUserByLogin("jan123").getBirthDate());
    }

    @Test(expected = UserNotFoundException.class)
    public void updateUserWithNotExistingId() {
        userService.updateUserDetails((long) 123123, userUpdate);
    }


    @Test
    public void updateUserPassword() {
        String newPassword = "testPassword";
        userService.updateUserPassword((long) 1, newPassword);
        assertTrue(passwordEncoder.matches(newPassword, userService.getUserByLogin("jan123").getPassword()));
    }


    @Test(expected = UserNotFoundException.class)
    public void updateUserPasswordForUserWithNotExistingId() {
        userService.updateUserPassword((long) 123123, userUpdate.getPassword());
    }
}