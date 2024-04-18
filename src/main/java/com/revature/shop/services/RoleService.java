package com.revature.shop.services;

import com.revature.shop.daos.RoleDao;

public class RoleService {
    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public String getRoleIdByName(String name) {
        return roleDao.findAll().stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .get().getId();
    }
}
