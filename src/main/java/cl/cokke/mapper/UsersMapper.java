package cl.cokke.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cl.cokke.model.Users;

@Mapper
public interface UsersMapper {

	@Select("SELECT * FROM users WHERE email = #{email}")
	public Users findByEmail(@Param("email") String email);
}
