package com.github.xuning888.helloim.gateway.core.manage;


/**
 * 小根堆轻量实现
 *
 * @author xuning
 * @date 2026/2/12 17:21
 */
public final class InFlightPqueue {

    private int size;

    private RetryMessage[] messages;

    public InFlightPqueue(int initCap) {
        messages = new RetryMessage[initCap];
        this.size = 0;
    }

    public void push(RetryMessage msg) {
        if (msg == null) {
            return;
        }
        int n = this.size;
        int c = messages.length;
        if (n + 1 > c) { // 扩容
            RetryMessage[] copy = new RetryMessage[c << 1];
            System.arraycopy(messages, 0, copy, 0, n);
            messages = copy;
        }
        msg.setIndex(n);
        messages[n] = msg;
        this.size++;
        this.up(n);
    }

    public RetryMessage pop() {
        int n = this.size;
        int c = messages.length;
        if (n == 0) {
            return null;
        }
        swap(0, n - 1);
        down(0, n - 1);
        if (n < (c >> 1) && c > 16) {
            RetryMessage[] copy = new RetryMessage[c >> 1];
            System.arraycopy(messages, 0, copy, 0, n);
            messages = copy;
        }
        RetryMessage remove = messages[n - 1];
        remove.setIndex(-1);
        messages[n - 1] = null;
        this.size--;
        return remove;
    }

    public RetryMessage remove(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        int n = this.size;
        if (i != n - 1) {
            swap(i, n - 1);
            down(i, n - 1);
            up(i);
        }
        RetryMessage remove = messages[n - 1];
        remove.setIndex(-1);
        messages[n - 1] = null;
        this.size--;
        return remove;
    }

    public RetryMessage peekAndShift(long max) {
        if (this.size == 0) {
            return null;
        }
        RetryMessage first = messages[0];
        if (first.getPri() > max) {
            return null;
        }
        this.pop();
        return first;
    }

    public RetryMessage peek() {
        if (this.size == 0) {
            return null;
        }
        return messages[0];
    }

    public int size() {
        return this.size;
    }

    private void up(int i) {
        int j = (i - 1) / 2;
        while (messages[i].getPri() <= messages[j].getPri()) {
            swap(i, j);
            i = j;
            j = (i - 1) / 2;
            if (i == j) {
                break;
            }
        }
    }

    private void down(int i, int n) {
        while (i < n) {
            int l = i * 2 + 1;
            int t = i;
            if (l < n && messages[t].getPri() > messages[l].getPri()) {
                t = l;
            }
            int r = l + 1;
            if (r < n && messages[t].getPri() > messages[r].getPri()) {
                t = r;
            }
            if (i == t) {
                break;
            }
            swap(i, t);
            i = t;
        }
    }

    private void swap(int i, int j) {
        if (i == j) {
            return;
        }
        RetryMessage temp = messages[i];
        messages[i] = messages[j];
        messages[j] = temp;

        // reset index
        messages[i].setIndex(i);
        messages[j].setIndex(j);
    }
}
