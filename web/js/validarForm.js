$(function() {

    console.log($('#formCadastro'))
    $('#formCadastro').validate({
        rules: {
            nome: {required: true,
                   minlength: 2},
            email: {required: true,
                    email: true},
            senha: {required: true},
            confirmaSenha: {senha: true,
                        equalTo: '#password'},
            cpf: {required: true,
                  cpf: true},
            nascimento: {required: true},
            genero: {required: true},
            pais: {required: true},
        },
        messages: {
            nome: {required: 'Campo de preenchimeto obrigatório',
                   minlength: 'Informe, no mínimo, dois caracteres'},
            email: {required: 'Campo de preenchimeto obrigatório',
                    email: 'Informe um email válido'},
            senha: {required: 'Campo de preenchimeto obrigatório'},
            confirmaSenha: {required: 'Campo de preenchimeto obrigatório',
                        equalTo: 'Campos não conferem'},
            cpf: {required: 'Campo de preenchimeto obrigatório'},
            nascimento: {required: 'Campo de preenchimeto obrigatório'},
            genero: {required: 'Campo de preenchimeto obrigatório'},
            pais: {required: 'Campo de preenchimeto obrigatório'}
        },
        errorClass: 'erro',
        errorElement: 'span',

        // destacar campos invalidos
        // element é o elemento do DOM que esta invalido
        highlight:function(element) {
            $(element).addClass('is-invalid').removeClass('is-valid');
        },
        // destacar campos validos
        // element é o elemento do DOM que esta invalido
        unhighlight:function(element) {
            $(element).addClass('is-valid').removeClass('is-invalid');
        }
    }); // fim validate

    $('#cpf').mask('999.999.999-99')
}) 