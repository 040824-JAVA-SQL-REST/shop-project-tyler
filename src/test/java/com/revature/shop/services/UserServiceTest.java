package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.revature.shop.daos.UserDao;
import com.revature.shop.models.Role;
import com.revature.shop.models.User;
import com.revature.shop.utils.custom_exceptions.ResourceNotFoundException;

public class UserServiceTest {
    private UserService userService;
    private RoleService roleService;
    private UserDao userDao;

    @Before
    public void setup() {
        roleService = Mockito.mock(RoleService.class);
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao, roleService);
    }

    @Test
    public void isUniqueEmailShouldReturnFalseIfEmailIsNotUnique() {
        String notUniqueEmail = "User1@test.com";
        List<User> mockedUsers = List.of(
                new User("User1@test.com", "p@2sWord", "User One"),
                new User("User2@test.com", "p@2sWord", "User Two"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        boolean actual = userService.isUniqueEmail(notUniqueEmail);

        assertFalse(actual);
    }

    @Test
    public void IsUniqueEmailShouldReturnTrueIfEmailIsUnique() {
        String UniqueEmail = "User3@test.com";
        List<User> mockedUsers = List.of(
                new User("User1@test.com", "p@2sWord", "User One"),
                new User("User2@test.com", "p@2sWord", "User Two"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        boolean actual = userService.isUniqueEmail(UniqueEmail);

        assertTrue(actual);
    }

    @Test
    public void isValidEmailShouldReturnTrueIfEmailIsValid() {
        String validEmail = "user1@test.com";

        boolean actual = userService.isValidEmail(validEmail);

        assertTrue(actual);
    }

    @Test
    public void isvalidEmailShouldReturnFalseIfEmailIsInvalid() {
        String invalidEmail = "user1";

        boolean actual = userService.isValidEmail(invalidEmail);

        assertFalse(actual);
    }

    @Test
    public void IsValidPasswordShouldReturnTrueIfPasswordIsValid() {
        String validPassword = "p@2sWord";

        boolean actual = userService.isValidPassword(validPassword);

        assertTrue(actual);
    }

    @Test
    public void IsValidPasswordShouldReturnFalseIfPasswordIsInvalid() {
        String invalidPassword = "pwd";

        boolean actual = userService.isValidPassword(invalidPassword);

        assertFalse(actual);
    }

    @Test
    public void isValidFirstNameShouldReturnTrueIfFirstNameIsValid() {
        String validFirstName = "Test";

        boolean actual = userService.isValidFirstName(validFirstName);

        assertTrue(actual);
    }

    @Test
    public void isValidFirstNameShouldReturnFalseIfFirstNameIsInvalid() {
        String invalidFirstName = null;

        boolean actual = userService.isValidFirstName(invalidFirstName);

        assertFalse(actual);
    }

    @Test
    public void isValidLastNameShouldReturnTrueIfLastNameIsValid() {
        String validLastName = "Test";

        boolean actual = userService.isValidLastName(validLastName);

        assertTrue(actual);
    }

    @Test
    public void isValidLastNameShouldReturnFalseIfLastNameIsInvalid() {
        String invalidLastName = null;

        boolean actual = userService.isValidLastName(invalidLastName);

        assertFalse(actual);
    }

    @Test
    public void loginShouldReturnUserIfUserIsInDatabase() {
        String email = "User2@test.com";
        String password = "p@2sWord";
        List<User> mockedUsers = List.of(
                new User("User1@test.com", "p@2sWord", "User One"),
                new User("User2@test.com", "p@2sWord", "User Two"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);
        try (MockedStatic<BCrypt> mockedBcrypt = Mockito.mockStatic(BCrypt.class)) {
            mockedBcrypt.when(() -> BCrypt.checkpw(password, mockedUsers.get(1).getPassword())).thenReturn(true);
            Optional<User> foundUser = userService.login(email, password);
            User user = foundUser.get();

            assertTrue(foundUser.isPresent());
            assertEquals("User2@test.com", user.getEmail());
        }
    }

    @Test
    public void loginShouldReturnNullIfUserIsNotInDatabase() {
        String email = "User3@test.com";
        String password = "p@2sWord";
        List<User> mockedUsers = List.of(
                new User("User1@test.com", "p@2sWord", "User One"),
                new User("User2@test.com", "p@2sWord", "User Two"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);
        try (MockedStatic<BCrypt> mockedBcrypt = Mockito.mockStatic(BCrypt.class)) {
            mockedBcrypt.when(() -> BCrypt.checkpw(password, mockedUsers.get(1).getPassword())).thenReturn(true);
            Optional<User> foundUser = userService.login(email, password);

            assertTrue(foundUser.isEmpty());
        }
    }

    @Test
    public void loginShouldReturnNullIfPasswordIsIncorrect() {
        String email = "user1@test.com";
        String incorrectPassword = "pwd";
        List<User> mockedUsers = List.of(
                new User("User1@test.com", "p@2sWord", "User One"),
                new User("User2@test.com", "p@2sWord", "User Two"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);
        try (MockedStatic<BCrypt> mockedBcrypt = Mockito.mockStatic(BCrypt.class)) {
            mockedBcrypt.when(() -> BCrypt.checkpw(incorrectPassword, mockedUsers.get(1).getPassword()))
                    .thenReturn(true);
            Optional<User> foundUser = userService.login(email, incorrectPassword);

            assertTrue(foundUser.isEmpty());
        }
    }

    @Test
    public void saveShouldReturnUserIfEmailAndPasswordAreCorrect() {
        User correctUser = new User("User1@test.com", "p@2sWord", "User One");

        String mockedRoleId = "1";
        Mockito.when(roleService.getRoleIdByName("DEFAULT")).thenReturn(mockedRoleId);

        User mockedUser = new User("1", "User1@test.com", "p@2sWord", "User One", "1");
        Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(mockedUser);

        User actual = userService.save(correctUser);

        assertEquals("1", actual.getId());
        assertEquals("User1@test.com", actual.getEmail());
        assertEquals("1", actual.getRoleId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveShouldReturnExceptionIfDefaultRoleIdNotFound() {
        User correctUser = new User("1", "User1@test.com", "p@2sWord", "User One", "1");
        Mockito.when(roleService.getRoleIdByName("DEFAULT")).thenReturn(null);

        userService.save(correctUser);
    }

    @Test
    public void authenticateUserShouldReturnTrueIfUserExistsAndHasTheCorrectRole() {
        User correctUser = new User("1", "User1@test.com", "p@2sWord", "User One", "1");
        String roleName = "DEFAULT";

        List<User> mockedUsers = List.of(
                new User("1", "User1@test.com", "p@2sWord", "User One", "1"),
                new User("2", "User2@test.com", "p@2sWord", "User Two", "2"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        List<Role> mockedRoles = List.of(
                new Role("1", "DEFUALT"),
                new Role("2", "ADMIN"));
        Mockito.when(roleService.getRoleIdByName(roleName)).thenReturn(mockedRoles.get(0).getId());

        boolean actual = userService.authenticateUser(correctUser, roleName);

        assertTrue(actual);
    }

    @Test
    public void authenticateUserShouldReturnFalseIfUserExistsAndHasTheIncorrectRole() {
        User correctUser = new User("1", "User1@test.com", "p@2sWord", "User One", "1");
        String roleName = "DEFAULT";

        List<User> mockedUsers = List.of(
                new User("1", "User1@test.com", "p@2sWord", "User One", "2"),
                new User("2", "User2@test.com", "p@2sWord", "User Two", "1"));
        Mockito.when(userDao.findAll()).thenReturn(mockedUsers);

        List<Role> mockedRoles = List.of(
                new Role("1", "DEFUALT"),
                new Role("2", "ADMIN"));
        Mockito.when(roleService.getRoleIdByName(roleName)).thenReturn(mockedRoles.get(0).getId());

        boolean actual = userService.authenticateUser(correctUser, roleName);

        assertFalse(actual);
    }
}
