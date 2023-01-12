package ch.cern.todo.Dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("taskCategory")
@EqualsAndHashCode

public final class UpdateTaskCategoryDto {

    private String name;
    private String description;
}
