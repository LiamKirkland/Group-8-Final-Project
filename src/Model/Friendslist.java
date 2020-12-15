/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LeemKirk
 */
@Entity
@Table(name = "FRIENDSLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Friendslist.findAll", query = "SELECT f FROM Friendslist f")
    , @NamedQuery(name = "Friendslist.findById", query = "SELECT f FROM Friendslist f WHERE f.id = :id")
    , @NamedQuery(name = "Friendslist.findByList", query = "SELECT f FROM Friendslist f WHERE f.list = :list")})
public class Friendslist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LIST")
    private String list;

    public Friendslist() {
    }

    public Friendslist(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
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
        if (!(object instanceof Friendslist)) {
            return false;
        }
        Friendslist other = (Friendslist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Friendslist[ id=" + id + " ]";
    }
    
}
