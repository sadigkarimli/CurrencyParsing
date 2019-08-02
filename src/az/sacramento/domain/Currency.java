package az.sacramento.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Currency {
    private String currencyType;
    private String currencyCode;
    private String currencyNominal;
    private String currencyName;
    private BigDecimal currencyValue;

    public Currency() {

    }

    public Currency(String currencyType, String currencyCode, String currencyNominal, String currencyName, BigDecimal currencyValue) {
        this.currencyType = currencyType;
        this.currencyCode = currencyCode;
        this.currencyNominal = currencyNominal;
        this.currencyName = currencyName;
        this.currencyValue = currencyValue;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyNominal() {
        return currencyNominal;
    }

    public void setCurrencyNominal(String currencyNominal) {
        this.currencyNominal = currencyNominal;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return getCurrencyType().equals(currency.getCurrencyType()) &&
                getCurrencyCode().equals(currency.getCurrencyCode()) &&
                getCurrencyNominal().equals(currency.getCurrencyNominal()) &&
                getCurrencyName().equals(currency.getCurrencyName()) &&
                getCurrencyValue().equals(currency.getCurrencyValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencyType(), getCurrencyCode(), getCurrencyNominal(), getCurrencyName(), getCurrencyValue());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyType='" + currencyType + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyNominal='" + currencyNominal + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", currencyValue=" + currencyValue +
                '}';
    }


}
