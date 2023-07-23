package com.GouthamPeddi.DoctorApp.repository;

import com.GouthamPeddi.DoctorApp.model.AuthenticationToken;
import com.GouthamPeddi.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken , Long> {

    AuthenticationToken findFirstByPatient(Patient patient);


    AuthenticationToken findFirstByTokenValue(String tokenValue);

}
