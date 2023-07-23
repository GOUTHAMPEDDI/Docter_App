package com.GouthamPeddi.DoctorApp.service;

import com.GouthamPeddi.DoctorApp.model.Appointment;
import com.GouthamPeddi.DoctorApp.model.AuthenticationToken;
import com.GouthamPeddi.DoctorApp.model.Patient;
import com.GouthamPeddi.DoctorApp.model.dto.SignInInput;
import com.GouthamPeddi.DoctorApp.model.dto.SignUpOutput;
import com.GouthamPeddi.DoctorApp.repository.IAuthTokenRepo;
import com.GouthamPeddi.DoctorApp.repository.IDoctorRepo;
import com.GouthamPeddi.DoctorApp.repository.IPatientRepo;
import com.GouthamPeddi.DoctorApp.service.emailUtility.EmailHandler;
import com.GouthamPeddi.DoctorApp.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    IDoctorRepo doctorRepo;

    @Autowired
    IAuthTokenRepo authTokenRepo;

    @Autowired
    AppointmentService appointmentService;


    public SignUpOutput signUpPatient(Patient patient){
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = patient.getPatientEmail();

        if(newEmail==null){
            signUpStatus = false;
            signUpStatusMessage = "invalid Email";
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        Patient existingPatient = patientRepo.findFirstByPatientEmail(newEmail);

        if(existingPatient != null){
            signUpStatus = false;
            signUpStatusMessage = "patient already registered...!";
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(patient.getPatientPassword());

            patient.setPatientPassword(encryptedPassword);

            patientRepo.save(patient);

            return new SignUpOutput(signUpStatus , "Patient registered successfully");
        }catch(Exception e){
            signUpStatus=false;
            signUpStatusMessage="Internal error occurred during sign up";
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

    }


    public String signInPatient(SignInInput signInInput){

        String signInStatusMessage = null;
        String patientEmail = signInInput.getEmail();

        if(patientEmail==null){

            signInStatusMessage = "invalid Email";
            return signInStatusMessage;
        }

        Patient existingPatient = patientRepo.findFirstByPatientEmail(patientEmail);

        if(existingPatient==null){
            signInStatusMessage="Email not registered";
            return signInStatusMessage;
        }

        try{
            String encryotedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(encryotedPassword.equals(existingPatient.getPatientPassword())){
                AuthenticationToken authToken = new AuthenticationToken(existingPatient);
                authTokenRepo.save(authToken);

                EmailHandler.sendEmail(patientEmail,"Email Testing", authToken.getTokenValue());
                return "Token sent to your Email";
            }else{
                signInStatusMessage = "invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e){

            signInStatusMessage="Internal error occured during signIn";
            return signInStatusMessage;
        }
    }

    public String signOutPatient(String email){
        Patient patient = patientRepo.findFirstByPatientEmail(email);
        authTokenRepo.delete(authTokenRepo.findFirstByPatient(patient));
        return "Patient signed out successfully.";
    }


    public boolean scheduleAppointment(Appointment appointment){
        Long patientId = appointment.getPatient().getPatientId();
        boolean isPatientValid = patientRepo.existsById(patientId);

        Long doctorId = appointment.getDoctor().getDoctorId();
        boolean isDoctorValid = doctorRepo.existsById(doctorId);

        if(isDoctorValid && isPatientValid){
            appointmentService.scheduleAppointment(appointment);
            return true;
        }else{
            return false;
        }
    }

    public String cancelAppointment(String email){
        Patient patient = patientRepo.findFirstByPatientEmail(email);
        Appointment appointment = appointmentService.getAppointmentForPatient(patient);
        appointmentService.cancelAppointment(appointment);
        return "appointment cancelled";
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }


}
