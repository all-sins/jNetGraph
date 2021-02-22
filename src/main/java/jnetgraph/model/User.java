package jnetgraph.model;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    @Email(message = "Invalid e-mail format")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "userstatus")
    private UserStatus userstatus;

    @OneToMany(mappedBy = "stcliId")
    private List<SpeedtestCLI> speedtestCLIList;

    @OneToMany(mappedBy = "tsuimpl_id")
    private List<TsuImpl> tsuImplTest;


    //TODO: I don't understand what fetch type means. It is advised not to use it, but without it I can't bypass lazy initialize fail error.
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                                                    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles= new HashSet<>();



    public List<TsuImpl> getTsuImplTest() {
        return tsuImplTest;
    }



    public void setTsuImplTest(List<TsuImpl> tsuImplTest) {
        this.tsuImplTest = tsuImplTest;
    }




    public User() {
    }



    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;



    }
    public User(Long id, String name, String surname, String email, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;



    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public UserStatus getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(UserStatus userstatus) {
        this.userstatus = userstatus;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SpeedtestCLI> getSpeedtestCLIList() {
        return speedtestCLIList;
    }

    public UserStatus getUserStatus() {
        return userstatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userstatus = userStatus;
    }

    public void setSpeedtestCLIList(List<SpeedtestCLI> speedtestCLIList) {
        this.speedtestCLIList = speedtestCLIList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", userstatus=" + userstatus +
                '}';
    }
}
