package shop.mtcoding.myapp.model.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.mtcoding.myapp.dto.user.LoginReqDto;

@Mapper
public interface UserRepository {

    public User findByUsernameAndPassword(LoginReqDto loginReqDto);

    public int insert(User user);

    public int updateById(User user);

    public int deleteById(int id);

    public List<User> findAll();

    public User findById(int id);

}
