package com.example.springplayground.api.issue;

import com.example.springplayground.retrofit.RetrofitService;
import com.example.springplayground.service.model.Issue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// https://stackoverflow.com/q/6827752 -> Difference between Spring Annotations
@Component
public class IssueService {

    private final RetrofitService retrofitService;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public IssueService(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }


    @Cacheable("issues") // Not BEAN, but an Annotation
    public Future<List<Issue>> getIssues() throws RuntimeException {
        Call<List<Issue>> retrofitCall = retrofitService.getIssues();
        Response<List<Issue>> response;

        try {
            response = retrofitCall.execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException(
                        response.errorBody() != null
                                ? response.errorBody().string()
                                : "Unknown error"
                );
            }

            return executor.submit(response::body);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Custom error message");
        }
    }

    public Future<Issue> getIssue(BigDecimal id) throws RuntimeException {
        Call<Issue> retrofitCall = retrofitService.getIssue(id);
        Response<Issue> response;

        try {
            response = retrofitCall.execute();

            if (!response.isSuccessful()) {
                throw new IOException(
                        response.errorBody() != null
                                ? response.errorBody().string()
                                : "Unknown error"
                );
            }

            return executor.submit(response::body);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Custom error message");
        }
    }
}
