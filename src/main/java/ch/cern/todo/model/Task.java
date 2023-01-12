package ch.cern.todo.model;
import ch.cern.todo.Dto.UpdateTaskCategoryDto;
import ch.cern.todo.Dto.UpdateTaskDto;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.FetchStrategy;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)

    private String name;

    @Column(length = 500)
    private String description;

    private Date deadline;

    @ManyToOne(optional = false)
    @JoinColumn(name="category_id",referencedColumnName = "id")
    private TaskCategory category;
    public void update(UpdateTaskDto updateTaskDto, TaskCategory newTaskCategory){
        if(newTaskCategory != null){
            setCategory(newTaskCategory);
        }
        if(!Strings.isNullOrEmpty(updateTaskDto.getName())){
            setName(updateTaskDto.getName());
        }
        if(!Strings.isNullOrEmpty(updateTaskDto.getDescription())){
            setDescription(updateTaskDto.getDescription());
        }
        if(updateTaskDto.getDeadline()!=null){
            setDeadline(updateTaskDto.getDeadline());
        }
    }


}
