package ru.yudina.springcourse.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.yudina.springcourse.controllers.PhoneBookController;
import ru.yudina.springcourse.model.Contact;

import java.util.List;
import java.util.Random;

@Component
public class ContactScheduler {
    private final PhoneBookController phoneBookController;
    private static final Logger logger = LoggerFactory.getLogger(ContactScheduler.class);

    public ContactScheduler(PhoneBookController phoneBookController) {
        this.phoneBookController = phoneBookController;
    }

    @Scheduled(fixedDelay = 10000)
    public void deleteRandomContactBySchedule() {
        List<Contact> contacts = phoneBookController.getAllContacts();

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
        phoneBookController.deleteContact(deletedContact);
    }
}
