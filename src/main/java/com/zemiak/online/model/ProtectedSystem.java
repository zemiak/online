package com.zemiak.online.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQueries({
    @NamedQuery(name = "ProtectedSystem.findAll", query = "select e from ProtectedSystem e where e.disabled != true order by e.lastSeen desc"),
    @NamedQuery(name = "ProtectedSystem.findByName", query = "select e from ProtectedSystem e where e.name = :name")
})
public class ProtectedSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int OUTAGE_MINUTES = 30;

    @Id
    @SequenceGenerator(name="pk_sequence", sequenceName="entity_id_seq_system", allocationSize=1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
    @Basic(optional = false)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
    @XmlTransient
    private Set<Outage> outages;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSeen;

    @NotNull
    private Boolean disabled;

    public ProtectedSystem() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Outage> getOutages() {
        return outages;
    }

    public void setOutages(Set<Outage> outages) {
        this.outages = outages;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final ProtectedSystem other = (ProtectedSystem) obj;
        return Objects.equals(this.id, other.id);
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public static ProtectedSystem create() {
        ProtectedSystem system = new ProtectedSystem();
        system.setLastSeen(new Date());
        system.setCreated(system.getLastSeen());
        system.setDisabled(false);

        return system;
    }

    public Boolean isOutage() {
        if (null == getLastSeen()) {
            return true;
        }
        
        long diff = (new Date().getTime()) - getLastSeen().getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff) > OUTAGE_MINUTES;
    }
}
