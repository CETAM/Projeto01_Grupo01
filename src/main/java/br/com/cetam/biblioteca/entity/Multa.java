package br.com.cetam.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multa")
public class Multa {

    public enum StatusMulta {
        PENDENTE, PAGA, CANCELADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emprestimo_id", nullable = false, unique = true)
    private Emprestimo emprestimo;

    @Column(name = "dias_atraso", nullable = false)
    private Integer diasAtraso;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusMulta status = StatusMulta.PENDENTE;

    @Column(name = "data_geracao", nullable = false)
    private LocalDate dataGeracao = LocalDate.now();

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Emprestimo getEmprestimo() { return emprestimo; }
    public void setEmprestimo(Emprestimo emprestimo) { this.emprestimo = emprestimo; }
    public Integer getDiasAtraso() { return diasAtraso; }
    public void setDiasAtraso(Integer diasAtraso) { this.diasAtraso = diasAtraso; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public StatusMulta getStatus() { return status; }
    public void setStatus(StatusMulta status) { this.status = status; }
    public LocalDate getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDate dataGeracao) { this.dataGeracao = dataGeracao; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
}
