package com.example.springplayground.api.todo;

import com.example.springplayground.controller.api.TodoApi;
import com.example.springplayground.controller.model.TodoDTO;
import com.example.springplayground.service.model.Todo;
import com.example.springplayground.util.MapperUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController implements TodoApi {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public ResponseEntity<List<TodoDTO>> getTodoList() {
        List<Todo> todos = todoService.getTodo();
        return ResponseEntity.ok(MapperUtil.mapList(todos, TodoDTO.class));
    }
}
