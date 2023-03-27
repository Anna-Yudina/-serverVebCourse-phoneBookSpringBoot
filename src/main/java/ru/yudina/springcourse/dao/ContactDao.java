package ru.yudina.springcourse.dao;

import org.springframework.stereotype.Repository;
import ru.yudina.springcourse.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ContactDao {
    private List<Contact> contactList = new ArrayList<>();
    private static AtomicInteger idSequence = new AtomicInteger(0);

    public ContactDao() {
        contactList.add(new Contact(getNewId(), "Иван", "Иванов", "9123456789"));
        contactList.add(new Contact(getNewId(), "Петр", "Петров", "9123456566"));
        contactList.add(new Contact(getNewId(), "Вася", "Сидоров", "9121116566"));
    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public void add(Contact contact) {
        contact.setId(getNewId());
        contactList.add(contact);
    }

    public void deleteContact(Contact contact) {
        contactList = contactList.stream().filter(c -> c.getId() != contact.getId()).collect(Collectors.toList());
    }

    public void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds) {
        contactList = contactList.stream().filter(c -> !checkedContactsIds.contains(c.getId())).collect(Collectors.toList());
    }
}
