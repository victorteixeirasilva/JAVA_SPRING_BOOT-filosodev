package br.com.learn.AppRH.repositorys;

import br.com.learn.AppRH.models.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, String> {

    Optional<List<Vaga>> findByNome(String nome);

}
