// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.model.transit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.busko.routemanager.model.transit.Country;

privileged aspect Country_Roo_Jpa_Entity {
    
    declare @type: Country: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Country.id;
    
    @Version
    @Column(name = "version")
    private Integer Country.version;
    
    public Long Country.getId() {
        return this.id;
    }
    
    public void Country.setId(Long id) {
        this.id = id;
    }
    
    public Integer Country.getVersion() {
        return this.version;
    }
    
    public void Country.setVersion(Integer version) {
        this.version = version;
    }
    
}
