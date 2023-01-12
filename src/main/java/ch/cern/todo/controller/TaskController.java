package ch.cern.todo.controller;

import ch.cern.todo.Dto.UpdateTaskDto;
import ch.cern.todo.exception.FieldRequirementException;
import ch.cern.todo.exception.ResourceNotFoundException;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.service.TaskCategoryService;
import ch.cern.todo.service.TaskService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {


    private final TaskService taskService;
    private final TaskCategoryService taskCategoryService;


    @Autowired
    public TaskController(TaskService taskService, TaskCategoryService taskCategoryService){
        this.taskService = taskService;
        this.taskCategoryService = taskCategoryService;
    }



    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks(){
        return taskService.getTasks();
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Task getTask(@PathVariable("id") Long id){
        return checkResourceFound(id);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Task createTask(@RequestBody Task task, @RequestParam Long taskCategoryId){
        TaskCategory category = taskCategoryService.getTaskCategory(taskCategoryId)
                .orElseThrow(() -> ResourceNotFoundException.generateFailedIdLookup("TaskCategory",taskCategoryId));

        if (Strings.isNullOrEmpty(task.getName()) ){
            throw FieldRequirementException.generateMissingFieldException("Task","name");
        }

        checkDeadline(task.getDeadline());

        return taskService.createTask(task,category);

    }
    @PostMapping("/createWithCategoryName")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Task createTaskWithCategoryName(@RequestBody Task task, @RequestParam String taskCategoryName){
        TaskCategory category = taskCategoryService.getTaskCategoryByName(taskCategoryName)
                .orElseGet(() -> {
                    TaskCategory newCat = new TaskCategory(Long.valueOf(0),taskCategoryName,"");

                    return taskCategoryService.createTaskCategory(newCat);
                });

        if (Strings.isNullOrEmpty(task.getName()) ){
            throw FieldRequirementException.generateMissingFieldException("Task","name");

        }

        checkDeadline(task.getDeadline());

        return taskService.createTask(task,category);

    }



    @DeleteMapping(path="{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") Long id){
        final Task task = checkResourceFound(id);
        taskService.deleteTask(task);
    }



    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{id}")
    public Task updateTask(@PathVariable("id") Long id,
                           @RequestBody UpdateTaskDto updateTaskDto,
                           @RequestParam(required = false) Long taskCategoryId){

        Task task = checkResourceFound(id);
        TaskCategory newTaskCategory = null;
        if(taskCategoryId != null){
            newTaskCategory =  taskCategoryService.getTaskCategory(taskCategoryId)
                    .orElseThrow(() -> ResourceNotFoundException.generateFailedIdLookup("TaskCategory",taskCategoryId));

        }
        if(updateTaskDto.getDeadline() != null)
            checkDeadline(updateTaskDto.getDeadline());

        return taskService.updateTask(task,updateTaskDto,newTaskCategory);

    }
    private void checkDeadline(Date deadline){
        if(deadline == null){
            throw FieldRequirementException.generateMissingFieldException("Task","deadline");
        }

        if( deadline.before(new Date())) //read
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Deadline must be in future."
            );
    }
    private Task checkResourceFound(Long id){
        return taskService.getTask(id)
                .orElseThrow(() -> ResourceNotFoundException.generateFailedIdLookup("Task",id));

    }
}
