package br.com.alec.RestAPI.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(length=500)
    @NotBlank
    private String order_desc;
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private Tool tool;
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private Customer customer;
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private Staff staff;
    @OneToOne
    private Log log;
    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
    private Date begin_date;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING, locale = "pt-BR", timezone = "Brazil/East")
    private Date close_date;
    @Column(length = 1)
    @Convert(converter = StatusConv.class)
    private Status status;

    public Order() {
    }

    public Order(String order_desc, Date begin_date, Tool tool, Customer customer, Staff staff) {
        this.order_desc = order_desc;
        this.begin_date = begin_date;
        this.tool = tool;
        this.customer = customer;
        this.staff = staff;
    }

    @PrePersist
    public void prePersist(){
        setStatus(Status.Pending);
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getClose_date() {
        return close_date;
    }

    public void setClose_date(Date close_date) {
        this.close_date = close_date;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", order_desc='" + order_desc + '\'' +
                ", customer=" + customer.toString() +
                ", tool=" + tool.toString() +
                ", staff=" + staff.toString() +
                ", status='" + status + '\'' +
                '}';
    }
}
