package be.kdg.film_project.controller.api.dto.director;

public class DirectorDto {
    private int id;
    public String directorName;
    public int birth;
    public String award;

    public DirectorDto() {
    }

    public DirectorDto(int id, String directorName, int birth, String award) {
        this.id = id;
        this.directorName = directorName;
        this.birth = birth;
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
