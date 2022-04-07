package es.uma.turingFintech;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DepositadaEnId implements Serializable {
    private String cuentaReferencia;
    private String pooledAccount;

    public DepositadaEnId(String cuentaReferencia, String pooledAccount) {
        this.cuentaReferencia = cuentaReferencia;
        this.pooledAccount = pooledAccount;
    }

    public DepositadaEnId(){

    }

    //Getters and Setters


    public String getCuentaReferencia() {
        return cuentaReferencia;
    }

    public void setCuentaReferencia(String cuentaReferencia) {
        this.cuentaReferencia = cuentaReferencia;
    }

    public String getPooledAccount() {
        return pooledAccount;
    }

    public void setPooledAccount(String pooledAccount) {
        this.pooledAccount = pooledAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositadaEnId that = (DepositadaEnId) o;
        return Objects.equals(cuentaReferencia, that.cuentaReferencia) && Objects.equals(pooledAccount, that.pooledAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuentaReferencia, pooledAccount);
    }

    @Override
    public String toString() {
        return "DepositadaEnId{" +
                "cuentaReferencia='" + cuentaReferencia + '\'' +
                ", pooledAccount='" + pooledAccount + '\'' +
                '}';
    }
}
