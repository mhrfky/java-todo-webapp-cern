package ch.cern.todo.controller;

import ch.cern.todo.Dto.UpdateTaskCategoryDto;
import ch.cern.todo.exception.FieldRequirementException;
import ch.cern.todo.exception.ResourceNotFoundException;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.service.TaskCategoryService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/taskCategories")
public class TaskCategoryController {


    private final TaskCategoryService taskCategoryService;
    @Autowired
    public TaskCategoryController(TaskCategoryService taskCategoryService){this.taskCategoryService = taskCategoryService;}
    
    
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskCategory> getTaskCategories(){
        return taskCategoryService.getTaskCategories();
    }
    
    
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TaskCategory getTaskCategory(@PathVariable("id") Long id){
        return checkResourceFound(id);
    }
    
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TaskCategory createTaskCategory(@RequestBody TaskCategory taskCategory){

        if (Strings.isNullOrEmpty(taskCategory.getName()) ){
            throw FieldRequirementException.generateMissingFieldException("TaskCategory","name");
        }
        if(taskCategoryService.getTaskCategoryByName(taskCategory.getName()).isPresent()){
            throw FieldRequirementException.generateDuplicateFieldException("TaskCategory","name");
        }


        return taskCategoryService.createTaskCategory(taskCategory);

    }
    
    
    
    @DeleteMapping(path="{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskCategory(@PathVariable("id") Long id){
        final TaskCategory category = checkResourceFound(id);
        taskCategoryService.deleteTaskCategory(category);
    }
    
    
    //duzenle
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{id}")
    public TaskCategory updateTaskCategory(@PathVariable("id") Long id,
                                           @RequestBody UpdateTaskCategoryDto taskCategoryDto){

        final TaskCategory category = checkResourceFound(id);


        return taskCategoryService.updateTaskCategory(category,taskCategoryDto);

    }
    private TaskCategory checkResourceFound(Long id){
        return taskCategoryService.getTaskCategory(id)
                .orElseThrow(() -> ResourceNotFoundException.generateFailedIdLookup("TaskCategory",id));

    }


}
