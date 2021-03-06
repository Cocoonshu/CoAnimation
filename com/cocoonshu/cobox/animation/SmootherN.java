package com.cocoonshu.cobox.animation;

/**
 * Smoother of Value
 * @author Cocoonshu
 * @date 2015-03-26 17:04:39
 */
public class SmootherN {

    private float   mFactor            = 1E-1F;
    private float   mError             = 1E-2F;
    private float[] mCurrentValues     = null;
    private float[] mDestinationValues = null;
    private boolean mIsBypassed        = false;
    

    public SmootherN(int n) {
        mCurrentValues = new float[n];
        mDestinationValues = new float[n];
    }

    public SmootherN(int n, float factor, float error) {
        mCurrentValues = new float[n];
        mDestinationValues = new float[n];
        mFactor = factor;
        mError = error;
    }

    public void setBypassed(boolean isBypassed) {
        mIsBypassed = isBypassed;
    }

    public boolean isBypassed() {
        return mIsBypassed;
    }

    public boolean smooth() {
        if (mIsBypassed) {
            int loopSize = mCurrentValues.length;
            for (int i = 0; i < loopSize; i++) {
                mCurrentValues[i] = mDestinationValues[i];
            }
            return false;
        }

        
        float destinationSum = 0;
        float currentSum     = 0;
        {
            int loopSize = mCurrentValues.length;
            for (int i = 0; i < loopSize; i++) {
                mCurrentValues[i] = mCurrentValues[i] + (mDestinationValues[i] - mCurrentValues[i]) * mFactor;
                destinationSum += mDestinationValues[i];
                currentSum += mCurrentValues[i];
            }
        }
        if (Math.abs(destinationSum - currentSum) > mError) {
            return true;
        } else {
            int loopSize = mCurrentValues.length;
            for (int i = 0; i < loopSize; i++) {
                mCurrentValues[i] = mDestinationValues[i];
            }
            return false;
        }
    }

    public void forceFinish() {
        int loopSize = mCurrentValues.length;
        for (int i = 0; i < loopSize; i++) {
            mCurrentValues[i] = mDestinationValues[i];
        }
    }

    public final float getError() {
        return mError;
    }

    public final float getFactor() {
        return mFactor;
    }

    public float getCurrentValue(int index) {
        return mCurrentValues[index];
    }

    public float getDestinationValue(int index) {
        return mDestinationValues[index];
    }
    
    public void setCurrentValue(int index, float value) {
    	mCurrentValues[index] = value;
    }
    
    public void setDestinationValue(int index, float value) {
    	mDestinationValues[index] = value;
    }

    public void setCurrentValue(float...currentValues) {
        int currentValuesSize = currentValues.length < mCurrentValues.length ?
                                currentValues.length : mCurrentValues.length;
        for (int i = 0; i < currentValuesSize; i++) {
            mCurrentValues[i] = currentValues[i];
        }
    }

    public void setDestinationValue(float...destinationValues) {
        int destinationValuesSize = destinationValues.length < mDestinationValues.length ?
                                    destinationValues.length : mDestinationValues.length;
        for (int i = 0; i < destinationValuesSize; i++) {
            mDestinationValues[i] = destinationValues[i];
        }
    }
}