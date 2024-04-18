package com.revature.shop.services;

import java.util.List;

import com.revature.shop.daos.UserDao;
import com.revature.shop.models.User;
import com.revature.shop.utils.custom_exceptions.ResourceNotFoundException;

public class UserService {
    private final UserDao userDao;
    private final RoleService roleService;

    public UserService(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    public boolean isUniqueUsername(String username) {
        List<User> users = userDao.findAll();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }

    public void saveUser(User user) {
        String defaultId = roleService.getRoleIdByName("DEFAULT");
        if (defaultId == null || defaultId.isEmpty()) {
            throw new ResourceNotFoundException("DEFAULT role not found");
        }

        user.setRoleId(defaultId);
        userDao.save(user);
    }

    public User getUserByUsername(String username) {
        return userDao.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .get();
    }
}
