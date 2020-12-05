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
@Table(name = "MAINUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mainuser.findAll", query = "SELECT m FROM Mainuser m")
    , @NamedQuery(name = "Mainuser.findById", query = "SELECT m FROM Mainuser m WHERE m.id = :id")
    , @NamedQuery(name = "Mainuser.findByName", query = "SELECT m FROM Mainuser m WHERE m.name = :name")
    , @NamedQuery(name = "Mainuser.findByUsername", query = "SELECT m FROM Mainuser m WHERE m.username = :username")
    , @NamedQuery(name = "Mainuser.findByPassword", query = "SELECT m FROM Mainuser m WHERE m.password = :password")})
public class Mainuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;

    public Mainuser() {
    }

    public Mainuser(Integer id) {
        this.id = id;
    }

    public Mainuser(Integer id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(object instanceof Mainuser)) {
            return false;
        }
        Mainuser other = (Mainuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Mainuser[ id=" + id + " ]";
    }
    
    //Test Please work
    
}
