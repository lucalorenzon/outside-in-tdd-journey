package it.gabrieletondi.journey.mockreturnsmock;

import java.util.Objects;

public class Money {
    private final String currency;
    private final int inCents;

    private Money(String currency, int inCents) {
        this.currency = currency;
        this.inCents = inCents;
    }

    public static Money euro(int inCents) {
        return new Money("EUR", inCents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return inCents == money.inCents &&
                currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, inCents);
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency='" + currency + '\'' +
                ", inCents=" + inCents +
                '}';
    }
}
