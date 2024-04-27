package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.RoleDao;
import com.revature.shop.models.Role;

public class RoleServiceTest {
    private RoleService roleService;
    private RoleDao roleDao;

    @Before
    public void setup() {
        roleDao = Mockito.mock(RoleDao.class);
        roleService = new RoleService(roleDao);
    }

    @Test
    public void getRoleIdByNameShouldReturnRoleIdWithValidName() {
        List<Role> mockedRoles = List.of(
                new Role("1", "role1"),
                new Role("2", "role2"));
        Mockito.when(roleDao.findAll()).thenReturn(mockedRoles);

        String actual = roleService.getRoleIdByName("role1");

        assertEquals(actual, "1");
    }

}
