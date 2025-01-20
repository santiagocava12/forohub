package com.aluracursos.forohub.forohub.controlador;

import com.aluracursos.forohub.forohub.modelo.Topico;
import com.aluracursos.forohub.forohub.repositorio.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid Topico topico) {
        Optional<Topico> topicoExistente = topicoRepository.findByTituloAndMensaje(topico.getTitulo(), topico.getMensaje());
        if (topicoExistente.isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.ok(nuevoTopico);
    }

    @GetMapping
    public ResponseEntity<List<Topico>> obtenerTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return ResponseEntity.ok(topico.get());
        } else {
            return ResponseEntity.status(404).body("Tópico no encontrado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid Topico topicoActualizado) {
        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        if (!topicoExistente.isPresent()) {
            return ResponseEntity.status(404).body("Tópico no encontrado.");
        }

        Optional<Topico> topicoDuplicado = topicoRepository.findByTituloAndMensaje(topicoActualizado.getTitulo(), topicoActualizado.getMensaje());
        if (topicoDuplicado.isPresent() && !topicoDuplicado.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }

        Topico topico = topicoExistente.get();
        topico.setTitulo(topicoActualizado.getTitulo());
        topico.setMensaje(topicoActualizado.getMensaje());
        topico.setEstado(topicoActualizado.getEstado());
        topico.setAutor(topicoActualizado.getAutor());
        topico.setCurso(topicoActualizado.getCurso());

        Topico topicoGuardado = topicoRepository.save(topico);
        return ResponseEntity.ok(topicoGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        if (!topicoExistente.isPresent()) {
            return ResponseEntity.status(404).body("Tópico no encontrado.");
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.ok("Tópico eliminado correctamente.");
    }
}
