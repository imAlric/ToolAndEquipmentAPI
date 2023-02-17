package br.com.alec.RestAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=128)
    private String fullname;
    @Column(length=11)
    @NotNull
    private String cpf;
    @Column(length=11)
    private String phone;
    @Column(length=82)
    private String email;
    @Column(length=1)
    @NotNull
    @Convert(converter = StatusConv.class)
    private Status status;

    public Customer() {
    }

    public Customer(String fullname, String cpf, String email, String phone) {
        this.fullname = fullname;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
    }

    public Customer(long id, String fullname, String cpf, String email, String phone, Status status) {
        this.id = id;
        this.fullname = fullname;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Customer(String fullname, String cpf, String email, String phone, Status status) {
        this.fullname = fullname;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public Customer(long id, String fullname, String cpf, String email, String phone) {
        this.id = id;
        this.fullname = fullname;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
    }

    @PrePersist
    public void prePersist(){
        setStatus(Status.Active);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
