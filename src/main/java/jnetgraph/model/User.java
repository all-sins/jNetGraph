package jnetgraph.model;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Component
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
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

    public User() {
    }



    public User(String name, String surname, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userstatus=" + userstatus +
                '}';
    }
}
