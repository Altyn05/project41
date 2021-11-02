let items = document.querySelector('#items');

items.addEventListener('click', evt => {
    if (evt.target.hasAttribute('data-item-id')) {
        evt.preventDefault();
        const itemId = evt.target.getAttribute('data-item-id');
        addToCart(itemId);
        $('#cart-modal').modal('show');
    }
})