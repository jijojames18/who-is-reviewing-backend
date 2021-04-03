package com.jijojames.app.Model;

import com.jijojames.app.Enum.PRStatus;

public class UserStatus {

    private String userId;

    private PRStatus status;

    public String getUserId() {
        return this.userId;
    }

    public PRStatus getStatus() {
        return this.status;
    }
}
