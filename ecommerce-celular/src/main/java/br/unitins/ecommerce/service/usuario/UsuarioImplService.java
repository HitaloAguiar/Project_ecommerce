package br.unitins.ecommerce.service.usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.PessoaFisicaDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.PessoaFisica;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.UsuarioRepository;
import br.unitins.ecommerce.service.avaliacao.AvaliacaoService;
import br.unitins.ecommerce.service.endereco.EnderecoService;
import br.unitins.ecommerce.service.pessoafisica.PessoaFisicaService;
import br.unitins.ecommerce.service.telefone.TelefoneService;
import br.unitins.ecommerce.service.usuario.lista_desejo.ListaDesejoService;

@ApplicationScoped
public class UsuarioImplService implements UsuarioService {

    @Inject
    Validator validator;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @Inject
    TelefoneService telefoneService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    ListaDesejoService listaDesejoService;

    @Inject
    AvaliacaoService avaliacaoService;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        
        return usuarioRepository.findAll()
                                    .stream()
                                    .map(UsuarioResponseDTO::new)
                                    .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO getById(Long id) throws NotFoundException {
        
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NotFoundException("Não encontrado");

        return new UsuarioResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDto) throws ConstraintViolationException {
        
        validar(usuarioDto);

        Usuario entity = new Usuario();

        entity.setPessoaFisica(insertPessoaFisica(usuarioDto.pessoaFisicaDto()));

        entity.setSenha(usuarioDto.senha());

        entity.setEndereco(insertEndereco(usuarioDto.endereco()));

        entity.setTelefonePrincipal(insertTelefone(usuarioDto.telefonePrincipal()));

        if (usuarioDto.telefoneOpcional() != null)
            entity.setTelefoneOpcional(insertTelefone(usuarioDto.telefoneOpcional()));

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDto) throws ConstraintViolationException, NotFoundException {
        
        validar(usuarioDto);

        Usuario entity = usuarioRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setPessoaFisica(insertPessoaFisica(usuarioDto.pessoaFisicaDto()));

        entity.setSenha(usuarioDto.senha());

        updateEndereco(entity, usuarioDto.endereco());

        updateTelefonePrincipal(entity, usuarioDto.telefonePrincipal());

        updateTelefoneOpcional(entity, usuarioDto.telefoneOpcional());

        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Usuario usuario = usuarioRepository.findById(id);

        avaliacaoService.delete(usuario);

        if (usuarioRepository.isPersistent(usuario))
            usuarioRepository.delete(usuario);

        else
            throw new NotFoundException("Nenhum usuario encontrado");
    }

    @Override
    public Long count() {
        
        return usuarioRepository.count();
    }

    @Override
    public ListaDesejoResponseDTO getListaDesejo(Long id) throws NullPointerException {
        
        return listaDesejoService.getListaDesejo(id);
    }

    @Override
    public void insertProdutoIntoListaDesejo(ListaDesejoDTO listaDto) throws NullPointerException {
        
        listaDesejoService.insertProdutoIntoListaDesejo(listaDto);
    }

    @Override
    public void deleteProdutoFromListaDesejo(Long id, Long idProduto) {
        
        listaDesejoService.deleteProdutoFromListaDesejo(id, idProduto);
    }

    @Override
    public void deleteProdutoFromAllListaDesejo(Produto produto) {

        listaDesejoService.deleteProdutoFromAllListaDesejo(produto);
    }

    @Override
    public Integer countListaDesejo(Long id) throws NullPointerException {
        
        return listaDesejoService.countListaDesejo(id);
    }

    @Override
    public List<UsuarioResponseDTO> getByNome(String nome) throws NullPointerException {
        
        List<Usuario> list = usuarioRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum usuario encontrado");

        return list.stream()
                    .map(UsuarioResponseDTO::new)
                    .collect(Collectors.toList());
    }

    private PessoaFisica insertPessoaFisica (PessoaFisicaDTO pessoaFisicaDTO) throws ConstraintViolationException {

        return pessoaFisicaService.insertPessoaFisica(pessoaFisicaDTO);
    }

    private Telefone insertTelefone (TelefoneDTO telefoneDTO) throws ConstraintViolationException {

        return telefoneService.insertTelefone(telefoneDTO);
    }

    private void updateTelefonePrincipal(Usuario usuario, TelefoneDTO telefoneDto) {

        telefoneService.updateTelefonePrincipal(usuario, telefoneDto);
    }

    private void updateTelefoneOpcional(Usuario usuario, TelefoneDTO telefoneDto) {

        telefoneService.updateTelefoneOpcional(usuario, telefoneDto);
    }

    private Endereco insertEndereco(EnderecoDTO enderecoDto) throws ConstraintViolationException {
        
        return enderecoService.insertEndereco(enderecoDto);
    }

    private void updateEndereco(Usuario usuario, EnderecoDTO enderecoDTO) {

        enderecoService.updateEndereco(usuario, enderecoDTO);
    }
    
    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
