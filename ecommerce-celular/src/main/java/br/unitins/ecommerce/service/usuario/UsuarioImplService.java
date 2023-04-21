package br.unitins.ecommerce.service.usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.ecommerce.dto.endereco.EnderecoDTO;
import br.unitins.ecommerce.dto.telefone.TelefoneDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioDTO;
import br.unitins.ecommerce.dto.usuario.UsuarioResponseDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoDTO;
import br.unitins.ecommerce.dto.usuario.listadesejo.ListaDesejoResponseDTO;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.usuario.Telefone;
import br.unitins.ecommerce.model.usuario.Usuario;
import br.unitins.ecommerce.repository.CelularRepository;
import br.unitins.ecommerce.repository.EnderecoRepository;
import br.unitins.ecommerce.repository.MunicipioRepository;
import br.unitins.ecommerce.repository.TelefoneRepository;
import br.unitins.ecommerce.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioImplService implements UsuarioService {

    @Inject
    Validator validator;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    CelularRepository celularRepository;

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
    public ListaDesejoResponseDTO getListaDesejo(Long id) throws NullPointerException {
        
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        return new ListaDesejoResponseDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDto) throws ConstraintViolationException {
        
        validar(usuarioDto);

        Usuario entity = new Usuario();

        entity.setNome(usuarioDto.nome());

        entity.setEmail(usuarioDto.email());

        entity.setSenha(usuarioDto.senha());

        entity.setCpf(usuarioDto.cpf());

        entity.setEndereco(insertEndereco(usuarioDto.endereco()));

        entity.setTelefonePrincipal(insertTelefone(usuarioDto.telefonePrincipal()));

        if (usuarioDto.telefoneOpcional() != null)
            entity.setTelefoneOpcional(insertTelefone(usuarioDto.telefoneOpcional()));

        usuarioRepository.persist(entity);

        return new UsuarioResponseDTO(entity);
    }

    @Override
    public void insertListaDesejo(ListaDesejoDTO listaDto) throws NullPointerException {
        
        validar(listaDto);

        Usuario usuario = usuarioRepository.findById(listaDto.idUsuario());

        if (usuario == null)
            throw new NullPointerException("usuario não encontrado");

        usuario.setProdutos(celularRepository.findById(listaDto.idProduto()));
    }

    @Override
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDto) throws ConstraintViolationException, NotFoundException {
        
        validar(usuarioDto);

        Usuario entity = usuarioRepository.findById(id);

        if (entity == null)
            throw new NotFoundException("Número fora das opções disponíveis");

        entity.setNome(usuarioDto.nome());

        entity.setEmail(usuarioDto.email());

        entity.setSenha(usuarioDto.senha());

        entity.setCpf(usuarioDto.cpf());

        Long idEndereco = entity.getEndereco().getId();

        entity.setEndereco(insertEndereco(usuarioDto.endereco()));

        deleteEndereco(idEndereco);

        Long idTelefone = entity.getTelefonePrincipal().getId();

        entity.setTelefonePrincipal(insertTelefone(usuarioDto.telefonePrincipal()));

        deleteTelefone(idTelefone);

        if (usuarioDto.telefoneOpcional() != null) {
         
            idTelefone = entity.getTelefoneOpcional().getId();

            entity.setTelefoneOpcional(insertTelefone(usuarioDto.telefoneOpcional()));

            deleteTelefone(idTelefone);
        }
        
        else if (entity.getTelefoneOpcional() != null) {
         
            idTelefone = entity.getTelefoneOpcional().getId();

            entity.setTelefoneOpcional(null);

            deleteTelefone(idTelefone);
        }

        return new UsuarioResponseDTO(entity);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Usuario usuario = usuarioRepository.findById(id);

        if (usuarioRepository.isPersistent(usuario))
            usuarioRepository.delete(usuario);

        else
            throw new NotFoundException("Nenhum usuario encontrado");
    }

    @Override
    public void deleteProdutoFromListaDesejo(Long id, Long idProduto) {
        
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null)
            return;

        usuario.getProdutos().remove(celularRepository.findById(idProduto));
    }

    @Override
    public Long count() {
        
        return usuarioRepository.count();
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

    @Override
    public List<UsuarioResponseDTO> getByNome(String nome) throws NullPointerException {
        
        List<Usuario> list = usuarioRepository.findByNome(nome);

        if (list == null)
            throw new NullPointerException("nenhum usuario encontrado");

        return list.stream()
                    .map(UsuarioResponseDTO::new)
                    .collect(Collectors.toList());
    }

    private Telefone insertTelefone (TelefoneDTO telefoneDTO) throws ConstraintViolationException {

        validar(telefoneDTO);

        Telefone telefone = new Telefone();

        telefone.setCodigoArea(telefoneDTO.codigoArea());
        telefone.setNumero(telefoneDTO.numero());

        telefoneRepository.persist(telefone);

        return telefone;
    }

    private void deleteTelefone (Long id) throws NotFoundException, IllegalArgumentException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Telefone telefone = telefoneRepository.findById(id);

        if (telefoneRepository.isPersistent(telefone))
            telefoneRepository.delete(telefone);

        else
            throw new NotFoundException("Nenhum Telefone encontrado");
    }

    private Endereco insertEndereco(EnderecoDTO enderecoDto) throws ConstraintViolationException {
        
        validar(enderecoDto);

        Endereco endereco = new Endereco();

        endereco.setLogradouro(enderecoDto.logradouro());

        endereco.setBairro(enderecoDto.bairro());

        endereco.setNumero(enderecoDto.numero());

        endereco.setComplemento(enderecoDto.complemento());

        endereco.setCep(enderecoDto.cep());

        endereco.setMunicipio(municipioRepository.findById(enderecoDto.idMunicipio()));

        enderecoRepository.persist(endereco);

        return endereco;
    }

    private void deleteEndereco (Long id) throws NotFoundException, IllegalArgumentException {

        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Endereco endereco = enderecoRepository.findById(id);

        if (enderecoRepository.isPersistent(endereco))
            enderecoRepository.delete(endereco);

        else
            throw new NotFoundException("Nenhum endereço encontrado");
    }
    
    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    private void validar(TelefoneDTO telefoneDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    private void validar(ListaDesejoDTO listaDto) throws ConstraintViolationException {

        Set<ConstraintViolation<ListaDesejoDTO>> violations = validator.validate(listaDto);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
