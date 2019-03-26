package com.sarkisian.gh.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppExecutors {

    private final Executor mIoExecutor;
    private final Executor mMainThreadExecutor;

    public AppExecutors(Executor io, Executor mainThread) {
        this.mIoExecutor = io;
        this.mMainThreadExecutor = mainThread;
    }

    @Inject
    public AppExecutors() {
        this(Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    public Executor io() {
        return mIoExecutor;
    }

    public Executor mainThread() {
        return mMainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

}
