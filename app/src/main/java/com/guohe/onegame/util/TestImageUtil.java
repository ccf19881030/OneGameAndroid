package com.guohe.onegame.util;

import com.guohe.onegame.R;

import java.util.Random;

/**
 * Created by 水寒 on 2017/8/15.
 */

public class TestImageUtil {

    private static int[] testDynamicImgs = {R.mipmap.test_img1, R.mipmap.test_img2,
            R.mipmap.test_img3, R.mipmap.test_img4,
            R.mipmap.test_img5, R.mipmap.test_img6,
            R.mipmap.test_img7, R.mipmap.test_img8};

    private static int[] testHeadImgs = { R.mipmap.test_head_img1, R.mipmap.test_head_img2,
            R.mipmap.test_head_img3, R.mipmap.test_head_img4,
            R.mipmap.test_head_img5, R.mipmap.test_head_img6,
            R.mipmap.test_head_img7

    };

    private static Random random = new Random();

    public static int getDynamicImgRes(){
        return testDynamicImgs[random.nextInt(testDynamicImgs.length)];
    }

    public static int getImgByIndex(int index){
        return testDynamicImgs[index % testDynamicImgs.length];
    }

    public static int getHeadImgRes(){
        return testHeadImgs[random.nextInt(testHeadImgs.length)];
    }
}
