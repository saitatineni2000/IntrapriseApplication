package com.catapult.android.model;

/**
 * Created by madhuvanam on 6/4/15.
 */
public class Staff {
     String mlastName;
     String mfirstName;
     int mstaffId;
     String mRole;
     String mstartTime;
     String moutTime;
     String mPhone;

    public Staff() {
    }

    public Staff(String mlastName, String mfirstName, int mstaffId, String mRole, String mstartTime, String moutTime, String mPhone, String mEmail, String mStatus) {
        this.mlastName = mlastName;
        this.mfirstName = mfirstName;
        this.mstaffId = mstaffId;
        this.mRole = mRole;
        this.mstartTime = mstartTime;
        this.moutTime = moutTime;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.mStatus = mStatus;
    }

    String mEmail;
     String mStatus;

    public String getMlastName() {
        return mlastName;
    }

    public void setMlastName(String mlastName) {
        this.mlastName = mlastName;
    }

    public String getMfirstName() {
        return mfirstName;
    }

    public void setMfirstName(String mfirstName) {
        this.mfirstName = mfirstName;
    }

    public int getMstaffId() {
        return mstaffId;
    }

    public void setMstaffId(int mstaffId) {
        this.mstaffId = mstaffId;
    }

    public String getmRole() {
        return mRole;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public String getMstartTime() {
        return mstartTime;
    }

    public void setMstartTime(String mstartTime) {
        this.mstartTime = mstartTime;
    }

    public String getMoutTime() {
        return moutTime;
    }

    public void setMoutTime(String moutTime) {
        this.moutTime = moutTime;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

}
