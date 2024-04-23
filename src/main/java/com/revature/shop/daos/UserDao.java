package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.Role;
import com.revature.shop.models.User;
import com.revature.shop.utils.ConnectionFactory;

public class UserDao implements CrudDao<User> {

    @Override
    public User save(User obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO users (id, email, password, full_name, role_id) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());
            ps.setString(4, obj.getFullName());
            ps.setString(5, obj.getRoleId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties file");
        }

        return obj;
    }

    @Override
    public User update(User obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRoleId(rs.getString("role_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return users;
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public Role getUserRoleByUser(User user) {
        Role role = new Role();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT " +
                        "u.id, " +
                        "r.id as role_id, " +
                        "r.name as role_name " +
                        "FROM users u " +
                        "JOIN roles r on r.id = u.role_id " +
                        "WHERE r.id = ?");) {
            ps.setString(1, user.getRoleId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                role.setId(rs.getString("role_id"));
                role.setName(rs.getString("role_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
        return role;
    }

}
