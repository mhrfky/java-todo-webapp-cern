package ch.cern.todo.service;

import ch.cern.todo.Dto.UpdateTaskDto;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskrepository;

    @Autowired
    public TaskService(TaskRepository taskrepository){this.taskrepository = taskrepository;    }

    @Transactional(readOnly = true)
    public List<Task> getTasks(){
        return taskrepository.findAll();
    }

    @Transactional
    public Task createTask(Task task,TaskCategory category) {
        task.setCategory(category);


        return taskrepository.save(task);
    }
    @Transactional
    public void deleteTask(Task task) {
        taskrepository.delete(task);
    }
    @Transactional
    public Task updateTask(Task task, UpdateTaskDto updateTaskDto,TaskCategory newTaskCategory) {

        task.update(updateTaskDto,newTaskCategory);
        taskrepository.save(task);
        return task;
    }

    @Transactional(readOnly = true)
    public Optional<Task> getTask(Long id) {
        return taskrepository.findById(id);
    }
}
