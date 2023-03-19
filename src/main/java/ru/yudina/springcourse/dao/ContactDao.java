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
        Contact contact = new Contact();
        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");
        contactList.add(contact);
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
        System.out.println(contactList);
    }

    public void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds) {
        contactList = contactList.stream().filter(c -> !checkedContactsIds.contains(c.getId())).collect(Collectors.toList());
    }
}
