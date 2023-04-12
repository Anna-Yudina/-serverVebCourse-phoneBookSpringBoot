package ru.yudina.springcourse.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yudina.springcourse.converter.ContactDtoToContactConverter;
import ru.yudina.springcourse.converter.ContactToContactDtoConverter;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.model.ContactValidation;
import ru.yudina.springcourse.service.ContactService;
import ru.yudina.springcourse.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/phonebook/api/v1")
public class PhoneBookController {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactService contactService;

    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public PhoneBookController(ContactService contactService,
                               ContactDtoToContactConverter contactDtoToContactConverter,
                               ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactService = contactService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
    }

    @GetMapping("/get/all")
    public List<ContactDto> getAllContacts() {
        logger.info("Вызвали метод getAllContacts() в PhoneBookController");
        List<Contact> contacts = contactService.getAllContacts();

        List<ContactDto> contactsDTO = new ArrayList<>();

        for(Contact contact : contacts){
            contactsDTO.add(contactToContactDtoConverter.convert(contact));
        }
        logger.info("Полученный список контактов: {}", contacts);

        return contactsDTO;
    }

    @PostMapping("/add")
    public ContactValidation addContact(@RequestBody ContactDto contactDTO) {
        logger.info("Вызвали метод addContact с переданным контактом: {}", contactDTO.toString());

        Contact contact = contactDtoToContactConverter.convert(contactDTO);
        ContactValidation contactValidation = contactService.addContact(contact);

        if (contactValidation.isValid()) {
            return contactValidation;
        } else {
            RuntimeException ex = new RuntimeException("Возникла ошибка при добавлении контакта");
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @PostMapping("/delete")
    public void deleteContact(@RequestBody int deletedContactId) {
        logger.info("Вызвали метод deleteContact с переданным контактом: {}", deletedContactId);

        try {
            contactService.deleteContact(deletedContactId);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    @PostMapping("/deleted_checked")
    public void deleteCheckedContacts(@RequestBody ArrayList<Integer> checkedContactsIds) {
        logger.info("Вызвали метод deleteCheckedContacts с переданными id контактов: {}", checkedContactsIds);

        try {
            contactService.deleteCheckedContacts(checkedContactsIds);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }
}
