package com.afs.restapi.service;

import com.afs.restapi.entity.Todo;
import com.afs.restapi.exception.TodoNotFoundException;
import com.afs.restapi.repository.TodoRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Tool(name="findTodos", description = "Find all my todo tasks.")
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateById(Integer id, Todo newTodo) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        if (newTodo.getDone() != null) {
            todo.setDone(newTodo.getDone());
        }
        if (newTodo.getText() != null) {
            todo.setText(newTodo.getText());
        }
        return todoRepository.save(todo);
    }

    public void deleteById(Integer id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }
}
