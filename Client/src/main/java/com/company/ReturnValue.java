package com.company;

/**
 * Created by John on 4/30/2017.
 */
public class ReturnValue {
    private boolean retValue_ = false;
    public void SetValue(boolean retValue) {
        retValue_ = retValue;
    }
    public boolean GetValue() {
        return retValue_;
    }
}