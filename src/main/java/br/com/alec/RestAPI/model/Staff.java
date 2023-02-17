package br.com.alec.RestAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=128)
    private String fullname;
    @Column(length=11)
    @NotNull
    private String cpf;
    @Column(length=32)
    private String role;
    @Column(length=1)
    @NotNull
    @Convert(converter = StatusConv.class)
    private Status status;

    public Staff() {
    }

    public Staff(long id, String fullname, String cpf, String role) {
        this.id = id;
        this.fullname = fullname;
        this.cpf = cpf;
        this.role = role;
    }

    public Staff(long id, String fullname, String cpf, String role, Status status) {
        this.id = id;
        this.fullname = fullname;
        this.cpf = cpf;
        this.role = role;
        this.status = status;
    }

    public Staff(String fullname, String cpf, String role, Status status) {
        this.fullname = fullname;
        this.cpf = cpf;
        this.role = role;
        this.status = status;
    }

    public Staff(String fullname, String cpf, String role) {
        this.fullname = fullname;
        this.cpf = cpf;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
