package es.uma.turingFintech;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
public class DepositadaEnId {
    private String cuentaReferencia;
    private String pooledAccount;

    public DepositadaEnId(String cuentaReferencia, String pooledAccount) {
        this.cuentaReferencia = cuentaReferencia;
        this.pooledAccount = pooledAccount;
    }

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
    }
}
