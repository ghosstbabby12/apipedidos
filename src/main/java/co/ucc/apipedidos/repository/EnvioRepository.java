package co.ucc.apipedidos.repository;

import co.ucc.apipedidos.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    List<Envio> findByPedidoId(Long pedidoId);
}
