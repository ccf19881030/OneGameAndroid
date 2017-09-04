package com.guohe.onegame;

import com.guohe.onegame.presenter.AccountPresenter;
import com.guohe.onegame.presenter.BasePresenter;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void serviceInterfaceTest() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        AccountPresenter presenter = new AccountPresenter();
        presenter.requestLoginByMobile("15129245672", new BasePresenter.OnPresenterResult<Boolean>() {
            @Override
            public void result(Boolean result) {
                Assert.assertTrue(result);
                signal.countDown();
            }
        });
        signal.await();
    }
}