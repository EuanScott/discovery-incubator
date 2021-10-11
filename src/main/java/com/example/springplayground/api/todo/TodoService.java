package com.example.springplayground.api.todo;

import com.example.springplayground.retrofit.RetrofitService;
import com.example.springplayground.service.model.Todo;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Component
public class TodoService {

    RetrofitService retrofitService;

    public TodoService(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public List<Todo> getTodo() throws RuntimeException {

        Call<List<Todo>> retrofitCall = retrofitService.listTodos();
        Response<List<Todo>> response;

        try {
            response = retrofitCall.execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        response.errorBody() != null
                                ? response.errorBody().string()
                                : "Unknown error"
                );
            }

            return response.body();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Custom error message");
        }
    }
}
