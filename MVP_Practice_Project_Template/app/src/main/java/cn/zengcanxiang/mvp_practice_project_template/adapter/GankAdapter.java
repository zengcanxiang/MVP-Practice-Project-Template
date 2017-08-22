package cn.zengcanxiang.mvp_practice_project_template.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.WebActivity;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;


public class GankAdapter extends HelperAdapter<Gank> {

    public GankAdapter(List<Gank> data, Context context) {
        super(data, context, R.layout.item_gank);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, int position, final Gank item) {
        viewHolder.setText(R.id.title, spannableValue(item));
        if (item.getImages() != null) {
            ImageView view = viewHolder.getView(R.id.img);
            viewHolder.setVisible(R.id.img, View.VISIBLE);
            Glide.with(mContext).load(item.getImages().get(0)).into(view);
        }
        viewHolder.getView(R.id.item_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, WebActivity.class);
                i.putExtra(WebActivity.KEY_WEB_URL, item.getUrl());
                mContext.startActivity(i);
            }
        });
    }

    private CharSequence spannableValue(Gank gank) {
        String gankStr = gank.getDesc() + " @" + gank.getWho();
        SpannableString spannableString = new SpannableString(gankStr);
        spannableString.setSpan(new
                RelativeSizeSpan(0.8f), gank.getDesc().length() + 1, gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new
                ForegroundColorSpan(Color.GRAY), gank.getDesc().length() + 1, gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
