package com.pvdong.lesson3.repository;

import com.pvdong.lesson3.entity.Horse;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class HorseRepositoryBaseImpl implements HorseRepositoryBase {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Horse> customFilterMethod(Integer trainerId, Integer year) {
        TypedQuery<Horse> query = entityManager.createQuery("select h from Horse h join HorseAccount hc on h.id = hc.horse_id join Account a on hc.account_id = a.id join Trainer t on a.id = t.account.id where t.id = :id and year(h.foaled) = :year", Horse.class);
        query.setParameter("id", trainerId).setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public List<Horse> getHorseListByTrainerId(Integer trainerId) {
        TypedQuery<Horse> query = entityManager.createQuery("select h from Horse h join HorseAccount hc on h.id = hc.horse_id join Account a on hc.account_id = a.id join Trainer t on a.id = t.account.id where t.id = :id", Horse.class);
        query.setParameter("id", trainerId);
        return query.getResultList();
    }
}
