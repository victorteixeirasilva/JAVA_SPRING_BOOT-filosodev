package br.com.learn.AppRH.repositorys;

import br.com.learn.AppRH.models.Candidato;
import br.com.learn.AppRH.models.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, String> {
    List<Candidato> findByVaga(Optional<Vaga> vaga);

    Candidato findByRg(String rg);

    List<Candidato> findByNomeCandidato(String nomeCandidato);
}
