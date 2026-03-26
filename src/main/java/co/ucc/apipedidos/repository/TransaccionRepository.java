package co.ucc.apipedidos.repository;

import co.ucc.apipedidos.model.Transaccion;
import co.ucc.apipedidos.model.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByPedidoId(Long pedidoId);

    List<Transaccion> findByTipo(TipoTransaccion tipo);
}
