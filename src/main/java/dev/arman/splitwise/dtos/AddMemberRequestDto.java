package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class AddMemberRequestDto {
    private Long groupCreatorId;
    private Long groupId;
    private Long memberId;
}
