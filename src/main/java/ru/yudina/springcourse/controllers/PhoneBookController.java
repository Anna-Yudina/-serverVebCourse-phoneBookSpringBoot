package ru.yudina.springcourse.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.model.ContactValidation;
import ru.yudina.springcourse.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        logger.info("called method getAllContacts");
        return contactService.getAllContacts();
    }

    @PostMapping("/addContact")
    public ContactValidation addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @PostMapping("/deleteContact")
    public void deleteContact(@RequestBody Contact contact) {
        contactService.deleteContact(contact);
    }

    @PostMapping("/deleteCheckedContacts")
    public void deleteCheckedContacts(@RequestBody ArrayList<Integer> checkedContactsIds) {
        contactService.deleteCheckedContacts(checkedContactsIds);
    }
}
