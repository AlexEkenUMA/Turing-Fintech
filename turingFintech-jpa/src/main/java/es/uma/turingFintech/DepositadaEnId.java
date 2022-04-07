package es.uma.turingFintech;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

<<<<<<< HEAD
@Embeddable
public class DepositadaEnId {
    private String cuentaReferencia;
    private String pooledAccount;

=======

public class DepositadaEnId implements Serializable {
    private String cuentaReferencia;
    private String pooledAccount;

>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    public DepositadaEnId(String cuentaReferencia, String pooledAccount) {
        this.cuentaReferencia = cuentaReferencia;
        this.pooledAccount = pooledAccount;
    }

<<<<<<< HEAD
    public String getIBANcuentaReferencia() {
        return cuentaReferencia;
    }

    public void setcuentaReferencia(String cuentaReferencia) {
        this.cuentaReferencia = cuentaReferencia;
    }

    public String getpooledAccount() {
        return pooledAccount;
    }

    public void setpooledAccount(String pooledAccount) {
        this.pooledAccount = pooledAccount;
    }

    public String getCuentaReferencia() {
        return cuentaReferencia;
    }

    public void setCuentaReferencia(String cuentaReferencia) {
        this.cuentaReferencia = cuentaReferencia;
    }

    @Override
    public String toString() {
        return "DepositadaEnId{" +
                "cuentaReferencia='" + cuentaReferencia + '\'' +
                ", pooledAccount='" + pooledAccount + '\'' +
                '}';
=======
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
>>>>>>> 48d576a34ccf23590b4dba81f5324b7e04edadbb
    }

    @Override
    public String toString() {
        return "DepositadaEnId{" +
                "cuentaReferencia='" + cuentaReferencia + '\'' +
                ", pooledAccount='" + pooledAccount + '\'' +
                '}';
    }

}
