package cn.zengcanxiang.mvp_practice_project_template.core.meizhi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import br.com.mauker.materialsearchview.MaterialSearchView;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.MVPBaseActivity;
import cn.zengcanxiang.mvp_practice_project_template.core.meizhi.mvp.MeizhiModel;
import cn.zengcanxiang.mvp_practice_project_template.core.meizhi.mvp.MeizhiPresenter;
import cn.zengcanxiang.mvp_practice_project_template.util.FileUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

public class MeizhiImgActivity extends MVPBaseActivity<MeizhiPresenter, MeizhiModel> {

    private ImageView ivMeizhi;
    private AppBarLayout appBar;
    protected boolean isToolBarHiding = false;
    private PhotoViewAttacher attacher;
    private Bitmap girl;
    private Toolbar toolbar;
    private String title;
    private MaterialSearchView searchView;

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_meizhi;
    }

    @Override
    public void disposeBusiness() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        setTitle(title);
        ivMeizhi.setImageDrawable(ShareElement.shareDrawable);
        attacher = new PhotoViewAttacher(ivMeizhi);
        Glide.with(this).load(intent.getStringExtra("url")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ivMeizhi.setImageBitmap(resource);
                attacher.update();
                girl = resource;
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                ivMeizhi.setImageDrawable(errorDrawable);
            }
        });
        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                hideOrShowToolBar();
            }
        });
    }

    @Override
    public void initViews() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        ivMeizhi = (ImageView) findViewById(R.id.iv_meizhi);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        appBar.setAlpha(0.6f);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setTransitionName(ivMeizhi, "meizhi");
    }

    @Override
    public void setViewsListener() {

    }

    protected void hideOrShowToolBar() {
        appBar.animate()
                .translationY(isToolBarHiding ? 0 : -appBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        isToolBarHiding = !isToolBarHiding;
    }

    @Override
    public boolean isImmersed() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_girl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (!FileUtil.isSDCardEnable() || girl == null) {
                    Snackbar.make(ivMeizhi, "禽兽，妹子拒绝了你的请求", Snackbar.LENGTH_SHORT).show();
                } else {
                    saveMeizhiImage();
                }
                break;
            case R.id.action_share:
                shareGirlImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveMeizhiImage() {
        Uri uri = FileUtil.saveBitmapToSDCard(girl, title);
        if (uri == null) {
            Snackbar.make(ivMeizhi, "禽兽，妹子拒绝了你的请求", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(scannerIntent);
            Snackbar.make(ivMeizhi, "妹子已经躺在你的图库里了", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void shareGirlImage() {
        Uri uri = FileUtil.saveBitmapToSDCard(girl, title);
        if (uri == null) {
            Snackbar.make(ivMeizhi, "禽兽，妹子拒绝了你的请求", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(shareIntent, title));
        }
    }

}
