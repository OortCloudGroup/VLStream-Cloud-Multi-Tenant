package org.springblade.modules.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.modules.system.pojo.entity.UserOauth;
import org.springblade.modules.system.mapper.UserOauthMapper;
import org.springblade.modules.system.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * Service implementation class
 *
 * @author Chill
 */
//@Master
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
