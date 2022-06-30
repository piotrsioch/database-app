package sample.LoggedIn.Medicines;


import java.util.Date;

public class Medicine {
    private int id;
    private String name;
    private float dose;
    private Date useDuration;
    private String purpose;
    private String status;

    public Medicine(String name, float dose, Date useDuration, String purpose, String status) {
        this.name = name;
        this.dose = dose;
        this.useDuration = useDuration;
        this.purpose = purpose;
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String taken) {
        status = taken;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public Date getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(Date useDuration) {
        this.useDuration = useDuration;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
