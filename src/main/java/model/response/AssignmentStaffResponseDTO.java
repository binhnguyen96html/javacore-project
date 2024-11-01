package model.response;

public class AssignmentStaffResponseDTO {

    private Long id;
    private String fullName;
    private boolean checked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "AssignmentStaffResponseDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", checked=" + checked +
                '}';
    }

  
}
