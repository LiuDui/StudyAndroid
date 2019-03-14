package entity;

public class NumInCHEN {
    private Integer numInAbric;
    private String numInEnglish;

    public NumInCHEN(Integer numInAbric, String numInEnglish) {
        this.numInAbric = numInAbric;
        this.numInEnglish = numInEnglish;
    }

    public Integer getNumInAbric() {
        return numInAbric;
    }

    public void setNumInAbric(Integer numInAbric) {
        this.numInAbric = numInAbric;
    }

    public String getNumInEnglish() {
        return numInEnglish;
    }

    public void setNumInEnglish(String numInEnglish) {
        this.numInEnglish = numInEnglish;
    }
}
