package freelance.project.bank_system.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String averageName;

    @Column(nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(nullable = false)
    private String passportSeriesAndNumber;

    @Column(nullable = false)
    private LocalDateTime dateOfBirth;

    @Column(nullable = false)
    private LocalDateTime dataEntryDate = LocalDateTime.now();

    @OneToOne(mappedBy = "data")
    private User user;

    public Data(){}

    public Data(String firstName, String averageName, String lastName, String email,
                String passportSeriesAndNumber, LocalDateTime dateOfBirth, User user) {
        this.firstName = firstName;
        this.averageName = averageName;
        this.lastName = lastName;
        this.email = email;
        this.passportSeriesAndNumber = passportSeriesAndNumber;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAverageName() {
        return averageName;
    }

    public void setAverageName(String averageName) {
        this.averageName = averageName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportSeriesAndNumber() {
        return passportSeriesAndNumber;
    }

    public void setPassportSeriesAndNumber(String passportSeriesAndNumber) {
        this.passportSeriesAndNumber = passportSeriesAndNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getDataEntryDate() {
        return dataEntryDate;
    }

    public void setDataEntryDate(LocalDateTime dataEntryDate) {
        this.dataEntryDate = dataEntryDate;
    }
}
