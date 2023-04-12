package ru.yudina.springcourse.dao;

import ru.yudina.springcourse.model.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long> {
    List<Contact> getAllContacts();

    void add(Contact contact);

    void deleteContact(int deletedContactId);

    void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds);

    List<Contact> findByPhone(String phone);
}
