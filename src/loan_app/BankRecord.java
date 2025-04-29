package loan_app;

public class BankRecord {
	public String id, sex, region, married, car, saveAct, currentAct, mortgage, pep;
    public int age, children;
    public double income;

    public BankRecord(String id, int age, String sex, String region, double income, String married,
                      int children, String car, String saveAct, String currentAct, String mortgage, String pep) {
        this.id = id;
        this.age = age;
        this.sex = sex;
        this.region = region;
        this.income = income;
        this.married = married;
        this.children = children;
        this.car = car;
        this.saveAct = saveAct;
        this.currentAct = currentAct;
        this.mortgage = mortgage;
        this.pep = pep;
    }

    // Getters
    public String getId() { return id; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public String getRegion() { return region; }
    public double getIncome() { return income; }
    public String getMarried() { return married; }
    public int getChildren() { return children; }
    public String getCar() { return car; }
    public String getSaveAct() { return saveAct; }
    public String getCurrentAct() { return currentAct; }
    public String getMortgage() { return mortgage; }
    public String getPep() { return pep; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setAge(int age) { this.age = age; }
    public void setSex(String sex) { this.sex = sex; }
    public void setRegion(String region) { this.region = region; }
    public void setIncome(double income) { this.income = income; }
    public void setMarried(String married) { this.married = married; }
    public void setChildren(int children) { this.children = children; }
    public void setCar(String car) { this.car = car; }
    public void setSaveAct(String saveAct) { this.saveAct = saveAct; }
    public void setCurrentAct(String currentAct) { this.currentAct = currentAct; }
    public void setMortgage(String mortgage) { this.mortgage = mortgage; }
    public void setPep(String pep) { this.pep = pep; }
}
