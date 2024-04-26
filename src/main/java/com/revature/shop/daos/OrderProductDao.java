package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.OrderProduct;
import com.revature.shop.utils.ConnectionFactory;

public class OrderProductDao implements CrudDao<OrderProduct> {

    @Override
    public OrderProduct save(OrderProduct obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO order_products (order_id, product_id, quantity) VALUES (?, ?, ?)")) {
            ps.setString(1, obj.getOrderId());
            ps.setString(2, obj.getProductId());
            ps.setInt(3, obj.getQuantity());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public OrderProduct update(OrderProduct obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<OrderProduct> findAll() {
        List<OrderProduct> orderProducts = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM order_products");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderId(rs.getString("order_id"));
                orderProduct.setProductId(rs.getString("product_id"));
                orderProduct.setQuantity(rs.getInt("quantity"));
                orderProducts.add(orderProduct);
            }
            return orderProducts;
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public OrderProduct findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
