package com.guohe.onegame.view.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guohe.onegame.MvpPresenter;
import com.guohe.onegame.R;
import com.guohe.onegame.util.DimenUtil;
import com.guohe.onegame.util.FrescoUtils;
import com.guohe.onegame.util.LogUtil;
import com.guohe.onegame.util.RefreshUtil;
import com.guohe.onegame.util.TestImageUtil;
import com.guohe.onegame.view.adapter.MineDynamicGridAdapter;
import com.guohe.onegame.view.mine.CreditValueActivtiy;
import com.guohe.onegame.view.mine.MineInfoActivity;
import com.guohe.onegame.view.mine.MyFansActivity;
import com.guohe.onegame.view.mine.MyFollowdActivity;
import com.guohe.onegame.view.mine.MyWalletActivity;
import com.guohe.onegame.view.mine.SettingActivity;
import com.guohe.onegame.view.team.MyTeamActivity;
import com.wou.commonutils.TextUtil;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 水寒 on 2017/8/7.
 */

public class MainFragment4 extends BaseMainFragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private MineDynamicGridAdapter mAdapter;
    private SimpleDraweeView mHeaderDraw;
    private GridLayoutManager mGridLayoutManager;
    private PtrFrameLayout mPtrFrameLayout;
    private int mUserId;
    private boolean mIsMine;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private TextView mDynamicNumber;
    private TextView mFollwedNumber;
    private TextView mFollwmeNumber;

    @Override
    public void initPresenter(List<MvpPresenter> presenters) {

    }

    @Override
    public void turnToOtherView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_main_page4;
    }

    @Override
    protected void initData() {
        FrescoUtils.setCircle(mHeaderDraw, getResources().getColor(R.color.app_background));
        FrescoUtils.loadRes(mHeaderDraw, TestImageUtil.getHeadImgRes(), null, DimenUtil.dp2px(57), DimenUtil.dp2px(57), null);
        TextUtil.setNumberText(this.getContext(), mDynamicNumber, 23);
        TextUtil.setNumberText(this.getContext(), mFollwedNumber, 162);
        TextUtil.setNumberText(this.getContext(), mFollwmeNumber, 85);
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if(bundle != null){
            mUserId = bundle.getInt("userid");
            mIsMine = mUserId == 1;
        }else{
            mIsMine = true;
            mUserId = 1;
        }
        mHeaderDraw = getView(R.id.header_icon);
        ImageButton attentionButton = getView(R.id.attention_icon);
        attentionButton.setOnClickListener(this);
        getView(R.id.header_icon).setOnClickListener(this);
        getView(R.id.mine_role).setOnClickListener(this);
        TextView creditScore = getView(R.id.mine_credit_score);
        creditScore.setOnClickListener(this);
        getView(R.id.mine_menu_dynamic).setOnClickListener(this);
        getView(R.id.mine_menu_myfollowed).setOnClickListener(this);
        getView(R.id.mine_menu_followme).setOnClickListener(this);
        getView(R.id.mine_wallet).setOnClickListener(this);
        getView(R.id.mine_progress).setOnClickListener(this);

        if(mIsMine){
            attentionButton.setImageResource(R.mipmap.icon_setting);
            creditScore.setText("信用积分156");
        }else{
            attentionButton.setImageResource(R.mipmap.icon_not_followed);
            creditScore.setText("23岁");
        }
        mRecyclerView = getView(R.id.personal_recyclerview);
        bindRecyclerView();
        mPtrFrameLayout = refreshView(R.id.main_mine_refreshview, new RefreshUtil.OnRefresh() {
            @Override
            public void refreshBegin(PtrFrameLayout frame) {

            }
        });
        mAppBarLayout = getView(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mPtrFrameLayout.setEnabled(true);
                } else {
                    mPtrFrameLayout.setEnabled(false);
                }
                if(verticalOffset <= -125){
                    if(mNickNameHideListener != null){
                        mNickNameHideListener.changed(true, "星期天的午餐");
                    }
                }else{
                    if(mNickNameHideListener != null){
                        mNickNameHideListener.changed(false, "星期天的午餐");
                    }
                }
            }
        });
        mCoordinatorLayout = getView(R.id.app_coordinatorlayout);

        mDynamicNumber = getView(R.id.mine_menu_dynamic_number);
        mFollwedNumber = getView(R.id.mine_menu_myfollowed_number);
        mFollwmeNumber = getView(R.id.mine_menu_followme_number);
    }

    private NickNameHideListener mNickNameHideListener;
    /**
     * 监听昵称显示和隐藏
     */
    public void setOnNickNameHideListener(NickNameHideListener listener){
        mNickNameHideListener = listener;
    }

    public interface NickNameHideListener{
        void changed(boolean hide, String nickname);
    }

    private int mRecyclerScrollStatu;
    private void bindRecyclerView(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mRecyclerScrollStatu = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.d("dy == " + dy);
                if(dy < 0 && mRecyclerScrollStatu == RecyclerView.SCROLL_STATE_SETTLING
                        && mGridLayoutManager.findFirstVisibleItemPosition() <= 2){
                    mAppBarLayout.setExpanded(true);
                }
            }
        });
        mRecyclerView.setHasFixedSize(false);
        mGridLayoutManager = new GridLayoutManager(this.getContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                if(position % 3 == 0){
                    outRect.top = 1;
                }else{
                    outRect.top = 1;
                    outRect.left = 1;
                }
            }
        });
        mAdapter = new MineDynamicGridAdapter(this.getContext());
        //HeaderAndFooterRecyclerViewAdapter headAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
       /* View headerView = LayoutInflater.from(
                this.getContext()).inflate(R.layout.header_mine_dynamic_list, null);
        RecyclerViewUtils.setHeaderView(mRecyclerView, headerView);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.attention_icon:
                if(mIsMine){
                    SettingActivity.startActivity(MainFragment4.this.getContext());
                }else{
                    //TODO 判断是否关注了

                }
                break;
            case R.id.header_icon:
                if(mIsMine){  //个人信息
                    MineInfoActivity.startActivity(MainFragment4.this.getContext());
                }
                break;
            case R.id.mine_role:

                break;
            case R.id.mine_credit_score:
                CreditValueActivtiy.startActivity(MainFragment4.this.getContext());
                break;
            case R.id.mine_menu_dynamic:
                mAppBarLayout.setExpanded(false);
                break;
            case R.id.mine_menu_myfollowed:
                MyFollowdActivity.startActivity(MainFragment4.this.getContext());
                break;
            case R.id.mine_menu_followme:
                MyFansActivity.startActivity(MainFragment4.this.getContext());
                break;
            case R.id.mine_wallet:
                MyWalletActivity.startActivity(MainFragment4.this.getContext());
                break;
            case R.id.mine_progress:
                MyTeamActivity.startActivity(MainFragment4.this.getContext());
                break;
        }
    }
}
