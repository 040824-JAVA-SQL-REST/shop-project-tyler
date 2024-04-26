package com.revature.shop.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.shop.models.CartProduct;
import com.revature.shop.utils.ConnectionFactory;

public class CartProductDao implements CrudDao<CartProduct> {

    @Override
    public CartProduct save(CartProduct obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "INSERT INTO cart_products (cart_id, product_id, quantity, cost) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, obj.getCartId());
            ps.setString(2, obj.getProductId());
            ps.setInt(3, obj.getQuantity());
            ps.setFloat(4, obj.getCost());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }

        return obj;
    }

    @Override
    public CartProduct update(CartProduct obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "UPDATE cart_products SET quantity = ? WHERE cart_id = ? AND product_id = ?")) {
            ps.setInt(1, obj.getQuantity());
            ps.setString(2, obj.getCartId());
            ps.setString(3, obj.getProductId());
            // the trigger in the databse should auto-update the cost
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

    public boolean deleteByCartIdAndProductId(String cartId, String productId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "DELETE FROM cart_products WHERE cart_id = ? AND product_id = ?")) {
            ps.setString(1, cartId);
            ps.setString(2, productId);

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
    public List<CartProduct> findAll() {
        List<CartProduct> cartProducts = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM cart_products");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CartProduct cartProduct = new CartProduct();
                cartProduct.setCartId(rs.getString("cart_id"));
                cartProduct.setProductId(rs.getString("product_id"));
                cartProduct.setQuantity(rs.getInt("quantity"));
                cartProduct.setCost(rs.getFloat("cost"));
                cartProducts.add(cartProduct);
            }
            return cartProducts;
        } catch (SQLException e) {
            throw new RuntimeException("SQL EXCEPTION!\n" + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

    @Override
    public CartProduct findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public float getCartPrice(String cartId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
                PreparedStatement ps = conn
                        .prepareStatement(
                                "SELECT SUM(cost) as total_cost FROM cart_products WHERE cart_id = ?")) {
            ps.setString(1, cartId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            float totalCost = rs.getFloat("total_cost");
            return totalCost;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database " + e);
        } catch (IOException e) {
            throw new RuntimeException("IO EXCEPTION!\n" + e);
        }
    }

}
