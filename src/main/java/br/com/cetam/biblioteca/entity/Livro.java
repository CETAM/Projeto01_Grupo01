package br.com.cetam.biblioteca.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false, length = 160)
    private String titulo;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 80)
    private String categoria;

    @Min(value = 0, message = "O ano deve ser positivo")
    @Column(name = "ano_publicacao")
    private Integer anoPublicacao;

    @NotNull(message = "A quantidade total é obrigatória")
    @Min(value = 1, message = "A quantidade total deve ser pelo menos 1")
    @Column(name = "quantidade_total", nullable = false)
    private Integer quantidadeTotal = 1;

    @Column(name = "quantidade_disponivel", nullable = false)
    private Integer quantidadeDisponivel = 1;

    @NotNull(message = "Selecione um autor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @NotNull(message = "Selecione uma editora")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Integer anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public Integer getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(Integer quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }
    public Integer getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }
}
