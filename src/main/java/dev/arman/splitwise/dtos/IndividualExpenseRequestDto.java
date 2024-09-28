package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class IndividualExpenseRequestDto {
    private Long paidUserId;
    private List<Long> owedUserIds;
    private String description;
    private int amount;
    private String typeOfOperation;
}
