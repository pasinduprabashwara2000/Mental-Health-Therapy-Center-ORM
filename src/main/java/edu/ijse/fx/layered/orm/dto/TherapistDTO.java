package edu.ijse.fx.layered.orm.dto;

public class TherapistDTO {

    private int id;
    private String therapistName;
    private String programName;
    private String specialization;
    private int contactNo;

    public TherapistDTO() {
    }

    public TherapistDTO(int id, String therapistName, String programName, String specialization, int contactNo) {
        this.id = id;
        this.therapistName = therapistName;
        this.programName = programName;
        this.specialization = specialization;
        this.contactNo = contactNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTherapistName() {
        return therapistName;
    }

    public void setTherapistName(String therapistName) {
        this.therapistName = therapistName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

}
