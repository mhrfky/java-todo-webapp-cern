package ch.cern.todo.service;

import ch.cern.todo.Dto.UpdateTaskCategoryDto;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryrepository;
    @Autowired
    public TaskCategoryService(TaskCategoryRepository taskCategoryrepository){this.taskCategoryrepository = taskCategoryrepository;}
    
    @Transactional(readOnly = true)
    public List<TaskCategory> getTaskCategories(){
        return taskCategoryrepository.findAll();
    }

    @Transactional
    public TaskCategory createTaskCategory(TaskCategory taskCategory) {
        return taskCategoryrepository.save(taskCategory);
    }
    @Transactional
    public void deleteTaskCategory(TaskCategory category) {
        taskCategoryrepository.delete(category);
    }
    @Transactional
    public TaskCategory updateTaskCategory(TaskCategory taskCategory, UpdateTaskCategoryDto taskCategoryDto) {
        taskCategory.update(taskCategoryDto);
        return taskCategory;

    }
    public Optional<TaskCategory> getTaskCategoryByName(String name){
        return Optional.ofNullable(taskCategoryrepository.findByName(name));
    }

    @Transactional(readOnly = true)
    public Optional<TaskCategory> getTaskCategory(Long id) {
        return taskCategoryrepository.findById(id);
    }
}
