package com.GouthamPeddi.DoctorApp.service;

import com.GouthamPeddi.DoctorApp.model.AuthenticationToken;
import com.GouthamPeddi.DoctorApp.repository.IAuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthTokenRepo authTokenRepo;

    public boolean authenticate(String email,String tokenValue){
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(tokenValue);

        if(authToken==null){
            return false;
        }

        String Email = authToken.getPatient().getPatientEmail();
        return Email.equals(email);
    }


}
