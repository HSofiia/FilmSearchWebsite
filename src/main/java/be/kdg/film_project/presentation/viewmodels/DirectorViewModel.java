package be.kdg.film_project.presentation.viewmodels;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class DirectorViewModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "directorName should not be empty!")
    public String directorName;

    @Positive(message = "The birth year should be positive")
    public int birth;

    @NotBlank(message = "award field should not be empty!")
    public String award;

    public DirectorViewModel(int id, String name, int birth, String award) {
        this.id = id;
        this.directorName = name;
        this.birth = birth;
        this.award = award;
    }

    public DirectorViewModel(String directorName, int birth, String award) {
        this.directorName = directorName;
        this.birth = birth;
        this.award = award;
    }

    public DirectorViewModel() {
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public void setAward(String award) {
        this.award = award;
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

    public int getBirth() {
        return birth;
    }

    public String getAward() {
        return award;
    }

    @Override
    public String toString() {
        return "DirectorViewModel{" +
                "id=" + id +
                ", directorName='" + directorName + '\'' +
                ", birth=" + birth +
                ", award='" + award + '\'' +
                '}';
    }
}
