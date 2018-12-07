package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryProductRepo implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Product save(final Product product) {
        products.putIfAbsent(product.getId(), product);
        return product;
    }

    public Optional<Product> update(final Product product) {
        if (!products.containsKey(product.getId())) {
            return Optional.empty();
        }
        products.put(product.getId(), product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> findById(final String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findAll(final Sort sort) {
        return null;
    }

    @Override
    public Page<Product> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public List<Product> findAllById(final Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(final String s) {

    }

    @Override
    public void delete(final Product entity) {

    }

    @Override
    public void deleteAll(final Iterable<? extends Product> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Product> List<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Product> S saveAndFlush(final S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(final Iterable<Product> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Product getOne(final String s) {
        return null;
    }

    @Override
    public <S extends Product> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Product> List<S> findAll(final Example<S> example) {
        return null;
    }

    @Override
    public <S extends Product> List<S> findAll(final Example<S> example, final Sort sort) {
        return null;
    }

    @Override
    public <S extends Product> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Product> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Product> boolean exists(final Example<S> example) {
        return false;
    }
}
