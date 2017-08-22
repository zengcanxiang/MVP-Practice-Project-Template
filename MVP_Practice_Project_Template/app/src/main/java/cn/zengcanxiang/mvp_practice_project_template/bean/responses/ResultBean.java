package cn.zengcanxiang.mvp_practice_project_template.bean.responses;


public class ResultBean<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
