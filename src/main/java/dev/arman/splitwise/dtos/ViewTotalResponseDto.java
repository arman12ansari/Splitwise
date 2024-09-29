package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class ViewTotalResponseDto {
    private int amount;
    private String status;
    private String message;
}
