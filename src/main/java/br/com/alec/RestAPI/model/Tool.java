package br.com.alec.RestAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "tools")
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=42)
    @NotBlank
    private String tool_type;
    @Column(length=82)
    @NotBlank
    private String tool_brand;
    @Column(length=1)
    @Convert(converter = StatusConv.class)
    private Status status;

    public Tool() {
    }

    public Tool(String tool_type, String tool_brand) {
        this.tool_type = tool_type;
        this.tool_brand = tool_brand;
    }

    public Tool(long id, String tool_type, String tool_brand, Status status) {
        this.id = id;
        this.tool_type = tool_type;
        this.tool_brand = tool_brand;
        this.status = status;
    }

    public Tool(String tool_type, String tool_brand, Status status) {
        this.tool_type = tool_type;
        this.tool_brand = tool_brand;
        this.status = status;
    }

    public Tool(long id, String tool_type, String tool_brand) {
        this.id = id;
        this.tool_type = tool_type;
        this.tool_brand = tool_brand;
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

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }

    public String getTool_brand() {
        return tool_brand;
    }

    public void setTool_brand(String tool_brand) {
        this.tool_brand = tool_brand;
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
                ", tool_brand='" + tool_brand + '\'' +
                ", tool_type='" + tool_type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
