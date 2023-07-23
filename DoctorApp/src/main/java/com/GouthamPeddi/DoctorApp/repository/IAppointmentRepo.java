package com.GouthamPeddi.DoctorApp.repository;

import com.GouthamPeddi.DoctorApp.model.Appointment;
import com.GouthamPeddi.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepo extends JpaRepository<Appointment , Long> {

    Appointment findFirstByPatient(Patient patient);

}
