package com.revature.shop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.revature.shop.daos.CategoryDao;
import com.revature.shop.models.Category;

public class CategoryServiceTest {
    private CategoryService categoryService;
    private CategoryDao categoryDao;

    @Before
    public void setup() {
        categoryDao = Mockito.mock(CategoryDao.class);
        categoryService = new CategoryService(categoryDao);
    }

    @Test
    public void findAllShouldReturnAllCategories() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        List<Category> actual = categoryService.findAll();

        assertEquals(actual, mockedCategories);
    }

    @Test
    public void findCategoryByNameShouldReturnCategoryWIthValidName() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        Optional<Category> actual = categoryService.findCategoryByName("cat1");

        assertEquals(actual.get(), mockedCategories.get(0));
    }

    @Test
    public void findCategoryByNameShouldReturnEmptyOptionalWithInvalidName() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        Optional<Category> actual = categoryService.findCategoryByName("cat5");

        assertTrue(actual.isEmpty());
    }

    @Test
    public void isUniqueNameShouldReturnTrueIfNameIsUnique() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        boolean actual = categoryService.isUniqueName("fwea");

        assertTrue(actual);
    }

    @Test
    public void isUniqueNameShouldReturnFalseIfNameIsNotUnique() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        boolean actual = categoryService.isUniqueName("cat1");

        assertFalse(actual);
    }

    @Test
    public void isValidNameShoudlReturnTrueIfNameIsValid() {
        boolean actual = categoryService.isValidName("cat1");

        assertTrue(actual);
    }

    @Test
    public void isValidNameShoudlReturnFalseIfNameIsNotValid() {
        boolean actual = categoryService.isValidName("");

        assertFalse(actual);
    }

    @Test
    public void isValidIdShoudlReturnTrueIfIdIsValid() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        boolean actual = categoryService.isValidId("1");
        assertTrue(actual);
    }

    @Test
    public void isValidIdShoudlReturnFalseIfIdIsNotValid() {
        List<Category> mockedCategories = List.of(
                new Category("1", "cat1"),
                new Category("2", "cat2"));
        Mockito.when(categoryDao.findAll()).thenReturn(mockedCategories);

        boolean actual = categoryService.isValidId("10");

        assertFalse(actual);
    }
}
