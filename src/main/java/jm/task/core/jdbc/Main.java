package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args)  {
        UserServiceImpl userService = new UserServiceImpl();
        userService.saveUser("andrw", "me", (byte)24);

    }
}
