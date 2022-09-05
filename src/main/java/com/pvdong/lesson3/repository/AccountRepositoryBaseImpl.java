package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Account;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AccountRepositoryBaseImpl implements AccountRepositoryBase {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account getAccountByHorseId(Integer horseId) {
        TypedQuery<Account> query = entityManager.createQuery("select a from Account a join HorseAccount hc on a.id = hc.account_id join Horse h on hc.horse_id = h.id where h.id = :id", Account.class);
        query.setParameter("id", horseId);
        List<Account> accounts = query.getResultList();
        return CollectionUtils.isEmpty(accounts) ? null : accounts.get(0);
    }
}
