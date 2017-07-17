package cn.zengcanxiang.mvp_practice_project_template.bean.responses;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;


public class ToDayDataBean {
    @JSONField(name = "Android")
    private List<Gank> androidList;
    @JSONField(name = "休息视频")
    private List<Gank> restVideo;
    @JSONField(name = "iOS")
    private List<Gank> iOSList;
    @JSONField(name = "福利")
    private List<Gank> boonList;
    @JSONField(name = "拓展资源")
    private List<Gank> resourcesList;
    @JSONField(name = "瞎推荐")
    private List<Gank> recommendList;
    @JSONField(name = "前端")
    private List<Gank> frontEndList;
    @JSONField(name = "App")
    private List<Gank> appList;

    @JSONField(name = "Android")
    public List<Gank> getAndroidList() {
        return androidList;
    }

    @JSONField(name = "Android")
    public void setAndroidList(List<Gank> androidList) {
        this.androidList = androidList;
    }

    @JSONField(name = "休息视频")
    public List<Gank> getRestVideo() {
        return restVideo;
    }

    @JSONField(name = "休息视频")
    public void setRestVideo(List<Gank> restVideo) {
        this.restVideo = restVideo;
    }

    @JSONField(name = "iOS")
    public List<Gank> getiOSList() {
        return iOSList;
    }

    @JSONField(name = "iOS")
    public void setiOSList(List<Gank> iOSList) {
        this.iOSList = iOSList;
    }

    @JSONField(name = "福利")
    public List<Gank> getBoonList() {
        return boonList;
    }

    @JSONField(name = "福利")
    public void setBoonList(List<Gank> boonList) {
        this.boonList = boonList;
    }

    @JSONField(name = "拓展资源")
    public List<Gank> getResourcesList() {
        return resourcesList;
    }

    @JSONField(name = "拓展资源")
    public void setResourcesList(List<Gank> resourcesList) {
        this.resourcesList = resourcesList;
    }

    @JSONField(name = "瞎推荐")
    public List<Gank> getRecommendList() {
        return recommendList;
    }

    @JSONField(name = "瞎推荐")
    public void setRecommendList(List<Gank> recommendList) {
        this.recommendList = recommendList;
    }

    @JSONField(name = "前端")
    public List<Gank> getFrontEndList() {
        return frontEndList;
    }

    @JSONField(name = "前端")
    public void setFrontEndList(List<Gank> frontEndList) {
        this.frontEndList = frontEndList;
    }

    @JSONField(name = "App")
    public List<Gank> getAppList() {
        return appList;
    }

    @JSONField(name = "App")
    public void setAppList(List<Gank> appList) {
        this.appList = appList;
    }

    public boolean isEmpty() {
        return boonList == null;
    }
}
