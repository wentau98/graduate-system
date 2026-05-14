package com.secondhand.mapper.vice;

import com.secondhand.entity.vice.ActivityBanner;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ActivityBannerMapper {

    // 查询当前时间在活动时间内、启用的海报（按更新时间倒序，取最新3条）
    @Select("SELECT * FROM activity_banner " +
            "WHERE is_active = 1 " +
            "AND start_time <= NOW() " +
            "AND end_time >= NOW() " +
            "ORDER BY update_time DESC " +
            "LIMIT 5")
    List<ActivityBanner> selectActiveBanners();
}