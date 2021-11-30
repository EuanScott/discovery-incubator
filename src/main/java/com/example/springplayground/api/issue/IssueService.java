package com.example.springplayground.api.issue;

import com.example.springplayground.exception.ServiceException;
import com.example.springplayground.retrofit.RetrofitService;
import com.example.springplayground.service.model.Issue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

// https://stackoverflow.com/q/6827752 -> Difference between Spring Annotations
@Component
public class IssueService {

    private final RetrofitService retrofitService;

    public IssueService(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }


    @Cacheable("issues") // Not BEAN, but an Annotation
    public List<Issue> getIssues() throws ServiceException {
        Call<List<Issue>> retrofitCall = retrofitService.getIssues();
        Response<List<Issue>> response;

        try {
            response = retrofitCall.execute();

            if (!response.isSuccessful()) {
                // throw new RuntimeException(
                //         response.errorBody() != null
                //                 ? response.errorBody().string()
                //                 : "Unable to get list of Issues"
                // );
                throw new ServiceException(
                        response.errorBody() != null
                                ? "Unable to get list of Issues: " + response.errorBody().string()
                                : "Unable to get list of Issues: Reason Unknown"
                );
            }

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            // throw new RuntimeException("An Unknown error occurred. Please try again later");
            throw new ServiceException("An Unknown error occurred. Please try again later");
        }
    }

    public Issue getIssue(BigDecimal id) throws ServiceException {
        Call<Issue> retrofitCall = retrofitService.getIssue(id);
        Response<Issue> response;

        try {
            response = retrofitCall.execute();

            if (!response.isSuccessful()) {
                // throw new IOException(
                //         response.errorBody() != null
                //                 ? response.errorBody().string()
                //                 : "The Issue does not exist"
                // );
                throw new ServiceException(
                        response.errorBody() != null
                                ? "Unable to get list of Issues: " + response.errorBody().string()
                                : "Unable to get list of Issues: Reason Unknown"
                );
            }

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
            // throw new RuntimeException("An Unknown error occurred. Please try again later");
            throw new ServiceException("An Unknown error occurred. Please try again later");
        }
    }
}
