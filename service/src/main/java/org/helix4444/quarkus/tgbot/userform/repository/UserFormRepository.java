package org.helix4444.quarkus.tgbot.userform.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import org.helix4444.quarkus.tgbot.userform.entity.UserForm;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserFormRepository
        implements PanacheRepository<UserForm> {

    @Transactional
    public void saveUserForm(UserForm userForm) {
        this.persist(userForm);
    }

    @Transactional
    public UserForm getUserFormByChatId(Long chatId) {
        return this.find("chatId", chatId).firstResult();
    }

    @Transactional
    public void updateUserFormLastName(String newLastName, Long chatId) {
        this.update("lastName = ?1 WHERE chatId = ?2", newLastName, chatId);
    }

    @Transactional
    public void updateUserFormFirstName(String newFirstName, Long chatId) {
        this.update("firstName = ?1 WHERE chatId = ?2", newFirstName, chatId);
    }

    @Transactional
    public void updateUserFormMiddleName(String newMiddleName, Long chatId) {
        this.update("middleName = ?1 WHERE chatId = ?2", newMiddleName, chatId);
    }

}
