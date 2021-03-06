package ca.albertahealthservices.contacttracing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lca/albertahealthservices/contacttracing/PlotActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 1, 16})
/* compiled from: PlotActivity.kt */
public final class PlotActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public String TAG = "PlotActivity";
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_plot);
        WebView webView = (WebView) _$_findCachedViewById(R.id.webView);
        String str = "webView";
        Intrinsics.checkExpressionValueIsNotNull(webView, str);
        webView.setWebViewClient(new WebViewClient());
        WebView webView2 = (WebView) _$_findCachedViewById(R.id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView2, str);
        WebSettings settings = webView2.getSettings();
        Intrinsics.checkExpressionValueIsNotNull(settings, "webView.settings");
        settings.setJavaScriptEnabled(true);
        int intExtra = getIntent().getIntExtra("time_period", 1);
        Observable create = Observable.create(new PlotActivity$onCreate$observableStreetRecords$1(this));
        String str2 = "Observable.create<List<S….onNext(result)\n        }";
        Intrinsics.checkExpressionValueIsNotNull(create, str2);
        Observable create2 = Observable.create(new PlotActivity$onCreate$observableStatusRecords$1(this));
        Intrinsics.checkExpressionValueIsNotNull(create2, str2);
        Disposable subscribe = Observable.zip(create, create2, PlotActivity$onCreate$zipResult$1.INSTANCE).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe((Consumer<? super T>) new PlotActivity$onCreate$zipResult$2<Object>(this, intExtra));
        String str3 = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("zipResult: ");
        sb.append(subscribe);
        Log.d(str3, sb.toString());
        ((WebView) _$_findCachedViewById(R.id.webView)).loadData("Loading...", "text/html", "UTF-8");
    }
}
