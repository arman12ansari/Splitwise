package dev.arman.splitwise.dtos;

import dev.arman.splitwise.services.strategies.settleUpStrategy.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class SettleUpGroupResponseDto {
    private List<Transaction> transactions;
    private String status;
    private String message;
}
