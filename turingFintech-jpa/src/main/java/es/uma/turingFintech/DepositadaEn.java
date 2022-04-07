package es.uma.turingFintech;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(DepositadaEnId.class)
public class DepositadaEn {
    @Column (nullable = false)
    private double saldo;

    //relacion muchos-uno depositadaen-cuentareferencia
    @Id @ManyToOne
    private CuentaReferencia cuentaReferencia;
    //relacion muchos-uno depositadaen-pooledaccount
    @Id @ManyToOne
    private PooledAccount pooledAccount;

    public DepositadaEn(double saldo) {
        this.saldo = saldo;
    }

    public DepositadaEn() {
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositadaEn that = (DepositadaEn) o;
        return Double.compare(that.saldo, saldo) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saldo);
    }

}
