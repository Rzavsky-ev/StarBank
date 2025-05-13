package org.skypro.starbank.model.bankingEntities;

import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(UUID id, String userName, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getUserName() + " " + getFirstName() + " " + getLastName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User other)) {
            return false;
        }

        return (this.id == other.id && this.userName.equals(other.userName)
                && this.firstName.equals(other.firstName) &&
                this.lastName.equals(other.lastName));
    }

    @Override
    public String toString() {
        return getId() + " " + getFullName();
    }

}
