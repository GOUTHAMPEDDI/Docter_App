package com.GouthamPeddi.DoctorApp.controller;

import com.GouthamPeddi.DoctorApp.model.Doctor;
import com.GouthamPeddi.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("Doctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @PostMapping("Doctor")
    public String addDoctor(Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

}
