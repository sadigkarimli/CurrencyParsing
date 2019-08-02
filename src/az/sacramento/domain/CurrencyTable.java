package az.sacramento.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrencyTable {
    private LocalDate date;
    private String name;
    private String description;
    private List<Currency> currencies = new ArrayList<>();

    public CurrencyTable() {
        this.date = null;
        this.description = "";
        this.name = "";
    }

    public CurrencyTable(LocalDate date, String name, String description, List<Currency> currencies) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.currencies = currencies;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyTable)) return false;
        CurrencyTable that = (CurrencyTable) o;
        return getDate().equals(that.getDate()) &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription()) &&
                getCurrencies().equals(that.getCurrencies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getName(), getDescription(), getCurrencies());
    }

    @Override
    public String toString() {
        return "CurrencyTable{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
