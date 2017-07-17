package cn.zengcanxiang.mvp_practice_project_template.api;

import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.bean.ResultBean;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ToDayDataBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * web服务器API
 */
public interface WebApi {

    @GET("api/day/history")
    Call<ResultBean<List<String>>> history();

    @GET("api/day/{year}/{month}/{day}")
    Call<ResultBean<ToDayDataBean>> dayData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);
}
