package com.GouthamPeddi.DoctorApp.repository;

import com.GouthamPeddi.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepo extends JpaRepository<Patient , Long> {

    Patient findFirstByPatientEmail(String newEmail);

}
