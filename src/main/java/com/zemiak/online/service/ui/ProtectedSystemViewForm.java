package com.zemiak.online.service.ui;

import com.zemiak.online.model.ProtectedSystem;
import com.zemiak.online.service.OutageService;
import com.zemiak.online.service.ProtectedSystemService;
import java.io.Serializable;
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

    public int getAll() {
        return outages.findByProtectedSystem(id).size();
    }

    public int getLastMonth() {
        return outages.findByProtectedSystemAndInterval(id, "1 month").size();
    }

    public int getLastYear() {
        return outages.findByProtectedSystemAndInterval(id, "1 year").size();
    }
}
