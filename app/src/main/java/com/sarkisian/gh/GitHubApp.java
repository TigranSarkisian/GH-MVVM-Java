package com.sarkisian.gh;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.sarkisian.gh.di.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;


public class GitHubApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        installLeakCanary();
        turnOnStrictMode();
        RxJavaPlugins.setErrorHandler(throwable -> Timber.e(throwable.toString()));
        Timber.plant(new Timber.DebugTree());
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    private void installLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void turnOnStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

}
