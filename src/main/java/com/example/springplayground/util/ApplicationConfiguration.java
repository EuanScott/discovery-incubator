package com.example.springplayground.util;

import com.example.springplayground.retrofit.RetrofitService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class ApplicationConfiguration {

    // https://stackoverflow.com/a/40620318 -> Why not to use @Autowired, even though in this case it is suitable
    private final Environment env;

    // TODO: 2021/10/31 REMOVE!
    @Autowired
    private Environment environment;

    // TODO: 2021/10/31 TEST THE DIFFERENT PROFILES ARE RUNNING CORRECTLY 

    public ApplicationConfiguration(Environment env) {
        Assert.notNull(env, "API Environment Variable must not be null!");
        this.env = env;
    }

    @Bean("retrofitServiceEnv")
    @Profile("!test")
    public String retrofitServiceEnv() {
        return env.getProperty("retrofit.service.url");
    }

    @Bean("okHttp")
    public OkHttpClient okHttp() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        return okHttpClientBuilder.build();
    }

    @Bean
    public RetrofitService retrofitService(
            @Qualifier("retrofitServiceEnv") String env,
            @Qualifier("okHttp") OkHttpClient okHttpClient
    ) {
        System.out.println("\nEnvironment: ");
        System.out.println("URL: " + env);
        for (String profileName : environment.getActiveProfiles()) {
            System.out.println("Profile: " + profileName);
        }
        System.out.println("\n");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(env)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(RetrofitService.class);
    }
}
