package HW_Module_03;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Person {
    private String personalId;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private List<Fine> fines;

    public Person(String personalId, String firstName, String lastName, String address, String phone) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.fines = new ArrayList<>();
    }

    public void addFine(Fine fine) {
        fines.add(fine);
    }

    public boolean removeFine(String fineId) {
        return fines.removeIf(fine -> fine.getId().equals(fineId));
    }

    public Fine getFine(String fineId) {
        return fines.stream()
                .filter(fine -> fine.getId().equals(fineId))
                .findFirst()
                .orElse(null);
    }

    public List<Fine> getFinesByType(String type) {
        return fines.stream()
                .filter(fine -> fine.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Fine> getFinesByCity(String city) {
        return fines.stream()
                .filter(fine -> fine.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public double getTotalFinesAmount() {
        return fines.stream()
                .mapToDouble(Fine::getAmount)
                .sum();
    }

    public String getPersonalId() { return personalId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public List<Fine> getFines() { return new ArrayList<>(fines); }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setFines(List<Fine> fines) { this.fines = new ArrayList<>(fines); }

    @Override
    public String toString() {
        return String.format("ID: %s | Имя: %s %s | Адрес: %s | Телефон: %s | Штрафов: %d | Общая сумма: %.2f",
                personalId, firstName, lastName, address, phone, fines.size(), getTotalFinesAmount());
    }
}
