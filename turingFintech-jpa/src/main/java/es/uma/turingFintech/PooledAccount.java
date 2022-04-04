package es.uma.turingFintech;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PooledAccount extends CuentasFintech implements Serializable {


    public PooledAccount(String IBAN, String SWIFT, Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        super(IBAN, SWIFT, fecha_Apertura, estado, tipo, saldo);
    }

    public PooledAccount(Date fecha_Apertura, boolean estado, String tipo, Double saldo) {
        super(fecha_Apertura, estado, tipo, saldo);
    }

    public PooledAccount() {
    }


}
