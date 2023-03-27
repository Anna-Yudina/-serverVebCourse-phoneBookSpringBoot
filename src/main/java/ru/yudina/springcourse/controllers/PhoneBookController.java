package ru.yudina.springcourse.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.model.ContactValidation;
import ru.yudina.springcourse.service.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;

    public PhoneBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/getAllContacts")
    public List<Contact> getAllContacts() {
        logger.info("Вызвали метод getAllContacts()");
        List<Contact> contacts = contactService.getAllContacts();

        if (contacts == null) {
            RuntimeException ex = new RuntimeException("Список контактов пуст");
            logger.error(ex.getMessage());
            throw ex;
        }

        return contacts;
    }

    @PostMapping("/addContact")
    public ContactValidation addContact(@RequestBody Contact contact) {
        logger.info("Вызвали метод addContact с переданным контактом: {}", contact.toString());

        ContactValidation contactValidation = contactService.addContact(contact);

        if (contactValidation.isValid()) {
            return contactValidation;
        } else {
            RuntimeException ex = new RuntimeException("Возникла ошибка при добавлении контакта");
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @PostMapping("/deleteContact")
    public void deleteContact(@RequestBody Contact contact) {
        logger.info("Вызвали метод deleteContact с переданным контактом: {}", contact.toString());

        try {
            contactService.deleteContact(contact);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    @PostMapping("/deleteCheckedContacts")
    public void deleteCheckedContacts(@RequestBody ArrayList<Integer> checkedContactsIds) {
        logger.info("Вызвали метод deleteCheckedContacts с переданными id контактов: {}", checkedContactsIds);

        try {
            contactService.deleteCheckedContacts(checkedContactsIds);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void deleteRandomContactBySchedule() {
        List<Contact> contacts = getAllContacts();

        if (contacts.size() == 0){
            RuntimeException ex = new RuntimeException("Список контактов пуст, нечего удалять");
            logger.error(ex.getMessage());
            throw ex;
        }

        Random random = new Random();
        int index = random.nextInt(contacts.size());
        logger.debug("Random index: {}", index);
        Contact deletedContact = contacts.get(index);

        logger.info("Вызвали метод deleteRandomContactBySchedule по расписанию, удаляемый контакт: {}", deletedContact.toString());
        deleteContact(deletedContact);
    }
}
