package com.android.internal.telephony;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.Rlog;
import com.android.internal.annotations.VisibleForTesting;

@TargetApi(8)
public class RilWakelockInfo {
    private final String LOG_TAG = RilWakelockInfo.class.getSimpleName();
    private int mConcurrentRequests;
    private long mLastAggregatedTime;
    private long mRequestTime;
    private long mResponseTime;
    private int mRilRequestSent;
    private int mTokenNumber;
    private long mWakelockTimeAttributedSoFar;

    @VisibleForTesting
    public int getConcurrentRequests() {
        return this.mConcurrentRequests;
    }

    RilWakelockInfo(int rilRequest, int tokenNumber, int concurrentRequests, long requestTime) {
        concurrentRequests = validateConcurrentRequests(concurrentRequests);
        this.mRilRequestSent = rilRequest;
        this.mTokenNumber = tokenNumber;
        this.mConcurrentRequests = concurrentRequests;
        this.mRequestTime = requestTime;
        this.mWakelockTimeAttributedSoFar = 0;
        this.mLastAggregatedTime = requestTime;
    }

    private int validateConcurrentRequests(int concurrentRequests) {
        if (concurrentRequests > 0) {
            return concurrentRequests;
        }
        if (!Build.IS_DEBUGGABLE) {
            return 1;
        }
        IllegalArgumentException e = new IllegalArgumentException("concurrentRequests should always be greater than 0.");
        Rlog.e(this.LOG_TAG, e.toString());
        throw e;
    }

    int getTokenNumber() {
        return this.mTokenNumber;
    }

    int getRilRequestSent() {
        return this.mRilRequestSent;
    }

    void setResponseTime(long responseTime) {
        updateTime(responseTime);
        this.mResponseTime = responseTime;
    }

    void updateConcurrentRequests(int concurrentRequests, long time) {
        concurrentRequests = validateConcurrentRequests(concurrentRequests);
        updateTime(time);
        this.mConcurrentRequests = concurrentRequests;
    }

    synchronized void updateTime(long time) {
        this.mWakelockTimeAttributedSoFar += (time - this.mLastAggregatedTime) / ((long) this.mConcurrentRequests);
        this.mLastAggregatedTime = time;
    }

    long getWakelockTimeAttributedToClient() {
        return this.mWakelockTimeAttributedSoFar;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WakelockInfo{rilRequestSent=");
        stringBuilder.append(this.mRilRequestSent);
        stringBuilder.append(", tokenNumber=");
        stringBuilder.append(this.mTokenNumber);
        stringBuilder.append(", requestTime=");
        stringBuilder.append(this.mRequestTime);
        stringBuilder.append(", responseTime=");
        stringBuilder.append(this.mResponseTime);
        stringBuilder.append(", mWakelockTimeAttributed=");
        stringBuilder.append(this.mWakelockTimeAttributedSoFar);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
