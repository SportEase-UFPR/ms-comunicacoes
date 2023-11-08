package br.ufpr.mscomunicacoes.repository;

import br.ufpr.mscomunicacoes.model.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByIdCliente(Long idCliente);

    @Query(value = """
        UPDATE tb_notificacoes
        SET lida=true
        WHERE idCliente=?1
    """, nativeQuery = true)
    void marcarNotificacoesComoLida(long idCliente);
}
