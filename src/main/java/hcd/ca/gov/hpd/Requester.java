/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcd.ca.gov.hpd;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ssarkar
 */
@Entity
@Table(name = "requester", catalog = "hpd", schema = "")
@NamedQueries({
    @NamedQuery(name = "Requester.findAll", query = "SELECT r FROM Requester r"),
    @NamedQuery(name = "Requester.findById", query = "SELECT r FROM Requester r WHERE r.id = :id"),
    @NamedQuery(name = "Requester.findByLastName", query = "SELECT r FROM Requester r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "Requester.findByFirstName", query = "SELECT r FROM Requester r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Requester.findByEmailAddress", query = "SELECT r FROM Requester r WHERE r.emailAddress = :emailAddress"),
    @NamedQuery(name = "Requester.findByJurisdiction", query = "SELECT r FROM Requester r WHERE r.jurisdiction = :jurisdiction"),
    @NamedQuery(name = "Requester.findByCountry", query = "SELECT r FROM Requester r WHERE r.country = :country"),
    @NamedQuery(name = "Requester.findByDocumentType", query = "SELECT r FROM Requester r WHERE r.documentType = :documentType")})
public class Requester implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 255)
    @Column(name = "jurisdiction")
    private String jurisdiction;
    @Size(max = 255)
    @Column(name = "country")
    private String country;
    @Size(max = 255)
    @Column(name = "document_type")
    private String documentType;

    public Requester() {
    }

    public Requester(Integer id) {
        this.id = id;
    }

    public Requester(Integer id, String lastName, String firstName, String emailAddress) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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
        if (!(object instanceof Requester)) {
            return false;
        }
        Requester other = (Requester) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hcd.ca.gov.hpd.Requester[ id=" + id + " ]";
    }
    
}
