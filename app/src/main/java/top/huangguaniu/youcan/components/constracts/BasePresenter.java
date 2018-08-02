package top.huangguaniu.youcan.components.constracts;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 没时间了 先不做了 不影响任何地方 release rxtask cancelRx
 * @author hyx
 * @date 18-1-15
 */

public interface BasePresenter {
    CompositeDisposable rxTask = new CompositeDisposable();
}
