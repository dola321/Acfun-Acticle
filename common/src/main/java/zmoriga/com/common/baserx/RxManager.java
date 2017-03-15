package zmoriga.com.common.baserx;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.FlowableRange;

/**
 * 用于管理单个presenter的RxBus的事件和Rxjava相关代码的生命周期处理
 */

public class RxManager {
    public RxBus mRxBus = RxBus.getInstance();
    //管理rxbus订阅
    private Map<String, Observable<?>> mObservables = new HashMap<>();
    /*管理Observables 和 Subscribers订阅*/
    private CompositeDisposable mComposite = new CompositeDisposable();

    /**
     * RxBus注入监听
     * @param eventName
     * @param consumer
     */
    public <T>void on(String eventName, Consumer<T> consumer) {
        Observable<T> mObservable = mRxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        /*订阅管理*/
        mComposite.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param d
     */
    public void add(Disposable d) {
        /*订阅管理*/
        mComposite.add(d);
    }
    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear() {
        mComposite.clear();// 取消所有订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()) {
            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除rxbus观察
        }
    }
    //发送rxbus
    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }
}
