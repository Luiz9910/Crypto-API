package com.example.crypto.repository;

import com.example.crypto.dto.CoinTransactionDTO;
import com.example.crypto.entity.Coin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoinRepository {

    @Autowired
    private EntityManager entityManager;


    public List<CoinTransactionDTO> getAll() {
        String jpql = "select name, sum(quantity) from Coin c group by c.name";
        TypedQuery<CoinTransactionDTO> query = entityManager.createQuery(jpql, CoinTransactionDTO.class);

        return query.getResultList();
    }

    public List<Coin> getByName(String name) {
        String jpql = "SELECT c from Coin c where c.name like :name";
        TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional
    public Coin insert(Coin coin) {
        entityManager.persist(coin);
        return coin;
    }

    @Transactional
    public Coin update(Coin coin) {
        entityManager.merge(coin);
        return coin;
    }

    @Transactional
    public boolean remove(int id) {
        Coin coin = entityManager.find(Coin.class, id);

        if (coin == null) {
            throw new RuntimeException();
        }

        entityManager.remove(coin);
        return true;
    }
}
