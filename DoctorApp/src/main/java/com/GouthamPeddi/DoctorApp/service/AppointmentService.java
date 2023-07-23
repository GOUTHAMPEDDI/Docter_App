package com.GouthamPeddi.DoctorApp.service;

import com.GouthamPeddi.DoctorApp.model.Appointment;
import com.GouthamPeddi.DoctorApp.model.Patient;
import com.GouthamPeddi.DoctorApp.repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;

    public List<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }

    public void scheduleAppointment(Appointment appointment) {
        appointment.setAppointmentCreationTime(LocalDateTime.now());
        appointmentRepo.save(appointment);
    }

    public Appointment getAppointmentForPatient(Patient patient) {
        return appointmentRepo.findFirstByPatient(patient);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepo.delete(appointment);
    }
}
