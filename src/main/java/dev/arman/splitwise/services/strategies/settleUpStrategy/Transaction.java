package dev.arman.splitwise.services.strategies.settleUpStrategy;

import dev.arman.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class Transaction {
    private User from;
    private User to;
    private Integer amount;
}
