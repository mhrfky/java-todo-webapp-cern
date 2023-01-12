package ch.cern.todo.model;

import ch.cern.todo.Dto.UpdateTaskCategoryDto;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCategory {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    public void update(UpdateTaskCategoryDto taskCategoryDto){
        if(!Strings.isNullOrEmpty(taskCategoryDto.getName())){
            setName(taskCategoryDto.getName());
        }
        if(!Strings.isNullOrEmpty(taskCategoryDto.getDescription())){
            setDescription(taskCategoryDto.getDescription());
        }
    }
}
