package be.kdg.film_project.controller.api.dto.director;

public class NewDirectorDto {

    public String directorName;
    public int birth;
    public String award;

    public NewDirectorDto() {
    }

    public NewDirectorDto(String directorName, int birth, String award) {
        this.directorName = directorName;
        this.birth = birth;
        this.award = award;
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
