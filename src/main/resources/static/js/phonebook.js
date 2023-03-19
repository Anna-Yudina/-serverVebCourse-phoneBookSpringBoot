function Contact(firstName, lastName, phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.checked = false;
    this.shown = true;
}

new Vue({
    el: "#app",
    data: {
        validation: false,
        serverValidation: false,
        isChecked: false,
        firstName: "",
        lastName: "",
        phone: "",
        contacts: [],
        serverError: "",
        contactToDelete: null
    },
    methods: {
        contactToString: function (contact) {
            var note = "(";
            note += contact.firstName + ", ";
            note += contact.lastName + ", ";
            note += contact.phone;
            note += ")";
            return note;
        },

        convertContactList: function (contactListFromServer) {
            return contactListFromServer.map(function (contact, i) {
                return {
                    id: contact.id,
                    firstName: contact.firstName,
                    lastName: contact.lastName,
                    phone: contact.phone,
                    checked: false,
                    shown: true,
                    number: i + 1
                };
            });
        },

        addContact: function () {
            if (this.hasError) {
                this.validation = true;
                this.serverValidation = false;
                return;
            }

            var self = this;

            var contact = new Contact(this.firstName, this.lastName, this.phone);

            $.ajax({
                type: "POST",
                url: "/phoneBook/rpc/api/v1/addContact",
                contentType: "application/json",
                data: JSON.stringify(contact)
            }).done(function () {
                self.serverValidation = false;
            }).fail(function (ajaxRequest) {
                var contactValidation = JSON.parse(ajaxRequest.responseText);
                self.serverError = contactValidation.error;
                self.serverValidation = true;
            }).always(function () {
                self.loadData();
            });

            self.firstName = "";
            self.lastName = "";
            self.phone = "";
            self.validation = false;
        },

        loadData: function () {
            var self = this;

            $.get("/phoneBook/rpc/api/v1/getAllContacts").done(function (contactListFormServer) {
                self.contacts = self.convertContactList(contactListFormServer);
            });
        },

        deleteContact: function () {
            var self = this;

            $.ajax({
                type: "POST",
                url: "/phoneBook/rpc/api/v1/deleteContact",
                contentType: "application/json",
                data: JSON.stringify(this.contactToDelete)
            }).fail(function () {
                alert("Ошибка при удалении контакта");
            }).always(function () {
                self.loadData();
            });
        },

        showDeleteButtonConfirmDialog(deletedContact) {
            this.contactToDelete = deletedContact;

            new bootstrap.Modal(this.$refs.deleteButtonConfirmDialog).show();
        },

        deleteChecked: function() {
            var checkedContactsIds = this.contacts
                .filter(function (contact) {
                    return contact.isChecked === true;
                })
                .map(function(contact) {
                    return contact.id;
                });

            var self = this;

            $.ajax({
                type: "POST",
                url: "/phoneBook/rpc/api/v1/deleteCheckedContacts",
                contentType: "application/json",
                data: JSON.stringify(checkedContactsIds)
            }).fail(function () {
                alert("Ошибка при удалении выбранных контактов");
            }).always(function () {
                self.loadData();
            });
        },

        showDeleteCheckboxConfirmDialog() {
            new bootstrap.Modal(this.$refs.deleteCheckboxConfirmDialog).show();
        }
    },

    computed: {
        firstNameError: function () {
            if (this.firstName) {
                return {
                    message: "",
                    error: false
                };
            }

            return {
                message: "Поле Имя должно быть заполнено.",
                error: true
            };
        },

        lastNameError: function () {
            if (!this.lastName) {
                return {
                    message: "Поле Фамилия должно быть заполнено.",
                    error: true
                };
            }

            return {
                message: "",
                error: false
            };
        },
        phoneError: function () {
            if (!this.phone) {
                return {
                    message: "Поле Телефон должно быть заполнено.",
                    error: true
                };
            }

            var self = this;

            var sameContact = this.contacts.some(function (c) {
                return c.phone === self.phone;
            });

            if (sameContact) {
                return {
                    message: "Номер телефона не должен дублировать другие номера в телефонной книге.",
                    error: true
                };
            }

            return {
                message: "",
                error: false
            };
        },

        hasError: function () {
            return this.lastNameError.error || this.firstNameError.error || this.phoneError.error;
        }
    },

    created: function () {
        this.loadData();
    }
});
