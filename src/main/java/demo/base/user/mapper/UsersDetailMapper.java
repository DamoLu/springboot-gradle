package demo.base.user.mapper;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.param.mapperParam.FindActiveEmailParam;
import demo.base.user.pojo.param.mapperParam.UpdateDuplicateEmailParam;
import demo.base.user.pojo.po.UsersDetail;

public interface UsersDetailMapper {
	int insert(UsersDetail record);

    int insertSelective(UsersDetail record);
    
    int isNickNameExists(String nickName);
    
    int isActiveEmailExists(FindActiveEmailParam param);
    
    Long findUserIdByActivationEmail(FindActiveEmailParam param);
    
    String findUserNameByActivationEmail(FindActiveEmailParam param);
    
    String findEmailByUserId(Long userId);

	UsersDetail findUserDetail(Long userId);
	
	UsersDetail findUserDetailByNickName(String userName);
	
	int modifyRegistEmail(@Param("email")String email, @Param("userId")Long userId);
	
	int updateDuplicateEmail(UpdateDuplicateEmailParam param);
	
	String findHeadImage(@Param("userId")Long userId);
}