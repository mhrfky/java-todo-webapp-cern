package ch.cern.todo.repository;

import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory,Long> {

    public TaskCategory findByName(String name);
}
