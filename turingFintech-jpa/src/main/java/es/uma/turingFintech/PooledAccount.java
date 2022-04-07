package es.uma.turingFintech;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class PooledAccount extends CuentaFintech implements Serializable {
    //relacion uno-muchos pooledaccount-depositadaen
    @OneToMany (mappedBy = "pooledAccount")
    private List<DepositadaEn> listaDepositos;

    public PooledAccount(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        super(IBAN, SWIFT, fecha_Apertura, estado, tipo, saldo);
    }

    public PooledAccount() {
    }

    //Getters and Setters

    public List<DepositadaEn> getListaDepositos() {
        return listaDepositos;
    }

    public void setListaDepositos(List<DepositadaEn> listaDepositos) {
        this.listaDepositos = listaDepositos;
    }




    //toString

    @Override
    public String toString() {
        return "PooledAccount{" +
                "} " + super.toString();
    }
}
