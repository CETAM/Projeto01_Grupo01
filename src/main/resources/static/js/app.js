function configurarModalExclusao(baseUrl) {
    const modal = document.getElementById('modalExcluir');
    if (!modal) return;

    modal.addEventListener('show.bs.modal', function (event) {
        const botao = event.relatedTarget;
        const id = botao?.getAttribute('data-id');
        const nome = botao?.getAttribute('data-nome') || 'este registro';

        const nomeEl = document.getElementById('nomeExcluir');
        const form = document.getElementById('formExcluir');

        if (nomeEl) nomeEl.textContent = nome;
        if (form && id) form.action = baseUrl + id;
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.dashboard-card, .quick-card');
    cards.forEach((card, index) => {
        card.animate(
            [
                { opacity: 0, transform: 'translateY(12px)' },
                { opacity: 1, transform: 'translateY(0)' }
            ],
            {
                duration: 320,
                delay: Math.min(index * 45, 280),
                easing: 'ease-out',
                fill: 'both'
            }
        );
    });
});
