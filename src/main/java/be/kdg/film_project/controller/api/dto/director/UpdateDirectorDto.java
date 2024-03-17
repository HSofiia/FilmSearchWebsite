package be.kdg.film_project.controller.api.dto.director;

public class UpdateDirectorDto {
    public int birth;
    public String award;

    public UpdateDirectorDto() {
    }

    public UpdateDirectorDto(int birth, String award) {
        this.birth = birth;
        this.award = award;
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
