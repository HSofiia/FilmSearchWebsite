package be.kdg.film_project.domain;

import jakarta.persistence.*;

@Table(name = "director")
@Entity
public class Director {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "director_name")
    public String directorName;

    @Column(name = "birth")
    public int birth;

    @Column(name = "award")
    public String award;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;

    public Director(String name, int birth, String award) {
        this.directorName = name;
        this.birth = birth;
        this.award = award;
    }


    public Director(int id, String directorName, int birth, String award) {
        this.id = id;
        this.directorName = directorName;
        this.birth = birth;
        this.award = award;
    }

    public Director() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

}
