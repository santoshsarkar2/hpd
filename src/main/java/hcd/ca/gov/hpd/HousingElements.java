/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcd.ca.gov.hpd;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ssarkar
 */
@Entity
@Table(name = "housing_elements", catalog = "hpd", schema = "")
@NamedQueries({
    @NamedQuery(name = "HousingElements.findAll", query = "SELECT h FROM HousingElements h"),
    @NamedQuery(name = "HousingElements.findById", query = "SELECT h FROM HousingElements h WHERE h.id = :id"),
    @NamedQuery(name = "HousingElements.findByJurisdiction", query = "SELECT h FROM HousingElements h WHERE h.jurisdiction = :jurisdiction"),
    @NamedQuery(name = "HousingElements.findByCounty", query = "SELECT h FROM HousingElements h WHERE h.county = :county"),
    @NamedQuery(name = "HousingElements.findByDocumentType", query = "SELECT h FROM HousingElements h WHERE h.documentType = :documentType"),
    @NamedQuery(name = "HousingElements.findByDateReceived", query = "SELECT h FROM HousingElements h WHERE h.dateReceived = :dateReceived"),
    @NamedQuery(name = "HousingElements.findByPlanningPeriod", query = "SELECT h FROM HousingElements h WHERE h.planningPeriod = :planningPeriod"),
    @NamedQuery(name = "HousingElements.findByTrackingDate", query = "SELECT h FROM HousingElements h WHERE h.trackingDate = :trackingDate"),
    @NamedQuery(name = "HousingElements.findByFilename", query = "SELECT h FROM HousingElements h WHERE h.filename = :filename")})
public class HousingElements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "jurisdiction")
    private String jurisdiction;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "county")
    private String county;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "document_type")
    private String documentType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_received")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "planning_period")
    private String planningPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tracking_date")
    @Temporal(TemporalType.DATE)
    private Date trackingDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "filename")
    private String filename;
    @Lob
    @Column(name = "fileblob")
    private byte[] fileblob;

    public HousingElements() {
    }

    public HousingElements(Integer id) {
        this.id = id;
    }

    public HousingElements(Integer id, String jurisdiction, String county, String documentType, Date dateReceived, String planningPeriod, Date trackingDate, String filename) {
        this.id = id;
        this.jurisdiction = jurisdiction;
        this.county = county;
        this.documentType = documentType;
        this.dateReceived = dateReceived;
        this.planningPeriod = planningPeriod;
        this.trackingDate = trackingDate;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getPlanningPeriod() {
        return planningPeriod;
    }

    public void setPlanningPeriod(String planningPeriod) {
        this.planningPeriod = planningPeriod;
    }

    public Date getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(Date trackingDate) {
        this.trackingDate = trackingDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFileblob() {
        return fileblob;
    }

    public void setFileblob(byte[] fileblob) {
        this.fileblob = fileblob;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HousingElements)) {
            return false;
        }
        HousingElements other = (HousingElements) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hcd.ca.gov.hpd.HousingElements[ id=" + id + " ]";
    }
    
}
