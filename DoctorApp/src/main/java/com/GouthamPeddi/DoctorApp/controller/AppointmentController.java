package com.GouthamPeddi.DoctorApp.controller;

import com.GouthamPeddi.DoctorApp.model.Appointment;
import com.GouthamPeddi.DoctorApp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("Appointments")
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }


}
