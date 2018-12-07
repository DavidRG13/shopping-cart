package io.github.shopping.cart.repository;

import io.github.shopping.cart.repository.model.PlacedOrder;
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
public class InMemoryPlacedOrderRepo implements PlacedOrderRepository {

    private final Map<String, PlacedOrder> placedOrders = new HashMap<>();

    @Override
    public PlacedOrder save(final PlacedOrder placedOrder) {
        placedOrders.putIfAbsent(placedOrder.getId(), placedOrder);
        return placedOrder;
    }

    @Override
    public Optional<PlacedOrder> findById(final String productId) {
        return Optional.ofNullable(placedOrders.get(productId));
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public List<PlacedOrder> findAll() {
        return null;
    }

    @Override
    public List<PlacedOrder> findAll(final Sort sort) {
        return null;
    }

    @Override
    public Page<PlacedOrder> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public List<PlacedOrder> findAllById(final Iterable<String> strings) {
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
    public void delete(final PlacedOrder entity) {

    }

    @Override
    public void deleteAll(final Iterable<? extends PlacedOrder> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends PlacedOrder> List<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PlacedOrder> S saveAndFlush(final S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(final Iterable<PlacedOrder> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PlacedOrder getOne(final String s) {
        return null;
    }

    @Override
    public <S extends PlacedOrder> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PlacedOrder> List<S> findAll(final Example<S> example) {
        return null;
    }

    @Override
    public <S extends PlacedOrder> List<S> findAll(final Example<S> example, final Sort sort) {
        return null;
    }

    @Override
    public <S extends PlacedOrder> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PlacedOrder> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PlacedOrder> boolean exists(final Example<S> example) {
        return false;
    }
}
