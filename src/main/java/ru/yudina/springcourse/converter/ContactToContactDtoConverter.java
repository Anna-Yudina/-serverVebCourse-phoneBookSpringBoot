package ru.yudina.springcourse.converter;

import org.springframework.stereotype.Service;
import ru.yudina.springcourse.dto.ContactDto;
import ru.yudina.springcourse.model.Contact;

@Service
public class ContactToContactDtoConverter extends AbstractConverter<Contact, ContactDto> {
    @Override
    public ContactDto convert(Contact contact) {
        ContactDto contactDto = new ContactDto();

        contactDto.setId(contact.getId());
        contactDto.setFirstName(contact.getFirstName());
        contactDto.setLastName(contact.getLastName());
        contactDto.setPhone(contact.getPhone());

        return contactDto;
    }
}
