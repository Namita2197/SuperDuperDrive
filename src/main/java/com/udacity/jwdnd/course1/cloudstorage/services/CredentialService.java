package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public String createKey(){
        String key = RandomStringUtils.random(16, true, true);
        return key;
    }
    public String encryptPassword(String password, String key){
        return encryptionService.encryptValue(password, key);
    }
    public int deleteCredential(int credentialid){
        return this.credentialMapper.delete(credentialid);
    }

    public int addCredential(Credential credential){
        return this.credentialMapper.insert(credential);
    }
    public int updateCredential(Credential credential){
        return this.credentialMapper.update(credential);
    }
    public List<Credential> getCredentials(User user) throws Exception {
        List<Credential> credentials = credentialMapper.getAllCredentialsFromUser(user);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials;
    }
}
