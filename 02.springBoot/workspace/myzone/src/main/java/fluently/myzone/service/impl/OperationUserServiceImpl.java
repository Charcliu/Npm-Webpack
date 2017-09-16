package fluently.myzone.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fluently.myzone.dao.IAddUserDao;
import fluently.myzone.model.UserVO;
import fluently.myzone.service.IOperationUserService;

@Service
public class OperationUserServiceImpl implements IOperationUserService {

	@Resource
	private IAddUserDao addUserDao;
	
	@Override
	public void addUser(UserVO user) {
		addUserDao.addUser(user);
	}

}
