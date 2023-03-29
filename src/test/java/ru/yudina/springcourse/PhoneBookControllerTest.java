package ru.yudina.springcourse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yudina.springcourse.controllers.PhoneBookController;
import ru.yudina.springcourse.dao.ContactDao;
import ru.yudina.springcourse.model.Contact;
import ru.yudina.springcourse.service.ContactService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PhoneBookControllerTest {
    private PhoneBookController phoneBookController;

    @BeforeEach
    void prepare(){
        phoneBookController = new PhoneBookController(new ContactService(new ContactDao()));
    }

    @Test
    void getAllContactsCheckDefaultContactsSize() {
        List<Contact> receivedContacts = phoneBookController.getAllContacts();
        assertThat(receivedContacts).hasSize(3);
    }

    @Test
    void addContact() {
        int sizeListBeforeAdded = phoneBookController.getAllContacts().size();

        Contact contact = new Contact();
        contact.setFirstName("Anna");
        contact.setLastName("Yudina");
        contact.setPhone("777");

        phoneBookController.addContact(contact);
        int sizeListAfterAdded = phoneBookController.getAllContacts().size();

        assertThat(sizeListBeforeAdded + 1).isEqualTo(sizeListAfterAdded);
    }

    @Test
    void deleteContact() {
        List<Contact> contacts = phoneBookController.getAllContacts();
        int contactsListSizeBeforeDeleted = contacts.size();

        Random random = new Random();
        Contact deletedContact = contacts.get(random.nextInt(contacts.size()));

        phoneBookController.deleteContact(deletedContact);

        int contactsListSizeAfterDeleted = phoneBookController.getAllContacts().size();

        assertThat(contactsListSizeAfterDeleted).isEqualTo(contactsListSizeBeforeDeleted - 1);
    }

    @Test
    void deleteCheckedContacts() {
        int deletedContactsCount = 2;
        List<Contact> contacts = phoneBookController.getAllContacts();

        List<Integer> contactsIds = contacts.stream().map(Contact::getId).collect(Collectors.toList());

        Random random = new Random();
        ArrayList<Integer> deletedContactsIds = new ArrayList<>();

        for(int i = 0; i < deletedContactsCount; i++){
            int index = random.nextInt(contactsIds.size());
            deletedContactsIds.add(contactsIds.get(index));
            contactsIds.remove(index);
        }

        phoneBookController.deleteCheckedContacts(deletedContactsIds);

        assertThat(contacts.size() - deletedContactsIds.size())
                .isEqualTo(phoneBookController.getAllContacts().size());
    }
}
