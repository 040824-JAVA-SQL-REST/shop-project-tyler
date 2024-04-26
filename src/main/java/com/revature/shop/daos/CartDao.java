package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.Cart;
import com.revature.shop.utils.ConnectionFactory;

public class CartDao implements CrudDao<Cart> {

    @Override
    public Cart save(Cart obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO carts (id, user_id) VALUES (?, ?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public Cart update(Cart obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> carts = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM carts");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getString("id"));
                cart.setUserId(rs.getString("user_id"));
                carts.add(cart);
            }
            return carts;
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public Cart findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}