package cn.zengcanxiang.mvp_practice_project_template.adapter;

import android.content.Context;

import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;


public class HorizontalHistoryAdapter extends HelperAdapter<String> {
    public HorizontalHistoryAdapter(List<String> data, Context context) {
        super(data, context, R.layout.adapter_horizontal_history);
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, int position, String item) {


    }
}
