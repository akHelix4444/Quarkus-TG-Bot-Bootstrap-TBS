package org.helix4444.quarkus.tgbot.userform.flow;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormFinishHandler;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormFirstNameHandler;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormLastNameHandler;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormMiddleNameHandler;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormStartHandler;
import org.helix4444.quarkus.tgbot.userform.handler.UserFormHandler;

@ApplicationScoped
public class UserFormWorkflow {

    private UserFormHandler userFormHandler;

    @PostConstruct
    protected void registerUserFormWorkflow() {
        var finishHandler = new UserFormFinishHandler(null);
        var middleNameHandler = new UserFormMiddleNameHandler(finishHandler);
        var firstNameHandler = new UserFormFirstNameHandler(middleNameHandler);
        var lastNameHandler = new UserFormLastNameHandler(firstNameHandler);
        this.userFormHandler = new UserFormStartHandler(lastNameHandler);
    }

    public UserFormHandler getUserFormHandler() {
        return this.userFormHandler;
    }

}
