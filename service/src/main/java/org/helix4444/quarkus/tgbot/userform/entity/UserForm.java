package org.helix4444.quarkus.tgbot.userform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_form_t")
public class UserForm {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chat_id", nullable = false, updatable = false, unique = true)
    private Long chatId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

}
