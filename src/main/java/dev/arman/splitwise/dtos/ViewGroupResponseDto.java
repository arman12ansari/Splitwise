package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class ViewGroupResponseDto {
    private List<String> groupNames;
    private String status;
    private String message;
}
