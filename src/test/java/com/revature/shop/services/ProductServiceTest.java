package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.ProductDao;
import com.revature.shop.models.Product;

public class ProductServiceTest {
    private ProductService productService;
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(productDao);
    }

    @Test
    public void isUniqueNameShouldReturnTrueIfNameIsUnique() {
        String uniqueName = "testProdcutUnique";
        List<Product> mockedProducts = List.of(
                new Product("testProduct1", "prod1 desc", 1.0f),
                new Product("testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.findAll()).thenReturn(mockedProducts);

        boolean actual = productService.isUniqueName(uniqueName);

        assertTrue(actual);
    }

    @Test
    public void isUniqueNameShouldReturnFalseIfNameIsNotUnique() {
        String notUniqueName = "testProduct1";
        List<Product> mockedProducts = List.of(
                new Product("testProduct1", "prod1 desc", 1.0f),
                new Product("testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.findAll()).thenReturn(mockedProducts);

        boolean actual = productService.isUniqueName(notUniqueName);

        assertFalse(actual);
    }

    @Test
    public void isValidNameShouldReturnTrueIfNameIsValid() {
        String validName = "TestProduct";

        boolean actual = productService.isValidName(validName);

        assertTrue(actual);
    }

    @Test
    public void isValidNameShouldReturnFalseIfNameIsInvalid() {
        String invalidName = "abc";

        boolean actual = productService.isValidName(invalidName);

        assertFalse(actual);
    }

    @Test
    public void isValidPriceShouldReturnTrueIfPriceIsValid() {
        float validPrice = 9.98f;

        boolean actual = productService.isValidPrice(validPrice);

        assertTrue(actual);
    }

    @Test
    public void isValidPriceShouldReturnFalseIfPriceIsInvalid() {
        float invalidPrice = 0.0f;

        boolean actual = productService.isValidPrice(invalidPrice);

        assertFalse(actual);
    }

    @Test
    public void getProductByIdShouldReturnProductIfCorrectIdIsGiven() {
        String productId = "1";
        Product mockedProduct = new Product("1", "testProduct1", "prod1 desc", 1.0f);
        List<Product> mockedProducts = List.of(
                new Product("1", "testProduct1", "prod1 desc", 1.0f),
                new Product("2", "testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.findAll()).thenReturn(mockedProducts);

        Optional<Product> optProduct = productService.getProductById(productId);
        Product product = optProduct.get();

        assertTrue(optProduct.isPresent());
        assertEquals(mockedProduct.getName(), product.getName());
    }

    @Test
    public void getProductByIdShouldReturnNullIfIncorrectIdIsGiven() {
        String productId = "3";
        Product mockedProduct = new Product("2", "testProduct2", "prod2 desc", 4.0f);
        List<Product> mockedProducts = List.of(
                new Product("1", "testProduct1", "prod1 desc", 1.0f),
                new Product("2", "testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.findAll()).thenReturn(mockedProducts);

        Optional<Product> optProduct = productService.getProductById(productId);

        assertTrue(optProduct.isEmpty());
    }

    @Test
    public void getAllProductsShouldReturnAllProducts() {
        List<Product> mockedProducts = List.of(
                new Product("1", "testProduct1", "prod1 desc", 1.0f),
                new Product("2", "testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.findAll()).thenReturn(mockedProducts);

        assertEquals(mockedProducts, productService.getAllProducts());
    }

    @Test
    public void updateShouldChangeProductWhenIdIsCorrect() {
        Product updatedProduct = new Product("1", "newTestProduct1", "newTestProd desc", 1.0f);
        List<Product> mockedProducts = List.of(
                new Product("1", "testProduct1", "prod1 desc", 1.0f),
                new Product("2", "testProduct2", "prod2 desc", 98.0f));
        Mockito.when(productDao.update(updatedProduct))
                .thenReturn(new Product("1", "newTestProduct1", "newTestProd desc", 1.0f));

        Product actual = productService.update(updatedProduct);

        assertEquals(updatedProduct.getName(), actual.getName());
        assertEquals(updatedProduct.getDescription(), actual.getDescription());
    }
}
