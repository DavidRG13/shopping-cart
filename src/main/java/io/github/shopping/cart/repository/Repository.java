package io.github.shopping.cart.repository;

import java.util.Optional;

public interface Repository<K, V> {

    V save(V v);

    Optional<V> update(V v);

    Optional<V> findById(K k);
}
