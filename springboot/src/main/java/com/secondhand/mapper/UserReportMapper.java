package com.secondhand.mapper;

import com.secondhand.entity.UserReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface UserReportMapper {
    @Insert("INSERT INTO user_report(report_user_id,reported_user_id,product_id,report_type,report_reason,report_content) " +
            "VALUES(#{reportUserId},#{reportedUserId},#{productId},#{reportType},#{reportReason},#{reportContent})")
    int insert(UserReport ur);

    @Select("SELECT * FROM user_report")
    List<UserReport> selectAll();
}