package com.zemiak.online.service.ui;

import com.zemiak.online.model.Outage;
import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.service.data.OutageService;
import com.zemiak.online.service.data.ProtectedSystemService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named("protectedSystemViewForm")
public class ProtectedSystemViewForm implements Serializable {
    private Integer id;
    private ProtectedSystem bean;

    @Inject
    private ProtectedSystemService service;
    
    @Inject
    private OutageService outages;

    public ProtectedSystemViewForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String check() {
        bean = service.find(id);

        if (null == bean) {
            JsfMessages.addErrorMessage("Cannot find protected system #" + id);
            return close();
        }

        return null;
    }

    public ProtectedSystem getBean() {
        return bean;
    }

    public String close() {
        return "index";
    }

    public List<Outage> getOutages() {
        return outages.findByProtectedSystem(id);
    }
}
