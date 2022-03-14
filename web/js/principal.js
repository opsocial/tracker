$(function () {    
    $('#btAdicionar').click(function (e) {
        e.preventDefault();
        alertaProjetoEnviado();
        adicionarProdutoNoCarrinho();
    });


    function smooth_scroll(element) {
        var offset = 0;
        console.log($(element).offset())
        offset = 500;

        $('html, body').animate({
            scrollTop: offset
        }, 550)
    }

    $('.nav-link').click(function(e) {
    e.preventDefault();

    var id = $(this).attr("href");
    smooth_scroll(id);
    //     let offset = $(id).offset();
    //     console.log(offset)
    //     let targetOfSet = offset.top;
    //     menuHeight = $('nav').innerHeight();
    //     $('html, body').animate({
    //         scrollTop: targetOfSet - menuHeight
    //     }, 500)

    // console.log(targetOfSet);
})

    $('#qtdeProdutos').on('change', function () {
        adicionarProdutoNoCarrinho();
    });
})

function adicionarProdutoNoCarrinho() {
    montaListaDeProdutos();
}

function alertaProjetoEnviado() {
    alert("Projeto enviado");
}

function montaListaDeProdutos() {
    console.log($('#qtdeProdutos').val());
    let items = [];
    let qtde = new Number($('#qtdeProdutos').val());
        let produto = {
            nome: 'LONG_DRINK',
            valor: 2,
            quantidade: qtde
        }
        items.push(produto)

    console.log(items);
    setArrayStorage(items);
}

function setArrayStorage(arr) {
    localStorage.setItem("listaProduto", JSON.stringify(arr));
}

function getArrayStorage() {
    return JSON.parse(localStorage.getItem("listaProduto"));
}