package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.Category;
import com.revature.shop.utils.ConnectionFactory;

public class CategoryDao implements CrudDao<Category> {

    @Override
    public Category save(Category obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO categories (id, name) VALUES (?, ?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public Category update(Category obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Category> findAll() {
        List<Category> carts = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM categories");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                carts.add(category);
            }
            return carts;
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public Category findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
