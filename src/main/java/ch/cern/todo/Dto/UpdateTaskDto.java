package ch.cern.todo.Dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("task")
@EqualsAndHashCode
public final class UpdateTaskDto {

    private String name;
    private String description;
    private Date deadline;


}
