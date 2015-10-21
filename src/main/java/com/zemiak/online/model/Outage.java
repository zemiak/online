package com.zemiak.online.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = "Outage.findAliveBySystem", query = "select e from Outage e where e.system = :system and e.end is null"),
    @NamedQuery(name = "Outage.findAllBySystem", query = "select e from Outage e where e.system.id = :id")
})
public class Outage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Basic(optional = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @XmlTransient
    private ProtectedSystem system;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date end;

    public Outage() {
        start = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProtectedSystem getProtectedSystem() {
        return system;
    }

    public void setProtectedSystem(ProtectedSystem system) {
        this.system = system;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Outage other = (Outage) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Outage{" + "id=" + id + ", system=" + system + ", start=" + start + ", end=" + end + '}';
    }
}
