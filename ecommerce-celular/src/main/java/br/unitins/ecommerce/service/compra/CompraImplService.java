package br.unitins.ecommerce.service.compra;

import java.time.LocalDate;
import java.util.List;

import br.unitins.ecommerce.dto.compra.CompraResponseDTO;
import br.unitins.ecommerce.dto.compra.ItemCompraDTO;
import br.unitins.ecommerce.model.compra.Compra;
import br.unitins.ecommerce.model.compra.ItemCompra;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.CompraRepository;
import br.unitins.ecommerce.repository.ItemCompraRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CompraImplService implements CompraService {

    @Inject
    CompraRepository compraRepository;

    @Inject
    ItemCompraRepository itemCompraRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PanacheRepository<? extends Produto> produtoRepository;

    @Override
    public List<CompraResponseDTO> getAll(Long idUsuario) {
        
        List<Compra> list = compraRepository.findByUsuarioWhereIsFinished(usuarioRepository.findById(idUsuario));

        if (list == null)
            throw new NullPointerException("compra não encontrada");

        return list.stream().map(CompraResponseDTO::new).toList();
    }

    @Override
    public CompraResponseDTO getCompraEmAndamento(Long idUsuario) {
        
        Compra compra = compraRepository.findByUsuarioWhereIsNotFinished(usuarioRepository.findById(idUsuario));

        if (compra == null)
            throw new NullPointerException("Nenhuma compra em andamento");

        return new CompraResponseDTO(compra);
    }

    @Override
    @Transactional
    public void insertItemIntoCompra(Long idUsuario, ItemCompraDTO itemCompraDTO) throws NullPointerException {
        
        Produto produto = validar(itemCompraDTO);

        Compra compra = validar(idUsuario);

        Integer indice = validar(produto, compra.getItemCompra());

        ItemCompra itemCompra;

        if (indice != null) {

            itemCompra = compra.getItemCompra().get(indice);

            itemCompra.updateQuantidade(itemCompraDTO.quantidade());

            compra.plusTotalCompra(itemCompra.getPrecoUnitario() * itemCompraDTO.quantidade());
        }

        else {

            itemCompra = new ItemCompra(produto, itemCompraDTO.quantidade());

            itemCompraRepository.persist(itemCompra);

            compra.setItemCompra(itemCompra);

            compra.plusTotalCompra(itemCompra.getPrecoUnitario() * itemCompra.getQuantidade());
        }
    }

    @Override
    @Transactional
    public void removeItemCompra(Long idUsuario, Long idProduto) {
        
        Compra compra = compraRepository.findByUsuarioWhereIsNotFinished(usuarioRepository.findById(idUsuario));

        if (compra == null)
            throw new NullPointerException("Não há nenhuma compra em andamento");

        ItemCompra itemCompra = itemCompraRepository.findByProduto(produtoRepository.findById(idProduto));

        compra.minusTotalCompra(itemCompra.getPrecoUnitario() * itemCompra.getQuantidade());

        compra.getItemCompra().remove(itemCompra);
    }

    @Override
    @Transactional
    public void finishCompra(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario);
        
        Compra compra = compraRepository.findByUsuarioWhereIsNotFinished(usuario);

        if (compra == null)
            throw new NullPointerException("Não há nenhuma compra em andamento");

        if (compra.getItemCompra().size() == 0)
            throw new NullPointerException("Não há nenhum item dentro do carrinho");

        compra.setDataCompra(LocalDate.now());

        for (ItemCompra itemCompra : compra.getItemCompra()) {

            if (itemCompra.getProduto().getEstoque() < itemCompra.getQuantidade())
                throw new NullPointerException("quantidade em estoque insuficiente para a quantidade requisitada. Não é possível finalizar a compra");

            else
                itemCompra.getProduto().minusEstoque(itemCompra.getQuantidade());
        }

        compra.setEndereco(usuario.getEndereco());

        compra.setIfConcluida(true);
    }

    private Integer validar(Produto produto, List<ItemCompra> listaItens) {

        for (int i = 0; i < listaItens.size(); i++) {
            
            if (listaItens.get(i).contains(produto))
                return i;
        }

        return null;
    }

    private Compra validar(Long idUsuario) {

        Compra compra = compraRepository.findByUsuarioWhereIsNotFinished(usuarioRepository.findById(idUsuario));

        if (compra == null) {

            Compra newCompra = new Compra(usuarioRepository.findById(idUsuario));

            compraRepository.persist(newCompra);

            return newCompra;
        }

        else
            return compra;
    }

    private Produto validar(ItemCompraDTO itemCompraDTO) throws NullPointerException {

        Produto produto = produtoRepository.findById(itemCompraDTO.idProduto());

        if (produto.getEstoque() > itemCompraDTO.quantidade())
            return produto;

        else
            throw new NullPointerException("quantidade em estoque insuficiente para a quantidade requisitada");
    }
}
