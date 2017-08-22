package cn.zengcanxiang.mvp_practice_project_template.api;


import java.util.concurrent.TimeUnit;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiService {

    private WebApi mApi;

    private ApiService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLog())
                .connectTimeout(10, TimeUnit.SECONDS)   //链接超时10秒
                .readTimeout(10, TimeUnit.SECONDS)      //读取超时
                .writeTimeout(10, TimeUnit.SECONDS)     //写入超时
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.APP_URL_MAIN)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(WebApi.class);
    }

    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public static WebApi getInstance() {
        return SingletonHolder.INSTANCE.mApi;
    }

}
