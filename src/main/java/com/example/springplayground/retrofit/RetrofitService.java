package com.example.springplayground.retrofit;

import com.example.springplayground.service.model.Issue;
import com.example.springplayground.service.model.Supplier;
import com.example.springplayground.service.model.Todo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.math.BigDecimal;
import java.util.List;

public interface RetrofitService {

    @GET("Issues")
    Call<List<Issue>> getIssues();

    @GET("Issues/{id}")
    Call<Issue> getIssue(@Path("id") BigDecimal id);

    @GET("Suppliers")
    Call<List<Supplier>> getSuppliers();

    @GET("Suppliers/{id}")
    Call<Supplier> getSupplier(@Path("id") Integer id);

    @GET("todos")
    Call<List<Todo>> listTodos();
}