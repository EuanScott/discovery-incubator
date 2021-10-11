package com.example.springplayground.api.suppliers;

import com.example.springplayground.retrofit.RetrofitService;
import com.example.springplayground.service.model.Supplier;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Component
public class SupplierService {

    RetrofitService retrofitService;

    public SupplierService(RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    public List<Supplier> getSuppliers() throws RuntimeException {
        Call<List<Supplier>> retrofitCall = retrofitService.getSuppliers();
        Response<List<Supplier>> response;

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
