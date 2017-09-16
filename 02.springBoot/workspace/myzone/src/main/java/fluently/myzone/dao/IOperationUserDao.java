package fluently.myzone.dao;

import java.util.List;

import fluently.myzone.model.UserVO;

public interface IOperationUserDao {

	public void addUser(UserVO user);

	public List<UserVO> queryAllUser();
}
