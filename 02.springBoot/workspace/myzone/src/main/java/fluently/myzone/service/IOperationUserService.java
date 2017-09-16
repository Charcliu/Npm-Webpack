package fluently.myzone.service;

import java.util.List;

import fluently.myzone.model.UserVO;

public interface IOperationUserService {
	
	public void addUser(UserVO user);
	
	public List<UserVO> queryAllUser();
}
