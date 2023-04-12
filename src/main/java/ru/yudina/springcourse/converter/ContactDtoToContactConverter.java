package ru.yudina.springcourse.converter;

import org.springframework.stereotype.Service;
import ru.yudina.springcourse.dto.ContactDto;
import ru.yudina.springcourse.model.Contact;

@Service
public class ContactDtoToContactConverter extends AbstractConverter<ContactDto, Contact> {
    @Override
    public Contact convert(ContactDto contactDto) {
        Contact contact = new Contact();

        contact.setId(contactDto.getId());
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setPhone(contactDto.getPhone());

        return contact;
    }
}
