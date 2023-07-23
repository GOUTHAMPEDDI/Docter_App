package com.GouthamPeddi.DoctorApp.controller;

import com.GouthamPeddi.DoctorApp.model.Appointment;
import com.GouthamPeddi.DoctorApp.model.Patient;
import com.GouthamPeddi.DoctorApp.model.dto.SignInInput;
import com.GouthamPeddi.DoctorApp.model.dto.SignUpOutput;
import com.GouthamPeddi.DoctorApp.service.AuthenticationService;
import com.GouthamPeddi.DoctorApp.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("SignUP")
    public SignUpOutput signUpPatient(@RequestBody @Valid Patient patient){
        return patientService.signUpPatient(patient);
    }

    @PostMapping("SignIn")
    public String signInPatient(@RequestBody SignInInput signInInput){
        return patientService.signInPatient(signInInput);
    }

    @DeleteMapping("SignOut")
    public String signOutPatient(@RequestParam String email,@RequestParam String Token){
        if(authenticationService.authenticate(email,Token)){
            return patientService.signOutPatient(email);
        }else{
            return "Sign out not allowed for non authenticated user.";
        }
    }

    @PostMapping("Appointment/schedule/{email}/{tokenVal}")
    public String scheduleAppointment(@RequestBody Appointment appointment, @PathVariable String email , @PathVariable String tokenVal){
        if(authenticationService.authenticate(email,tokenVal)){
            boolean status = patientService.scheduleAppointment(appointment);
            return status ? "appointment scheduled":"error occurred during scheduling appointment";
        }else{
            return "Scheduling failed because invalid authentication";
        }

    }

    @DeleteMapping("appointment/cancel/{email}/{token}")
    public String  cancelAppointment(@PathVariable String email,@PathVariable String token) {

        if(authenticationService.authenticate(email,token)) {
            patientService.cancelAppointment(email);
            return "canceled Appointment successfully";
        }
        else
        {
            return "Scheduling failed because invalid authentication";
        }
    }

    @GetMapping("patients")
    List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }


}
