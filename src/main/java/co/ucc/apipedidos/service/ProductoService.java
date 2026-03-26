package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Producto;
import co.ucc.apipedidos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
