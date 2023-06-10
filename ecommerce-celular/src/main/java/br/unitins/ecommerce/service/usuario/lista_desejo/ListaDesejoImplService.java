package br.unitins.ecommerce.service.usuario.lista_desejo;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.UsuarioRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ListaDesejoImplService implements ListaDesejoService {
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PanacheRepository<? extends Produto> produtoRepository;

    @Override
    public ListaDesejoResponseDTO getListaDesejo(Long id) {
        
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        return new ListaDesejoResponseDTO(usuario);
    }

    @Override
    @Transactional
    public void insertProdutoIntoListaDesejo(Long idUsuario, Long idProduto) throws NullPointerException {

        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        usuario.setProdutos(produtoRepository.findById(idProduto));
    }

    @Override
    @Transactional
    public void deleteProdutoFromListaDesejo(Long idUsuario, Long idProduto) throws NullPointerException {
        
        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        usuario.getProdutos().remove(produtoRepository.findById(idProduto));
    }

    @Override
    @Transactional
    public void deleteProdutoFromAllListaDesejo(Produto produto) {
        
        List<Usuario> usuarios = usuarioRepository.findAll().list();

        for (Usuario usuario : usuarios) {
            
            if (usuario.getProdutos().contains(produto)) {

                deleteProdutoFromListaDesejo(usuario.getId(), produto.getId());
            }
        }
    }

    @Override
    public Integer countListaDesejo(Long id) throws NullPointerException {
        
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        if (usuario.getProdutos() == null)
            return null;

        return usuario.getProdutos().size();
    }
}
