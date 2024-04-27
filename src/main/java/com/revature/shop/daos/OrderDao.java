package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.Order;
import com.revature.shop.utils.ConnectionFactory;

public class OrderDao implements CrudDao<Order> {

    @Override
    public Order save(Order obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO orders (id, pending, payment_method, user_id) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getPending());
            ps.setString(3, obj.getPaymentMethod());
            ps.setString(4, obj.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public Order update(Order obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "UPDATE orders SET pending = ?, payment_method = ? WHERE id = ?")) {
            ps.setString(1, obj.getPending());
            ps.setString(2, obj.getPaymentMethod());
            ps.setString(3, obj.getId());

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order cart = new Order();
                cart.setId(rs.getString("id"));
                cart.setPending(rs.getString("pending"));
                cart.setPaymentMethod(rs.getString("payment_method"));
                cart.setUserId(rs.getString("user_id"));
                cart.setCreated_time(rs.getString("created_time"));
                orders.add(cart);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public Order findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
