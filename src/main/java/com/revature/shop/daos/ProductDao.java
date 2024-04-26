package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.Product;
import com.revature.shop.utils.ConnectionFactory;

public class ProductDao implements CrudDao<Product> {

    @Override
    public Product save(Product obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO products (id, name, description, price, category_id) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getName());
            ps.setString(3, obj.getDescription());
            ps.setFloat(4, obj.getPrice());
            ps.setString(5, obj.getCategoryId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public Product update(Product obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "UPDATE products SET Name = ?, Description = ?, Price = ?, category_id = ? WHERE id = ?")) {
            ps.setString(1, obj.getName());
            ps.setString(2, obj.getDescription());
            ps.setFloat(3, obj.getPrice());
            ps.setString(4, obj.getCategoryId());
            ps.setString(5, obj.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public boolean delete(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "DELETE FROM products WHERE id = ?")) {
            ps.setString(1, id);

            int numRows = ps.executeUpdate();
            if (numRows == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setCategoryId(rs.getString("category_id"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return products;
    }

    @Override
    public Product findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
