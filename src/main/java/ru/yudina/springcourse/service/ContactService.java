package ru.yudina.springcourse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yudina.springcourse.controllers.PhoneBookController;
import ru.yudina.springcourse.dao.ContactDaoImpl;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.model.ContactValidation;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private final ContactDaoImpl contactDaoImpl;

    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    public ContactService(ContactDaoImpl contactDaoImpl) {
        this.contactDaoImpl = contactDaoImpl;
    }

    private boolean isExistContactWithPhone(String phone) {
        List<Contact> contactList = contactDaoImpl.getAllContacts();
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
        contactDaoImpl.add(contact);
        return contactValidation;
    }

    public List<Contact> getAllContacts() {
        logger.info("Вызвали метод getAllContacts() в ContactService");
        return contactDaoImpl.getAllContacts();
    }

    public void deleteContact(int deletedContactId) {
        contactDaoImpl.deleteContact(deletedContactId);
    }

    public void deleteCheckedContacts(ArrayList<Integer> checkedContactsIds) {
        contactDaoImpl.deleteCheckedContacts(checkedContactsIds);
    }
}
