package ru.yudina.springcourse.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ru.yudina.springcourse.controllers.PhoneBookController;
import ru.yudina.springcourse.model.Contact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> clazz;

    public GenericDaoImpl(Class<T> type) {
        this.clazz = type;
    }

    @Transactional
    @Override
    public void create(T obj) {
        logger.info("Вызываем метод create(), пришел obj " + obj.toString());
        entityManager.persist(obj);
    }

    @Transactional
    @Override
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Transactional
    @Override
    public void remove(int id) {
        Contact contact = entityManager.find(Contact.class, id);
        entityManager.remove(contact);
    }

    @Override
    public T getById(PK id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    @Override
    public List<T> findAll() {
        logger.info("Вызвали метод findAll() в GenericDaoImpl");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        CriteriaQuery<T> select = cq.select(root);
        TypedQuery<T> q = entityManager.createQuery(select);
        return q.getResultList();
    }

    public void removeSelected(ArrayList<Integer> ids){
        Query qDeleteVisitors = entityManager.createQuery("delete from Contact contact where contact.id in (?1)");
        qDeleteVisitors.setParameter(1, ids);
        qDeleteVisitors.executeUpdate();
    }
}
