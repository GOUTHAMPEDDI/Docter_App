package com.GouthamPeddi.DoctorApp.service;

import com.GouthamPeddi.DoctorApp.model.Doctor;
import com.GouthamPeddi.DoctorApp.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    IDoctorRepo doctorRepo;

    public List<Doctor> getAllDoctors(){
        return doctorRepo.findAll();
    }

    public String addDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
        return "doctor added.";
    }
}
