package ru.yudina.springcourse.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.yudina.springcourse.controllers.PhoneBookController;
import ru.yudina.springcourse.model.Contact;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {

    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getAllContacts() {
        logger.info("Вызвали метод getAllContacts() в ContactDaoImpl");
        return findAll();
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(clazz);

        Root<Contact> root = cq.from(clazz);

        cq.where(cb.equal(root.get("phone"), phone));

        CriteriaQuery<Contact> select = cq.select(root);
        TypedQuery<Contact> q = entityManager.createQuery(select);

        return q.getResultList();
    }

    @Override
    public void add(Contact contact) {
        create(contact);
    }

    @Override
    public void deleteContact(int deletedContactId) {
        remove(deletedContactId);
    }

    @Override
    public void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds) {
        removeSelected(checkedContactsIds);
    }
}
