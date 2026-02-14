package com.github.xuning888.helloim.contract.util.timer;

public class SystemTimerReaper implements Timer {
    private static final long WORK_TIMEOUT_MS = 200L;

    class Reaper extends ShutdownableThread {
        Reaper(String name) {
            super(name, false);
        }

        @Override
        public void doWork() {
            try {
                timer.advanceClock(WORK_TIMEOUT_MS);
            } catch (InterruptedException ex) {
                // Ignore.
            }
        }
    }

    private final Timer timer;
    private final Reaper reaper;

    public SystemTimerReaper(String reaperThreadName, Timer timer) {
        this.timer = timer;
        this.reaper = new Reaper(reaperThreadName);
        this.reaper.start();
    }

    @Override
    public void add(TimerTask timerTask) {
        timer.add(timerTask);
    }

    @Override
    public boolean advanceClock(long timeoutMs) throws InterruptedException {
        return timer.advanceClock(timeoutMs);
    }

    @Override
    public int size() {
        return timer.size();
    }

    @Override
    public void scheduler(long intervalMs, Runnable runnable) {
        this.timer.scheduler(intervalMs, runnable);
    }

    @Override
    public void close() throws Exception {
        reaper.initiateShutdown();
        // Improve shutdown time by waking up the reaper thread
        // blocked on poll by sending a no-op.
        timer.add(new TimerTask(0) {
            @Override
            public void run() {}
        });
        reaper.awaitShutdown();
        timer.close();
    }
}
