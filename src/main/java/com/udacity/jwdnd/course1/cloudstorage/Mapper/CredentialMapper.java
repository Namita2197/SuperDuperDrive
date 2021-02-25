package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username} , key = #{key}, password = #{password} WHERE credentialid=#{credentialid}")
    int update(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<Credential> getAllCredentialsFromUser(User user);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int delete(int credentialid);
}
