package com.stuportal.usermanager.models;

import com.stuportal.usermanager.exceptions.serverExceptions.InternalServerException;
import com.stuportal.usermanager.utilities.DatePrefixedSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GenericGenerator(
            name = "userSeq",
            strategy = "com.stuportal.usermanager.utilities.DatePrefixedSequenceIdGenerator",
            parameters = @Parameter(name = DatePrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50")
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private String id;

    private String firstName;

    private String lastName;

    private String fatherName;

    @Column(unique = true)
    private String idNumber;

    private String email;

    private String phoneNumber;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private Role role;

    public User(){}

    public User(String idNumber, String firstName, String lastName, Role role){
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        this.password = hashtext;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String generateAndSetPass(int digit) throws InternalServerException{
        Random rand = new Random();
        String pass = String.format("%0" + digit + "d", rand.nextInt(10000));
        try{
            this.setPassword(pass);
            return pass;
        }catch (Exception e){
            throw new InternalServerException();
        }
    }

    public boolean authenticate(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return this.password.equals(hashtext);
    }
}