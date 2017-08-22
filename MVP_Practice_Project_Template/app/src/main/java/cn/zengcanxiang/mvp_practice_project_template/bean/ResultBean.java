package cn.zengcanxiang.mvp_practice_project_template.bean;


import java.util.List;

public class ResultBean<T> {
    private List<String> category;
    private T results;
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
