package dev.arman.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */

@Getter
@Setter
@Entity(name = "users")
public class User extends BaseModel {
    private String name;

    private String phone;

    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;

    @ManyToMany(mappedBy = "members")
    private List<Group> groups;
}
