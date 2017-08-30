package com.guohe.onegame.view.team;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.custome.WeakRefrenceHandler;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.base.BaseActivity;
import com.wou.commonutils.TextUtil;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

/**
 * Created by 水寒 on 2017/8/28.
 * 海报列表
 */

public class PosterListActivity extends BaseActivity {

    private static final int HAND_BG_COLOR = 0x0001;
    private static final int HAND_BITMAP = 0x0002;

    private DiscreteScrollView mPosterPick;
    private PosterAdapter mAdapter;

    private WeakRefrenceHandler<PosterListActivity> mHandler =
            new WeakRefrenceHandler<PosterListActivity>(this) {
        @Override
        protected void handleMessage(PosterListActivity ref, Message msg) {
            PosterViewHolder holder;
            switch (msg.what){
                case HAND_BG_COLOR:
                    holder = (PosterViewHolder) mPosterPick.getViewHolder(msg.arg1);
                    GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{msg.arg2, msg.arg2, msg.arg2, 0x000000});
                    holder.colorBg.setBackgroundDrawable(drawable);
                    break;
                case HAND_BITMAP:
                    holder = (PosterViewHolder) mPosterPick.getViewHolder(msg.arg1);
                    holder.posterImage.setImageBitmap((Bitmap)msg.obj);
                    break;
            }
        }
    };

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    protected void customeToolbar(TextView titleText, TextView toolbarMenu, ImageButton moreButton) {
        titleText.setText("生成海报");
    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_poster_list;
    }

    @Override
    protected void initView() {
        mPosterPick = getView(R.id.team_picker);
        mPosterPick.setOrientation(Orientation.HORIZONTAL);
        //mPosterPick.addOnItemChangedListener();
        mAdapter = new PosterAdapter();
        mPosterPick.setAdapter(mAdapter);
        mPosterPick.setItemTransitionTimeMillis(500);
        mPosterPick.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.9f)
                .build());
    }

    @Override
    protected void initData() {

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, PosterListActivity.class);
        context.startActivity(intent);
    }

    class PosterAdapter extends RecyclerView.Adapter<PosterViewHolder>{

        @Override
        public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PosterViewHolder(LayoutInflater.from(PosterListActivity.this)
                .inflate(R.layout.item_poster_list, parent, false));
        }

        @Override
        public void onBindViewHolder(PosterViewHolder holder, final int position) {
            TextUtil.setNumberText(PosterListActivity.this, holder.price, 2);
            FrescoUtils.getBitmapByRes(TestImageUtil.getImgByIndex(position),
                    PosterListActivity.this, DimenUtil.dp2px(260),
                    DimenUtil.dp2px(180), new FrescoUtils.BitmapListener() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            if(bitmap == null) return;
                            Message msg = Message.obtain();
                            msg.what = HAND_BITMAP;
                            msg.obj = bitmap;
                            msg.arg1 = position;
                            mHandler.sendMessage(msg);
                            // Palette的部分
                            Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch vibrant = palette.getVibrantSwatch();
                                    if(vibrant == null) return;
                                    Message msg = Message.obtain();
                                    msg.what = HAND_BG_COLOR;
                                    msg.arg1 = position;
                                    msg.arg2 = vibrant.getRgb();
                                    mHandler.sendMessage(msg);
                                }
                            });
                        }

                        @Override
                        public void onFail() {
                            Message msg = Message.obtain();
                            msg.what = HAND_BG_COLOR;
                            msg.arg1 = position;
                            msg.arg2 = 0x32b16c;
                            mHandler.sendMessage(msg);
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }

    class PosterViewHolder extends RecyclerView.ViewHolder{

        private ImageView posterImage;
        private View colorBg;
        private TextView price;
        public PosterViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.item_poster_image);
            colorBg = itemView.findViewById(R.id.item_poster_colorbg);
            price = (TextView) itemView.findViewById(R.id.item_poster_pricenum);
        }
    }
}
