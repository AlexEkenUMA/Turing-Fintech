package es.uma.turingFintech;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
public class DepositadaEnId {
    private CuentaReferencia IBANcuentaReferencia;
    private PooledAccount IBANpooledAccount;

    public DepositadaEnId(CuentaReferencia IBANcuentaReferencia, PooledAccount IBANpooledAccount) {
        this.IBANcuentaReferencia = IBANcuentaReferencia;
        this.IBANpooledAccount = IBANpooledAccount;
    }

    public CuentaReferencia getIBANcuentaReferencia() {
        return IBANcuentaReferencia;
    }

    public void setIBANcuentaReferencia(CuentaReferencia IBANcuentaReferencia) {
        this.IBANcuentaReferencia = IBANcuentaReferencia;
    }

    public PooledAccount getIBANpooledAccount() {
        return IBANpooledAccount;
    }

    public void setIBANpooledAccount(PooledAccount IBANpooledAccount) {
        this.IBANpooledAccount = IBANpooledAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositadaEnId that = (DepositadaEnId) o;
        return IBANcuentaReferencia.equals(that.IBANcuentaReferencia) && IBANpooledAccount.equals(that.IBANpooledAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IBANcuentaReferencia, IBANpooledAccount);
    }
}
