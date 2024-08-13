/*
 * The MIT License
 *
 * Copyright 2019 Dr M H B Ariyaratne<buddhika.ari@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lk.gov.health.phsp.entity;

import lk.gov.health.phsp.enums.InstitutionType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import lk.gov.health.phsp.enums.OwnershipType;
import lk.gov.health.phsp.pojcs.Nameable;

/**
 *
 * @author Dr M H B Ariyaratne, buddhika.ari@gmail.com
 */
@Entity
public class Institution implements Serializable, Nameable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;

    @Enumerated(EnumType.ORDINAL)
    private OwnershipType ownershipType;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "TNAME", length = 100)
    private String tname;

    @Column(name = "SNAME", length = 100)
    private String sname;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "MOBILE", length = 20)
    private String mobile;

    @Column(name = "WEB", length = 100)
    private String web;

    @Column(name = "CITYNAME", nullable = false, length = 100)
    private String cityName;

    @Column(name = "POINUMBER", length = 50)
    private String poiNumber;

    @ManyToOne
    private Institution supplyInstitution;
    @ManyToOne
    private Institution alternativeSupplyInstitution;
    private Long lastHin;

    @ManyToOne
    private Institution parent;
    @ManyToOne
    private Area gnArea;
    @ManyToOne
    private Area phmArea;
    @ManyToOne
    private Area phiArea;
    @ManyToOne
    private Area dsDivision;
    @ManyToOne
    private Area mohArea;
    @ManyToOne
    private Area district;
    @ManyToOne
    private Area rdhsArea;
    @ManyToOne
    private Area province;
    @ManyToOne
    private Area pdhsArea;

    @ManyToOne
    private WebUser creater;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;
    @ManyToOne
    private WebUser editer;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date editedAt;
    //Retairing properties
    private boolean retired;
    @ManyToOne
    private WebUser retirer;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date retiredAt;
    private String retireComments;

    private boolean pmci;

    private Double currentStock;

    @Transient
    InstitutionType institutionTypeRootTrans;

    @Transient
    private String displayName;

    @Transient
    private String insName;

    @Override
    public Long getId() {
        return id;
    }

    public InstitutionType getInstitutionTypeRootTrans() {
        if (getInstitutionType() == null) {
            institutionTypeRootTrans = InstitutionType.Other;
        }
        switch (getInstitutionType()) {
            case Base_Hospital:
            case District_General_Hospital:
            case Divisional_Hospital:
            case Hospital:
            case Ministry_of_Health:
            case National_Hospital:
            case Teaching_Hospital:
            case Provincial_General_Hospital:
            case Indigenous_Medicine_Department:
            case Ayurvedic_Department:
            case Provincial_Ayurvedic_Department:
            case District_Ayurvedic_Department:
            case Ayurvedic_Hospital:
            case Herbal_Guardian:

                institutionTypeRootTrans = InstitutionType.Hospital;
            default:
                institutionTypeRootTrans = getInstitutionType();
        }
        return institutionTypeRootTrans;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (id == null) {
            return null;
        } else {
            return id.toString();
        }
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public WebUser getCreater() {
        return creater;
    }

    public void setCreater(WebUser creater) {
        this.creater = creater;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public WebUser getEditer() {
        return editer;
    }

    public void setEditer(WebUser editer) {
        this.editer = editer;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public WebUser getRetirer() {
        return retirer;
    }

    public void setRetirer(WebUser retirer) {
        this.retirer = retirer;
    }

    public Date getRetiredAt() {
        return retiredAt;
    }

    public void setRetiredAt(Date retiredAt) {
        this.retiredAt = retiredAt;
    }

    public String getRetireComments() {
        return retireComments;
    }

    public void setRetireComments(String retireComments) {
        this.retireComments = retireComments;
    }

    public String getLastPartOfName() {
        if (name == null) {
            return "";
        }
        return name.substring(name.lastIndexOf(" ") + 1);
    }

    public Long getLastHin() {
        return lastHin;
    }

    public void setLastHin(Long lastHin) {
        this.lastHin = lastHin;
    }

    public String getPoiNumber() {
        return poiNumber;
    }

    public void setPoiNumber(String poiNumber) {
        this.poiNumber = poiNumber;
    }

    public Institution getParent() {
        return parent;
    }

    public void setParent(Institution parent) {
        this.parent = parent;
    }

    public Area getGnArea() {
        return gnArea;
    }

    public void setGnArea(Area gnArea) {
        this.gnArea = gnArea;
    }

    public Area getPhmArea() {
        return phmArea;
    }

    public void setPhmArea(Area phmArea) {
        this.phmArea = phmArea;
    }

    public Area getPhiArea() {
        return phiArea;
    }

    public void setPhiArea(Area phiArea) {
        this.phiArea = phiArea;
    }

    public Area getDsDivision() {
        return dsDivision;
    }

    public void setDsDivision(Area dsDivision) {
        this.dsDivision = dsDivision;
    }

    public Area getDistrict() {
        return district;
    }

    public void setDistrict(Area district) {
        this.district = district;
    }

    public Area getRdhsArea() {
        return rdhsArea;
    }

    public void setRdhsArea(Area rdhsArea) {
        this.rdhsArea = rdhsArea;
    }

    public Area getProvince() {
        return province;
    }

    public void setProvince(Area province) {
        this.province = province;
    }

    public Area getPdhsArea() {
        return pdhsArea;
    }

    public void setPdhsArea(Area pdhsArea) {
        this.pdhsArea = pdhsArea;
    }

    public Institution getSupplyInstitution() {
        return supplyInstitution;
    }

    public void setSupplyInstitution(Institution supplyInstitution) {
        this.supplyInstitution = supplyInstitution;
    }

    public boolean isPmci() {
        return pmci;
    }

    public void setPmci(boolean pmci) {
        this.pmci = pmci;
    }

    public Area getMohArea() {
        return mohArea;
    }

    public void setMohArea(Area mohArea) {
        this.mohArea = mohArea;
    }

    public String getDisplayName() {
        if (this.name == null) {
            displayName = "";
        } else {
            displayName = this.name;
        }
        return displayName;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCode() {
        if (code == null || code.trim().equals("")) {
            String tmpName = name + "    ";
            code = tmpName.substring(0, 3);
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public Double getCurrentStock() {
        if (currentStock == null) {
            currentStock = 0.0;
        }
        return currentStock;
    }

    public void setCurrentStock(Double currentStock) {
        this.currentStock = currentStock;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Institution getAlternativeSupplyInstitution() {
        return alternativeSupplyInstitution;
    }

    public void setAlternativeSupplyInstitution(Institution alternativeSupplyInstitution) {
        this.alternativeSupplyInstitution = alternativeSupplyInstitution;
    }

    public OwnershipType getOwnershipType() {
        if (ownershipType == null) {
            ownershipType = OwnershipType.CPC_OWNED;
        }
        return ownershipType;
    }

    public void setOwnershipType(OwnershipType ownershipType) {
        this.ownershipType = ownershipType;
    }

}
