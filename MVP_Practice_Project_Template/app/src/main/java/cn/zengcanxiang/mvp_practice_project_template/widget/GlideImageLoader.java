package cn.zengcanxiang.mvp_practice_project_template.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof Gank) {
            Glide.with(context).load(((Gank) path).getUrl()).into(imageView);
        } else {
            Glide.with(context).load(path).into(imageView);
        }
    }

}
