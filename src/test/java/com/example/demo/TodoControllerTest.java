package com.example.demo;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(TodoController.class)
@AutoConfigureRestTestClient
public class TodoControllerTest {

    @Autowired
    private RestTestClient client;

    @Test
    public void shouldReturnAllTodos() {
        client.get()
                .uri("/api/todos")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Todo>>() {})
                .value(todos -> assertThat(todos).isNotNull());
    }

    @Test
    public void shouldAddNewTodo() {
        Todo newTodo = new Todo(null, "Master RestTestClient", false);

        client.post()
                .uri("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTodo)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo.class)
                .value(todo -> {
                    assertThat(todo.getId()).isNotNull();
                    assertThat(todo.getTitle()).isEqualTo("Master RestTestClient");
                    assertThat(todo.isCompleted()).isFalse();
                });
    }

    @Test
    public void shouldRemoveTodoWhenItExists() {
        Todo tempTodo = new Todo(null, "Task to delete", false);
        
        // 1. Create a temporary todo and extract it directly from the response body
        Todo createdTodo = client.post()
                .uri("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(tempTodo)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Todo.class)
                .returnResult()
                .getResponseBody();

        // 2. Delete the created todo using its generated ID
        client.delete()
                .uri("/api/todos/" + createdTodo.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistentTodo() {
        client.delete()
                .uri("/api/todos/9999")
                .exchange()
                .expectStatus().isNotFound();
    }
}