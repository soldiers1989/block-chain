package com.xdaocloud.futurechain.dto.resp.user;

import com.xdaocloud.futurechain.dto.resp.orders.AchievementResponse;
import com.xdaocloud.futurechain.dto.user.AssetsDTO;

import java.io.Serializable;

public class WebUserResponse implements Serializable {

    private static final long serialVersionUID = 3887348366681779458L;


    private UserInfoResponse userInfo;

    /**
     * a级代理
     */
    private AchievementResponse achievement_a;

    /**
     * b级代理
     */
    private AchievementResponse achievement_b;


    /**
     * 用户资产
     */
    private AssetsDTO assets;


    public AssetsDTO getAssets() {
        return assets;
    }

    public void setAssets(AssetsDTO assets) {
        this.assets = assets;
    }

    public UserInfoResponse getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoResponse userInfo) {
        this.userInfo = userInfo;
    }

    public AchievementResponse getAchievement_a() {
        return achievement_a;
    }

    public void setAchievement_a(AchievementResponse achievement_a) {
        this.achievement_a = achievement_a;
    }

    public AchievementResponse getAchievement_b() {
        return achievement_b;
    }

    public void setAchievement_b(AchievementResponse achievement_b) {
        this.achievement_b = achievement_b;
    }


}
