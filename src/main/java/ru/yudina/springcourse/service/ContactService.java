package ru.yudina.springcourse.service;

import org.springframework.stereotype.Service;
import ru.yudina.springcourse.dao.ContactDao;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.model.ContactValidation;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private final ContactDao contactDao;

    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDao.getAllContacts();
        for (Contact contact : contactList) {
            if (contact.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    private ContactValidation validateContact(Contact contact) {
        ContactValidation contactValidation = new ContactValidation();
        contactValidation.setValid(true);
        if (contact.getFirstName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Имя должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getLastName().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Фамилия должно быть заполнено.");
            return contactValidation;
        }

        if (contact.getPhone().isEmpty()) {
            contactValidation.setValid(false);
            contactValidation.setError("Поле Телефон должно быть заполнено.");
            return contactValidation;
        }

        if (isExistContactWithPhone(contact.getPhone())) {
            contactValidation.setValid(false);
            contactValidation.setError("Номер телефона не должен дублировать другие номера в телефонной книге.");
            return contactValidation;
        }
        return contactValidation;
    }

    public ContactValidation addContact(Contact contact) {
        ContactValidation contactValidation = validateContact(contact);
        contactDao.add(contact);
        return contactValidation;
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public void deleteContact(Contact contact) {
        contactDao.deleteContact(contact);
    }

    public void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds) {
        contactDao.deleteCheckedContacts(checkedContactsIds);
    }
}
