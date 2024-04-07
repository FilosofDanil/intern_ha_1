package entities;
/**
 * Entity class which describes Employee.
 * * This class is only containing data about employees, their jobs and companies.
 */
public class Employee {
    private String name;
    private String surname;
    private Integer salary;
    private String companyName;
    //Soon will be changed to Date data type
    private String hiringDate;
    private String jobs;

    public Employee() {
    }

    //Soon may be added some patterns, in order to initialize our entity, like Builder or Factory


    public Employee(String name, String surname, Integer salary, String companyName, String hiringDate, String jobs) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.companyName = companyName;
        this.hiringDate = hiringDate;
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public String getJobs() {
        return jobs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                ", companyName='" + companyName + '\'' +
                ", hiringDate='" + hiringDate + '\'' +
                ", jobs='" + jobs + '\'' +
                '}';
    }
}
