package dev.arman.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */

@Getter
@Setter
@Entity(name = "groups")
public class Group extends BaseModel {
    private String name;

    private String description;

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private User createdBy;
}
