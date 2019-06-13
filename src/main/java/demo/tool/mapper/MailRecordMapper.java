package demo.tool.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import demo.tool.pojo.MailRecord;

public interface MailRecordMapper {
    int insert(MailRecord record);

    int insertSelective(MailRecord record);
    
    int cleanMailRecord(@Param("validTime")Date validTime);
    
}