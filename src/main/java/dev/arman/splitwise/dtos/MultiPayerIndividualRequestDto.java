package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class MultiPayerIndividualRequestDto {
    private Long createById;
    private List<Long> paidByUserIds;
    private List<Integer> amountPaid;
    private List<Integer> percent;
    private String description;
}
