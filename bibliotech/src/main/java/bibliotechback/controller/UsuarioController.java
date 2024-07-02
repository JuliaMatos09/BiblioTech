package bibliotechback.controller;

import bibliotechback.entity.Usuario;
import bibliotechback.repository.UsuarioRepository;
import bibliotechback.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Usuario usuario) {

        // Verificação se o e-mail já está cadastrado
        Optional<Usuario> existeUsuario = usuarioService.findByEmail(usuario.getEmail());
        if (existeUsuario.isPresent()) {
            return ResponseEntity.badRequest().body("O email já está sendo utilizado!");
        }

        // Salva o usuário no banco de dados
        Usuario novoUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}






